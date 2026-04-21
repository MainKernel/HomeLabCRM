package com.homelab.suit.inventory.model;

public enum ItemType {
    REGULAR, // Звичайні компоненти (конденсатори, резистори) - стакаються
    UNIQUE   // Унікальні пристрої (радіостанції, осцилографи) - мають серійник, кількість завжди 1
}