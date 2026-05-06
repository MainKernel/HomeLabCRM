package com.homelab.suit.inventory.controller;

import com.homelab.suit.inventory.dto.*;
import com.homelab.suit.inventory.model.*;
import com.homelab.suit.inventory.service.ItemService;
import jakarta.validation.Valid;
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

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final ItemService itemService;


    @GetMapping
    public Page<TableItemRequestDto> getTablePage(
            @RequestHeader(value = "X-Workspace-Id") Long workspaceId,
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
    public List<CategoryDto> getCategories(
            @RequestHeader(value = "X-Workspace-Id", defaultValue = "1") Long workspaceId){
        return itemService.getCategories(workspaceId);
    }
    @GetMapping("/locations")
    public List<LocationDto> getLocations(
            @RequestHeader(value = "X-Workspace-Id", defaultValue = "1") Long workspaceId
    ){
        return itemService.getLocations(workspaceId);
    }
    @GetMapping("/item/{id}")
    public Item getItemById(@PathVariable Long id){

        return itemService.getItemById(id);
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<?> updateItem(
            @PathVariable Long id,
            @Valid @RequestPart("itemDetails") ItemUpdateDto itemUpdateDto,
            @RequestPart (value = "imageFile", required = false) MultipartFile newImage,
            @RequestPart(value = "newDocuments", required = false) List<MultipartFile> newDocuments,
            Principal principal
    ){
        itemService.updateItem(itemUpdateDto, newImage, newDocuments, principal);
        return ResponseEntity.ok().build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createItemWithFiles(
            @Valid @RequestPart("itemDetails")ItemCreationDto itemCreationDto,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestPart(value = "documents", required = false) List<MultipartFile> documents,
            Principal principal
            ){
        itemService.saveNewItem(itemCreationDto, imageFile, documents, principal);
        return ResponseEntity.ok().build();
    }
}