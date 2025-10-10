package com.nokia.grpc2sztp.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Data Transfer Objects for Authentication operations
 */
public class AuthDto {
    
    /**
     * Request for user authentication
     */
    public static class AuthRequest {
        private String username;
        private String password;
        @SerializedName("org_id")
        private String orgId;
        
        public AuthRequest() {}
        
        public AuthRequest(String username, String password, String orgId) {
            this.username = username;
            this.password = password;
            this.orgId = orgId;
        }
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getOrgId() { return orgId; }
        public void setOrgId(String orgId) { this.orgId = orgId; }
    }
    
    /**
     * Response from user authentication
     */
    public static class AuthResponse {
        private String token;
        @SerializedName("user_id")
        private String userId;
        private String role;
        @SerializedName("expires_at")
        private long expiresAt;
        
        public AuthResponse() {}
        
        public AuthResponse(String token, String userId, String role, long expiresAt) {
            this.token = token;
            this.userId = userId;
            this.role = role;
            this.expiresAt = expiresAt;
        }
        
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
        
        public long getExpiresAt() { return expiresAt; }
        public void setExpiresAt(long expiresAt) { this.expiresAt = expiresAt; }
    }
}