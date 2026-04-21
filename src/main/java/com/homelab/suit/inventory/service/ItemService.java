package com.homelab.suit.inventory.service;

import com.homelab.suit.inventory.dto.CategoryDto;
import com.homelab.suit.inventory.dto.LocationDto;
import com.homelab.suit.inventory.dto.TableItemRequestDto;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemPagingRepository itemPagingRepository;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public Page<TableItemRequestDto> getTablePage(Long workspaceId, Pageable pageable){
        Page<Item> page = itemPagingRepository.findByWorkspaceId(workspaceId, pageable);

        return page.map(this::convertToDto);
    }
    public Page<TableItemRequestDto> getTablePageBySearchQuery(Long workspaceId, String query, Pageable pageable){
          return itemPagingRepository.findFuzzy(workspaceId, query, pageable).map(this::convertToDto);
    }

    public List<CategoryDto> getCategories(){
        return categoryRepository.findAll().stream().map(this::convertCategoryToDto).toList();
    }

    public List<LocationDto> getLocations(){
        // TODO add workspace field, and find by location id
        return locationRepository.findAll().stream().map(this::convertLocationToDto).toList();
    }

    public Item getItemById(Long id){
        Optional<Item> byId = itemRepository.findById(id);
         return byId.orElseThrow(() -> new ItemNotFoundException("Item with id " + id + " not found"));
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
