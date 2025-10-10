package com.nokia.grpc2sztp;

import com.nokia.grpc2sztp.config.ConfigurationManager;
import com.nokia.grpc2sztp.interceptor.AuthenticationInterceptor;
import com.nokia.grpc2sztp.service.LoginServiceImpl;
import com.nokia.grpc2sztp.service.OwnershipVoucherServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Main Spring Boot application class that starts both gRPC server and EDP Serial Number Service
 * This integrated application provides:
 * 1. gRPC services for SZTP (LoginService, OwnershipVoucherService)
 * 2. EDP Serial Number synchronization from FRNG/MAC databases
 * 3. RESTful endpoints via Spring Boot (if needed)
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.nokia.grpc2sztp", "com.nokia.edp.serialno"})
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    
    private Server server;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    /**
     * Start gRPC server when Spring Boot application starts
     */
    @Bean
    public CommandLineRunner startGrpcServer() {
        return args -> {
            ConfigurationManager config = ConfigurationManager.getInstance();
            int port = config.getGrpcPort();
            
            // Create LoginService instance
            LoginServiceImpl loginService = new LoginServiceImpl();
            
            server = ServerBuilder.forPort(port)
                .intercept(new AuthenticationInterceptor())
                .addService(loginService)
                .addService(new OwnershipVoucherServiceImpl())
                .build()
                .start();
                
            logger.info("gRPC Server started on port {}", port);
            System.out.println("\n=== Integrated gRPC SZTP + EDP Service Started ===");
            System.out.println("gRPC Server running on port: " + port);
            System.out.println("Spring Boot HTTP Server running on port: 8080");
            System.out.println();
            System.out.println("Available gRPC services:");
            System.out.println("  - login.v1.LoginService");
            System.out.println("  - ovgs.v1.OwnershipVoucherService");
            System.out.println();
            System.out.println("EDP Serial Number Service:");
            System.out.println("  - Scheduled synchronization from FRNG/MAC databases");
            System.out.println("  - Target: EDP_SERIALNO table");
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
            System.out.println("=================================================\n");
            
            // Add shutdown hook for gRPC server
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                logger.info("*** Shutting down gRPC server since JVM is shutting down");
                System.err.println("*** Shutting down gRPC server since JVM is shutting down");
                try {
                    if (server != null) {
                        server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
                    }
                } catch (InterruptedException e) {
                    logger.error("Error during server shutdown", e);
                    e.printStackTrace(System.err);
                }
                logger.info("*** Server shut down");
                System.err.println("*** Server shut down");
            }));
            
            // Block and wait for gRPC server termination
            if (server != null) {
                server.awaitTermination();
            }
        };
    }
}
