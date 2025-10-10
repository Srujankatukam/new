package com.nokia.grpc2sztp.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * DTO for Group API requests and responses
 */
public class GroupDto {
    
    public static class CreateGroupRequest {
        private String parent;
        private String description;
        
        public CreateGroupRequest() {}
        
        public CreateGroupRequest(String parent, String description) {
            this.parent = parent;
            this.description = description;
        }
        
        public String getParent() { return parent; }
        public void setParent(String parent) { this.parent = parent; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }
    
    public static class CreateGroupResponse {
        @SerializedName("group_id")
        private String groupId;
        private String status;
        private String message;
        
        public CreateGroupResponse() {}
        
        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class GetGroupResponse {
        @SerializedName("group_id")
        private String groupId;
        private String description;
        @SerializedName("child_group_ids")
        private List<String> childGroupIds;
        private List<UserDto> users;
        @SerializedName("cert_ids")
        private List<String> certIds;
        private List<ComponentDto> components;
        
        public GetGroupResponse() {}
        
        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public List<String> getChildGroupIds() { return childGroupIds; }
        public void setChildGroupIds(List<String> childGroupIds) { this.childGroupIds = childGroupIds; }
        public List<UserDto> getUsers() { return users; }
        public void setUsers(List<UserDto> users) { this.users = users; }
        public List<String> getCertIds() { return certIds; }
        public void setCertIds(List<String> certIds) { this.certIds = certIds; }
        public List<ComponentDto> getComponents() { return components; }
        public void setComponents(List<ComponentDto> components) { this.components = components; }
    }
}