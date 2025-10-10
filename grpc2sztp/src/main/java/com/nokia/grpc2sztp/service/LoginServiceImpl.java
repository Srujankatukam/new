package com.nokia.grpc2sztp.service;

import com.nokia.grpc2sztp.dto.AuthDto;
import com.nokia.grpc2sztp.exception.SztpApiException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import login.v1.AuthRequest;
import login.v1.AuthResponse;
import login.v1.LoginServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the LoginService gRPC service
 * Handles user authentication through SZTP API integration
 */
public class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    
    private final SZTPWrapperService sztpService;
    
    public LoginServiceImpl() {
        this.sztpService = SZTPWrapperService.getInstance();
    }
    
    @Override
    public void login(AuthRequest request, StreamObserver<AuthResponse> responseObserver) {
        logger.info("Login attempt for user: {} in org: {}", request.getUsername(), request.getOrgId());
        
        try {
            // Validate request
            if (request.getUsername().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Username is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getPassword().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Password is required")
                    .asRuntimeException());
                return;
            }
            
            if (request.getOrgId().trim().isEmpty()) {
                responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription("Organization ID is required")
                    .asRuntimeException());
                return;
            }
            
            // Create authentication request for SZTP API
            AuthDto.AuthRequest authRequest = new AuthDto.AuthRequest(
                request.getUsername(),
                request.getPassword(),
                request.getOrgId()
            );
            
            // Authenticate user through SZTP API
            AuthDto.AuthResponse authResponse = sztpService.authenticateUser(authRequest);
            
            if (authResponse == null || authResponse.getToken() == null) {
                logger.warn("Authentication failed for user: {} in org: {}", request.getUsername(), request.getOrgId());
                responseObserver.onError(Status.UNAUTHENTICATED
                    .withDescription("Invalid username or password")
                    .asRuntimeException());
                return;
            }
            
            // Create gRPC response
            AuthResponse response = AuthResponse.newBuilder()
                .setToken(authResponse.getToken())
                .build();
            
            logger.info("Successfully authenticated user: {} with role: {}", 
                request.getUsername(), authResponse.getRole());
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            
        } catch (SztpApiException e) {
            logger.error("SZTP API error during authentication for user: {}", request.getUsername(), e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Authentication service error: " + e.getMessage())
                .asRuntimeException());
        } catch (Exception e) {
            logger.error("Unexpected error during authentication for user: {}", request.getUsername(), e);
            responseObserver.onError(Status.INTERNAL
                .withDescription("Authentication service error: " + e.getMessage())
                .asRuntimeException());
        }
    }
}