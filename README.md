# BookTrackr 📚

A simple, secure web app for tracking textbooks: add inventory, assign to students, monitor returns, and export CSV reports.

## Features
- User authentication (login/logout) with Spring Security
- Add single textbooks or batch-add with ID ranges (e.g., `2025-01` → `2025-25`)
- Filtered dashboard that shows **only** the logged-in user’s inventory
- Status counters (total, assigned, unassigned, overdue)
- CSV export of your current stock
- Clean, minimal UI (Thymeleaf)

## Tech Stack
- **Java 21**, **Spring Boot** (Web, Security)
- **Spring Data** (JPA or Mongo—fill in your choice)
- **Thymeleaf** (server-side templates)
- Build: **Maven**
- Database: **PostgreSQL**

## Screenshots
<img width="1280" height="760" alt="Screenshot 2025-08-09 at 13 09 58" src="https://github.com/user-attachments/assets/6219446b-95d4-4393-b923-146df8ce889b" />
<img width="1280" height="760" alt="Screenshot 2025-08-09 at 13 08 57" src="https://github.com/user-attachments/assets/27ef934e-73e0-4735-8fff-072c52afa25f" />
Textbook Stock.csv:(https://github.com/user-attachments/files/21699942/Textbook.Stock.csv)


## Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+
- A running database (if not using embedded H2)

### Configuration
Create `src/main/resources/application.properties` (or `.yml`) with your settings:

```properties
# Server
server.port=8080

# Spring Security (example placeholders)
spring.security.user.name=<admin_username>
spring.security.user.password=<admin_password>

# Database (update based on your DB)
# --- H2 (dev) ---
spring.datasource.url=jdbc:h2:mem:booktrackr;DB_CLOSE_DELAY=-1;MODE=PostgreSQL
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=update

# --- PostgreSQL example ---
# spring.datasource.url=jdbc:postgresql://localhost:5432/booktrackr
# spring.datasource.username=<user>
# spring.datasource.password=<password>
# spring.jpa.hibernate.ddl-auto=update
