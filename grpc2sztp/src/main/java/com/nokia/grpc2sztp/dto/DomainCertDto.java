package com.nokia.grpc2sztp.dto;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for Domain Certificate API requests and responses
 */
public class DomainCertDto {
    
    public static class CreateDomainCertRequest {
        @SerializedName("group_id")
        private String groupId;
        @SerializedName("certificate_der")
        private String certificateDer; // Base64 encoded certificate
        @SerializedName("revocation_checks")
        private boolean revocationChecks;
        @SerializedName("expiry_time")
        private String expiryTime; // ISO timestamp string
        
        public CreateDomainCertRequest() {}
        
        public CreateDomainCertRequest(String groupId, String certificateDer, boolean revocationChecks, String expiryTime) {
            this.groupId = groupId;
            this.certificateDer = certificateDer;
            this.revocationChecks = revocationChecks;
            this.expiryTime = expiryTime;
        }
        
        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        
        public String getCertificateDer() { return certificateDer; }
        public void setCertificateDer(String certificateDer) { this.certificateDer = certificateDer; }
        
        public boolean isRevocationChecks() { return revocationChecks; }
        public void setRevocationChecks(boolean revocationChecks) { this.revocationChecks = revocationChecks; }
        
        public String getExpiryTime() { return expiryTime; }
        public void setExpiryTime(String expiryTime) { this.expiryTime = expiryTime; }
    }
    
    public static class CreateDomainCertResponse {
        @SerializedName("cert_id")
        private String certId;
        private String status;
        private String message;
        
        public CreateDomainCertResponse() {}
        
        public String getCertId() { return certId; }
        public void setCertId(String certId) { this.certId = certId; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
    
    public static class GetDomainCertRequest {
        @SerializedName("cert_id")
        private String certId;
        
        public GetDomainCertRequest() {}
        
        public GetDomainCertRequest(String certId) {
            this.certId = certId;
        }
        
        public String getCertId() { return certId; }
        public void setCertId(String certId) { this.certId = certId; }
    }
    
    public static class GetDomainCertResponse {
        @SerializedName("cert_id")
        private String certId;
        @SerializedName("group_id")
        private String groupId;
        @SerializedName("certificate_der")
        private String certificateDer; // Base64 encoded certificate
        @SerializedName("revocation_checks")
        private boolean revocationChecks;
        @SerializedName("expiry_time")
        private String expiryTime;
        
        public GetDomainCertResponse() {}
        
        public String getCertId() { return certId; }
        public void setCertId(String certId) { this.certId = certId; }
        
        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        
        public String getCertificateDer() { return certificateDer; }
        public void setCertificateDer(String certificateDer) { this.certificateDer = certificateDer; }
        
        public boolean isRevocationChecks() { return revocationChecks; }
        public void setRevocationChecks(boolean revocationChecks) { this.revocationChecks = revocationChecks; }
        
        public String getExpiryTime() { return expiryTime; }
        public void setExpiryTime(String expiryTime) { this.expiryTime = expiryTime; }
    }
}