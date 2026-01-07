package com.fulfilment.application.monolith.warehouses.adapters.database;

import com.fulfilment.application.monolith.warehouses.domain.models.Warehouse;
import com.fulfilment.application.monolith.warehouses.domain.ports.WarehouseStore;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class WarehouseRepository implements WarehouseStore, PanacheRepository<DbWarehouse> {

  @Override
  public void create(Warehouse warehouse) {
      persist(toEntity(warehouse));
  }
    @Override
    public List<Warehouse> findAllWarehouses() {
        return list("archivedAt IS NULL").stream()       // PanacheRepository<DbWarehouse>.listAll()
                .map(this::toDomain)    // convert DbWarehouse → domain Warehouse
                .collect(Collectors.toList());
    }
  @Override
  @Transactional
  public void update(Warehouse warehouse) {
      DbWarehouse db =
              find("businessUnitCode = ?1 AND archivedAt IS NULL", warehouse.businessUnitCode).firstResult();
      if (db == null) return;

      db.capacity = warehouse.capacity;
      db.stock = warehouse.stock;
      db.archivedAt = warehouse.archivedAt != null ? warehouse.archivedAt.toLocalDateTime() : null;
  }

  @Override
  public void remove(Warehouse warehouse) {
      DbWarehouse db = find("businessUnitCode = ?1 AND archivedAt IS NULL", warehouse.businessUnitCode)
              .firstResult();
      if (db != null) {
          db.archivedAt = java.time.LocalDateTime.now();
      }
  }

  @Override
  public Warehouse findByBusinessUnitCode(String buCode) {
      DbWarehouse db =
              find("businessUnitCode = ?1 AND archivedAt IS NULL", buCode).firstResult();
      return db == null ? null : toDomain(db);
  }

    private DbWarehouse toEntity(Warehouse w) {
        DbWarehouse db = new DbWarehouse();
        db.businessUnitCode = w.businessUnitCode;
        db.location = w.location;
        db.capacity = w.capacity;
        db.stock = w.stock;
        db.createdAt = w.creationAt != null ? w.creationAt.toLocalDateTime() : null;
        db.archivedAt = w.archivedAt != null ? w.archivedAt.toLocalDateTime() : null;
        return db;
    }

    private Warehouse toDomain(DbWarehouse db) {
        Warehouse w = new Warehouse();
        w.businessUnitCode = db.businessUnitCode;
        w.location = db.location;
        w.capacity = db.capacity;
        w.stock = db.stock;
        w.creationAt = db.createdAt != null ? db.createdAt.atZone(java.time.ZoneId.systemDefault()) : null;
        w.archivedAt = db.archivedAt != null ? db.archivedAt.atZone(java.time.ZoneId.systemDefault()) : null;
        return w;
    }

}
