# EDP Serial Number Service

A Spring Boot application that synchronizes serial number data from FRNG Oracle Database and MAC Address SQL Server to a target SQL Server database.

## Overview

This service replaces the previous Python-based solution that wrote data to Kubernetes clusters. Instead, it now writes directly to a SQL Server database.

### Functionality

1. **Fetch Serial Numbers**: Retrieves serial numbers from FRNG Oracle Database based on configured customer codes and item numbers
2. **Fetch MAC Addresses**: Retrieves MAC address details from SQL Server database
3. **Data Synchronization**: Writes combined data to target SQL Server (`EDP_SERIALNO` table)
4. **Scheduled Execution**: Runs periodically (default: every hour) to keep data in sync

## Architecture

### Data Sources
- **FRNG Oracle Database**: Source for serial numbers and item descriptions
- **MAC Address SQL Server**: Source for MAC address details
- **Target SQL Server**: Destination database for synchronized data

### Technology Stack
- Java 17
- Spring Boot 3.2.0
- Gradle
- Oracle JDBC Driver
- Microsoft SQL Server JDBC Driver
- Spring Data JPA
- Lombok

## Prerequisites

- Java 17 or higher
- Gradle 7.x or higher (or use included Gradle wrapper)
- Network access to:
  - FRNG Oracle Database
  - MAC Address SQL Server
  - Target SQL Server (partner-rebate.database.windows.net)

## Configuration

Edit `src/main/resources/application.properties` to configure:

### Job Scheduler
```properties
job.interval.seconds=3600        # Run every hour
job.run-on-start=true           # Execute on startup
```

### FRNG Oracle Database
```properties
frng.datasource.url=jdbc:oracle:thin:@zncusnfrngdb01.americas.nsn-net.net:1521:frng
frng.datasource.username=NIRND_SNCUSTVOUCHER
frng.datasource.password=ForNIRND_SNCUSTVOUCHER2022!
```

### Query Filters
```properties
frng.end-customer-codes=1000034759,1000062207,...
frng.sold-to-customer-codes=1000034759,1000062207,...
frng.item-nums=3HE17011AB,3HE20156AA
```

### MAC Address SQL Server
```properties
mac.datasource.url=jdbc:sqlserver://US70uwapp139.zam.alcatel-lucent.com;databaseName=PROLIGENT_DW
mac.datasource.username=NI_RPC_MAC_Query
mac.datasource.password=b&8ZF76P5%@>
```

### Target SQL Server
```properties
target.datasource.url=jdbc:sqlserver://partner-rebate.database.windows.net;databaseName=partners
target.datasource.username=partner_admin
target.datasource.password=P@rtner!2024#
```

## Database Schema

The service writes to the `EDP_SERIALNO` table with the following structure:

```sql
CREATE TABLE EDP_SERIALNO (
    ID BIGINT PRIMARY KEY IDENTITY(1,1),
    SERIAL_NUM VARCHAR(100) NOT NULL,
    ITEM_DESC VARCHAR(255),
    MAC_ADDRESS VARCHAR(50),
    UPDATED_MAC VARCHAR(50),
    IEN VARCHAR(20),
    MODEL VARCHAR(255),
    CREATED_DATE DATETIME,
    UPDATED_DATE DATETIME
);
```

**Note**: The table must be created manually before running the service. The service will not auto-create tables (`spring.jpa.hibernate.ddl-auto=none`).

## Building the Project

### Using Gradle Wrapper (Recommended)
```bash
cd edp-serialno-service
./gradlew clean build
```

### Using Local Gradle
```bash
cd edp-serialno-service
gradle clean build
```

## Running the Application

### Run with Gradle
```bash
./gradlew bootRun
```

### Run JAR file
```bash
java -jar build/libs/edp-serialno-service-1.0.0.jar
```

### Run with custom properties
```bash
java -jar build/libs/edp-serialno-service-1.0.0.jar --job.interval.seconds=7200
```

## Project Structure

```
edp-serialno-service/
├── src/
│   ├── main/
│   │   ├── java/com/nokia/edp/serialno/
│   │   │   ├── config/           # Configuration classes
│   │   │   │   └── DataSourceConfig.java
│   │   │   ├── entity/           # JPA entities
│   │   │   │   └── EdpSerialNo.java
│   │   │   ├── repository/       # Data repositories
│   │   │   │   └── EdpSerialNoRepository.java
│   │   │   ├── service/          # Business logic
│   │   │   │   ├── EdpSerialNoService.java
│   │   │   │   ├── FrngDataService.java
│   │   │   │   └── MacAddressService.java
│   │   │   ├── scheduler/        # Scheduled jobs
│   │   │   │   └── EdpSerialNoScheduler.java
│   │   │   └── EdpSerialnoServiceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Test classes
├── build.gradle
├── settings.gradle
├── gradlew
└── README.md
```

## Key Classes

### EdpSerialNoService
Main orchestration service that:
- Fetches data from FRNG
- Gets MAC addresses
- Compares with existing data
- Adds new records and removes obsolete ones

### FrngDataService
Handles Oracle database queries to fetch serial numbers based on:
- End customer codes
- Sold-to customer codes
- Item numbers

### MacAddressService
Retrieves MAC addresses from SQL Server and increments them by 1.

### EdpSerialNoScheduler
Manages periodic execution with:
- Configurable interval
- Optional startup execution
- Lock mechanism to prevent concurrent runs

## Logging

Application logs include:
- Job execution start/end times
- Number of records fetched from each source
- Number of records added/removed
- Any errors or warnings

Logs are written to console with timestamps. Configure logging levels in `application.properties`.

## Migration from Python Service

### Key Differences
- **Target**: SQL Server database instead of Kubernetes clusters
- **Language**: Java Spring Boot instead of Python
- **Build Tool**: Gradle instead of pip
- **Packaging**: JAR file instead of Python scripts

### What's Removed
- Kubernetes client dependencies
- YAML generation for K8s CRDs
- Third-party delegation processing
- K8s cluster operations

### What's Added
- JPA/Hibernate for database operations
- Spring Data repositories
- Multi-datasource configuration
- Proper transaction management

## Troubleshooting

### Database Connection Issues
- Verify network connectivity to all databases
- Check firewall rules
- Ensure credentials are correct
- Confirm database names and server addresses

### Job Not Running
- Check `job.run-on-start` configuration
- Verify `job.interval.seconds` is set correctly
- Review application logs for errors

### Data Not Syncing
- Verify customer codes and item numbers are correct
- Check SQL query permissions on source databases
- Ensure target table exists and schema matches

## Monitoring

Monitor the application through:
- Application logs
- Database query logs
- Job execution timestamps
- Record count changes in `EDP_SERIALNO` table

## Support

For issues or questions, review the application logs first. Common issues include:
- Network/firewall restrictions
- Incorrect credentials
- Missing database permissions
- Table schema mismatches

## License

Internal Nokia project - all rights reserved.
