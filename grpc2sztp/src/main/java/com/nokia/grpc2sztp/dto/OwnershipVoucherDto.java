package com.nokia.grpc2sztp.dto;

import java.time.Duration;

/**
 * DTO for Ownership Voucher API requests and responses
 */
public class OwnershipVoucherDto {
    
    public static class GetOwnershipVoucherRequest {
        private String cert_id;
        private ComponentDto component;
        private String lifetime; // Duration in ISO format or seconds
        
        public GetOwnershipVoucherRequest() {}
        
        public GetOwnershipVoucherRequest(String certId, ComponentDto component, String lifetime) {
            this.cert_id = certId;
            this.component = component;
            this.lifetime = lifetime;
        }
        
        public String getCertId() { return cert_id; }
        public void setCertId(String certId) { this.cert_id = certId; }
        
        public ComponentDto getComponent() { return component; }
        public void setComponent(ComponentDto component) { this.component = component; }
        
        public String getLifetime() { return lifetime; }
        public void setLifetime(String lifetime) { this.lifetime = lifetime; }
    }
    
    public static class GetOwnershipVoucherResponse {
        private String voucher_cms; // Base64 encoded voucher data
        private String public_key_der;

        public GetOwnershipVoucherResponse() {}
        
        public String getVoucherCms() { return voucher_cms; }
        public void setVoucherCms(String voucherCms) { this.voucher_cms = voucherCms; }
        
        public String getPublicKeyDer() { return public_key_der; }
        public void setPublicKeyDer(String publicKeyDer) { this.public_key_der = publicKeyDer; }
    }
}