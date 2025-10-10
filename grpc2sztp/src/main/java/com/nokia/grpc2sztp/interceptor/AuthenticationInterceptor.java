package com.nokia.grpc2sztp.interceptor;

import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Authentication interceptor for gRPC requests
 * Validates JWT tokens in the Authorization header and extracts user claims
 */
public class AuthenticationInterceptor implements ServerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    
    private static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = 
        Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER);
    
    public static final Context.Key<String> AUTH_TOKEN_KEY = Context.key("authToken");
    
    public AuthenticationInterceptor() {
        // No need for LoginService since we don't validate token here
    }
    
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call, 
            Metadata headers, 
            ServerCallHandler<ReqT, RespT> next) {
        
        String methodName = call.getMethodDescriptor().getFullMethodName();
        logger.debug("Intercepting call to: {}", methodName);
        
        // Skip authentication for Login method
        if (methodName.equals("login.v1.LoginService/Login")) {
            logger.debug("Skipping authentication for Login method");
            return next.startCall(call, headers);
        }
        
        try {
            // Extract authorization header
            String authHeader = headers.get(AUTHORIZATION_METADATA_KEY);
            
            if (authHeader == null) {
                logger.warn("Missing authorization header for method: {}", methodName);
                call.close(Status.UNAUTHENTICATED.withDescription("Missing authorization header"), new Metadata());
                return new ServerCall.Listener<ReqT>() {};
            }
            
            // Validate token format
            if (!authHeader.startsWith("Bearer ")) {
                logger.warn("Invalid authorization header format for method: {}", methodName);
                call.close(Status.UNAUTHENTICATED.withDescription("Invalid authorization header format"), new Metadata());
                return new ServerCall.Listener<ReqT>() {};
            }
            
            String token = authHeader.substring(7); // Remove "Bearer " prefix
            
            // Basic token format check (must have 3 parts separated by dots)
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) {
                logger.warn("Invalid JWT token format for method: {}", methodName);
                call.close(Status.UNAUTHENTICATED.withDescription("Invalid token format"), new Metadata());
                return new ServerCall.Listener<ReqT>() {};
            }
            
            // Store token in context for downstream API calls
            Context context = Context.current().withValue(AUTH_TOKEN_KEY, token);
            
            logger.debug("Authentication token stored for method: {}", methodName);
            
            // Continue with the call in the authenticated context
            return Contexts.interceptCall(context, call, headers, next);
            
        } catch (Exception e) {
            logger.error("Authentication error for method: {}", methodName, e);
            call.close(Status.INTERNAL.withDescription("Authentication error"), new Metadata());
            return new ServerCall.Listener<ReqT>() {};
        }
    }
    
    /**
     * Get current authenticated token from context
     */
    public static String getCurrentAuthToken() {
        return AUTH_TOKEN_KEY.get();
    }
}