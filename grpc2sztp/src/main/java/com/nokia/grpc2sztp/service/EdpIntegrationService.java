package com.nokia.grpc2sztp.service;

import com.nokia.edp.serialno.entity.EdpSerialNo;
import com.nokia.edp.serialno.repository.EdpSerialNoRepository;
import com.nokia.grpc2sztp.dto.ComponentDto;
import com.nokia.grpc2sztp.dto.SerialDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Integration service that bridges EDP Serial Number data with gRPC SZTP services
 * This service provides methods to query and utilize serial number data from the
 * EDP_SERIALNO database table within the gRPC context
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EdpIntegrationService {
    
    private final EdpSerialNoRepository edpSerialNoRepository;
    
    /**
     * Get serial information from EDP database by serial number
     * Maps EDP entity to gRPC SerialDto response
     */
    public Optional<SerialDto.GetSerialResponse> getSerialInfo(String serialNumber) {
        log.debug("Fetching serial info for: {}", serialNumber);
        
        Optional<EdpSerialNo> edpSerial = edpSerialNoRepository.findBySerialNum(serialNumber);
        
        return edpSerial.map(this::mapToSerialResponse);
    }
    
    /**
     * Get serial information by both IEN and serial number
     * This matches the gRPC service signature
     */
    public Optional<SerialDto.GetSerialResponse> getSerialInfo(String ien, String serialNumber) {
        log.debug("Fetching serial info for IEN: {}, Serial: {}", ien, serialNumber);
        
        Optional<EdpSerialNo> edpSerial = edpSerialNoRepository.findBySerialNum(serialNumber);
        
        // Optionally filter by IEN if provided
        if (edpSerial.isPresent() && ien != null && !ien.isEmpty()) {
            EdpSerialNo serial = edpSerial.get();
            if (!ien.equals(serial.getIen())) {
                log.warn("IEN mismatch for serial {}: expected {}, found {}", 
                    serialNumber, ien, serial.getIen());
                return Optional.empty();
            }
        }
        
        return edpSerial.map(this::mapToSerialResponse);
    }
    
    /**
     * Check if a serial number exists in EDP database
     */
    public boolean serialExists(String serialNumber) {
        return edpSerialNoRepository.existsBySerialNum(serialNumber);
    }
    
    /**
     * Get all serial numbers from EDP database
     */
    public List<String> getAllSerialNumbers() {
        return edpSerialNoRepository.findAllDistinctSerialNums();
    }
    
    /**
     * Get ComponentDto from EDP serial number
     * This is useful for integration with gRPC requests
     */
    public Optional<ComponentDto> getComponentDto(String serialNumber) {
        log.debug("Creating ComponentDto for serial: {}", serialNumber);
        
        Optional<EdpSerialNo> edpSerial = edpSerialNoRepository.findBySerialNum(serialNumber);
        
        return edpSerial.map(serial -> {
            ComponentDto component = new ComponentDto();
            component.setSerialNumber(serial.getSerialNum());
            component.setIen(serial.getIen());
            component.setModel(serial.getModel() != null ? serial.getModel() : serial.getItemDesc());
            component.setMacAddr(serial.getUpdatedMac() != null ? 
                serial.getUpdatedMac() : serial.getMacAddress());
            return component;
        });
    }
    
    /**
     * Validate if a serial number can be used for SZTP operations
     * Checks if serial exists and has valid MAC address
     */
    public boolean isValidForSztp(String serialNumber) {
        Optional<EdpSerialNo> edpSerial = edpSerialNoRepository.findBySerialNum(serialNumber);
        
        if (edpSerial.isEmpty()) {
            log.warn("Serial number not found in EDP database: {}", serialNumber);
            return false;
        }
        
        EdpSerialNo serial = edpSerial.get();
        
        // Check if MAC address is present
        boolean hasMac = (serial.getUpdatedMac() != null && !serial.getUpdatedMac().isEmpty()) ||
                        (serial.getMacAddress() != null && !serial.getMacAddress().isEmpty());
        
        if (!hasMac) {
            log.warn("Serial number {} has no MAC address", serialNumber);
            return false;
        }
        
        // Check if IEN is present
        if (serial.getIen() == null || serial.getIen().isEmpty()) {
            log.warn("Serial number {} has no IEN", serialNumber);
            return false;
        }
        
        return true;
    }
    
    /**
     * Get all valid serials for SZTP operations
     * Returns only serials that have both IEN and MAC address
     */
    public List<String> getValidSerialsForSztp() {
        List<EdpSerialNo> allSerials = edpSerialNoRepository.findAll();
        
        return allSerials.stream()
            .filter(serial -> {
                boolean hasMac = (serial.getUpdatedMac() != null && !serial.getUpdatedMac().isEmpty()) ||
                                (serial.getMacAddress() != null && !serial.getMacAddress().isEmpty());
                boolean hasIen = serial.getIen() != null && !serial.getIen().isEmpty();
                return hasMac && hasIen;
            })
            .map(EdpSerialNo::getSerialNum)
            .collect(Collectors.toList());
    }
    
    /**
     * Get statistics about EDP serial numbers
     */
    public EdpStatistics getStatistics() {
        List<EdpSerialNo> allSerials = edpSerialNoRepository.findAll();
        
        long total = allSerials.size();
        long withMac = allSerials.stream()
            .filter(s -> (s.getUpdatedMac() != null && !s.getUpdatedMac().isEmpty()) ||
                        (s.getMacAddress() != null && !s.getMacAddress().isEmpty()))
            .count();
        long withIen = allSerials.stream()
            .filter(s -> s.getIen() != null && !s.getIen().isEmpty())
            .count();
        long validForSztp = allSerials.stream()
            .filter(s -> {
                boolean hasMac = (s.getUpdatedMac() != null && !s.getUpdatedMac().isEmpty()) ||
                                (s.getMacAddress() != null && !s.getMacAddress().isEmpty());
                boolean hasIen = s.getIen() != null && !s.getIen().isEmpty();
                return hasMac && hasIen;
            })
            .count();
        
        return new EdpStatistics(total, withMac, withIen, validForSztp);
    }
    
    /**
     * Helper method to map EDP entity to gRPC SerialDto.GetSerialResponse
     */
    private SerialDto.GetSerialResponse mapToSerialResponse(EdpSerialNo edpSerial) {
        SerialDto.GetSerialResponse response = new SerialDto.GetSerialResponse();
        
        // Map MAC address (prefer updated MAC)
        String macAddr = edpSerial.getUpdatedMac() != null ? 
            edpSerial.getUpdatedMac() : edpSerial.getMacAddress();
        response.setMacAddr(macAddr);
        
        // Map model/item description
        response.setModel(edpSerial.getModel() != null ? 
            edpSerial.getModel() : edpSerial.getItemDesc());
        
        // Set status as active if present in database
        response.setStatus("active");
        
        // Note: publicKeyDer and groupIds are not available in EDP data
        // These would need to be fetched from SZTP backend if needed
        
        log.debug("Mapped serial {} to response with MAC: {}, Model: {}", 
            edpSerial.getSerialNum(), macAddr, response.getModel());
        
        return response;
    }
    
    /**
     * Statistics class for EDP serial number data
     */
    public static class EdpStatistics {
        private final long totalSerials;
        private final long serialsWithMac;
        private final long serialsWithIen;
        private final long validForSztp;
        
        public EdpStatistics(long totalSerials, long serialsWithMac, 
                           long serialsWithIen, long validForSztp) {
            this.totalSerials = totalSerials;
            this.serialsWithMac = serialsWithMac;
            this.serialsWithIen = serialsWithIen;
            this.validForSztp = validForSztp;
        }
        
        public long getTotalSerials() { return totalSerials; }
        public long getSerialsWithMac() { return serialsWithMac; }
        public long getSerialsWithIen() { return serialsWithIen; }
        public long getValidForSztp() { return validForSztp; }
        
        @Override
        public String toString() {
            return String.format("EDP Statistics: Total=%d, WithMAC=%d, WithIEN=%d, ValidForSZTP=%d",
                totalSerials, serialsWithMac, serialsWithIen, validForSztp);
        }
    }
}
