package com.nokia.edp.serialno.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service for fetching MAC address data from SQL Server
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MacAddressService {

    @Qualifier("macJdbcTemplate")
    private final JdbcTemplate macJdbcTemplate;

    /**
     * Fetch MAC addresses for given serial numbers
     * @param serials List of serial numbers
     * @return Map of serial number to MAC address information (BeginMAC and UpdatedMAC)
     */
    public Map<String, MacAddressInfo> getMacAddresses(List<String> serials) {
        if (serials == null || serials.isEmpty()) {
            log.warn("No serials provided to fetch MAC addresses");
            return Collections.emptyMap();
        }

        log.info("Fetching MAC addresses for {} serial numbers", serials.size());

        // Build the stored procedure call
        StringBuilder declareSql = new StringBuilder("DECLARE @TopSN AS NOKIA.StringList;\n");
        StringBuilder insertSql = new StringBuilder("INSERT INTO @TopSN (ItemValue) VALUES ");
        
        for (int i = 0; i < serials.size(); i++) {
            if (i > 0) insertSql.append(",");
            insertSql.append("('").append(serials.get(i)).append("')");
        }
        insertSql.append(";\n");
        
        String execSql = "EXEC [NOKIA].[GetMACDetails] @TopSNs = @TopSN;\n";
        String fullSql = declareSql.toString() + insertSql.toString() + execSql;

        Map<String, MacAddressInfo> macAddressMap = new HashMap<>();

        try {
            List<Map<String, Object>> rows = macJdbcTemplate.queryForList(fullSql);
            log.info("Fetched {} rows from MAC address database", rows.size());

            for (Map<String, Object> row : rows) {
                String topSN = (String) row.get("TopSN");
                String beginMAC = (String) row.get("MCMBeginMAC");
                Object macDiff = row.get("MCMMACDifference");

                if (topSN != null && beginMAC != null && !beginMAC.trim().isEmpty()) {
                    String updatedMAC = incrementMac(beginMAC);
                    macAddressMap.put(topSN, new MacAddressInfo(beginMAC, updatedMAC, macDiff));
                    log.debug("Serial: {}, BeginMAC: {}, UpdatedMAC: {}", topSN, beginMAC, updatedMAC);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching MAC addresses from SQL Server", e);
            throw new RuntimeException("Failed to fetch MAC addresses", e);
        }

        return macAddressMap;
    }

    /**
     * Increment MAC address by 1
     * @param mac MAC address in format XX:XX:XX:XX:XX:XX
     * @return Incremented MAC address
     */
    private String incrementMac(String mac) {
        if (mac == null || mac.isEmpty()) {
            return mac;
        }

        String macClean = mac.replace(":", "").toUpperCase();
        if (macClean.length() != 12) {
            log.warn("Invalid MAC address format: {}", mac);
            return mac;
        }

        try {
            long macInt = Long.parseLong(macClean, 16) + 1;
            String macNew = String.format("%012X", macInt);
            
            // Format as XX:XX:XX:XX:XX:XX
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < macNew.length(); i += 2) {
                if (i > 0) formatted.append(":");
                formatted.append(macNew, i, i + 2);
            }
            
            return formatted.toString();
        } catch (NumberFormatException e) {
            log.error("Error parsing MAC address: {}", mac, e);
            return mac;
        }
    }

    /**
     * Inner class to hold MAC address information
     */
    public static class MacAddressInfo {
        private final String beginMac;
        private final String updatedMac;
        private final Object macDifference;

        public MacAddressInfo(String beginMac, String updatedMac, Object macDifference) {
            this.beginMac = beginMac;
            this.updatedMac = updatedMac;
            this.macDifference = macDifference;
        }

        public String getBeginMac() {
            return beginMac;
        }

        public String getUpdatedMac() {
            return updatedMac;
        }

        public Object getMacDifference() {
            return macDifference;
        }
    }
}
