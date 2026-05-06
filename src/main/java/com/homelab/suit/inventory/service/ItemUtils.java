package com.homelab.suit.inventory.service;

import com.homelab.suit.inventory.dto.ItemCreationDto;
import com.homelab.suit.inventory.dto.ItemUpdateDto;
import com.homelab.suit.inventory.exceptions.*;
import com.homelab.suit.inventory.model.*;
import com.homelab.suit.inventory.repository.CategoryRepository;
import com.homelab.suit.inventory.repository.ItemRepository;
import com.homelab.suit.inventory.repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemUtils {
    private final FileStorageService fileStorageService;
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;


    @Transactional
    public void saveToDatabase(ItemCreationDto itemCreationDto,
                                                       MultipartFile multipartFile,
                                                       List<MultipartFile> documents,
                                               String userName){
        // Map item before saving
        Item newItem = mapToItem(itemCreationDto);
        //Creating log
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setItem(newItem);
        inventoryLog.setChangeType(LogType.AUDIT);
        inventoryLog.setQuantityChanged(BigDecimal.ZERO);
        inventoryLog.setUnitPrice(BigDecimal.ZERO);
        inventoryLog.setTotalPrice(BigDecimal.ZERO);
        inventoryLog.setCreatedAt(OffsetDateTime.now());
        inventoryLog.setChangedBy(userName);
        inventoryLog.setNote("New item created. ");



        // Saving preview item and adding to item
        if(multipartFile != null && !multipartFile.isEmpty()){
            try {
                String imgUrl = fileStorageService.storePreviewImage(multipartFile);
                newItem.setImageUrl(imgUrl);
                // Added info about preview img addition
                inventoryLog.setNote(inventoryLog.getNote() + "\n File preview added.");
            } catch (Exception ex){
                throw new ItemPreviewNotSavedException("Помилка при збереженні прев'ю зображення!");
            }
        }
        // Saving documents and adding to item
        if(documents != null && !documents.isEmpty()){
            try {
                List<ItemDocument> itemDocuments = fileStorageService.saveDocuments(documents, newItem);
                newItem.setDocuments(itemDocuments);

                inventoryLog.setNote(inventoryLog.getNote() + " Documents added.");
            } catch (Exception ex){
                throw new DocumentsNotSavedException("Документи не збережено!");
            }
        }

        //Saving item to database
        newItem.addHistoryLog(inventoryLog);
        itemRepository.save(newItem);
    }

    private Item mapToItem(ItemCreationDto creationDto){
        Category category = categoryRepository
                .findByNameAndWorkspaceId(creationDto.category(), creationDto.workspaceId())
                .orElseGet( () -> categoryRepository.save(Category.builder()
                        .name(creationDto.category())
                        .workspaceId(creationDto.workspaceId())
                        .build())
        );
        Location location = locationRepository.findByName(creationDto.location()).orElseGet( () ->
                locationRepository.save(
                        Location.builder()
                                .name(creationDto.location())
                                .workspaceId(creationDto.workspaceId())
                                .build()
                )
        );
        return Item.builder()
                .sku(creationDto.sku())
                .name(creationDto.name())
                .workspaceId(creationDto.workspaceId())
                .unitOfMeasure(creationDto.unitsOfMeasure())
                .category(category)
                .packageType(creationDto.packageType())
                .minStock(creationDto.minStock())
                .location(location)
                .parameters(creationDto.parameters())
                .serialNumber(creationDto.serialNumber())
                .note(creationDto.note())
                .type(creationDto.type())
                .totalQuantity(creationDto.stock())
                .build();

    }

    @Transactional // Обов'язково для оновлення!
    public void updateItem(ItemUpdateDto updateDto, MultipartFile newImage, List<MultipartFile> newDocuments, Principal principal) {
        // 1. Дістаємо "живий" об'єкт з бази
        Item itemFromDB = itemRepository.findById(updateDto.getId()).orElseThrow(
                () -> new ItemNotFoundException("Компонент з ID " + updateDto.getId() + " не знайдено")
        );

        // 2. Створюємо лог
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setItem(itemFromDB); // Прив'язуємо до поточної деталі
        inventoryLog.setChangedBy(principal.getName());
        inventoryLog.setChangeType(LogType.UPDATE);
        inventoryLog.setNote("Item updated. ");
        inventoryLog.setCreatedAt(OffsetDateTime.now());

        // Безпечне віднімання (якщо null, вважаємо 0)
        BigDecimal oldQty = itemFromDB.getTotalQuantity() != null ? itemFromDB.getTotalQuantity() : BigDecimal.ZERO;
        BigDecimal newQty = updateDto.getTotalQuantity() != null ? updateDto.getTotalQuantity() : BigDecimal.ZERO;
        inventoryLog.setQuantityChanged(newQty.subtract(oldQty));
        inventoryLog.setUnitPrice(BigDecimal.ZERO);
        inventoryLog.setTotalPrice(BigDecimal.ZERO);

        // 3. Оновлюємо Категорію
        if (updateDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateDto.getCategoryId())
                    .orElseThrow(() -> new CategoryNotFoundException("Категорія не знайдена"));
            itemFromDB.setCategory(category);
        } else if (updateDto.getCategoryName() != null && !updateDto.getCategoryName().isBlank()) {
            Category newCategory = categoryRepository.save(Category.builder() // ЗБЕРІГАЄМО в БД!
                    .workspaceId(updateDto.getWorkspaceId())
                    .name(updateDto.getCategoryName())
                    .build());
            itemFromDB.setCategory(newCategory);
        }

        if (updateDto.getLocationId() != null) {
            Location location = locationRepository.findById(updateDto.getLocationId())
                    .orElseThrow(() -> new LocationNotFoundException("Місцезнаходження не знайдено"));
            itemFromDB.setLocation(location);
        } else if (updateDto.getLocationName() != null && !updateDto.getLocationName().isBlank()) {
            Location newLocation = locationRepository.save(Location.builder() // ЗБЕРІГАЄМО в БД!
                    .workspaceId(updateDto.getWorkspaceId())
                    .name(updateDto.getLocationName())
                    .build());
            itemFromDB.setLocation(newLocation);
        }

        // 5. Оновлюємо базові текстові поля
        itemFromDB.setSku(updateDto.getSku());
        itemFromDB.setName(updateDto.getName());
        itemFromDB.setUnitOfMeasure(updateDto.getUnitOfMeasure());
        itemFromDB.setPackageType(updateDto.getPackageType());
        itemFromDB.setMinStock(updateDto.getMinStock());
        itemFromDB.setParameters(updateDto.getParameters());
        itemFromDB.setSerialNumber(updateDto.getSerialNumber());
        itemFromDB.setNote(updateDto.getNote());
        itemFromDB.setType(updateDto.getItemType());
        itemFromDB.setTotalQuantity(updateDto.getTotalQuantity());

// 6. ВИДАЛЕННЯ СТАРИХ ДОКУМЕНТІВ
        if (updateDto.getDocuments() != null && itemFromDB.getDocuments() != null) {

            // 1. Отримуємо список ID тих документів, які фронтенд просить ЗАЛИШИТИ
            List<Long> keptDocumentIds = updateDto.getDocuments().stream()
                    .map(ItemDocument::getId)
                    .toList();

            // 2. Знаходимо документи в БД, чиїх ID немає в списку на збереження
            List<ItemDocument> documentsToDelete = itemFromDB.getDocuments().stream()
                    .filter(doc -> !keptDocumentIds.contains(doc.getId()))
                    .toList();

            // 3. Видаляємо їх
            for (ItemDocument doc : documentsToDelete) {
                fileStorageService.deleteFileFromDisk(doc.getFileUrl());
                itemFromDB.getDocuments().remove(doc); // Hibernate видалить з БД
            }
        }


        // 7. ЗБЕРЕЖЕННЯ НОВОГО ФОТО (виправлено !newImage.isEmpty())
        if (newImage != null && !newImage.isEmpty()) {
            try {
                // Видаляємо старе фото з диска, якщо воно було
                if (itemFromDB.getImageUrl() != null) {
                    fileStorageService.deleteFileFromDisk(itemFromDB.getImageUrl());
                }
                String imgUrl = fileStorageService.storePreviewImage(newImage);
                itemFromDB.setImageUrl(imgUrl);
                inventoryLog.setNote(inventoryLog.getNote() + "Image updated. ");
            } catch (Exception ex) {
                throw new ItemPreviewNotSavedException("Помилка при збереженні прев'ю зображення!");
            }
        }

        // 8. ЗБЕРЕЖЕННЯ НОВИХ ДОКУМЕНТІВ
        if (newDocuments != null && !newDocuments.isEmpty()) {
            try {
                List<ItemDocument> itemDocuments = fileStorageService.saveDocuments(newDocuments, itemFromDB);
                itemFromDB.getDocuments().addAll(itemDocuments);
                inventoryLog.setNote(inventoryLog.getNote() + "New documents added. ");
            } catch (Exception ex) {
                throw new DocumentsNotSavedException("Документи не збережено!");
            }
        }

        // 9. Зберігаємо все в базу
        itemFromDB.addHistoryLog(inventoryLog);
        itemRepository.save(itemFromDB);
    }

}
