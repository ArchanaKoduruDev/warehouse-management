package com.fulfilment.application.monolith.warehouses.exceptions;

public abstract class WarehouseException extends RuntimeException {
    public WarehouseException(String message) {
        super(message);
    }
}

