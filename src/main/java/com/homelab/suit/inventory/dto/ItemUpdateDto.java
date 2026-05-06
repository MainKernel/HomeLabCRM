package com.homelab.suit.inventory.dto;

import com.homelab.suit.inventory.model.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemUpdateDto {
    private Long id;
    private String sku;
    private String name;
    private String imageUrl;
    private Long workspaceId;
    private UnitOfMeasure unitOfMeasure;
    private Long categoryId;
    private String categoryName;
    private String packageType;
    private BigDecimal totalQuantity;
    private BigDecimal minStock;
    private Long locationId;
    private String locationName;
    private Map<String, String> parameters;
    private String serialNumber;
    private String note;
    private ItemType itemType;
    private List<ItemDocument> documents;
}
