# Integrated gRPC SZTP + EDP Serial Number Service

## Overview

This is a unified Spring Boot application that combines:
- **gRPC SZTP Services** - LoginService and OwnershipVoucherService for Secure Zero Touch Provisioning
- **EDP Serial Number Service** - Automated synchronization from FRNG Oracle and MAC SQL Server databases

## Quick Start

### Prerequisites
- Java 17+
- Network access to FRNG Oracle, MAC SQL Server, and Target SQL Server
- SZTP API endpoint configured

### Build
```bash
./gradlew clean build -x test
```

### Run
```bash
java -jar build/libs/grpc2sztp-integrated.jar
```

### Verify
```bash
# Check gRPC services
grpcurl -plaintext localhost:9090 list

# Check Spring Boot
curl http://localhost:8080/actuator/health
```

## What's Integrated

### Services Running
| Service | Port | Protocol | Purpose |
|---------|------|----------|---------|
| gRPC Server | 9090 | gRPC | SZTP operations (Login, OwnershipVoucher) |
| Spring Boot | 8080 | HTTP | REST endpoints, Actuator |
| EDP Scheduler | N/A | Internal | Periodic DB sync (every hour) |

### Architecture
```
┌─────────────────────────────────────────────────┐
│         Integrated Application                  │
│                                                  │
│  ┌──────────────┐      ┌───────────────────┐  │
│  │ gRPC SZTP    │      │ EDP Serial Number │  │
│  │ Services     │◄────►│ Service           │  │
│  │              │      │                   │  │
│  │ - Login      │      │ - FRNG Oracle     │  │
│  │ - Voucher    │      │ - MAC SQL Server  │  │
│  │ - Groups     │      │ - Target DB       │  │
│  │ - Serials    │      │ - Scheduler       │  │
│  └──────────────┘      └───────────────────┘  │
│         ▲                       ▲              │
│         │                       │              │
│         ├───────────────────────┤              │
│         │  EdpIntegrationService│              │
│         │  (Bridges EDP + gRPC) │              │
│         └───────────────────────┘              │
│                                                  │
│         Spring Boot Framework                   │
│         (JPA, Scheduling, REST)                 │
└─────────────────────────────────────────────────┘
```

## Key Features

### gRPC Services
- ✅ JWT authentication (LoginService)
- ✅ Ownership voucher management
- ✅ Group operations (create, get, delete)
- ✅ Serial management (add, get, remove)
- ✅ Domain certificate management
- ✅ User role management
- ✅ Integration with SZTP backend APIs

### EDP Service
- ✅ Scheduled sync from FRNG Oracle (serial numbers)
- ✅ MAC address enrichment from SQL Server
- ✅ Target database synchronization (EDP_SERIALNO table)
- ✅ Automatic hourly execution (configurable)
- ✅ Transaction management
- ✅ Multi-datasource configuration

### Integration Layer
- ✅ Query EDP serial data from gRPC services
- ✅ Validate serials for SZTP operations
- ✅ Map EDP entities to gRPC DTOs
- ✅ Statistics and monitoring

## Configuration

Edit `src/main/resources/application.properties`:

### Critical Settings
```properties
# Ports
grpc.server.port=9090
server.port=8080

# SZTP API (REQUIRED)
sztp.api.base.url=https://your-sztp-api.com
sztp.api.auth.certificate-path=/path/to/cert.p12
sztp.api.auth.certificate-password=your-password

# EDP Scheduler
job.interval.seconds=3600        # Sync every hour
job.run-on-start=true           # Run on startup

# Database Credentials (REQUIRED)
frng.datasource.url=jdbc:oracle:thin:@...
frng.datasource.username=...
frng.datasource.password=...

mac.datasource.url=jdbc:sqlserver://...
mac.datasource.username=...
mac.datasource.password=...

target.datasource.url=jdbc:sqlserver://...
target.datasource.username=...
target.datasource.password=...
```

## Usage Examples

### 1. gRPC Login
```bash
grpcurl -plaintext -d '{
  "username": "admin@example.com",
  "password": "password",
  "org_id": "org123",
  "user_type": "admin"
}' localhost:9090 login.v1.LoginService/Login
```

### 2. Check EDP Sync
Monitor logs for:
```
Starting EDP Serial Number data synchronization...
Retrieved 150 serial numbers from FRNG
Successfully added 10 serial numbers to database
Data synchronization completed successfully!
```

### 3. Query EDP Data (via Integration Service)
```java
// In your gRPC service
@Autowired
private EdpIntegrationService edpIntegrationService;

// Get serial information
Optional<SerialDto.GetSerialResponse> serial = 
    edpIntegrationService.getSerialInfo("SERIAL123");

// Validate for SZTP
boolean isValid = edpIntegrationService.isValidForSztp("SERIAL123");
```

## Database Schema

The EDP service requires the `EDP_SERIALNO` table:

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

**Note:** Run this on your target database before starting the application.

## Project Structure

```
grpc2sztp-integrated/
├── src/main/java/com/nokia/
│   ├── grpc2sztp/                  # gRPC SZTP components
│   │   ├── Application.java        # Main Spring Boot app
│   │   ├── service/
│   │   │   ├── LoginServiceImpl.java
│   │   │   ├── OwnershipVoucherServiceImpl.java
│   │   │   ├── SZTPWrapperService.java
│   │   │   └── EdpIntegrationService.java  ← Integration layer
│   │   ├── dto/
│   │   ├── config/
│   │   └── interceptor/
│   └── edp/serialno/              # EDP components
│       ├── config/DataSourceConfig.java
│       ├── entity/EdpSerialNo.java
│       ├── repository/EdpSerialNoRepository.java
│       ├── service/
│       │   ├── EdpSerialNoService.java
│       │   ├── FrngDataService.java
│       │   └── MacAddressService.java
│       └── scheduler/EdpSerialNoScheduler.java
├── src/main/resources/
│   └── application.properties
├── build.gradle.kts
├── README.md                      ← This file
├── INTEGRATION_GUIDE.md           ← Detailed guide
└── INTEGRATION_SUMMARY.md         ← Integration details
```

## Deployment

### Using Systemd (Linux)
```bash
# Copy JAR to deployment location
sudo cp build/libs/grpc2sztp-integrated.jar /opt/grpc2sztp/

# Create systemd service
sudo nano /etc/systemd/system/grpc2sztp.service
```

```ini
[Unit]
Description=Integrated gRPC SZTP and EDP Service
After=network.target

[Service]
Type=simple
User=appuser
WorkingDirectory=/opt/grpc2sztp
ExecStart=/usr/bin/java -jar /opt/grpc2sztp/grpc2sztp-integrated.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

```bash
# Enable and start
sudo systemctl daemon-reload
sudo systemctl enable grpc2sztp
sudo systemctl start grpc2sztp
sudo systemctl status grpc2sztp
```

### Using Docker
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/grpc2sztp-integrated.jar app.jar
EXPOSE 8080 9090
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
docker build -t grpc2sztp-integrated .
docker run -d -p 8080:8080 -p 9090:9090 grpc2sztp-integrated
```

## Monitoring

### Log Files
Application logs show both gRPC and EDP activities:
```bash
# Systemd logs
journalctl -u grpc2sztp -f

# Docker logs
docker logs -f <container-id>
```

### Key Log Patterns
```
# Startup
gRPC Server started on port 9090
Spring Boot HTTP Server running on port: 8080

# EDP Sync
Starting EDP Serial Number data synchronization...
Retrieved X serial numbers from FRNG
Successfully added N serial numbers to database

# gRPC Requests
Authenticating user: admin with orgId: org123
Adding serial: SERIAL123 to group: GROUP456
```

### Health Checks
```bash
# Spring Boot actuator
curl http://localhost:8080/actuator/health

# gRPC services
grpcurl -plaintext localhost:9090 list
```

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Port 9090 already in use | Change `grpc.server.port` in properties |
| Database connection failed | Verify credentials and network access |
| EDP sync not running | Check `@EnableScheduling` and `job.run-on-start` |
| Missing MAC addresses | Verify MAC SQL Server connectivity |
| gRPC authentication fails | Check SZTP API endpoint configuration |

## Performance

### Resource Usage
- Memory: 200-500 MB
- CPU: Low (spikes during EDP sync)
- Disk: ~74MB (JAR file)

### Tuning
```bash
# Increase heap size
java -Xmx512m -Xms256m -jar grpc2sztp-integrated.jar

# Adjust sync interval
--job.interval.seconds=7200  # Every 2 hours
```

## Testing

### Manual Testing
```bash
# 1. Build
./gradlew clean build -x test

# 2. Run
java -jar build/libs/grpc2sztp-integrated.jar

# 3. Test gRPC
grpcurl -plaintext localhost:9090 list

# 4. Test EDP (check logs)
tail -f application.log | grep "EDP Serial"

# 5. Query database
# Use SQL client to verify EDP_SERIALNO table
```

### Automated Testing
```bash
# Run all tests
./gradlew test

# Run with coverage
./gradlew test jacocoTestReport
```

## Documentation

- **README.md** (this file) - Quick start and overview
- **INTEGRATION_GUIDE.md** - Comprehensive usage guide
- **INTEGRATION_SUMMARY.md** - Technical integration details

## Support

### Common Issues
See [INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md#troubleshooting) for detailed troubleshooting.

### Logs
Enable debug logging:
```properties
logging.level.com.nokia.grpc2sztp=DEBUG
logging.level.com.nokia.edp=DEBUG
```

## Version

- **Version:** 1.0-SNAPSHOT
- **Java:** 17
- **Spring Boot:** 3.2.0
- **gRPC:** 1.64.0

## License

Internal Nokia project - all rights reserved.

---

**Build Status:** ✅ Successful  
**JAR Size:** 74MB  
**Java Files:** 25  
**Integration:** Complete  

For detailed information, see [INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)
