package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.CreateWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.LocationResolver;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseAlreadyExistsException;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseCapacityExceededException;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseInvalidLocationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CreateWarehouseUseCase implements CreateWarehouseOperation {

    private static final Logger log = Logger.getLogger(CreateWarehouseUseCase.class);

    private final LocationResolver locationResolver;
    private final WarehouseStore warehouseStore;

    @Inject
    public CreateWarehouseUseCase(WarehouseStore warehouseStore, LocationResolver locationResolver) {
        this.warehouseStore = warehouseStore;
        this.locationResolver = locationResolver;
    }

  @Override
  @Transactional
  public void create(Warehouse warehouse) { // domain Warehouse
      if (warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode) != null) {
          throw new WarehouseAlreadyExistsException(warehouse.businessUnitCode);
      }

      var location = locationResolver.resolveByIdentifier(warehouse.location);
      if (location == null) {
          throw new WarehouseInvalidLocationException(warehouse.location);
      }

      if (warehouse.capacity > location.maxCapacity) {
          throw new WarehouseCapacityExceededException(warehouse.businessUnitCode, location.maxCapacity);
      }

      if (warehouse.stock > warehouse.capacity) {
          throw new WarehouseCapacityExceededException(warehouse.businessUnitCode, warehouse.capacity);
      }

      warehouse.creationAt = java.time.ZonedDateTime.now();
      log.infof("Creating warehouse: %s at location %s", warehouse.businessUnitCode, warehouse.location);

      warehouseStore.create(warehouse); // passes domain Warehouse to repo
  }
}
