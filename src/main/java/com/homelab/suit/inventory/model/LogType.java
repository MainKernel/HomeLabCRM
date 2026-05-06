package com.homelab.suit.inventory.model;

public enum LogType {
    IN,     // Прихід на склад
    OUT,    // Списання зі складу (наприклад, під проект)
    AUDIT,   // Інвентаризація (знайшли загублене або списали зламане)
    UPDATE, // Оновлення компонента

}
