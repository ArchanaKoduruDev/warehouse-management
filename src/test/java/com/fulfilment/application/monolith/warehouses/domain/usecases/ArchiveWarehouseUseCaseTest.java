package com.fulfilment.application.monolith.warehouses.domain.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseNotFoundException;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArchiveWarehouseUseCaseTest {

    WarehouseStore warehouseStore;
    ArchiveWarehouseUseCase useCase;

    @BeforeEach
    void setUp() {
        warehouseStore = mock(WarehouseStore.class);
        useCase = new ArchiveWarehouseUseCase(warehouseStore);
    }

    @Test
    void testArchiveWarehouseSuccess() {
        Warehouse w = new Warehouse();
        w.businessUnitCode = "BU001";

        when(warehouseStore.findByBusinessUnitCode("BU001")).thenReturn(w);

        useCase.archive(w);

        assertNotNull(w.archivedAt);
        verify(warehouseStore).update(w);
    }

    @Test
    void testArchiveWarehouseNotFound() {
        Warehouse w = new Warehouse();
        w.businessUnitCode = "BU002";

        when(warehouseStore.findByBusinessUnitCode("BU002")).thenReturn(null);

        assertThrows(WarehouseNotFoundException.class, () -> useCase.archive(w));
    }
}
