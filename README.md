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

## Timezone Configuration

The application is configured to handle timezone conversion for the `createdAt` field in sent violations. The system:

1. **Stores time in GMT**: All `createdAt` timestamps are stored in GMT/UTC in the database
2. **Displays in local time**: Times are automatically converted to the configured local timezone for display
3. **Configurable timezone**: The target timezone can be configured via the `server.timezone` property

### Configuration

Set the desired timezone in `application.yaml`:

```yaml
server:
  timezone: Asia/Almaty  # Default timezone for display
```

### Supported Timezones

Common timezone IDs that can be used:
- `Asia/Almaty` - Kazakhstan time (UTC+6)
- `Europe/Moscow` - Moscow time (UTC+3)
- `UTC` - Coordinated Universal Time
- `America/New_York` - Eastern Time
- `Europe/London` - British Time

### Implementation Details

- **TimezoneUtil**: Utility class for timezone conversion
- **SentViolationsConverter**: Automatically converts GMT times to local time
- **TimezoneConfig**: Sets the default timezone for the application

## Тестовые учетные записи

ADMIN:
admin / admin123
MANAGER:
manager / manager123
OPERATOR:
operator / operator123