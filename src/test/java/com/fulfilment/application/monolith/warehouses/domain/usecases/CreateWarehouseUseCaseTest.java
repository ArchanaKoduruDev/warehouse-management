package com.fulfilment.application.monolith.warehouses.domain.usecases;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.exceptions.*;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateWarehouseUseCaseTest {

    WarehouseStore warehouseStore;
    LocationResolver locationResolver;
    CreateWarehouseUseCase useCase;

    @BeforeEach
    void setUp() {
        warehouseStore = mock(WarehouseStore.class);
        locationResolver = mock(LocationResolver.class);
        useCase = new CreateWarehouseUseCase(warehouseStore, locationResolver);
    }

    @Test
    void testCreateWarehouseSuccess() {
        Warehouse w = new Warehouse();
        w.businessUnitCode = "BU001";
        w.location = "LOC001";
        w.capacity = 50;
        w.stock = 20;

        when(warehouseStore.findByBusinessUnitCode("BU001")).thenReturn(null);
        when(locationResolver.resolveByIdentifier("LOC001")).thenReturn(new com.fulfilment.application.monolith.warehouses.domain.models.Location("LOC001", 1, 100));

        useCase.create(w);

        verify(warehouseStore).create(w);
        assertNotNull(w.creationAt);
    }

    @Test
    void testCreateWarehouseBusinessUnitExists() {
        Warehouse w = new Warehouse();
        w.businessUnitCode = "BU001";

        when(warehouseStore.findByBusinessUnitCode("BU001")).thenReturn(new Warehouse());

        WarehouseAlreadyExistsException ex = assertThrows(WarehouseAlreadyExistsException.class, () -> useCase.create(w));
        assertTrue(ex.getMessage().contains("BU001"));
    }

    @Test
    void testCreateWarehouseInvalidLocation() {
        Warehouse w = new Warehouse();
        w.businessUnitCode = "BU002";
        w.location = "INVALID";

        when(warehouseStore.findByBusinessUnitCode("BU002")).thenReturn(null);
        when(locationResolver.resolveByIdentifier("INVALID")).thenReturn(null);

        assertThrows(WarehouseInvalidLocationException.class, () -> useCase.create(w));
    }

    @Test
    void testCreateWarehouseCapacityExceedsLocation() {
        Warehouse w = new Warehouse();
        w.businessUnitCode = "BU003";
        w.location = "LOC002";
        w.capacity = 200;

        when(warehouseStore.findByBusinessUnitCode("BU003")).thenReturn(null);
        when(locationResolver.resolveByIdentifier("LOC002")).thenReturn(new com.fulfilment.application.monolith.warehouses.domain.models.Location("LOC002", 1, 100));

        assertThrows(WarehouseCapacityExceededException.class, () -> useCase.create(w));
    }

    @Test
    void testCreateWarehouseStockExceedsCapacity() {
        Warehouse w = new Warehouse();
        w.businessUnitCode = "BU004";
        w.location = "LOC003";
        w.capacity = 50;
        w.stock = 60;

        when(warehouseStore.findByBusinessUnitCode("BU004")).thenReturn(null);
        when(locationResolver.resolveByIdentifier("LOC003")).thenReturn(new com.fulfilment.application.monolith.warehouses.domain.models.Location("LOC003", 1, 100));

        assertThrows(WarehouseCapacityExceededException.class, () -> useCase.create(w));
    }
}

