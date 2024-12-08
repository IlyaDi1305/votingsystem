# Voting System for Lunch Decisions

## Overview
This is a REST API built with Spring Boot for a voting system where users can decide where to have lunch. The API supports two roles: **Admin** and **User**.

- **Admins** can:
  - Add and update restaurants.
  - Add and update daily menuItems for restaurants.
- **Users** can:
  - Vote for a restaurant where they want to have lunch.
  - Change their vote before 11:00 AM. After that, votes cannot be changed.

The project is designed for backend use only and assumes a frontend will be built separately.

---

### Users
- Register and login (basic authentication).
- Vote for a restaurant.
- Change their vote before 11:00 AM.

### Admins
- Add, update, and delete restaurants.
- Add, update, and delete daily menuItems for restaurants.

---

## Technologies
- **Spring Boot**
- **Spring Data JPA** with H2 database (in-memory)
- **Spring Security**
- **Swagger/OpenAPI** for API documentation
- **Lombok** to reduce boilerplate code
