package com.sarabarbara.compra.exceptions.item;

/**
 * ItemNotFoundException class
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 12/03/2025
 */

public class ItemNotFoundException extends RuntimeException {

    public ItemNotFoundException(String message) {
        super(message);
    }

}
