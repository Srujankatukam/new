# Migration Guide: Python Service to Java Spring Boot

This guide helps you migrate from the Python-based service to the new Java Spring Boot application.

## Overview of Changes

### What Changed

| Aspect | Python Service | Java Spring Boot Service |
|--------|---------------|-------------------------|
| **Language** | Python 3.11 | Java 17 |
| **Framework** | APScheduler | Spring Boot + Spring Scheduler |
| **Build Tool** | pip/requirements.txt | Gradle |
| **Target** | Kubernetes Clusters | SQL Server Database |
| **Dependencies** | oracledb, pyodbc, kubernetes | Oracle JDBC, MSSQL JDBC, Spring Data JPA |
| **Configuration** | .env file (sztp.env) | application.properties |
| **Deployment** | systemd service | JAR file / Docker / K8s |

### Key Functional Changes

#### Removed Features
- ❌ Kubernetes cluster integration
- ❌ YAML file generation
- ❌ kubectl operations
- ❌ ThirdPartyDelegation CRD processing
- ❌ K8s Serial CRD management

#### Added Features
- ✅ Direct SQL Server database writes
- ✅ JPA/Hibernate for database operations
- ✅ Spring Data repositories
- ✅ Transaction management
- ✅ Multi-datasource configuration

## Migration Steps

### 1. Prerequisites

Install required software:
```bash
# Install Java 17 (or higher)
sudo apt update
sudo apt install openjdk-17-jdk

# Verify installation
java -version
```

### 2. Database Setup

Create the target table in SQL Server:
```sql
-- Connect to partner-rebate.database.windows.net
-- Database: partners

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

Use the provided `database-schema.sql` file for this step.

### 3. Configuration Migration

Map your Python environment variables to Java application properties:

#### Python (sztp.env)
```properties
JOB_INTERVAL_SECONDS=3600
FRNG_USER_NAME=NIRND_SNCUSTVOUCHER
FRNG_PASSWORD=Rm9yTklSTkRfU05DVVNUVk9VQ0hFUjIwMjIh
FRNG_HOST=zncusnfrngdb01.americas.nsn-net.net
FRNG_PORT=1521
FRNG_SERVICE=frng
```

#### Java (application.properties)
```properties
job.interval.seconds=3600
frng.datasource.url=jdbc:oracle:thin:@zncusnfrngdb01.americas.nsn-net.net:1521:frng
frng.datasource.username=NIRND_SNCUSTVOUCHER
frng.datasource.password=ForNIRND_SNCUSTVOUCHER2022!
```

**Important**: The Python service used base64-encoded passwords. The Java service uses plain text passwords in properties files. Consider using environment variables or Spring Cloud Config for production.

### 4. Build the Project

```bash
cd edp-serialno-service
./gradlew clean build
```

### 5. Run the Application

#### Option 1: Using the start script
```bash
./start.sh
```

#### Option 2: Using Gradle
```bash
./gradlew bootRun
```

#### Option 3: Running the JAR
```bash
java -jar build/libs/edp-serialno-service-1.0.0.jar
```

### 6. Verify Functionality

Check the logs to ensure:
1. ✅ Application starts successfully
2. ✅ Database connections are established
3. ✅ Data is fetched from FRNG
4. ✅ MAC addresses are retrieved
5. ✅ Records are written to target database

Query the target database:
```sql
SELECT COUNT(*) FROM EDP_SERIALNO;
SELECT TOP 10 * FROM EDP_SERIALNO ORDER BY CREATED_DATE DESC;
```

## Configuration Comparison

### Python Service Files
- `de_service.py` → Main service orchestrator
- `job.py` → Business logic
- `sztp.env` → Environment variables
- `logging.conf` → Logging configuration
- `requirements.txt` → Dependencies

### Java Spring Boot Files
- `EdpSerialnoServiceApplication.java` → Main application
- `EdpSerialNoService.java` → Business logic orchestrator
- `FrngDataService.java` → FRNG data fetching
- `MacAddressService.java` → MAC address fetching
- `EdpSerialNoScheduler.java` → Scheduled job
- `application.properties` → Configuration
- `build.gradle` → Dependencies

## Data Flow Comparison

### Python Service
```
┌─────────────┐
│ FRNG Oracle │ ──┐
└─────────────┘   │
                  ├──> Python Script ──> K8s YAML ──> K8s Cluster
┌─────────────┐   │
│ SQL Server  │ ──┘
│ (MAC Data)  │
└─────────────┘
```

### Java Spring Boot Service
```
┌─────────────┐
│ FRNG Oracle │ ──┐
└─────────────┘   │
                  ├──> Spring Boot App ──> SQL Server
┌─────────────┐   │                        (EDP_SERIALNO)
│ SQL Server  │ ──┘
│ (MAC Data)  │
└─────────────┘
```

## Deployment Options

### Option 1: Systemd Service (Linux)

Create `/etc/systemd/system/edp-serialno.service`:
```ini
[Unit]
Description=EDP Serial Number Service
After=network.target

[Service]
Type=simple
User=appuser
WorkingDirectory=/opt/edp-serialno-service
ExecStart=/usr/bin/java -jar /opt/edp-serialno-service/edp-serialno-service-1.0.0.jar
Restart=always
RestartSec=10

[Install]
WantedBy=multi-user.target
```

Enable and start:
```bash
sudo systemctl daemon-reload
sudo systemctl enable edp-serialno
sudo systemctl start edp-serialno
sudo systemctl status edp-serialno
```

### Option 2: Docker Container

Create `Dockerfile`:
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY build/libs/edp-serialno-service-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
docker build -t edp-serialno-service .
docker run -d -p 8080:8080 --name edp-service edp-serialno-service
```

### Option 3: Kubernetes Deployment

The service can be deployed to Kubernetes, but now it writes to SQL Server instead of managing K8s resources.

## Troubleshooting

### Common Issues

#### 1. Database Connection Errors
**Python Error**: `oracledb.Error: ORA-12154`
**Java Error**: `java.sql.SQLException: IO Error: The Network Adapter could not establish the connection`

**Solution**: Check network connectivity, firewall rules, and database URLs.

#### 2. Missing Dependencies
**Python Error**: `ModuleNotFoundError: No module named 'oracledb'`
**Java Error**: `java.lang.ClassNotFoundException: oracle.jdbc.OracleDriver`

**Solution**: 
- Python: `pip install -r requirements.txt`
- Java: Dependencies are in `build.gradle` and downloaded by Gradle

#### 3. Scheduler Not Running
**Python**: Check `RUN_ON_START` and `JOB_INTERVAL_SECONDS` in `sztp.env`
**Java**: Check `job.run-on-start` and `job.interval.seconds` in `application.properties`

### Logging

**Python**: Uses `logging.conf` file
```python
logging.config.fileConfig('logging.conf')
```

**Java**: Uses Spring Boot logging configuration
```properties
logging.level.com.nokia.edp=DEBUG
```

## Testing

### Validate Data Migration

```sql
-- Check record counts
SELECT 
    (SELECT COUNT(*) FROM EDP_SERIALNO) as TotalRecords,
    (SELECT COUNT(DISTINCT SERIAL_NUM) FROM EDP_SERIALNO) as UniqueSerials,
    (SELECT COUNT(*) FROM EDP_SERIALNO WHERE MAC_ADDRESS IS NULL) as MissingMAC;

-- Compare with FRNG source
-- (Run similar queries on FRNG to compare)
```

### Test Scheduler

Set a short interval for testing:
```properties
job.interval.seconds=300  # 5 minutes
job.run-on-start=true
```

Watch the logs to confirm periodic execution.

## Rollback Plan

If you need to rollback to the Python service:

1. Stop the Java service
2. Restart the Python service with systemd:
   ```bash
   sudo systemctl start sztp_google_service
   ```
3. Data in SQL Server will remain, but won't be updated
4. K8s clusters will need to be manually synchronized

## Security Considerations

### Python Service
- Passwords base64-encoded in `.env` file
- Kubeconfig file required for K8s access

### Java Service
- Passwords in plain text in `application.properties`
- **Recommendation**: Use environment variables or Spring Cloud Config Vault
- No K8s credentials needed

### Hardening Java Service

Use environment variables:
```bash
export FRNG_PASSWORD="ForNIRND_SNCUSTVOUCHER2022!"
export MAC_PASSWORD="b&8ZF76P5%@>"
export TARGET_PASSWORD="P@rtner!2024#"

java -jar edp-serialno-service-1.0.0.jar \
  --frng.datasource.password=${FRNG_PASSWORD} \
  --mac.datasource.password=${MAC_PASSWORD} \
  --target.datasource.password=${TARGET_PASSWORD}
```

Or use Spring Boot's encrypted properties with Jasypt.

## Performance Comparison

| Metric | Python Service | Java Service |
|--------|---------------|--------------|
| Startup Time | ~2-3 seconds | ~5-8 seconds |
| Memory Usage | ~100-200 MB | ~200-400 MB |
| CPU Usage | Low | Low-Medium |
| Database Connections | 2 (Oracle + SQL) | 3 (Oracle + SQL + SQL) |

## Support and Maintenance

### Python Service (Legacy)
- Location: `/workspace/service/`
- Maintainer: Original team
- Status: Deprecated

### Java Service (Current)
- Location: `/workspace/edp-serialno-service/`
- Documentation: This file + README.md
- Status: Active

## Next Steps

1. ✅ Complete database schema setup
2. ✅ Configure application properties
3. ✅ Build and test the application
4. ✅ Deploy to target environment
5. ✅ Monitor initial runs
6. ✅ Validate data in SQL Server
7. ✅ Decommission Python service (after validation period)
8. ✅ Update operational documentation

## Questions?

Review the main [README.md](README.md) for detailed application information.
