package com.nokia.grpc2sztp;

import com.nokia.grpc2sztp.config.ConfigurationManager;
import com.nokia.grpc2sztp.interceptor.AuthenticationInterceptor;
import com.nokia.grpc2sztp.service.LoginServiceImpl;
import com.nokia.grpc2sztp.service.OwnershipVoucherServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Main application class that starts the gRPC server
 */
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    private Server server;
    private ConfigurationManager config;
    
    public static void main(String[] args) throws IOException, InterruptedException {
        final Application app = new Application();
        app.start();
        app.blockUntilShutdown();
    }
    
    private void start() throws IOException {
        config = ConfigurationManager.getInstance();
        int port = config.getGrpcPort();
        
        // Create LoginService instance to pass to AuthenticationInterceptor
        LoginServiceImpl loginService = new LoginServiceImpl();
        
        server = ServerBuilder.forPort(port)
            .intercept(new AuthenticationInterceptor())
            .addService(loginService)
            .addService(new OwnershipVoucherServiceImpl())
            .build()
            .start();
            
        logger.info("gRPC Server started on port {}", port);
        System.out.println("=== gRPC SZTP Server Started ===");
        System.out.println("Server running on port: " + port);
        System.out.println();
        System.out.println("Available services:");
        System.out.println("  - login.v1.LoginService");
        System.out.println("  - ovgs.v1.OwnershipVoucherService");
        System.out.println();
        System.out.println("Usage:");
        System.out.println("  1. First call Login to get JWT token");
        System.out.println("  2. Use the JWT token in Authorization header for other services");
        System.out.println("     Format: 'Authorization: Bearer <jwt_token>'");
        System.out.println();
        System.out.println("Integration with SZTP APIs:");
        System.out.println("  - Base URL: " + config.getSztpApiBaseUrl());
        System.out.println("  - Timeout: " + config.getApiTimeoutSeconds() + " seconds");
        System.out.println("  - Retry attempts: " + config.getRetryAttempts());
        System.out.println();
        System.out.println("Press Ctrl+C to stop the server");
        System.out.println("=====================================");
        
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                logger.info("*** Shutting down gRPC server since JVM is shutting down");
                System.err.println("*** Shutting down gRPC server since JVM is shutting down");
                try {
                    Application.this.stop();
                } catch (InterruptedException e) {
                    logger.error("Error during server shutdown", e);
                    e.printStackTrace(System.err);
                }
                logger.info("*** Server shut down");
                System.err.println("*** Server shut down");
            }
        });
    }
    
    private void stop() throws InterruptedException {
        if (server != null) {
            logger.info("Stopping gRPC server...");
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }
    
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
