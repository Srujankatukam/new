-- SQL Server Script to create EDP_SERIALNO table
-- Run this script on the target database before starting the application
-- Database: partners
-- Server: partner-rebate.database.windows.net

USE partners;
GO

-- Drop table if exists (optional - use with caution in production)
-- DROP TABLE IF EXISTS EDP_SERIALNO;
-- GO

-- Create EDP_SERIALNO table
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
GO

-- Create index on SERIAL_NUM for faster lookups
CREATE INDEX IDX_EDP_SERIALNO_SERIAL_NUM ON EDP_SERIALNO(SERIAL_NUM);
GO

-- Verify table creation
SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'EDP_SERIALNO';
GO

-- Sample query to view data
-- SELECT * FROM EDP_SERIALNO ORDER BY CREATED_DATE DESC;
