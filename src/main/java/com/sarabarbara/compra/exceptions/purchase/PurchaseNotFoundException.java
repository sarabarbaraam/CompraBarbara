package com.sarabarbara.compra.exceptions.purchase;

public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException(String message) {
        super(message);
    }

}
