package com.homelab.suit.inventory.service;

import com.homelab.suit.inventory.dto.*;
import com.homelab.suit.inventory.exceptions.ItemNotFoundException;
import com.homelab.suit.inventory.model.Category;
import com.homelab.suit.inventory.model.Item;
import com.homelab.suit.inventory.model.Location;
import com.homelab.suit.inventory.model.UnitOfMeasure;
import com.homelab.suit.inventory.repository.CategoryRepository;
import com.homelab.suit.inventory.repository.ItemPagingRepository;
import com.homelab.suit.inventory.repository.ItemRepository;
import com.homelab.suit.inventory.repository.LocationRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemPagingRepository itemPagingRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final ItemUtils itemUtils;

    public Page<TableItemRequestDto> getTablePage(Long workspaceId, Pageable pageable){
        return itemPagingRepository.findByWorkspaceId(workspaceId, pageable).map(this::convertToDto);
    }
    public Page<TableItemRequestDto> getTablePageBySearchQuery(Long workspaceId, String query, Pageable pageable){
          return itemPagingRepository.findFuzzy(workspaceId, query, pageable).map(this::convertToDto);
    }

    public List<CategoryDto> getCategories(Long workspaceId){
        return categoryRepository.findByWorkspaceId(workspaceId)
                .stream().map(this::convertCategoryToDto).toList();
    }

    public List<LocationDto> getLocations(Long workspaceId){
        return locationRepository.findByWorkspaceId(workspaceId)
                .stream().map(this::convertLocationToDto).toList();
    }

    public Item getItemById(Long id){
        Optional<Item> byId = itemRepository.findById(id);
         return byId.orElseThrow(() -> new ItemNotFoundException("Item with id " + id + " not found"));
    }

    public void saveNewItem(ItemCreationDto itemDto,
                                            MultipartFile previewImg,
                                            List<MultipartFile> documents,
                                            Principal principal){
        itemUtils.saveToDatabase(itemDto, previewImg, documents, principal.getName());
    }

    public void updateItem(ItemUpdateDto itemDto,
                           MultipartFile previewImg,
                           List<MultipartFile> documents,Principal principal){
        itemUtils.updateItem(itemDto, previewImg, documents, principal);
    }

    private TableItemRequestDto convertToDto(Item item){
        return new TableItemRequestDto(
                item.getId(),
                item.getImageUrl(),
                item.getName(),
                item.getLocation() != null ? item.getLocation().getName() : "Немає локації",
                item.getTotalQuantity() != null ? item.getTotalQuantity().toString() : "0",
                item.getUnitOfMeasure() != null ? item.getUnitOfMeasure().toString() : UnitOfMeasure.UDEF.toString()
        );
    }

    private CategoryDto convertCategoryToDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    private LocationDto convertLocationToDto(Location location){
        return new LocationDto(
                location.getId(),
                location.getName()
        );
    }

}
