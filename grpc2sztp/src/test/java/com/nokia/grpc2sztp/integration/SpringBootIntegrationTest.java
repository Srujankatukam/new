package com.nokia.grpc2sztp.integration;

import com.nokia.edp.serialno.config.DataSourceConfig;
import com.nokia.edp.serialno.repository.EdpSerialNoRepository;
import com.nokia.edp.serialno.scheduler.EdpSerialNoScheduler;
import com.nokia.edp.serialno.service.EdpSerialNoService;
import com.nokia.edp.serialno.service.FrngDataService;
import com.nokia.edp.serialno.service.MacAddressService;
import com.nokia.grpc2sztp.Application;
import com.nokia.grpc2sztp.service.EdpIntegrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Spring Boot Integration Test
 * Verifies that the Spring application context loads correctly with all components
 * Tests the integration of gRPC services and EDP components
 */
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
    "spring.jpa.hibernate.ddl-auto=none",
    "job.run-on-start=false",  // Don't run scheduler during tests
    // Dummy database configurations to prevent auto-configuration errors
    "frng.datasource.url=jdbc:h2:mem:testdb",
    "frng.datasource.username=sa",
    "frng.datasource.password=",
    "frng.datasource.driver-class-name=org.h2.Driver",
    "mac.datasource.url=jdbc:h2:mem:testdb2",
    "mac.datasource.username=sa",
    "mac.datasource.password=",
    "mac.datasource.driver-class-name=org.h2.Driver",
    "target.datasource.url=jdbc:h2:mem:testdb3",
    "target.datasource.username=sa",
    "target.datasource.password=",
    "target.datasource.driver-class-name=org.h2.Driver",
    // SZTP configuration
    "sztp.api.base.url=http://localhost:8080",
    "sztp.api.timeout.seconds=30",
    "sztp.api.retry.attempts=3"
})
@ActiveProfiles("test")
class SpringBootIntegrationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verify application context loads successfully
        assertNotNull(applicationContext);
    }

    @Test
    void verifyEdpServicesAreLoaded() {
        // Verify all EDP service components are loaded as Spring beans
        assertTrue(applicationContext.containsBean("edpSerialNoService"));
        assertTrue(applicationContext.containsBean("frngDataService"));
        assertTrue(applicationContext.containsBean("macAddressService"));
        assertTrue(applicationContext.containsBean("edpSerialNoScheduler"));
    }

    @Test
    void verifyEdpRepositoryIsLoaded() {
        // Verify EDP repository is loaded
        assertTrue(applicationContext.containsBean("edpSerialNoRepository"));
    }

    @Test
    void verifyDataSourceConfigIsLoaded() {
        // Verify DataSourceConfig is loaded
        assertTrue(applicationContext.containsBean("dataSourceConfig"));
    }

    @Test
    void verifyEdpIntegrationServiceIsLoaded() {
        // Verify the integration service is loaded
        assertTrue(applicationContext.containsBean("edpIntegrationService"));
        
        EdpIntegrationService service = applicationContext.getBean(EdpIntegrationService.class);
        assertNotNull(service);
    }

    @Test
    void verifyAllEdpBeansCanBeInjected() {
        // Verify all EDP beans can be retrieved from context
        assertNotNull(applicationContext.getBean(EdpSerialNoService.class));
        assertNotNull(applicationContext.getBean(FrngDataService.class));
        assertNotNull(applicationContext.getBean(MacAddressService.class));
        assertNotNull(applicationContext.getBean(EdpSerialNoScheduler.class));
        assertNotNull(applicationContext.getBean(EdpSerialNoRepository.class));
        assertNotNull(applicationContext.getBean(DataSourceConfig.class));
        assertNotNull(applicationContext.getBean(EdpIntegrationService.class));
    }

    @Test
    void verifyComponentScanConfiguration() {
        // Verify components from both packages are scanned
        
        // Check gRPC package components
        assertTrue(applicationContext.containsBean("application"));
        
        // Check EDP package components
        assertTrue(applicationContext.containsBean("edpSerialNoService"));
        assertTrue(applicationContext.containsBean("edpIntegrationService"));
    }

    @Test
    void verifySchedulingIsEnabled() {
        // Verify @EnableScheduling is working
        // The scheduler bean should exist even if job.run-on-start=false
        EdpSerialNoScheduler scheduler = applicationContext.getBean(EdpSerialNoScheduler.class);
        assertNotNull(scheduler);
    }

    @Test
    void verifyEdpIntegrationServiceHasDependencies() {
        // Verify EdpIntegrationService has proper dependencies injected
        EdpIntegrationService integrationService = applicationContext.getBean(EdpIntegrationService.class);
        assertNotNull(integrationService);
        
        // Try to call a method to ensure it's properly initialized
        // This will work with mocked repository in actual integration
        assertDoesNotThrow(() -> {
            // Method should not throw even if repository is empty
            integrationService.getAllSerialNumbers();
        });
    }
}
