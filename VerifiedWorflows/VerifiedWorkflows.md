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


## Test Coverage Strategy

The project follows a risk-based testing approach.

- **High coverage** is enforced for domain models and use cases, where business rules and invariants are implemented.
- **Integration tests** validate REST endpoints and adapters end-to-end using Quarkus Test and Testcontainers.
- Generated code, DTOs, and thin adapters are intentionally excluded from strict coverage enforcement to avoid low-value tests.
```
## Test Coverage Strategy

The project follows a risk-based testing approach.

- **High coverage** is enforced for domain models and use cases, where business rules and invariants are implemented.
- **Integration tests** validate REST endpoints and adapters end-to-end using Quarkus Test and Testcontainers.
- Generated code, DTOs, and thin adapters are intentionally excluded from strict coverage enforcement to avoid low-value tests.

JaCoCo reports show ~97% coverage for domain use case.

## Error Handling & Validation

The application uses a centralized `GlobalExceptionHandler` to map domain exceptions
to consistent HTTP error responses.

All business validation errors return HTTP 400 with a structured JSON payload.

Example error response:

```json
{
  "error": "Warehouse with business unit code 'UNKNOWN' not found.",
  "exceptionType": "WarehouseNotFoundException",
  "code": 400
}