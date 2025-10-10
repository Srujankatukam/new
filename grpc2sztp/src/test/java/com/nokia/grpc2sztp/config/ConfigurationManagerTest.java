package com.nokia.grpc2sztp.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConfigurationManagerTest {

    private ConfigurationManager configManager;

    @BeforeEach
    void setUp() {
        configManager = ConfigurationManager.getInstance();
    }

    @Test
    void getInstance_ReturnsSingletonInstance() {
        // Act
        ConfigurationManager instance1 = ConfigurationManager.getInstance();
        ConfigurationManager instance2 = ConfigurationManager.getInstance();

        // Assert
        assertSame(instance1, instance2);
        assertNotNull(instance1);
    }

    @Test
    void getSztpApiBaseUrl_ReturnsDefaultValue() {
        // Act
        String baseUrl = configManager.getSztpApiBaseUrl();

        // Assert
        assertNotNull(baseUrl);
        assertFalse(baseUrl.trim().isEmpty());
        assertTrue(baseUrl.startsWith("http"));
    }

    @Test
    void getApiTimeoutSeconds_ReturnsPositiveValue() {
        // Act
        int timeout = configManager.getApiTimeoutSeconds();

        // Assert
        assertTrue(timeout > 0);
        assertTrue(timeout <= 300); // Should be reasonable timeout
    }

    @Test
    void getGrpcPort_ReturnsValidPort() {
        // Act
        int port = configManager.getGrpcPort();

        // Assert
        assertTrue(port > 0);
        assertTrue(port < 65536); // Valid port range
        assertTrue(port >= 1024); // Should not use system ports
    }

    @Test
    void getRetryAttempts_ReturnsPositiveValue() {
        // Act
        int retryAttempts = configManager.getRetryAttempts();

        // Assert
        assertTrue(retryAttempts > 0);
        assertTrue(retryAttempts <= 10); // Should be reasonable retry count
    }

    @Test
    void getApiEndpoint_ReturnsValidEndpoint() {
        // Act
        String endpoint = configManager.getApiEndpoint("test-method");

        // Assert
        assertNotNull(endpoint);
        assertFalse(endpoint.trim().isEmpty());
        assertTrue(endpoint.startsWith("http"));
        assertTrue(endpoint.contains("/api/test-method"));
    }

    @Test
    void configValues_AreConsistent() {
        // Act - call methods multiple times
        String baseUrl1 = configManager.getSztpApiBaseUrl();
        String baseUrl2 = configManager.getSztpApiBaseUrl();
        int port1 = configManager.getGrpcPort();
        int port2 = configManager.getGrpcPort();

        // Assert - should return same values
        assertEquals(baseUrl1, baseUrl2);
        assertEquals(port1, port2);
    }

    @Test
    void getApiEndpoint_WithDifferentMethods() {
        // Act
        String endpoint1 = configManager.getApiEndpoint("login");
        String endpoint2 = configManager.getApiEndpoint("groups");

        // Assert
        assertNotNull(endpoint1);
        assertNotNull(endpoint2);
        assertTrue(endpoint1.contains("/api/login"));
        assertTrue(endpoint2.contains("/api/groups"));
        assertNotEquals(endpoint1, endpoint2);
    }
}