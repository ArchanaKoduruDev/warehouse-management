package com.fulfilment.application.monolith.warehouses.exceptions;

public class WarehouseNotFoundException extends WarehouseException {
    public WarehouseNotFoundException(String businessUnitCode) {
        super("Warehouse with business unit code '" + businessUnitCode + "' not found.");
    }
}
