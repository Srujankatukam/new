package com.nokia.grpc2sztp.integration;

import com.nokia.grpc2sztp.Application;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validation test for application startup configuration
 * Tests Spring Boot annotations and startup configuration
 */
class ApplicationStartupValidationTest {

    @Test
    void testApplicationClassHasSpringBootAnnotation() {
        // Verify Application class has @SpringBootApplication
        assertTrue(Application.class.isAnnotationPresent(
            org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void testApplicationClassHasEnableScheduling() {
        // Verify Application class has @EnableScheduling for EDP scheduler
        assertTrue(Application.class.isAnnotationPresent(
            org.springframework.scheduling.annotation.EnableScheduling.class));
    }

    @Test
    void testApplicationClassHasComponentScan() {
        // Verify Application class has @ComponentScan
        assertTrue(Application.class.isAnnotationPresent(
            org.springframework.context.annotation.ComponentScan.class));
        
        // Verify it scans both packages
        org.springframework.context.annotation.ComponentScan annotation = 
            Application.class.getAnnotation(org.springframework.context.annotation.ComponentScan.class);
        
        String[] basePackages = annotation.basePackages();
        assertEquals(2, basePackages.length);
        
        // Check both packages are included
        boolean hasGrpcPackage = false;
        boolean hasEdpPackage = false;
        
        for (String pkg : basePackages) {
            if (pkg.equals("com.nokia.grpc2sztp")) hasGrpcPackage = true;
            if (pkg.equals("com.nokia.edp.serialno")) hasEdpPackage = true;
        }
        
        assertTrue(hasGrpcPackage, "Should scan com.nokia.grpc2sztp package");
        assertTrue(hasEdpPackage, "Should scan com.nokia.edp.serialno package");
    }

    @Test
    void testApplicationHasMainMethod() {
        // Verify main method exists
        assertDoesNotThrow(() -> {
            Method mainMethod = Application.class.getMethod("main", String[].class);
            assertNotNull(mainMethod);
            
            // Verify it's static
            assertTrue(java.lang.reflect.Modifier.isStatic(mainMethod.getModifiers()));
            
            // Verify it's public
            assertTrue(java.lang.reflect.Modifier.isPublic(mainMethod.getModifiers()));
            
            // Verify return type is void
            assertEquals(void.class, mainMethod.getReturnType());
        });
    }

    @Test
    void testApplicationHasStartGrpcServerBean() {
        // Verify startGrpcServer bean method exists
        assertDoesNotThrow(() -> {
            Method beanMethod = Application.class.getMethod("startGrpcServer");
            assertNotNull(beanMethod);
            
            // Verify it has @Bean annotation
            assertTrue(beanMethod.isAnnotationPresent(
                org.springframework.context.annotation.Bean.class));
        });
    }

    @Test
    void testApplicationClassIsAccessible() {
        // Verify Application class can be loaded
        assertDoesNotThrow(() -> Class.forName("com.nokia.grpc2sztp.Application"));
    }

    @Test
    void testApplicationCanBeInstantiated() {
        // Verify Application instance can be created
        assertDoesNotThrow(() -> {
            Application app = new Application();
            assertNotNull(app);
        });
    }
}
