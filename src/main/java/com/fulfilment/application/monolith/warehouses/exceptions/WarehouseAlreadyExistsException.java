package com.fulfilment.application.monolith.warehouses.exceptions;

public class WarehouseAlreadyExistsException extends WarehouseException {
    public WarehouseAlreadyExistsException(String businessUnitCode) {
        super("Warehouse with business unit code '" + businessUnitCode + "' already exists.");
    }
}
