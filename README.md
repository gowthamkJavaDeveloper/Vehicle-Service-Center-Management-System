# Vehicle Service Center — Complete Backend Source

Spring Boot 4.1.1 / Java 17 / MySQL 8 / Flyway / JPA / Spring Security JWT.

## Setup
1. Create MySQL database: `CREATE DATABASE vehicle_service_db;`
2. Configure environment variables in Eclipse Run Configuration: `DB_PASSWORD`, `ADMIN_PASSWORD`, and `JWT_SECRET` (at least 32 random ASCII characters).
3. Import as **Existing Maven Project** and update Maven dependencies.
4. Run `VehiclesservicecenterApplication`. Flyway applies V1, V2 and V3 (never edit migrations already applied).
5. `POST /api/v1/auth/login` with `{"username":"admin","password":"<your ADMIN_PASSWORD>"}` to get a Bearer JWT.
6. Use Authorization: Bearer Token for all other endpoints.

## API endpoints
- `/api/v1/customers` — customer master
- `/api/v1/vehicles` — vehicle master
- `/api/v1/mechanics` — mechanic master
- `/api/v1/service-catalog` — service master (verify mapping in controller)
- `/api/v1/spare-parts` — spare parts master (verify mapping in controller)
- `/api/v1/bookings` — booking create/list/get/status
- `/api/v1/job-cards` — job card create/list/get/update/complete
- `/api/v1/stock-movements` — stock IN/OUT and usage against job cards
- `/api/v1/invoices/job-card/{jobCardId}?taxPercent=18` — generate invoice after completing job card
- `/api/v1/payments` — register partial/full payments
- `/actuator/health` — public health endpoint

## Important limitations before production
This is a **complete source-code baseline** for the major modules, **not a tested production-ready deployment**. Build and MySQL integration tests have not run here. Real-world production work still requires tests, concurrency-safe stock updates, price snapshots for invoices, idempotency for payments, full role/ownership policy, rate limits, account lifecycle, migrations review, monitoring, backup and CI.

## Order of workflow
Customer → Vehicle → Service Catalog → Mechanic → Booking → Job Card → Stock OUT → Complete Job Card → Generate Invoice → Payment.

**Do not put real secrets in Git.** Admin account is created on first startup; changing ADMIN_PASSWORD later does not rotate its stored password automatically.
