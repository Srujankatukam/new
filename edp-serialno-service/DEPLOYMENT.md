# Deployment Guide

## Quick Start

### 1. Build the Application
```bash
cd edp-serialno-service
./gradlew clean build
```

### 2. Create Database Table
Run the SQL script on target SQL Server:
```bash
# Connect to: partner-rebate.database.windows.net
# Database: partners
# User: partner_admin
# Then execute: database-schema.sql
```

### 3. Configure Application
Update `src/main/resources/application.properties` with your database credentials and settings.

### 4. Run the Application
```bash
./start.sh
```

## Detailed Deployment Steps

### Prerequisites Checklist
- [ ] Java 17 or higher installed
- [ ] Network access to FRNG Oracle Database
- [ ] Network access to MAC Address SQL Server
- [ ] Network access to Target SQL Server (partner-rebate.database.windows.net)
- [ ] EDP_SERIALNO table created in target database
- [ ] Database credentials available

### Database Setup

#### Connect to Target SQL Server
```bash
# Using sqlcmd (Linux/Windows)
sqlcmd -S partner-rebate.database.windows.net -d partners -U partner_admin -P "P@rtner!2024#"

# Or use Azure Data Studio / SQL Server Management Studio
```

#### Create Table
```sql
-- Copy from database-schema.sql or run:
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

#### Verify Table Creation
```sql
SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'EDP_SERIALNO';
```

### Configuration

#### Option 1: Update application.properties Directly
Edit `src/main/resources/application.properties`:
```properties
# Update these values
job.interval.seconds=3600
frng.datasource.url=jdbc:oracle:thin:@zncusnfrngdb01.americas.nsn-net.net:1521:frng
frng.datasource.username=NIRND_SNCUSTVOUCHER
frng.datasource.password=ForNIRND_SNCUSTVOUCHER2022!
# ... etc
```

#### Option 2: Use Environment Variables (Recommended for Production)
```bash
export JOB_INTERVAL_SECONDS=3600
export FRNG_DATASOURCE_URL="jdbc:oracle:thin:@zncusnfrngdb01.americas.nsn-net.net:1521:frng"
export FRNG_DATASOURCE_USERNAME="NIRND_SNCUSTVOUCHER"
export FRNG_DATASOURCE_PASSWORD="ForNIRND_SNCUSTVOUCHER2022!"
export MAC_DATASOURCE_URL="jdbc:sqlserver://US70uwapp139.zam.alcatel-lucent.com;databaseName=PROLIGENT_DW"
export MAC_DATASOURCE_USERNAME="NI_RPC_MAC_Query"
export MAC_DATASOURCE_PASSWORD="b&8ZF76P5%@>"
export TARGET_DATASOURCE_URL="jdbc:sqlserver://partner-rebate.database.windows.net;databaseName=partners"
export TARGET_DATASOURCE_USERNAME="partner_admin"
export TARGET_DATASOURCE_PASSWORD="P@rtner!2024#"
```

#### Option 3: Use External Configuration File
Create `application-prod.properties`:
```properties
# Production configuration
job.interval.seconds=3600
# ... add your production settings
```

Run with:
```bash
java -jar edp-serialno-service-1.0.0.jar --spring.profiles.active=prod
```

### Build

```bash
# Clean build
./gradlew clean build

# Build without tests
./gradlew clean build -x test

# Build with specific Java version
./gradlew clean build -Dorg.gradle.java.home=/path/to/java17
```

### Running the Application

#### Development Mode
```bash
# Using Gradle
./gradlew bootRun

# Using the JAR
java -jar build/libs/edp-serialno-service-1.0.0.jar

# Using start script
./start.sh
```

#### Production Mode with Environment Variables
```bash
java -jar edp-serialno-service-1.0.0.jar \
  --server.port=8080 \
  --job.interval.seconds=3600 \
  --logging.level.com.nokia.edp=INFO
```

#### With Custom Memory Settings
```bash
java -Xms512m -Xmx2g -jar edp-serialno-service-1.0.0.jar
```

#### Background Execution
```bash
nohup java -jar edp-serialno-service-1.0.0.jar > app.log 2>&1 &
```

### Systemd Service (Linux Production)

#### Create Service File
```bash
sudo nano /etc/systemd/system/edp-serialno.service
```

#### Service Configuration
```ini
[Unit]
Description=EDP Serial Number Service
After=network.target

[Service]
Type=simple
User=appuser
Group=appgroup
WorkingDirectory=/opt/edp-serialno-service
ExecStart=/usr/bin/java -Xms512m -Xmx2g -jar /opt/edp-serialno-service/edp-serialno-service-1.0.0.jar
SuccessExitStatus=143
TimeoutStopSec=10
Restart=always
RestartSec=10

# Environment variables (optional)
Environment="JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64"
Environment="JOB_INTERVAL_SECONDS=3600"

StandardOutput=journal
StandardError=journal
SyslogIdentifier=edp-serialno

[Install]
WantedBy=multi-user.target
```

#### Deploy and Start
```bash
# Copy JAR to deployment directory
sudo mkdir -p /opt/edp-serialno-service
sudo cp build/libs/edp-serialno-service-1.0.0.jar /opt/edp-serialno-service/
sudo cp src/main/resources/application.properties /opt/edp-serialno-service/

# Set permissions
sudo chown -R appuser:appgroup /opt/edp-serialno-service

# Enable and start service
sudo systemctl daemon-reload
sudo systemctl enable edp-serialno
sudo systemctl start edp-serialno

# Check status
sudo systemctl status edp-serialno

# View logs
sudo journalctl -u edp-serialno -f
```

### Docker Deployment

#### Create Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy JAR file
COPY build/libs/edp-serialno-service-1.0.0.jar app.jar

# Copy configuration (optional, can use env vars instead)
COPY src/main/resources/application.properties application.properties

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Build and Run Docker Image
```bash
# Build image
docker build -t edp-serialno-service:1.0.0 .

# Run container
docker run -d \
  --name edp-service \
  -p 8080:8080 \
  -e JOB_INTERVAL_SECONDS=3600 \
  -e FRNG_DATASOURCE_PASSWORD="${FRNG_PASSWORD}" \
  -e MAC_DATASOURCE_PASSWORD="${MAC_PASSWORD}" \
  -e TARGET_DATASOURCE_PASSWORD="${TARGET_PASSWORD}" \
  edp-serialno-service:1.0.0

# View logs
docker logs -f edp-service

# Stop container
docker stop edp-service
```

#### Docker Compose
Create `docker-compose.yml`:
```yaml
version: '3.8'
services:
  edp-service:
    build: .
    container_name: edp-serialno-service
    ports:
      - "8080:8080"
    environment:
      - JOB_INTERVAL_SECONDS=3600
      - JOB_RUN_ON_START=true
      - FRNG_DATASOURCE_URL=jdbc:oracle:thin:@zncusnfrngdb01.americas.nsn-net.net:1521:frng
      - FRNG_DATASOURCE_USERNAME=NIRND_SNCUSTVOUCHER
      - FRNG_DATASOURCE_PASSWORD=${FRNG_PASSWORD}
      - TARGET_DATASOURCE_PASSWORD=${TARGET_PASSWORD}
    restart: unless-stopped
    logging:
      driver: "json-file"
      options:
        max-size: "10m"
        max-file: "3"
```

Run with:
```bash
docker-compose up -d
```

### Kubernetes Deployment

#### Create Deployment YAML
Create `k8s-deployment.yaml`:
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: edp-serialno-service
  namespace: default
spec:
  replicas: 1
  selector:
    matchLabels:
      app: edp-serialno-service
  template:
    metadata:
      labels:
        app: edp-serialno-service
    spec:
      containers:
      - name: edp-service
        image: edp-serialno-service:1.0.0
        ports:
        - containerPort: 8080
        env:
        - name: JOB_INTERVAL_SECONDS
          value: "3600"
        - name: FRNG_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: edp-secrets
              key: frng-password
        - name: TARGET_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: edp-secrets
              key: target-password
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "2Gi"
            cpu: "1000m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 60
          periodSeconds: 30
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
---
apiVersion: v1
kind: Service
metadata:
  name: edp-serialno-service
spec:
  selector:
    app: edp-serialno-service
  ports:
  - protocol: TCP
    port: 8080
    targetPort: 8080
```

#### Create Secrets
```bash
kubectl create secret generic edp-secrets \
  --from-literal=frng-password='ForNIRND_SNCUSTVOUCHER2022!' \
  --from-literal=mac-password='b&8ZF76P5%@>' \
  --from-literal=target-password='P@rtner!2024#'
```

#### Deploy
```bash
kubectl apply -f k8s-deployment.yaml
kubectl get pods -l app=edp-serialno-service
kubectl logs -f deployment/edp-serialno-service
```

## Monitoring

### Application Logs
```bash
# Systemd service
sudo journalctl -u edp-serialno -f

# Docker
docker logs -f edp-service

# Kubernetes
kubectl logs -f deployment/edp-serialno-service

# Direct JAR execution
tail -f nohup.out
```

### Database Monitoring
```sql
-- Check recent records
SELECT TOP 100 * 
FROM EDP_SERIALNO 
ORDER BY CREATED_DATE DESC;

-- Count records by date
SELECT 
    CAST(CREATED_DATE AS DATE) as Date,
    COUNT(*) as RecordCount
FROM EDP_SERIALNO
GROUP BY CAST(CREATED_DATE AS DATE)
ORDER BY Date DESC;

-- Check for missing MAC addresses
SELECT COUNT(*) as MissingMAC
FROM EDP_SERIALNO
WHERE MAC_ADDRESS IS NULL OR MAC_ADDRESS = '';
```

### Health Checks
```bash
# If Spring Boot Actuator is enabled
curl http://localhost:8080/actuator/health
curl http://localhost:8080/actuator/info
```

## Troubleshooting

### Application Won't Start
1. Check Java version: `java -version`
2. Check logs for errors
3. Verify database connectivity:
   ```bash
   telnet partner-rebate.database.windows.net 1433
   ```
4. Validate configuration in `application.properties`

### Database Connection Errors
1. Check firewall rules
2. Verify credentials
3. Test connection with database client
4. Check connection string format

### Job Not Executing
1. Check `job.run-on-start` setting
2. Verify `job.interval.seconds` is set correctly
3. Check for lock contention in logs
4. Ensure scheduler is enabled (`@EnableScheduling`)

### Memory Issues
Increase heap size:
```bash
java -Xms1g -Xmx4g -jar edp-serialno-service-1.0.0.jar
```

## Rollback

### To Previous Version
1. Stop current service
2. Deploy previous JAR version
3. Restart service

```bash
sudo systemctl stop edp-serialno
sudo cp /backup/edp-serialno-service-0.9.0.jar /opt/edp-serialno-service/edp-serialno-service-1.0.0.jar
sudo systemctl start edp-serialno
```

### To Python Service
See [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) for rollback instructions.

## Backup and Recovery

### Database Backup
```sql
-- Backup EDP_SERIALNO table
SELECT * INTO EDP_SERIALNO_BACKUP_20250109
FROM EDP_SERIALNO;
```

### Configuration Backup
```bash
# Backup configuration
cp src/main/resources/application.properties application.properties.bak.$(date +%Y%m%d)
```

## Performance Tuning

### Database Connection Pool
Update `application.properties`:
```properties
# HikariCP settings
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
```

### JVM Tuning
```bash
java -Xms1g -Xmx2g \
  -XX:+UseG1GC \
  -XX:MaxGCPauseMillis=200 \
  -jar edp-serialno-service-1.0.0.jar
```

## Security

### Encrypt Passwords
Use Jasypt for encrypted properties:
```properties
# Add to build.gradle
implementation 'com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5'

# Encrypt password
java -cp jasypt-1.9.3.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI \
  input="P@rtner!2024#" \
  password=mySecretKey \
  algorithm=PBEWithMD5AndDES

# Use in properties
target.datasource.password=ENC(encrypted_value_here)
```

### SSL/TLS
Enable HTTPS:
```properties
server.ssl.enabled=true
server.ssl.key-store=classpath:keystore.jks
server.ssl.key-store-password=changeit
server.ssl.key-alias=tomcat
```

## Maintenance

### Stop Service
```bash
# Systemd
sudo systemctl stop edp-serialno

# Docker
docker stop edp-service

# Kubernetes
kubectl scale deployment edp-serialno-service --replicas=0
```

### Update Application
```bash
# 1. Build new version
./gradlew clean build

# 2. Stop service
sudo systemctl stop edp-serialno

# 3. Backup current JAR
sudo cp /opt/edp-serialno-service/edp-serialno-service-1.0.0.jar \
        /opt/edp-serialno-service/edp-serialno-service-1.0.0.jar.bak

# 4. Deploy new JAR
sudo cp build/libs/edp-serialno-service-1.0.0.jar /opt/edp-serialno-service/

# 5. Start service
sudo systemctl start edp-serialno

# 6. Verify
sudo systemctl status edp-serialno
```

## Support

For issues:
1. Check logs first
2. Verify database connectivity
3. Review configuration
4. Check network/firewall
5. Consult [README.md](README.md) and [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md)
