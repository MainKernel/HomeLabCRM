package com.homelab.suit.inventory.controller;

import com.homelab.suit.inventory.dto.CategoryDto;
import com.homelab.suit.inventory.dto.ItemCreationDto;
import com.homelab.suit.inventory.dto.LocationDto;
import com.homelab.suit.inventory.dto.TableItemRequestDto;
import com.homelab.suit.inventory.model.*;
import com.homelab.suit.inventory.service.ItemService;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final ItemService itemService;


    @GetMapping
    public Page<TableItemRequestDto> getTablePage(
            @RequestHeader(value = "X-Workspace-Id", defaultValue = "1") Long workspaceId,
            @RequestParam(required = false) String search,
            @ParameterObject @PageableDefault(size = 10, sort = "id") Pageable pageable
    ) {
        if (StringUtils.hasText(search)) {
            return itemService.getTablePageBySearchQuery(workspaceId, search, pageable);
        } else {
            return itemService.getTablePage(workspaceId, pageable);
        }
    }
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(){
        return itemService.getCategories();
    }
    @GetMapping("/locations")
    public List<LocationDto> getLocations(){
        return itemService.getLocations();
    }
    @GetMapping("/item/{id}")
    public Item getItemById(@PathVariable Long id){
        return itemService.getItemById(id);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createItemWithFiles(
            @RequestPart("itemDetails")ItemCreationDto itemCreationDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "documents", required = false) List<MultipartFile> documents
            ){
        System.out.println(itemCreationDto.toString());
        System.out.println(imageFile.getName());
        System.out.println(documents.toString());
        return ResponseEntity.ok().build();
    }
}