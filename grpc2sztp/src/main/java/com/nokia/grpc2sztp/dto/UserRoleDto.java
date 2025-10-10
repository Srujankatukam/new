package com.nokia.grpc2sztp.dto;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

/**
 * DTO for User Role related API requests and responses
 */
public class UserRoleDto {
    
    public static class UserRoleRequest {
        private String username;
        @SerializedName("user_type")
        private String userType;
        @SerializedName("org_id")
        private String orgId;
        @SerializedName("group_id")
        private String groupId;
        @SerializedName("user_role")
        private String userRole;
        
        public UserRoleRequest() {}
        
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getUserType() { return userType; }
        public void setUserType(String userType) { this.userType = userType; }
        public String getOrgId() { return orgId; }
        public void setOrgId(String orgId) { this.orgId = orgId; }
        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        public String getUserRole() { return userRole; }
        public void setUserRole(String userRole) { this.userRole = userRole; }
    }
    
    public static class UserRoleResponse {
        private String status;
        private String message;
        private Map<String, String> groups; // groupId -> role mapping
        
        public UserRoleResponse() {}
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Map<String, String> getGroups() { return groups; }
        public void setGroups(Map<String, String> groups) { this.groups = groups; }
    }
}