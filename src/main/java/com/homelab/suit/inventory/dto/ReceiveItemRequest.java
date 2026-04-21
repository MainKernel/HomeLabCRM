package com.homelab.suit.inventory.dto;

import com.homelab.suit.inventory.model.Item;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ReceiveItemRequest {
    // Всі дані про саму деталь (назва, артикул, категорія тощо)
    private Item item;

    // Параметри конкретної транзакції (партії)
    private BigDecimal quantityAdded;
    private BigDecimal unitPrice;
    private String logNote;
}
