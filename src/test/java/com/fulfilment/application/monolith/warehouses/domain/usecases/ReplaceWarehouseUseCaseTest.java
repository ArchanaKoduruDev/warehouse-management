package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.exceptions.*;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReplaceWarehouseUseCaseTest {

    WarehouseStore warehouseStore;
    ReplaceWarehouseUseCase useCase;

    @BeforeEach
    void setUp() {
        warehouseStore = mock(WarehouseStore.class);
        useCase = new ReplaceWarehouseUseCase(warehouseStore);
    }

    @Test
    void testReplaceWarehouseSuccess() {
        Warehouse oldW = new Warehouse();
        oldW.businessUnitCode = "BU001";
        oldW.stock = 50;
        oldW.capacity = 100;

        Warehouse newW = new Warehouse();
        newW.businessUnitCode = "BU001";
        newW.stock = 50;
        newW.capacity = 100;

        when(warehouseStore.findByBusinessUnitCode("BU001")).thenReturn(oldW);

        useCase.replace(newW);

        verify(warehouseStore).update(newW);
    }

    @Test
    void testReplaceWarehouseNotFound() {
        Warehouse newW = new Warehouse();
        newW.businessUnitCode = "BU002";

        when(warehouseStore.findByBusinessUnitCode("BU002")).thenReturn(null);

        assertThrows(WarehouseNotFoundException.class, () -> useCase.replace(newW));
    }

    @Test
    void testReplaceWarehouseStockMismatch() {
        Warehouse oldW = new Warehouse();
        oldW.businessUnitCode = "BU003";
        oldW.stock = 40;

        Warehouse newW = new Warehouse();
        newW.businessUnitCode = "BU003";
        newW.stock = 50;

        when(warehouseStore.findByBusinessUnitCode("BU003")).thenReturn(oldW);

        assertThrows(WarehouseStockMismatchException.class, () -> useCase.replace(newW));
    }

    @Test
    void testReplaceWarehouseCapacityTooSmall() {
        Warehouse oldW = new Warehouse();
        oldW.businessUnitCode = "BU004";
        oldW.stock = 50;

        Warehouse newW = new Warehouse();
        newW.businessUnitCode = "BU004";
        newW.stock = 50;
        newW.capacity = 40;

        when(warehouseStore.findByBusinessUnitCode("BU004")).thenReturn(oldW);

        assertThrows(WarehouseCapacityExceededException.class, () -> useCase.replace(newW));
    }
}

