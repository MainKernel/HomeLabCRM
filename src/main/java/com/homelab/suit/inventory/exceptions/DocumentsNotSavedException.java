package com.homelab.suit.inventory.exceptions;

public class DocumentsNotSavedException extends RuntimeException {
    public DocumentsNotSavedException(String message) {
        super(message);
    }
}
