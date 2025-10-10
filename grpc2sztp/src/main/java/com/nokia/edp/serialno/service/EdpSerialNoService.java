package com.nokia.edp.serialno.service;

import com.nokia.edp.serialno.entity.EdpSerialNo;
import com.nokia.edp.serialno.repository.EdpSerialNoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service for managing EDP Serial Number data
 * This service orchestrates data fetching from FRNG and MAC databases
 * and writes to the target SQL Server database
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EdpSerialNoService {

    private final FrngDataService frngDataService;
    private final MacAddressService macAddressService;
    private final EdpSerialNoRepository edpSerialNoRepository;

    /**
     * Main execution method that fetches data and writes to target database
     */
    @Transactional
    public void execute() {
        log.info("Starting EDP Serial Number data synchronization...");

        try {
            // Step 1: Fetch serial numbers from FRNG
            Map<String, String> frngSerials = frngDataService.fetchSerialData();
            log.info("Retrieved {} serial numbers from FRNG", frngSerials.size());

            if (frngSerials.isEmpty()) {
                log.warn("No serial numbers fetched from FRNG. Exiting.");
                return;
            }

            // Step 2: Fetch MAC addresses for these serials
            List<String> serialList = new ArrayList<>(frngSerials.keySet());
            Map<String, MacAddressService.MacAddressInfo> macAddresses = macAddressService.getMacAddresses(serialList);
            log.info("Retrieved MAC addresses for {} serial numbers", macAddresses.size());

            // Step 3: Get existing serials from target database
            List<String> existingSerials = edpSerialNoRepository.findAllDistinctSerialNums();
            Set<String> existingSerialsSet = new HashSet<>(existingSerials);
            log.info("Found {} existing serial numbers in target database", existingSerialsSet.size());

            // Step 4: Determine which serials to add and which to remove
            Set<String> serialsToAdd = new HashSet<>(frngSerials.keySet());
            serialsToAdd.removeAll(existingSerialsSet);
            
            Set<String> serialsToRemove = new HashSet<>(existingSerialsSet);
            serialsToRemove.removeAll(frngSerials.keySet());

            log.info("Serials to ADD: {}", serialsToAdd.size());
            log.info("Serials to REMOVE: {}", serialsToRemove.size());

            // Step 5: Add new serials to database
            int addedCount = 0;
            int missingMacCount = 0;
            
            for (String serial : serialsToAdd) {
                String itemDesc = frngSerials.get(serial);
                MacAddressService.MacAddressInfo macInfo = macAddresses.get(serial);
                
                EdpSerialNo entity = EdpSerialNo.builder()
                        .serialNum(serial)
                        .itemDesc(itemDesc)
                        .ien("6527")
                        .model(itemDesc)
                        .build();

                if (macInfo != null && macInfo.getUpdatedMac() != null && macInfo.getUpdatedMac().contains(":")) {
                    entity.setMacAddress(macInfo.getBeginMac());
                    entity.setUpdatedMac(macInfo.getUpdatedMac());
                } else {
                    log.warn("Missing or invalid MAC address for serial: {}", serial);
                    missingMacCount++;
                }

                edpSerialNoRepository.save(entity);
                addedCount++;
            }

            log.info("✅ Successfully added {} serial numbers to database", addedCount);
            if (missingMacCount > 0) {
                log.warn("⚠️  {} serial numbers were added without MAC addresses", missingMacCount);
            }

            // Step 6: Remove serials that are no longer in FRNG
            int removedCount = 0;
            for (String serial : serialsToRemove) {
                edpSerialNoRepository.deleteBySerialNum(serial);
                removedCount++;
            }

            if (removedCount > 0) {
                log.info("✅ Successfully removed {} serial numbers from database", removedCount);
            }

            log.info("Data synchronization completed successfully!");
            log.info("Summary - Added: {}, Removed: {}, Missing MAC: {}", addedCount, removedCount, missingMacCount);

        } catch (Exception e) {
            log.error("Error during data synchronization", e);
            throw new RuntimeException("Failed to execute EDP Serial Number synchronization", e);
        }
    }
}
