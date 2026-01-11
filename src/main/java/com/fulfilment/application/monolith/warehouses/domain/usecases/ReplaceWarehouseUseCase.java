package com.fulfilment.application.monolith.warehouses.domain.usecases;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fulfilment.application.monolith.warehouses.adapters.database.WarehouseRepository;
import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.ReplaceWarehouseOperation;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseCapacityExceededException;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseNotFoundException;
import com.fulfilment.application.monolith.warehouses.exceptions.WarehouseStockMismatchException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ReplaceWarehouseUseCase implements ReplaceWarehouseOperation {

    @Inject
    WarehouseStore warehouseStore;

    private static final Logger log = Logger.getLogger(ReplaceWarehouseUseCase.class);

  public ReplaceWarehouseUseCase(WarehouseStore warehouseStore) {
    this.warehouseStore = warehouseStore;
  }

  @Override
  public void replace(Warehouse newWarehouse) {
      Warehouse oldWarehouse =
              warehouseStore.findByBusinessUnitCode(newWarehouse.businessUnitCode);

      if (oldWarehouse == null) {
          throw new WarehouseNotFoundException(newWarehouse.businessUnitCode);
      }

      // Stock must match
      if (!oldWarehouse.stock.equals(newWarehouse.stock)) {
          throw new WarehouseStockMismatchException(newWarehouse.businessUnitCode);
      }

      // Capacity must fit existing stock
      if (newWarehouse.capacity < oldWarehouse.stock) {
          throw new WarehouseCapacityExceededException( newWarehouse.businessUnitCode, newWarehouse.capacity);
      }

    log.debugf("Updated warehouse entity: %s", toJsonSafe(newWarehouse));
    warehouseStore.update(newWarehouse);
  }

    public static String toJsonSafe(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString(); // fallback to default toString()
        }
    }
}
