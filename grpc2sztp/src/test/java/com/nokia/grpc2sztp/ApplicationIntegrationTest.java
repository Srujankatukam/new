package com.nokia.grpc2sztp;

import com.nokia.grpc2sztp.config.ConfigurationManager;
import com.nokia.grpc2sztp.service.LoginServiceImpl;
import com.nokia.grpc2sztp.service.OwnershipVoucherServiceImpl;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import login.v1.AuthRequest;
import login.v1.LoginServiceGrpc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ovgs.v1.CreateGroupRequest;
import ovgs.v1.OwnershipVoucherServiceGrpc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ApplicationIntegrationTest {

    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private LoginServiceGrpc.LoginServiceBlockingStub loginStub;
    private OwnershipVoucherServiceGrpc.OwnershipVoucherServiceBlockingStub ownershipStub;

    @BeforeEach
    void setUp() throws IOException {
        // Generate a unique in-process server name
        String serverName = InProcessServerBuilder.generateName();

        // Create the server
        Server server = InProcessServerBuilder
                .forName(serverName)
                .directExecutor()
                .addService(new LoginServiceImpl())
                .addService(new OwnershipVoucherServiceImpl())
                .build()
                .start();

        grpcCleanup.register(server);

        // Create clients
        loginStub = LoginServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName)
                        .directExecutor()
                        .build()));

        ownershipStub = OwnershipVoucherServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName)
                        .directExecutor()
                        .build()));
    }

    @Test
    void applicationConfiguration_IsLoaded() {
        // Act
        ConfigurationManager config = ConfigurationManager.getInstance();

        // Assert
        assertNotNull(config);
        assertNotNull(config.getSztpApiBaseUrl());
        assertTrue(config.getGrpcPort() > 0);
        assertTrue(config.getApiTimeoutSeconds() > 0);
        assertTrue(config.getRetryAttempts() > 0);
    }

    @Test
    void grpcServices_AreRegistered() {
        // Test that services can handle requests (even if they fail due to missing API)
        
        // Test LoginService
        assertThrows(Exception.class, () -> {
            AuthRequest request = AuthRequest.newBuilder()
                    .setUsername("")  // Invalid request should fail
                    .setPassword("pass")
                    .setOrgId("org")
                    .build();
            loginStub.login(request);
        });

        // Test OwnershipVoucherService
        assertThrows(Exception.class, () -> {
            CreateGroupRequest request = CreateGroupRequest.newBuilder()
                    .setParent("")  // Invalid request should fail
                    .setDescription("desc")
                    .build();
            ownershipStub.createGroup(request);
        });
    }

    @Test
    void services_HandleValidationCorrectly() {
        // Test LoginService validation
        try {
            AuthRequest request = AuthRequest.newBuilder()
                    .setUsername("")  // Empty username should trigger validation
                    .setPassword("password")
                    .setOrgId("org")
                    .build();
            loginStub.login(request);
            fail("Should have thrown exception for empty username");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("INVALID_ARGUMENT") || 
                      e.getMessage().contains("Username is required"));
        }

        // Test OwnershipVoucherService validation
        try {
            CreateGroupRequest request = CreateGroupRequest.newBuilder()
                    .setParent("")  // Empty parent should trigger validation
                    .setDescription("description")
                    .build();
            ownershipStub.createGroup(request);
            fail("Should have thrown exception for empty parent");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("INVALID_ARGUMENT") ||
                      e.getMessage().contains("Parent group ID is required"));
        }
    }

    @Test
    void configurationManager_SingletonBehavior() {
        // Act
        ConfigurationManager instance1 = ConfigurationManager.getInstance();
        ConfigurationManager instance2 = ConfigurationManager.getInstance();

        // Assert
        assertSame(instance1, instance2, "ConfigurationManager should be singleton");
    }

    @Test
    void apiEndpoints_AreConfigured() {
        // Act
        ConfigurationManager config = ConfigurationManager.getInstance();
        String loginEndpoint = config.getApiEndpoint("login");
        String groupsEndpoint = config.getApiEndpoint("groups");

        // Assert
        assertNotNull(loginEndpoint);
        assertNotNull(groupsEndpoint);
        assertTrue(loginEndpoint.contains("/api/login"));
        assertTrue(groupsEndpoint.contains("/api/groups"));
        assertNotEquals(loginEndpoint, groupsEndpoint);
    }
}