# Shivam Printers

Premium printing and graphic design company website built with Java 17, Spring Boot 3.x, Gradle, PostgreSQL, Thymeleaf, and Tailwind CSS.

## Tech Stack

- Java 17, Spring Boot 3.3.x, Gradle
- Spring Security, Spring Data JPA, Hibernate
- PostgreSQL, Flyway migrations
- Thymeleaf, Tailwind CSS, JavaScript
- Springdoc OpenAPI, OpenHTMLtoPDF

## Prerequisites

- JDK 17+
- Docker (for PostgreSQL)
- Node.js 18+ (for Tailwind CSS build)

## Quick Start

### 1. Start PostgreSQL

```bash
docker compose up -d
```

### 2. Build Tailwind CSS

```bash
npm install
npm run build:css
```

### 3. Run Application

```bash
./gradlew bootRun
```

Visit: http://localhost:8080

## Default Admin Credentials

- Email: `admin@shivamprinters.com`
- Password: `Admin@123456`

## API Documentation

- Swagger UI: http://localhost:8080/api/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api/docs

## Project Structure

```
src/main/java/com/shivamprinters/
├── config/          # App configuration, data initializer
├── security/        # Spring Security
├── controller/      # Web & REST controllers
├── service/         # Business logic
├── repository/      # JPA repositories
├── entity/          # JPA entities
├── dto/             # Request/Response DTOs
├── mapper/          # Entity-DTO mappers
├── exception/       # Global exception handling
└── util/            # Utilities
```

## Pages

| Page | URL |
|------|-----|
| Home | `/` |
| About | `/about` |
| Services | `/services` |
| Portfolio | `/portfolio` |
| Pricing | `/pricing` |
| Blog | `/blog` |
| Contact | `/contact` |
| Login | `/login` |
| Register | `/register` |
| Customer Dashboard | `/dashboard` |
| Admin Dashboard | `/admin` |

## REST API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/public/services` | List services |
| GET | `/api/public/portfolio` | List portfolio (paginated) |
| GET | `/api/public/testimonials` | Featured testimonials |
| GET | `/api/public/blog` | Published blog posts |
| GET | `/api/admin/stats` | Admin dashboard stats |
| GET | `/api/customer/orders` | Customer orders |

## Features

- Premium dark-mode UI with glassmorphism and animations
- Light/dark theme toggle
- Role-based access (ADMIN, CUSTOMER)
- Order management with PDF invoice generation
- File upload for requirements
- Email notifications (configurable)
- Portfolio masonry gallery with search & filtering
- SEO optimized meta tags

## Configuration

Edit `src/main/resources/application.yml`:

- Database connection
- Admin credentials
- Email settings (`app.mail.enabled: true` + SMTP credentials)
- Upload directory

## License

Proprietary — Shivam Printers © 2026
