# Integrated gRPC2SZTP + EDP Serial Number Service

## Overview

This is an integrated Spring Boot application that combines two key services:

1. **gRPC SZTP Service** - Provides gRPC services for SZTP (Secure Zero Touch Provisioning) operations
2. **EDP Serial Number Service** - Synchronizes serial number data from FRNG Oracle Database and MAC Address SQL Server to a target database

## Architecture

### Components

```
grpc2sztp-integrated
├── gRPC Services
│   ├── LoginService (JWT authentication)
│   └── OwnershipVoucherService (SZTP operations)
├── EDP Services
│   ├── EdpSerialNoService (data orchestration)
│   ├── FrngDataService (Oracle data fetching)
│   ├── MacAddressService (SQL Server MAC fetching)
│   └── EdpSerialNoScheduler (periodic sync)
├── Integration Layer
│   └── EdpIntegrationService (bridges EDP data with gRPC)
└── Spring Boot Framework
    ├── REST endpoints (port 8080)
    └── JPA/Hibernate for database operations
```

### Data Flow

```
┌──────────────┐      ┌──────────────┐
│ FRNG Oracle  │      │ MAC SQL      │
│   Database   │      │   Server     │
└──────┬───────┘      └──────┬───────┘
       │                     │
       └─────────┬───────────┘
                 ↓
         ┌──────────────────┐
         │ EDP Service      │
         │ (Scheduled Sync) │
         └─────────┬────────┘
                   ↓
         ┌──────────────────┐
         │ Target SQL       │
         │ (EDP_SERIALNO)   │
         └─────────┬────────┘
                   ↓
         ┌──────────────────┐
         │ Integration      │
         │ Service          │
         └─────────┬────────┘
                   ↓
         ┌──────────────────┐
         │ gRPC Services    │
         │ (SZTP APIs)      │
         └──────────────────┘
```

## Key Features

### gRPC SZTP Features
- JWT-based authentication via LoginService
- Ownership voucher management
- Serial number operations (add, get, remove)
- Group management (create, get, delete)
- Domain certificate management
- User role management
- Integration with SZTP backend APIs

### EDP Service Features
- Scheduled synchronization from FRNG and MAC databases
- Automatic data sync every hour (configurable)
- Multi-datasource configuration (Oracle + SQL Server)
- Transaction management
- Serial number validation
- MAC address enrichment

### Integration Features
- Query EDP serial data from gRPC services
- Validate serial numbers for SZTP operations
- Map EDP entities to gRPC DTOs
- Statistics and monitoring capabilities

## Configuration

### Application Properties

The application is configured via `application.properties`:

#### Server Configuration
```properties
spring.application.name=grpc2sztp-integrated
server.port=8080                    # Spring Boot HTTP port
grpc.server.port=9090              # gRPC server port
```

#### SZTP API Configuration
```properties
sztp.api.base.url=<SZTP_API_URL>
sztp.api.timeout.seconds=3000
sztp.api.retry.attempts=3
sztp.api.auth.certificate-path=<PATH_TO_CERT>
sztp.api.auth.certificate-password=<CERT_PASSWORD>
```

#### EDP Scheduler Configuration
```properties
job.interval.seconds=3600          # Sync every hour
job.run-on-start=true             # Run on startup
```

#### Database Configurations

**FRNG Oracle Database (Source)**
```properties
frng.datasource.url=jdbc:oracle:thin:@...
frng.datasource.username=NIRND_SNCUSTVOUCHER
frng.datasource.password=<PASSWORD>
frng.end-customer-codes=1000034759,1000062207,...
frng.sold-to-customer-codes=1000034759,1000062207,...
frng.item-nums=3HE17011AB,3HE20156AA
```

**MAC Address SQL Server (Source)**
```properties
mac.datasource.url=jdbc:sqlserver://...
mac.datasource.username=NI_RPC_MAC_Query
mac.datasource.password=<PASSWORD>
```

**Target SQL Server (Destination)**
```properties
target.datasource.url=jdbc:sqlserver://partner-rebate.database.windows.net...
target.datasource.username=partner_admin
target.datasource.password=<PASSWORD>
```

## Building the Application

### Prerequisites
- Java 17 or higher
- Gradle (wrapper included)
- Network access to all databases

### Build Commands

```bash
# Build without tests
./gradlew clean build -x test

# Build with tests
./gradlew clean build

# Run the application
./gradlew bootRun

# Or run the JAR directly
java -jar build/libs/grpc2sztp-integrated.jar
```

## Running the Application

### Using Gradle
```bash
cd grpc2sztp
./gradlew bootRun
```

### Using JAR
```bash
java -jar build/libs/grpc2sztp-integrated.jar
```

### With Custom Configuration
```bash
java -jar build/libs/grpc2sztp-integrated.jar \
  --grpc.server.port=9091 \
  --job.interval.seconds=7200 \
  --sztp.api.base.url=https://your-sztp-api.com
```

### Using Docker (if Dockerfile exists)
```bash
docker build -t grpc2sztp-integrated .
docker run -p 8080:8080 -p 9090:9090 grpc2sztp-integrated
```

## API Usage

### gRPC Services

#### 1. Login Service
```
Service: login.v1.LoginService
Port: 9090

Method: Login
Request:
{
  "username": "user@example.com",
  "password": "password",
  "org_id": "org123",
  "user_type": "admin"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "expires_in": 3600
}
```

#### 2. Ownership Voucher Service
```
Service: ovgs.v1.OwnershipVoucherService
Port: 9090

Methods:
- CreateGroup
- GetGroup
- DeleteGroup
- AddSerial
- GetSerial
- RemoveSerial
- AddUserRole
- RemoveUserRole
- GetUserRole
- CreateDomainCert
- GetOwnershipVoucher

All methods require Authorization header:
"Authorization: Bearer <jwt_token>"
```

### REST Endpoints (if needed)

Spring Boot provides actuator endpoints on port 8080:
- Health: http://localhost:8080/actuator/health
- Info: http://localhost:8080/actuator/info

## Database Schema

### EDP_SERIALNO Table

```sql
CREATE TABLE EDP_SERIALNO (
    ID BIGINT PRIMARY KEY IDENTITY(1,1),
    SERIAL_NUM VARCHAR(100) NOT NULL,
    ITEM_DESC VARCHAR(255),
    MAC_ADDRESS VARCHAR(50),
    UPDATED_MAC VARCHAR(50),
    IEN VARCHAR(20),
    MODEL VARCHAR(255),
    CREATED_DATE DATETIME2,
    UPDATED_DATE DATETIME2
);

CREATE INDEX IDX_EDP_SERIALNO_SERIAL_NUM ON EDP_SERIALNO(SERIAL_NUM);
```

**Note**: This table must exist before running the application.

## Integration Points

### Using EdpIntegrationService

The `EdpIntegrationService` provides methods to access EDP data from gRPC services:

```java
@Autowired
private EdpIntegrationService edpIntegrationService;

// Get serial information
Optional<SerialDto.GetSerialResponse> response = 
    edpIntegrationService.getSerialInfo(serialNumber);

// Validate serial for SZTP
boolean isValid = edpIntegrationService.isValidForSztp(serialNumber);

// Get component DTO
Optional<ComponentDto> component = 
    edpIntegrationService.getComponentDto(serialNumber);

// Get statistics
EdpStatistics stats = edpIntegrationService.getStatistics();
```

## Monitoring and Logging

### Log Levels
```properties
logging.level.root=INFO
logging.level.com.nokia.grpc2sztp=DEBUG
logging.level.com.nokia.edp=DEBUG
logging.level.okhttp3=INFO
```

### Key Log Messages
- gRPC server startup confirmation
- EDP sync job execution
- Database connection status
- Serial number sync statistics

### Monitoring EDP Sync

Watch for these log messages:
```
Starting EDP Serial Number data synchronization...
Retrieved X serial numbers from FRNG
Retrieved MAC addresses for Y serial numbers
Serials to ADD: Z
Successfully added N serial numbers to database
Data synchronization completed successfully!
```

## Troubleshooting

### Common Issues

#### 1. gRPC Server Fails to Start
- **Cause**: Port 9090 already in use
- **Solution**: Change `grpc.server.port` in properties

#### 2. Database Connection Errors
- **Cause**: Network/firewall restrictions or incorrect credentials
- **Solution**: 
  - Verify network connectivity
  - Check credentials in application.properties
  - Ensure databases are accessible

#### 3. EDP Sync Not Running
- **Cause**: Scheduler not enabled or job configuration issue
- **Solution**:
  - Verify `@EnableScheduling` is present
  - Check `job.run-on-start=true`
  - Review scheduler logs

#### 4. Spring Boot and gRPC Port Conflicts
- **Cause**: Port 8080 or 9090 already in use
- **Solution**: Configure different ports via properties

### Debug Commands

```bash
# Check if ports are available
netstat -an | grep 9090
netstat -an | grep 8080

# View application logs
tail -f logs/application.log

# Test gRPC connection
grpcurl -plaintext localhost:9090 list

# Check database connectivity
# Use SQL client to verify connections
```

## Performance Considerations

### Memory Usage
- Expected: 200-500 MB
- Adjust JVM heap: `-Xmx512m -Xms256m`

### Thread Configuration
- gRPC uses thread pool for concurrent requests
- Spring Boot manages HTTP threads separately
- EDP scheduler runs on Spring's scheduler thread pool

### Database Connections
- 3 datasources configured (FRNG, MAC, Target)
- Each maintains its own connection pool
- Monitor connection pool usage in production

## Security Considerations

### Credentials
- **IMPORTANT**: Store credentials securely
- Use environment variables in production
- Consider Spring Cloud Config Vault for secrets

```bash
# Example with environment variables
export FRNG_PASSWORD="<password>"
export MAC_PASSWORD="<password>"
export TARGET_PASSWORD="<password>"

java -jar grpc2sztp-integrated.jar \
  --frng.datasource.password=${FRNG_PASSWORD} \
  --mac.datasource.password=${MAC_PASSWORD} \
  --target.datasource.password=${TARGET_PASSWORD}
```

### Network Security
- gRPC: Consider TLS/SSL in production
- Database: Use encrypted connections
- SZTP API: Client certificates for authentication

## Deployment

### Systemd Service (Linux)

Create `/etc/systemd/system/grpc2sztp-integrated.service`:

```ini
[Unit]
Description=Integrated gRPC SZTP and EDP Service
After=network.target

[Service]
Type=simple
User=appuser
WorkingDirectory=/opt/grpc2sztp-integrated
ExecStart=/usr/bin/java -jar /opt/grpc2sztp-integrated/grpc2sztp-integrated.jar
Restart=always
RestartSec=10
Environment="JAVA_OPTS=-Xmx512m"

[Install]
WantedBy=multi-user.target
```

Enable and start:
```bash
sudo systemctl daemon-reload
sudo systemctl enable grpc2sztp-integrated
sudo systemctl start grpc2sztp-integrated
sudo systemctl status grpc2sztp-integrated
```

### Docker Deployment

If `Dockerfile` exists:
```bash
docker build -t grpc2sztp-integrated:latest .
docker run -d \
  -p 8080:8080 \
  -p 9090:9090 \
  --name grpc2sztp \
  -e SZTP_API_BASE_URL=https://api.example.com \
  grpc2sztp-integrated:latest
```

### Kubernetes Deployment

Example deployment configuration:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: grpc2sztp-integrated
spec:
  replicas: 2
  selector:
    matchLabels:
      app: grpc2sztp
  template:
    metadata:
      labels:
        app: grpc2sztp
    spec:
      containers:
      - name: grpc2sztp
        image: grpc2sztp-integrated:latest
        ports:
        - containerPort: 8080
          name: http
        - containerPort: 9090
          name: grpc
        env:
        - name: SZTP_API_BASE_URL
          valueFrom:
            configMapKeyRef:
              name: grpc2sztp-config
              key: api.url
```

## Project Structure

```
grpc2sztp/
├── build.gradle.kts          # Build configuration
├── settings.gradle.kts        # Project settings
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/nokia/grpc2sztp/
│   │   │   │   ├── Application.java          # Main Spring Boot app
│   │   │   │   ├── config/
│   │   │   │   ├── dto/
│   │   │   │   ├── interceptor/
│   │   │   │   ├── service/
│   │   │   │   │   ├── LoginServiceImpl.java
│   │   │   │   │   ├── OwnershipVoucherServiceImpl.java
│   │   │   │   │   ├── SZTPWrapperService.java
│   │   │   │   │   └── EdpIntegrationService.java  # Integration layer
│   │   │   │   └── exception/
│   │   │   └── com/nokia/edp/serialno/
│   │   │       ├── config/
│   │   │       │   └── DataSourceConfig.java
│   │   │       ├── entity/
│   │   │       │   └── EdpSerialNo.java
│   │   │       ├── repository/
│   │   │       │   └── EdpSerialNoRepository.java
│   │   │       ├── service/
│   │   │       │   ├── EdpSerialNoService.java
│   │   │       │   ├── FrngDataService.java
│   │   │       │   └── MacAddressService.java
│   │   │       └── scheduler/
│   │   │           └── EdpSerialNoScheduler.java
│   │   ├── resources/
│   │   │   └── application.properties
│   │   └── proto/
│   │       ├── login.proto
│   │       └── ovgs.proto
│   └── test/
├── build/
│   └── libs/
│       └── grpc2sztp-integrated.jar   # Executable JAR (74MB)
└── INTEGRATION_GUIDE.md               # This file
```

## Testing

### Manual Testing

#### 1. Test gRPC Login
```bash
grpcurl -plaintext -d '{
  "username": "admin",
  "password": "password",
  "org_id": "org1",
  "user_type": "admin"
}' localhost:9090 login.v1.LoginService/Login
```

#### 2. Check EDP Sync
- Watch logs for sync execution
- Query EDP_SERIALNO table:
```sql
SELECT COUNT(*) FROM EDP_SERIALNO;
SELECT TOP 10 * FROM EDP_SERIALNO ORDER BY CREATED_DATE DESC;
```

#### 3. Test Integration
- Use EdpIntegrationService methods via REST endpoint (if created)
- Or call directly from gRPC service implementation

### Automated Testing

```bash
# Run all tests
./gradlew test

# Run specific test class
./gradlew test --tests EdpSerialNoServiceTest

# Generate test report
./gradlew test jacocoTestReport
```

## Maintenance

### Regular Tasks
1. Monitor EDP sync job execution
2. Check database connection health
3. Review gRPC service logs
4. Monitor memory and CPU usage
5. Verify serial number data accuracy

### Updates and Changes
- Update database credentials periodically
- Review and adjust sync interval as needed
- Monitor database growth
- Update SZTP API endpoints if changed

## Support

### Log Files
- Application logs: Console output or configured log file
- Spring Boot: `/logs/spring-boot.log` (if configured)
- Database: Check database-specific logs

### Diagnostics
```bash
# Check application status
systemctl status grpc2sztp-integrated

# View recent logs
journalctl -u grpc2sztp-integrated -n 100 -f

# Check database connectivity
# Use appropriate database client tools
```

## Version History

- **1.0-SNAPSHOT**: Initial integrated release
  - Combined gRPC SZTP service with EDP Serial Number service
  - Added EdpIntegrationService for data bridging
  - Spring Boot framework integration
  - Multi-datasource configuration

## License

Internal Nokia project - all rights reserved.

## Contributors

- gRPC2SZTP Team
- EDP Serial Number Service Team
- Integration Team

---

For more information, refer to:
- Original EDP Service: `/edp-serialno-service/README.md`
- Original gRPC Service: Documentation in grpc2sztp
