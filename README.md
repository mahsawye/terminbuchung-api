# Terminbuchung API

A REST API for booking appointments at a hair salon (*Terminbuchung*), built with Spring Boot and PostgreSQL. I built it as a learning project to practise layered backend development.

## Features

- Manage **treatments** (name, duration, price) and **customers** with full CRUD
- Book, reschedule, cancel and delete **appointments**
- The end time is calculated automatically from the treatment's duration
- Overlapping bookings are rejected (with a 409 response)
- Input validation and consistent JSON error responses

## Tech stack

Java 25, Spring Boot 4, Spring Web, Spring Data JPA (Hibernate), PostgreSQL, Maven, Git/GitHub

## Project structure

```
controller/   receives HTTP requests and returns responses
service/      business rules (e.g. overlap check, duplicate email)
repository/   database access with Spring Data JPA
model/        JPA entities: User, Treatment, Appointment
dto/          request and response objects
exception/    custom exceptions and a global error handler
```

A request flows like this: `Controller -> Service -> Repository -> Database`.

## Getting started

### Prerequisites

- JDK 17 or newer
- PostgreSQL

### Setup

1. Clone the repository:
```
   git clone https://github.com/mahsawye/terminbuchung-api.git
   cd terminbuchung-api
```
2. Create the database:
```sql
   CREATE DATABASE terminbuchung;
```
3. Set the database password as an environment variable (PowerShell):
```
   $env:DB_PASSWORD="your_password"
```
On Linux/macOS: `export DB_PASSWORD=your_password`
4. Start the application:
```
   ./mvnw spring-boot:run
```
On Windows: `.\mvnw spring-boot:run`

The API runs at `http://localhost:8080`. The tables are created automatically on the first start.

### Protection against double bookings

The service checks for overlapping appointments before saving. To also protect against two requests arriving at exactly the same moment, run this once in your database:

```sql
ALTER TABLE appointments
ADD CONSTRAINT no_overlapping_bookings
EXCLUDE USING gist (tsrange(start_time, end_time) WITH &&)
WHERE (status = 'BOOKED');
```

The database then refuses overlapping bookings by itself, even if the Java check is bypassed by a race condition.

## API endpoints

### Treatments

| Method | Path | Description |
|---|---|---|
| GET | `/api/treatments` | List all treatments |
| GET | `/api/treatments/{id}` | Get one treatment |
| POST | `/api/treatments` | Create a treatment |
| PUT | `/api/treatments/{id}` | Update a treatment |
| DELETE | `/api/treatments/{id}` | Delete a treatment |

### Users

| Method | Path | Description |
|---|---|---|
| GET | `/api/users` | List all users |
| GET | `/api/users/{id}` | Get one user |
| POST | `/api/users` | Create a user (email must be unique) |
| PUT | `/api/users/{id}` | Update a user |
| DELETE | `/api/users/{id}` | Delete a user |

### Appointments

| Method | Path | Description |
|---|---|---|
| GET | `/api/appointments` | List all appointments |
| GET | `/api/appointments/{id}` | Get one appointment |
| POST | `/api/appointments` | Book an appointment |
| PATCH | `/api/appointments/{id}/reschedule` | Change the start time |
| PATCH | `/api/appointments/{id}/cancel` | Cancel (keeps the record) |
| DELETE | `/api/appointments/{id}` | Delete permanently |

### Example: book an appointment

Request:

```
POST /api/appointments
Content-Type: application/json

{
  "userId": 1,
  "treatmentId": 1,
  "startTime": "2026-10-20T14:00:00"
}
```

Response (`201 Created`):

```json
{
  "id": 1,
  "userId": 1,
  "userName": "Anna Schmidt",
  "treatmentId": 1,
  "treatmentName": "Haarschnitt",
  "startTime": "2026-10-20T14:00:00",
  "endTime": "2026-10-20T14:30:00",
  "status": "BOOKED"
}
```

### Error responses

All errors use the same JSON format:

```json
{
  "status": 409,
  "message": "This time slot is already booked",
  "errors": {}
}
```

| Status | When |
|---|---|
| 400 | Validation failed, malformed JSON or invalid parameter |
| 404 | Resource not found |
| 409 | Overlapping booking or duplicate email |

## What I learned

- Structuring a Spring Boot application in layers and why each layer has one job
- Dependency injection, JPA entities and relationships (`@ManyToOne`)
- Validation with DTOs and central exception handling with `@RestControllerAdvice`
- Race conditions and how a database constraint protects against them

## Possible next steps

- Authentication and roles (admin / customer) with Spring Security
- Unit and integration tests with JUnit
- CI with GitHub Actions
- Staff members and opening hours