package com.homelab.suit.inventory.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ItemCreationDto(
        String sku,
        String name,
        Long workspaceId,
        String unitsOfMeasure,
        String category,
        String packageType,
        BigDecimal stock,
        BigDecimal minStock,
        String location,
        Map<String, String> parameters,
        String serialNumber,
        String note,
        String type
) {
    @Override
    public String toString() {
        return "ItemCreationDto{" +
                "sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", workspaceId=" + workspaceId +
                ", unitsOfMeasure='" + unitsOfMeasure + '\'' +
                ", category='" + category + '\'' +
                ", packageType='" + packageType + '\'' +
                ", stock=" + stock +
                ", minStock=" + minStock +
                ", location='" + location + '\'' +
                ", parameters=" + parameters +
                ", serialNumber='" + serialNumber + '\'' +
                ", note='" + note + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
