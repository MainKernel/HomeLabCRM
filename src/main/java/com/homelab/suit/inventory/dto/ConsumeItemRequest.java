package com.homelab.suit.inventory.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConsumeItemRequest {
    private BigDecimal quantityToConsume;
    private String logNote;
}
