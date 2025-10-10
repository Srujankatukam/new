package com.nokia.grpc2sztp.util;

import com.nokia.grpc2sztp.interceptor.AuthenticationInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for easily accessing user context and claims from gRPC context
 * Provides convenient methods for common authentication and authorization operations
 * Also provides user context encryption/decryption functionality
 */
public class UserContextHelper {
    private static final Logger logger = LoggerFactory.getLogger(UserContextHelper.class);
    
    /**
     * User context data structure for JSON serialization
     */
    public static class UserContextData {
        private String username;
        private String orgId;
        private String orgRole;
        private boolean isSuperAdmin;
        private String sessionInfo;
        private long timestamp;
        
        public UserContextData() {}
        
        public UserContextData(String username, String orgId, String orgRole, 
                             boolean isSuperAdmin, String sessionInfo, long timestamp) {
            this.username = username;
            this.orgId = orgId;
            this.orgRole = orgRole;
            this.isSuperAdmin = isSuperAdmin;
            this.sessionInfo = sessionInfo;
            this.timestamp = timestamp;
        }
        
        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getOrgId() { return orgId; }
        public void setOrgId(String orgId) { this.orgId = orgId; }
        
        public String getOrgRole() { return orgRole; }
        public void setOrgRole(String orgRole) { this.orgRole = orgRole; }
        
        public boolean isSuperAdmin() { return isSuperAdmin; }
        public void setSuperAdmin(boolean superAdmin) { isSuperAdmin = superAdmin; }
        
        public String getSessionInfo() { return sessionInfo; }
        public void setSessionInfo(String sessionInfo) { this.sessionInfo = sessionInfo; }
        
        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
    }
    
    /**
     * Validate that the encrypted context is not too old (prevent replay attacks)
     */
    public static boolean isContextValid(UserContextData contextData, long maxAgeMillis) {
        if (contextData == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        long contextAge = currentTime - contextData.getTimestamp();
        
        if (contextAge > maxAgeMillis) {
            logger.warn("User context is too old: {} ms (max allowed: {} ms)", contextAge, maxAgeMillis);
            return false;
        }
        
        return true;
    }
    
    /**
     * Get current auth token from gRPC context
     */
    public static String getCurrentAuthToken() {
        return AuthenticationInterceptor.getCurrentAuthToken();
    }
}