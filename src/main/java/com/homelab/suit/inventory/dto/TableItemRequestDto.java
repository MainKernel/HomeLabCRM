package com.homelab.suit.inventory.dto;

public record TableItemRequestDto(
        Long id,
        String image,
        String name,
        String location,
        String totalQuantity,
        String unitOfMeasure
) {
}
