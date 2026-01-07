## API Endpoints

Warehouse Endpoints:
```bash
| Method | Endpoint          | Description                         |
| ------ | ----------------- | ----------------------------------- |
| GET    | `/warehouse`      | List all warehouses                 |
| POST   | `/warehouse`      | Create a new warehouse              |
| GET    | `/warehouse/{id}` | Get warehouse by Business Unit Code |
| DELETE | `/warehouse/{id}` | Archive warehouse (soft delete)     |

====================================================================
POST http://localhost:8080/warehouse

Body
{
  "id": "MWH.100",
  "location": "ZWOLLE-001",
  "capacity": 50,
  "stock": 10
}

Response:
201 created
{
  "id": "MWH.100",
  "location": "ZWOLLE-001",
  "capacity": 50,
  "stock": 10
}

====================================================================

GET http://localhost:8080/warehouse/MWH.100

Response:
{
  "id": "MWH.100",
  "location": "AMSTERDAM-001",
  "capacity": 50,
  "stock": 10
}

====================================================================

DELETE http://localhost:8080//warehouse/MWH.100

Response: 204 No Content

====================================================================

GET http://localhost:8080/warehouse/MWH.100

Response:
{
    "exceptionType": "com.fulfilment.application.monolith.warehouses.exceptions.WarehouseNotFoundException",
    "code": 500,
    "error": "Warehouse with business unit code 'MWH.999' not found."
}

Note: Attached screenshot for exception scenarios

```