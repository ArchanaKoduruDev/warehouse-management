# Questions

Here we have 3 questions related to the code base for you to answer. It is not about right or wrong, but more about what's the reasoning behind your decisions.

1. In this code base, we have some different implementation strategies when it comes to database access layer and manipulation. If you would maintain this code base, would you refactor any of those? Why?

**Answer:**
```txt
Yes, I would refactor parts of the database access layer to make the codebase more consistent, maintainable, and production-ready.

Observations

Currently, the codebase uses multiple persistence styles:

Active Record style (PanacheEntity)

Used in Store and Product

Repository + Domain Model separation

Used in Warehouse (DbWarehouse + domain Warehouse + WarehouseStore port)

Both approaches work technically, but mixing them in the same codebase increases cognitive load and makes long-term maintenance harder.

Refactor Store and Product to:

Use domain models

Introduce repository interfaces (ports)

Keep Panache/JPA inside adapters only

Make transaction boundaries explicit at use-case level

Avoid mixing persistence logic inside REST resources
```
----
2. When it comes to API spec and endpoints handlers, we have an Open API yaml file for the `Warehouse` API from which we generate code, but for the other endpoints - `Product` and `Store` - we just coded directly everything. What would be your thoughts about what are the pros and cons of each approach and what would be your choice?

**Answer:**
```txt
Both approaches used in this codebase are valid, but they serve different purposes and have different trade-offs.
My choice in this codebase

For a production system, I would standardize on:

OpenAPI-first for all externally exposed APIs

Reasoning:

This system already exposes integration points (legacy systems, consumers)

Contract stability is critical

It improves:

API governance

Versioning strategy

Consumer trust

For internal or experimental endpoints, I might still allow code-first initially, but with a clear path to OpenAPI adoption.

I would have handled if time permits below areas:
   Introduce versioning strategy (/v1/warehouse), avoid breaking changes without explicit version bump.
Structured logging (correlation IDs, business keys)

   Metrics (request latency, DB operations, error rates)

   Tracing (OpenTelemetry)

   Log business events, not just technical errors
   
   suggested design approach:
          ┌────────────┐
          │   REST API │
          └─────┬──────┘
                │
                ▼
        ┌─────────────────┐
        │   Use Cases     │
        │ (Application)   │
        └─────┬───────────┘
              │
              ▼
        ┌─────────────────┐
        │ Domain Models   │
        │ + Business Rules│
        └─────┬───────────┘
              │ (Port)
              ▼
        ┌─────────────────┐
        │ Repository      │
        │ (Adapter)       │
        └─────┬───────────┘
              ▼
        ┌─────────────────┐
        │   Database      │
        └─────────────────┘

After Commit:
Use Case → CDI Event → Legacy System

Ihis strategy helps:
Prevents sending uncommitted or rolled-back data to downstream systems

Avoids distributed consistency bugs

Enables retries, async processing later if needed

```