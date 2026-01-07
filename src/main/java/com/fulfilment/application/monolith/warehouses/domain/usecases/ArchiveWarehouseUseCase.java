package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ArchiveWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseNotFoundException;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ArchiveWarehouseUseCase implements ArchiveWarehouseOperation {

  private final WarehouseStore warehouseStore;
  private static final Logger log = Logger.getLogger(ArchiveWarehouseUseCase.class);

  public ArchiveWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void archive(Warehouse warehouse) {
      log.infof("Archiving warehouse: %s", warehouse.businessUnitCode);

      Warehouse existing =
              warehouseStore.findByBusinessUnitCode(warehouse.businessUnitCode);

      if (existing == null) {
          throw new WarehouseNotFoundException(warehouse.businessUnitCode);
      }

      existing.archivedAt = java.time.ZonedDateTime.now();

      warehouseStore.update(existing);
      log.infof("Warehouse %s successfully archived", warehouse.businessUnitCode);
  }
}
