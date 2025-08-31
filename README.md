# BookTrackr 📚

A simple, secure web app for tracking textbooks: add inventory, assign to students, monitor returns, and export CSV reports.

Check out the website here --> <https://booktrackr-sfvg.onrender.com>

## Features
- User authentication (login/logout) with Spring Security
- Add single textbooks or batch-add with ID ranges (e.g., `2025-01` → `2025-25`)
- Filtered dashboard showing **only** the logged-in user’s inventory
- Status counters (total, assigned, unassigned, overdue)
- CSV export of current stock
- Clean, minimal UI (Thymeleaf)

## Tech Stack
- **Java 21**, **Spring Boot** (Web, Security)
- **Spring Data JPA**
- **Thymeleaf** (server-side templates)
- Build: **Maven**
- Database: **PostgreSQL**

## Deployment
Deployed on **Render** with **Supabase Postgres**.
