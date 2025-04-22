# BDL Backend

A Spring Boot-based backend application for the BDL project.

## Technology Stack

- Java 21
- Spring Boot 3.4.3
- Spring Cloud 2024.0.0
- Spring Data JPA
- PostgreSQL
- Lombok
- OpenAPI/Swagger UI
- Spring Cloud OpenFeign

## Project Structure

```
src/main/java/kz/bdl/
├── config/         # Configuration classes
├── controller/     # REST controllers
├── converter/      # Data converters
├── dto/           # Data Transfer Objects
├── entity/        # JPA entities
├── exception/     # Custom exceptions
├── repository/    # JPA repositories
└── service/       # Business logic
```

## Prerequisites

- Java 21 or higher
- PostgreSQL
- Gradle

## Getting Started

1. Clone the repository
2. Configure your PostgreSQL database connection in `application.properties`
3. Run the application using Gradle:
   ```bash
   ./gradlew bootRun
   ```

## API Documentation

The API documentation is available through Swagger UI at:
```
http://localhost:8080/swagger-ui.html
```

## Development

The project uses:
- Spring Boot DevTools for hot reloading
- Lombok for reducing boilerplate code
- JUnit for testing

## Building

To build the project:
```bash
./gradlew build
```

## Docker Support

The project includes a Dockerfile for containerization. To build and run the Docker container:

```bash
docker build -t bdl-backend .
docker run -p 8080:8080 bdl-backend
```

## License

This project is proprietary and confidential. 