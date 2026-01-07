package com.fulfilment.application.monolith.warehouses.exceptions;

public class WarehouseInvalidLocationException extends WarehouseException {
    public WarehouseInvalidLocationException(String locationId) {
        super("Invalid location identifier: " + locationId);
    }
}
