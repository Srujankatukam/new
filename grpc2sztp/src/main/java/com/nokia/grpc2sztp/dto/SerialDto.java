package com.nokia.grpc2sztp.dto;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * DTO for Serial/Component related API requests and responses
 */
public class SerialDto {
    
    public static class AddSerialRequest {
        @SerializedName("group_id")
        private String groupId;
        private ComponentDto component;
        
        public AddSerialRequest() {}
        
        public AddSerialRequest(String groupId, ComponentDto component) {
            this.groupId = groupId;
            this.component = component;
        }
        
        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        public ComponentDto getComponent() { return component; }
        public void setComponent(ComponentDto component) { this.component = component; }
    }
    
    public static class AddSerialResponse {
        private String status;
        private String message;
        
        public AddSerialResponse() {}
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class GetSerialResponse {
        @SerializedName("public_key_der")
        private String publicKeyDer; // TPM's endorsement key in ASN.1 DER encoded format
        @SerializedName("group_ids")
        private List<String> groupIds;
        private String model;
        @SerializedName("mac_addr")
        private String macAddr;
        private String status; // Keep this for internal use
        
        public GetSerialResponse() {}
        
        public String getPublicKeyDer() { return publicKeyDer; }
        public void setPublicKeyDer(String publicKeyDer) { this.publicKeyDer = publicKeyDer; }
        public List<String> getGroupIds() { return groupIds; }
        public void setGroupIds(List<String> groupIds) { this.groupIds = groupIds; }
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getMacAddr() { return macAddr; }
        public void setMacAddr(String macAddr) { this.macAddr = macAddr; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}