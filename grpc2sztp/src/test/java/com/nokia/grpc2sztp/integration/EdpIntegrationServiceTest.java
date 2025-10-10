package com.nokia.grpc2sztp.integration;

import com.nokia.edp.serialno.entity.EdpSerialNo;
import com.nokia.edp.serialno.repository.EdpSerialNoRepository;
import com.nokia.grpc2sztp.dto.ComponentDto;
import com.nokia.grpc2sztp.dto.SerialDto;
import com.nokia.grpc2sztp.service.EdpIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Integration tests for EdpIntegrationService
 * Tests the bridge between EDP data and gRPC services
 */
@ExtendWith(MockitoExtension.class)
class EdpIntegrationServiceTest {

    @Mock
    private EdpSerialNoRepository edpSerialNoRepository;

    @InjectMocks
    private EdpIntegrationService edpIntegrationService;

    private EdpSerialNo testSerial;

    @BeforeEach
    void setUp() {
        testSerial = EdpSerialNo.builder()
                .id(1L)
                .serialNum("TEST-SERIAL-123")
                .itemDesc("Test Item Description")
                .macAddress("00:11:22:33:44:55")
                .updatedMac("00:11:22:33:44:56")
                .ien("6527")
                .model("Test Model 3HE17011AB")
                .createdDate(LocalDateTime.now())
                .updatedDate(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetSerialInfo_Success() {
        // Given
        when(edpSerialNoRepository.findBySerialNum("TEST-SERIAL-123"))
                .thenReturn(Optional.of(testSerial));

        // When
        Optional<SerialDto.GetSerialResponse> result = 
            edpIntegrationService.getSerialInfo("TEST-SERIAL-123");

        // Then
        assertTrue(result.isPresent());
        SerialDto.GetSerialResponse response = result.get();
        assertEquals("00:11:22:33:44:56", response.getMacAddr()); // Should use updatedMac
        assertEquals("Test Model 3HE17011AB", response.getModel());
        assertEquals("active", response.getStatus());
        
        verify(edpSerialNoRepository, times(1)).findBySerialNum("TEST-SERIAL-123");
    }

    @Test
    void testGetSerialInfo_NotFound() {
        // Given
        when(edpSerialNoRepository.findBySerialNum("NON-EXISTENT"))
                .thenReturn(Optional.empty());

        // When
        Optional<SerialDto.GetSerialResponse> result = 
            edpIntegrationService.getSerialInfo("NON-EXISTENT");

        // Then
        assertFalse(result.isPresent());
        verify(edpSerialNoRepository, times(1)).findBySerialNum("NON-EXISTENT");
    }

    @Test
    void testGetSerialInfoWithIen_Success() {
        // Given
        when(edpSerialNoRepository.findBySerialNum("TEST-SERIAL-123"))
                .thenReturn(Optional.of(testSerial));

        // When
        Optional<SerialDto.GetSerialResponse> result = 
            edpIntegrationService.getSerialInfo("6527", "TEST-SERIAL-123");

        // Then
        assertTrue(result.isPresent());
        verify(edpSerialNoRepository, times(1)).findBySerialNum("TEST-SERIAL-123");
    }

    @Test
    void testGetSerialInfoWithIen_IenMismatch() {
        // Given
        when(edpSerialNoRepository.findBySerialNum("TEST-SERIAL-123"))
                .thenReturn(Optional.of(testSerial));

        // When - different IEN
        Optional<SerialDto.GetSerialResponse> result = 
            edpIntegrationService.getSerialInfo("9999", "TEST-SERIAL-123");

        // Then
        assertFalse(result.isPresent());
        verify(edpSerialNoRepository, times(1)).findBySerialNum("TEST-SERIAL-123");
    }

    @Test
    void testSerialExists_True() {
        // Given
        when(edpSerialNoRepository.existsBySerialNum("TEST-SERIAL-123"))
                .thenReturn(true);

        // When
        boolean result = edpIntegrationService.serialExists("TEST-SERIAL-123");

        // Then
        assertTrue(result);
        verify(edpSerialNoRepository, times(1)).existsBySerialNum("TEST-SERIAL-123");
    }

    @Test
    void testSerialExists_False() {
        // Given
        when(edpSerialNoRepository.existsBySerialNum("NON-EXISTENT"))
                .thenReturn(false);

        // When
        boolean result = edpIntegrationService.serialExists("NON-EXISTENT");

        // Then
        assertFalse(result);
        verify(edpSerialNoRepository, times(1)).existsBySerialNum("NON-EXISTENT");
    }

    @Test
    void testGetAllSerialNumbers() {
        // Given
        List<String> serialNumbers = Arrays.asList("SERIAL-1", "SERIAL-2", "SERIAL-3");
        when(edpSerialNoRepository.findAllDistinctSerialNums())
                .thenReturn(serialNumbers);

        // When
        List<String> result = edpIntegrationService.getAllSerialNumbers();

        // Then
        assertEquals(3, result.size());
        assertTrue(result.contains("SERIAL-1"));
        assertTrue(result.contains("SERIAL-2"));
        assertTrue(result.contains("SERIAL-3"));
        verify(edpSerialNoRepository, times(1)).findAllDistinctSerialNums();
    }

    @Test
    void testGetComponentDto_Success() {
        // Given
        when(edpSerialNoRepository.findBySerialNum("TEST-SERIAL-123"))
                .thenReturn(Optional.of(testSerial));

        // When
        Optional<ComponentDto> result = 
            edpIntegrationService.getComponentDto("TEST-SERIAL-123");

        // Then
        assertTrue(result.isPresent());
        ComponentDto component = result.get();
        assertEquals("TEST-SERIAL-123", component.getSerialNumber());
        assertEquals("6527", component.getIen());
        assertEquals("Test Model 3HE17011AB", component.getModel());
        assertEquals("00:11:22:33:44:56", component.getMacAddr()); // updatedMac preferred
        
        verify(edpSerialNoRepository, times(1)).findBySerialNum("TEST-SERIAL-123");
    }

    @Test
    void testGetComponentDto_NotFound() {
        // Given
        when(edpSerialNoRepository.findBySerialNum("NON-EXISTENT"))
                .thenReturn(Optional.empty());

        // When
        Optional<ComponentDto> result = 
            edpIntegrationService.getComponentDto("NON-EXISTENT");

        // Then
        assertFalse(result.isPresent());
        verify(edpSerialNoRepository, times(1)).findBySerialNum("NON-EXISTENT");
    }

    @Test
    void testIsValidForSztp_Valid() {
        // Given - serial with both MAC and IEN
        when(edpSerialNoRepository.findBySerialNum("TEST-SERIAL-123"))
                .thenReturn(Optional.of(testSerial));

        // When
        boolean result = edpIntegrationService.isValidForSztp("TEST-SERIAL-123");

        // Then
        assertTrue(result);
        verify(edpSerialNoRepository, times(1)).findBySerialNum("TEST-SERIAL-123");
    }

    @Test
    void testIsValidForSztp_NoMac() {
        // Given - serial without MAC address
        EdpSerialNo serialNoMac = EdpSerialNo.builder()
                .serialNum("NO-MAC-SERIAL")
                .ien("6527")
                .build();
        when(edpSerialNoRepository.findBySerialNum("NO-MAC-SERIAL"))
                .thenReturn(Optional.of(serialNoMac));

        // When
        boolean result = edpIntegrationService.isValidForSztp("NO-MAC-SERIAL");

        // Then
        assertFalse(result);
        verify(edpSerialNoRepository, times(1)).findBySerialNum("NO-MAC-SERIAL");
    }

    @Test
    void testIsValidForSztp_NoIen() {
        // Given - serial without IEN
        EdpSerialNo serialNoIen = EdpSerialNo.builder()
                .serialNum("NO-IEN-SERIAL")
                .macAddress("00:11:22:33:44:55")
                .build();
        when(edpSerialNoRepository.findBySerialNum("NO-IEN-SERIAL"))
                .thenReturn(Optional.of(serialNoIen));

        // When
        boolean result = edpIntegrationService.isValidForSztp("NO-IEN-SERIAL");

        // Then
        assertFalse(result);
        verify(edpSerialNoRepository, times(1)).findBySerialNum("NO-IEN-SERIAL");
    }

    @Test
    void testIsValidForSztp_NotFound() {
        // Given
        when(edpSerialNoRepository.findBySerialNum("NON-EXISTENT"))
                .thenReturn(Optional.empty());

        // When
        boolean result = edpIntegrationService.isValidForSztp("NON-EXISTENT");

        // Then
        assertFalse(result);
        verify(edpSerialNoRepository, times(1)).findBySerialNum("NON-EXISTENT");
    }

    @Test
    void testGetValidSerialsForSztp() {
        // Given
        EdpSerialNo validSerial1 = EdpSerialNo.builder()
                .serialNum("VALID-1")
                .macAddress("00:11:22:33:44:55")
                .ien("6527")
                .build();
        
        EdpSerialNo validSerial2 = EdpSerialNo.builder()
                .serialNum("VALID-2")
                .updatedMac("00:11:22:33:44:66")
                .ien("6527")
                .build();
        
        EdpSerialNo invalidSerial = EdpSerialNo.builder()
                .serialNum("INVALID-1")
                .macAddress("00:11:22:33:44:77")
                // No IEN - invalid
                .build();
        
        List<EdpSerialNo> allSerials = Arrays.asList(validSerial1, validSerial2, invalidSerial);
        when(edpSerialNoRepository.findAll()).thenReturn(allSerials);

        // When
        List<String> result = edpIntegrationService.getValidSerialsForSztp();

        // Then
        assertEquals(2, result.size());
        assertTrue(result.contains("VALID-1"));
        assertTrue(result.contains("VALID-2"));
        assertFalse(result.contains("INVALID-1"));
        verify(edpSerialNoRepository, times(1)).findAll();
    }

    @Test
    void testGetStatistics() {
        // Given
        EdpSerialNo serial1 = EdpSerialNo.builder()
                .serialNum("SERIAL-1")
                .macAddress("00:11:22:33:44:55")
                .ien("6527")
                .build();
        
        EdpSerialNo serial2 = EdpSerialNo.builder()
                .serialNum("SERIAL-2")
                .updatedMac("00:11:22:33:44:66")
                .ien("6527")
                .build();
        
        EdpSerialNo serial3 = EdpSerialNo.builder()
                .serialNum("SERIAL-3")
                .ien("6527")
                // No MAC
                .build();
        
        EdpSerialNo serial4 = EdpSerialNo.builder()
                .serialNum("SERIAL-4")
                .macAddress("00:11:22:33:44:77")
                // No IEN
                .build();
        
        List<EdpSerialNo> allSerials = Arrays.asList(serial1, serial2, serial3, serial4);
        when(edpSerialNoRepository.findAll()).thenReturn(allSerials);

        // When
        EdpIntegrationService.EdpStatistics stats = edpIntegrationService.getStatistics();

        // Then
        assertEquals(4, stats.getTotalSerials());
        assertEquals(3, stats.getSerialsWithMac()); // serial1, serial2, serial4
        assertEquals(3, stats.getSerialsWithIen()); // serial1, serial2, serial3
        assertEquals(2, stats.getValidForSztp()); // serial1, serial2 (have both MAC and IEN)
        
        verify(edpSerialNoRepository, times(1)).findAll();
    }

    @Test
    void testGetComponentDto_PreferUpdatedMacOverMacAddress() {
        // Given - serial with both macAddress and updatedMac
        when(edpSerialNoRepository.findBySerialNum("TEST-SERIAL-123"))
                .thenReturn(Optional.of(testSerial));

        // When
        Optional<ComponentDto> result = 
            edpIntegrationService.getComponentDto("TEST-SERIAL-123");

        // Then
        assertTrue(result.isPresent());
        ComponentDto component = result.get();
        // Should prefer updatedMac (00:11:22:33:44:56) over macAddress (00:11:22:33:44:55)
        assertEquals("00:11:22:33:44:56", component.getMacAddr());
    }

    @Test
    void testGetComponentDto_FallbackToMacAddressWhenNoUpdatedMac() {
        // Given - serial with only macAddress (no updatedMac)
        EdpSerialNo serialOnlyMac = EdpSerialNo.builder()
                .serialNum("ONLY-MAC")
                .macAddress("00:11:22:33:44:77")
                .ien("6527")
                .model("Test Model")
                .build();
        when(edpSerialNoRepository.findBySerialNum("ONLY-MAC"))
                .thenReturn(Optional.of(serialOnlyMac));

        // When
        Optional<ComponentDto> result = 
            edpIntegrationService.getComponentDto("ONLY-MAC");

        // Then
        assertTrue(result.isPresent());
        ComponentDto component = result.get();
        assertEquals("00:11:22:33:44:77", component.getMacAddr());
    }
}
