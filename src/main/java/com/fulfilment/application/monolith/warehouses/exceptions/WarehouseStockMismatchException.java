package com.fulfilment.application.monolith.warehouses.exceptions;

public class WarehouseStockMismatchException extends WarehouseException {
    public WarehouseStockMismatchException(String businessUnitCode) {
        super("Stock of warehouse '" + businessUnitCode + "' does not match the previous warehouse.");
    }
}
