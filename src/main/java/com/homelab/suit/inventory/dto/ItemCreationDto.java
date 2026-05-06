package com.homelab.suit.inventory.dto;

import com.homelab.suit.inventory.model.ItemType;
import com.homelab.suit.inventory.model.UnitOfMeasure;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Map;

public record ItemCreationDto(
        String sku,
        @NotBlank(message = "name не може бути порожня")
        String name,
        @NotNull(message = "Потрібен Id workspace")
        Long workspaceId,
        @NotNull(message = "Треба обрати якусь одиницю виміру")
        UnitOfMeasure unitsOfMeasure,
        String category,
        String packageType,
        @Min(value = 0, message = "Кількість не може бути від'ємна")
        BigDecimal stock,
        @Min(value = 0, message = "Мінімальна кількість не може бути від'ємна")
        BigDecimal minStock,
        @NotBlank(message = "Потрібно місцезнаходження на складі")
        String location,
        Map<String, String> parameters,
        String serialNumber,
        String note,
        @NotNull
        ItemType type
) {}
