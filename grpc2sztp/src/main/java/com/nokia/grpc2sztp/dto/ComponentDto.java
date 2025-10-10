package com.nokia.grpc2sztp.dto;
import com.google.gson.annotations.SerializedName;
/**
 * DTO for Component API requests and responses
 */
public class ComponentDto {
    private String ien;
    @SerializedName("serial_number")
    private String serialNumber;
    private String model;
    private String macAddr;
    private String groupId;
    
    public ComponentDto() {}
    
    public ComponentDto(String ien, String serialNumber) {
        this.ien = ien;
        this.serialNumber = serialNumber;
    }
    
    public ComponentDto(String ien, String serialNumber, String model, String macAddr) {
        this.ien = ien;
        this.serialNumber = serialNumber;
        this.model = model;
        this.macAddr = macAddr;
    }
    
    public String getIen() { return ien; }
    public void setIen(String ien) { this.ien = ien; }
    public String getSerialNumber() { return serialNumber; }
    public void setSerialNumber(String serialNumber) { this.serialNumber = serialNumber; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getMacAddr() { return macAddr; }
    public void setMacAddr(String macAddr) { this.macAddr = macAddr; }
    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
}