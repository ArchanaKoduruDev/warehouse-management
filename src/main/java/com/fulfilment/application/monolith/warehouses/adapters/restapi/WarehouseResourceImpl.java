package com.fulfilment.application.monolith.warehouses.adapters.restapi;

import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseNotFoundException;
import com.warehouse.api.WarehouseResource;
import com.warehouse.api.beans.Warehouse;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;
import java.util.stream.Collectors;

public class WarehouseResourceImpl implements WarehouseResource {

    @Inject
    CreateWarehouseOperation createUseCase;
    @Inject
    ArchiveWarehouseOperation archiveUseCase;
    @Inject
    WarehouseStore warehouseStore;

    @Override
    public List<Warehouse> listAllWarehousesUnits() {
        return warehouseStore.findAllWarehouses().stream()
                .map(this::toApi)
                .collect(Collectors.toList());
    }
    @Override
    public Warehouse createANewWarehouseUnit(@NotNull Warehouse data) {
        // Convert API DTO to domain
        com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain = toDomain(data);

        // Pass domain object to use case
        createUseCase.create(domain);

        // Return API DTO
        return toApi(domain);
    }

    @Override
    public Warehouse getAWarehouseUnitByID(String id) {
        com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain =
                warehouseStore.findByBusinessUnitCode(id);

        if (domain == null) {
            throw new WarehouseNotFoundException(id);
        }

        return toApi(domain);
    }

    @Override
    public void archiveAWarehouseUnitByID(String id) {
        com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain =
                warehouseStore.findByBusinessUnitCode(id);

        if (domain == null) {
            throw new WarehouseNotFoundException(id);
        }

        archiveUseCase.archive(domain);
    }


    private com.fulfilment.application.monolith.warehouses.domain.models.Warehouse toDomain(Warehouse api) {
        if (api == null) return null;

        com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain =
                new com.fulfilment.application.monolith.warehouses.domain.models.Warehouse();

        domain.businessUnitCode = api.getId();
        domain.location = api.getLocation();
        domain.capacity = api.getCapacity();
        domain.stock = api.getStock();

        return domain;
    }

    private Warehouse toApi(com.fulfilment.application.monolith.warehouses.domain.models.Warehouse domain) {
        if (domain == null) return null;

        Warehouse api = new Warehouse();
        api.setId(domain.businessUnitCode);
        api.setLocation(domain.location);
        api.setCapacity(domain.capacity);
        api.setStock(domain.stock);
        return api;
    }
}
