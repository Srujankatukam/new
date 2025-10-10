package com.nokia.grpc2sztp.dto;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for User API requests and responses
 */
public class UserDto {
    private String username;
    @SerializedName("user_type")
    private String userType; // "USER" or "SERVICE_ACCOUNT"
    @SerializedName("org_id")
    private String orgId;
    @SerializedName("user_role")
    private String userRole; // "ADMIN", "ASSIGNER", "REQUESTOR", etc.
    
    public UserDto() {}
    
    public UserDto(String username, String userType, String orgId, String userRole) {
        this.username = username;
        this.userType = userType;
        this.orgId = orgId;
        this.userRole = userRole;
    }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
    public String getOrgId() { return orgId; }
    public void setOrgId(String orgId) { this.orgId = orgId; }
    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
}