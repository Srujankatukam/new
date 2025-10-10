package com.nokia.grpc2sztp.integration;

import com.nokia.edp.serialno.config.DataSourceConfig;
import com.nokia.edp.serialno.entity.EdpSerialNo;
import com.nokia.edp.serialno.repository.EdpSerialNoRepository;
import com.nokia.edp.serialno.scheduler.EdpSerialNoScheduler;
import com.nokia.edp.serialno.service.EdpSerialNoService;
import com.nokia.edp.serialno.service.FrngDataService;
import com.nokia.edp.serialno.service.MacAddressService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test to verify all EDP components are accessible and properly integrated
 * Tests class loading, method accessibility, and integration points
 */
class EdpComponentsAccessibilityTest {

    @Test
    void testEdpSerialNoEntityIsAccessible() {
        // Verify entity class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.edp.serialno.entity.EdpSerialNo"));
        
        // Verify entity can be instantiated
        EdpSerialNo entity = EdpSerialNo.builder()
                .serialNum("TEST-123")
                .itemDesc("Test Item")
                .macAddress("00:11:22:33:44:55")
                .ien("6527")
                .model("Test Model")
                .build();
        
        assertNotNull(entity);
        assertEquals("TEST-123", entity.getSerialNum());
        assertEquals("00:11:22:33:44:55", entity.getMacAddress());
        assertEquals("6527", entity.getIen());
    }

    @Test
    void testEdpSerialNoRepositoryIsAccessible() {
        // Verify repository interface can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.edp.serialno.repository.EdpSerialNoRepository"));
        
        Class<?> repoClass = EdpSerialNoRepository.class;
        
        // Verify key methods exist
        assertDoesNotThrow(() -> repoClass.getMethod("findBySerialNum", String.class));
        assertDoesNotThrow(() -> repoClass.getMethod("existsBySerialNum", String.class));
        assertDoesNotThrow(() -> repoClass.getMethod("findAllDistinctSerialNums"));
        assertDoesNotThrow(() -> repoClass.getMethod("deleteBySerialNum", String.class));
    }

    @Test
    void testEdpSerialNoServiceIsAccessible() {
        // Verify service class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.edp.serialno.service.EdpSerialNoService"));
        
        Class<?> serviceClass = EdpSerialNoService.class;
        
        // Verify execute method exists
        assertDoesNotThrow(() -> serviceClass.getMethod("execute"));
        
        // Verify it's a Spring component
        assertTrue(serviceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    void testFrngDataServiceIsAccessible() {
        // Verify service class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.edp.serialno.service.FrngDataService"));
        
        Class<?> serviceClass = FrngDataService.class;
        
        // Verify key method exists
        assertDoesNotThrow(() -> serviceClass.getMethod("fetchSerialData"));
        
        // Verify it's a Spring component
        assertTrue(serviceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    void testMacAddressServiceIsAccessible() {
        // Verify service class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.edp.serialno.service.MacAddressService"));
        
        Class<?> serviceClass = MacAddressService.class;
        
        // Verify key method exists
        Method[] methods = serviceClass.getDeclaredMethods();
        boolean hasMacMethod = false;
        for (Method method : methods) {
            if (method.getName().contains("Mac") || method.getName().contains("mac")) {
                hasMacMethod = true;
                break;
            }
        }
        assertTrue(hasMacMethod);
        
        // Verify it's a Spring component
        assertTrue(serviceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    void testEdpSerialNoSchedulerIsAccessible() {
        // Verify scheduler class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.edp.serialno.scheduler.EdpSerialNoScheduler"));
        
        Class<?> schedulerClass = EdpSerialNoScheduler.class;
        
        // Verify it's a Spring component
        assertTrue(schedulerClass.isAnnotationPresent(org.springframework.stereotype.Component.class));
        
        // Check for scheduled methods
        Method[] methods = schedulerClass.getDeclaredMethods();
        boolean hasScheduledMethod = false;
        for (Method method : methods) {
            if (method.isAnnotationPresent(org.springframework.scheduling.annotation.Scheduled.class)) {
                hasScheduledMethod = true;
                break;
            }
        }
        assertTrue(hasScheduledMethod);
    }

    @Test
    void testDataSourceConfigIsAccessible() {
        // Verify config class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.edp.serialno.config.DataSourceConfig"));
        
        Class<?> configClass = DataSourceConfig.class;
        
        // Verify it's a Spring configuration
        assertTrue(configClass.isAnnotationPresent(org.springframework.context.annotation.Configuration.class));
        
        // Verify DataSource bean methods exist
        Method[] methods = configClass.getDeclaredMethods();
        boolean hasFrngDataSource = false;
        boolean hasMacDataSource = false;
        boolean hasTargetDataSource = false;
        
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.contains("frng") || methodName.contains("Frng")) {
                hasFrngDataSource = true;
            }
            if (methodName.contains("mac") || methodName.contains("Mac")) {
                hasMacDataSource = true;
            }
            if (methodName.contains("target") || methodName.contains("Target")) {
                hasTargetDataSource = true;
            }
        }
        
        assertTrue(hasFrngDataSource, "Should have FRNG datasource bean");
        assertTrue(hasMacDataSource, "Should have MAC datasource bean");
        assertTrue(hasTargetDataSource, "Should have Target datasource bean");
    }

    @Test
    void testEdpPackageStructure() {
        // Verify all expected classes in EDP package exist
        String[] expectedClasses = {
            "com.nokia.edp.serialno.entity.EdpSerialNo",
            "com.nokia.edp.serialno.repository.EdpSerialNoRepository",
            "com.nokia.edp.serialno.service.EdpSerialNoService",
            "com.nokia.edp.serialno.service.FrngDataService",
            "com.nokia.edp.serialno.service.MacAddressService",
            "com.nokia.edp.serialno.scheduler.EdpSerialNoScheduler",
            "com.nokia.edp.serialno.config.DataSourceConfig"
        };
        
        for (String className : expectedClasses) {
            assertDoesNotThrow(() -> Class.forName(className), 
                "Class " + className + " should be accessible");
        }
    }

    @Test
    void testEdpIntegrationServiceIsAccessible() {
        // Verify integration service class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.grpc2sztp.service.EdpIntegrationService"));
        
        Class<?> serviceClass = com.nokia.grpc2sztp.service.EdpIntegrationService.class;
        
        // Verify it's a Spring component
        assertTrue(serviceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
        
        // Verify key integration methods exist
        assertDoesNotThrow(() -> serviceClass.getMethod("getSerialInfo", String.class));
        assertDoesNotThrow(() -> serviceClass.getMethod("getSerialInfo", String.class, String.class));
        assertDoesNotThrow(() -> serviceClass.getMethod("serialExists", String.class));
        assertDoesNotThrow(() -> serviceClass.getMethod("getAllSerialNumbers"));
        assertDoesNotThrow(() -> serviceClass.getMethod("getComponentDto", String.class));
        assertDoesNotThrow(() -> serviceClass.getMethod("isValidForSztp", String.class));
        assertDoesNotThrow(() -> serviceClass.getMethod("getValidSerialsForSztp"));
        assertDoesNotThrow(() -> serviceClass.getMethod("getStatistics"));
    }

    @Test
    void testEdpEntityHasJpaAnnotations() {
        // Verify entity has proper JPA annotations
        Class<?> entityClass = EdpSerialNo.class;
        
        assertTrue(entityClass.isAnnotationPresent(jakarta.persistence.Entity.class));
        assertTrue(entityClass.isAnnotationPresent(jakarta.persistence.Table.class));
        
        // Verify table name
        jakarta.persistence.Table tableAnnotation = entityClass.getAnnotation(jakarta.persistence.Table.class);
        assertEquals("EDP_SERIALNO", tableAnnotation.name());
    }

    @Test
    void testEdpEntityBuilderPattern() {
        // Verify entity supports builder pattern (Lombok)
        assertDoesNotThrow(() -> {
            EdpSerialNo entity = EdpSerialNo.builder()
                    .id(1L)
                    .serialNum("BUILDER-TEST")
                    .itemDesc("Builder Test")
                    .macAddress("00:11:22:33:44:55")
                    .updatedMac("00:11:22:33:44:56")
                    .ien("6527")
                    .model("Test Model")
                    .createdDate(LocalDateTime.now())
                    .updatedDate(LocalDateTime.now())
                    .build();
            
            assertNotNull(entity);
            assertEquals("BUILDER-TEST", entity.getSerialNum());
            assertEquals(1L, entity.getId());
        });
    }

    @Test
    void testAllEdpServicesHaveProperAnnotations() {
        // Test EdpSerialNoService
        Class<?> edpServiceClass = EdpSerialNoService.class;
        assertTrue(edpServiceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
        
        // Test FrngDataService
        Class<?> frngServiceClass = FrngDataService.class;
        assertTrue(frngServiceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
        
        // Test MacAddressService
        Class<?> macServiceClass = MacAddressService.class;
        assertTrue(macServiceClass.isAnnotationPresent(org.springframework.stereotype.Service.class));
    }

    @Test
    void testEdpSerialNoRepositoryExtendsJpaRepository() {
        // Verify repository extends Spring Data JPA repository
        Class<?> repoClass = EdpSerialNoRepository.class;
        
        // Check if it extends JpaRepository (interface inheritance)
        boolean extendsJpaRepo = false;
        for (Class<?> iface : repoClass.getInterfaces()) {
            if (iface.getName().contains("JpaRepository") || 
                iface.getName().contains("Repository")) {
                extendsJpaRepo = true;
                break;
            }
        }
        
        // Also check through class hierarchy
        if (!extendsJpaRepo) {
            Class<?> current = repoClass;
            while (current != null && !extendsJpaRepo) {
                for (Class<?> iface : current.getInterfaces()) {
                    if (iface.getName().contains("Repository")) {
                        extendsJpaRepo = true;
                        break;
                    }
                }
                current = current.getSuperclass();
            }
        }
        
        assertTrue(extendsJpaRepo, "Repository should extend JpaRepository or a Repository interface");
    }
}
