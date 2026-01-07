package com.fulfilment.application.monolith.warehouses.exceptions;

public class WarehouseCapacityExceededException extends WarehouseException {
    public WarehouseCapacityExceededException(String businessUnitCode, int maxCapacity) {
        super("Warehouse '" + businessUnitCode + "' capacity exceeds location maximum capacity of " + maxCapacity);
    }
}
