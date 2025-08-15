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

## CSV Import Functionality

The application includes a CSV import feature that allows bulk import of region, location, APK, and camera data.

### Access

The CSV import functionality is available at:
```
http://localhost:8080/csv-import-view
```

Access is restricted to users with MANAGER or ADMIN roles.

### CSV Format

The CSV file must contain the following columns in order:

1. `region_code` - Region code (must exist in the system)
2. `location_name_ru` - Location name in Russian
3. `apk_device_number` - APK device number
4. `apk_cert_number` - APK certificate number
5. `apk_cert_from` - Certificate issue date (format: yyyy-MM-dd HH:mm:ss)
6. `apk_cert_to` - Certificate expiry date (format: yyyy-MM-dd HH:mm:ss)
7. `camera_ip` - Camera IP address
8. `camera_code` - Camera code
9. `camera_direction` - Camera direction

### Example CSV File

```csv
region_code,location_name_ru,apk_device_number,apk_cert_number,apk_cert_from,apk_cert_to,camera_ip,camera_code,camera_direction
ALM,Алматы центр,APK001,CERT001,2024-01-01 00:00:00,2025-01-01 00:00:00,192.168.1.100,CAM001,Север
ALM,Алматы центр,APK002,CERT002,2024-01-01 00:00:00,2025-01-01 00:00:00,192.168.1.101,CAM002,Юг
```

### Business Logic

The import process follows these rules:

1. **Region Validation**: If the region code doesn't exist, an error is thrown
2. **Location Creation**: If the location doesn't exist, a new one is created with the specified region
3. **APK Processing**: 
   - If APK with the device number exists, it's updated with new data
   - If not, a new APK is created
4. **Camera Processing**:
   - If camera with the same code or IP exists, it's updated
   - If not, a new camera is created

### Error Handling

The import process provides detailed error reporting:
- Invalid CSV format
- Missing or invalid region codes
- Invalid date formats
- Database constraint violations

### Sample File

A sample CSV file is available for download at:
```
http://localhost:8080/files/sample_import.csv
```

## Тестовые учетные записи

ADMIN:
admin / admin123
MANAGER:
manager / manager123
OPERATOR:
operator / operator123
AUTO_MANAGER:
auto_manager / auto123