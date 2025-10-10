# Quick Start Guide

## 🎯 What You Have

A unified Spring Boot application combining:
- **gRPC SZTP Services** (port 9090)
- **EDP Serial Number Service** (background sync)

## ⚡ 3-Step Setup

### 1. Configure
Edit `src/main/resources/application.properties`:
```properties
# Required: SZTP API
sztp.api.base.url=https://your-sztp-api.com
sztp.api.auth.certificate-path=/path/to/cert.p12
sztp.api.auth.certificate-password=your-password

# Required: Database credentials
frng.datasource.password=YOUR_PASSWORD
mac.datasource.password=YOUR_PASSWORD
target.datasource.password=YOUR_PASSWORD
```

### 2. Build
```bash
./gradlew clean build -x test
```

### 3. Run
```bash
java -jar build/libs/grpc2sztp-integrated.jar
```

## ✅ Verify It's Working

```bash
# Check gRPC (should list 2 services)
grpcurl -plaintext localhost:9090 list

# Check Spring Boot (should return "UP")
curl http://localhost:8080/actuator/health
```

## 📋 What Runs Automatically

When you start the application:

1. ✅ Spring Boot starts on port 8080
2. ✅ gRPC server starts on port 9090
3. ✅ Database connections established (FRNG, MAC, Target)
4. ✅ EDP sync runs immediately (if `job.run-on-start=true`)
5. ✅ EDP sync scheduled every hour

## 🔧 Common Commands

### Run with custom config
```bash
java -jar grpc2sztp-integrated.jar \
  --grpc.server.port=9091 \
  --job.interval.seconds=7200
```

### Watch logs
```bash
# If running in foreground
# Logs appear in console

# If running as systemd service
journalctl -u grpc2sztp -f

# If running with Docker
docker logs -f <container-id>
```

### Test gRPC Login
```bash
grpcurl -plaintext -d '{
  "username": "admin@example.com",
  "password": "password",
  "org_id": "org123",
  "user_type": "admin"
}' localhost:9090 login.v1.LoginService/Login
```

### Check EDP sync status
Watch logs for these messages:
```
Starting EDP Serial Number data synchronization...
Retrieved X serial numbers from FRNG
Successfully added N serial numbers to database
Data synchronization completed successfully!
```

## 🎓 Key Concepts

### Ports
- **9090**: gRPC services (LoginService, OwnershipVoucherService)
- **8080**: Spring Boot HTTP (Actuator, REST)

### Database Tables
- **EDP_SERIALNO**: Target table where serial data is synced

### Sync Schedule
- Default: Every 3600 seconds (1 hour)
- Configurable via `job.interval.seconds`
- Runs on startup if `job.run-on-start=true`

### Integration
gRPC services can access EDP data via `EdpIntegrationService`:
```java
// Available to all Spring-managed beans
@Autowired
private EdpIntegrationService edpIntegrationService;

// Query serial data
Optional<SerialDto.GetSerialResponse> serial = 
    edpIntegrationService.getSerialInfo("SERIAL123");

// Validate for SZTP
boolean isValid = 
    edpIntegrationService.isValidForSztp("SERIAL123");
```

## 📚 Documentation

- **README.md** - Overview and full guide
- **INTEGRATION_GUIDE.md** - Detailed usage and deployment
- **INTEGRATION_SUMMARY.md** - Technical implementation details
- **QUICK_START.md** - This file

## 🔍 Troubleshooting

### Port already in use
```bash
# Change in application.properties
grpc.server.port=9091
server.port=8081
```

### Database connection fails
```bash
# Verify network access
ping zncusnfrngdb01.americas.nsn-net.net
ping partner-rebate.database.windows.net

# Check credentials in application.properties
```

### EDP sync not running
```bash
# Check configuration
job.run-on-start=true
job.interval.seconds=3600

# Enable debug logging
logging.level.com.nokia.edp=DEBUG
```

## 🚀 Production Deployment

### Using Systemd
```bash
# 1. Copy JAR
sudo cp build/libs/grpc2sztp-integrated.jar /opt/grpc2sztp/

# 2. Create service file
sudo nano /etc/systemd/system/grpc2sztp.service

# 3. Start service
sudo systemctl enable grpc2sztp
sudo systemctl start grpc2sztp
sudo systemctl status grpc2sztp
```

### Using Docker
```bash
docker build -t grpc2sztp-integrated .
docker run -d \
  -p 8080:8080 \
  -p 9090:9090 \
  --name grpc2sztp \
  grpc2sztp-integrated
```

## 💡 Quick Tips

1. **Always verify both ports**
   - Port 9090 for gRPC
   - Port 8080 for HTTP

2. **Watch startup logs**
   - Should see "gRPC Server started on port 9090"
   - Should see "Spring Boot HTTP Server running on port: 8080"

3. **Monitor EDP sync**
   - First sync happens on startup
   - Subsequent syncs every hour
   - Check logs for execution status

4. **Database required**
   - EDP_SERIALNO table must exist
   - Run database-schema.sql before first start

5. **Test incrementally**
   - First test gRPC endpoints
   - Then check EDP sync logs
   - Finally query EDP_SERIALNO table

## 📞 Need Help?

1. Check logs first
2. Review configuration in application.properties
3. Verify database connectivity
4. See detailed guides:
   - INTEGRATION_GUIDE.md for comprehensive help
   - INTEGRATION_SUMMARY.md for technical details

---

**Location:** `/workspace/grpc2sztp/`  
**Main File:** `build/libs/grpc2sztp-integrated.jar`  
**Size:** 74 MB  
**Status:** ✅ Ready to run
