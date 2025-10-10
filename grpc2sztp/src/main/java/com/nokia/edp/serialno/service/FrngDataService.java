package com.nokia.edp.serialno.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for fetching serial number data from FRNG Oracle Database
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FrngDataService {

    @Qualifier("frngJdbcTemplate")
    private final JdbcTemplate frngJdbcTemplate;

    @Value("${frng.end-customer-codes}")
    private String endCustomerCodes;

    @Value("${frng.sold-to-customer-codes}")
    private String soldToCustomerCodes;

    @Value("${frng.item-nums}")
    private String itemNums;

    /**
     * Fetch serial numbers from FRNG database
     * @return Map of serial number to item description
     */
    public Map<String, String> fetchSerialData() {
        List<String> endCustomerCodesList = parseCommaSeparated(endCustomerCodes);
        List<String> soldToCustomerCodesList = parseCommaSeparated(soldToCustomerCodes);
        List<String> itemNumsList = parseCommaSeparated(itemNums);

        String formattedItemNums = formatForInClause(itemNumsList);
        String formattedEndCustomerCodes = formatForInClause(endCustomerCodesList);
        String formattedSoldToCustomerCodes = formatForInClause(soldToCustomerCodesList);

        String query = String.format("""
            SELECT serial_num, item_desc
            FROM frng.ext_serial_shipments_srm_v
            WHERE item_num IN (%s)
            AND (end_customer_code IN (%s) OR sold_to_customer_code IN (%s))
            AND replacement_serial_num IS NULL
            
            UNION
            
            SELECT replacement_serial_num AS serial_num, item_desc
            FROM frng.ext_serial_shipments_srm_v
            WHERE item_num IN (%s)
            AND (end_customer_code IN (%s) OR sold_to_customer_code IN (%s))
            AND replacement_serial_num IS NOT NULL
            """,
            formattedItemNums, formattedEndCustomerCodes, formattedSoldToCustomerCodes,
            formattedItemNums, formattedEndCustomerCodes, formattedSoldToCustomerCodes
        );

        log.info("Executing FRNG query: {}", query);

        Map<String, String> serialToItemDesc = new HashMap<>();
        
        try {
            List<Map<String, Object>> rows = frngJdbcTemplate.queryForList(query);
            log.info("Fetched {} rows from FRNG database", rows.size());
            
            for (Map<String, Object> row : rows) {
                String serialNum = (String) row.get("SERIAL_NUM");
                String itemDesc = (String) row.get("ITEM_DESC");
                if (serialNum != null) {
                    serialToItemDesc.put(serialNum, itemDesc);
                }
            }
        } catch (Exception e) {
            log.error("Error fetching data from FRNG database", e);
            throw new RuntimeException("Failed to fetch data from FRNG database", e);
        }

        return serialToItemDesc;
    }

    private List<String> parseCommaSeparated(String value) {
        if (value == null || value.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private String formatForInClause(List<String> values) {
        return values.stream()
                .map(v -> "'" + v + "'")
                .collect(Collectors.joining(","));
    }
}
