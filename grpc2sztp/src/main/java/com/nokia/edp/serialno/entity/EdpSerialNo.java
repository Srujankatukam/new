package com.nokia.edp.serialno.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity class representing EDP_SERIALNO table in the target SQL Server database
 */
@Entity
@Table(name = "EDP_SERIALNO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EdpSerialNo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SERIAL_NUM", nullable = false, length = 100)
    private String serialNum;

    @Column(name = "ITEM_DESC", length = 255)
    private String itemDesc;

    @Column(name = "MAC_ADDRESS", length = 50)
    private String macAddress;

    @Column(name = "UPDATED_MAC", length = 50)
    private String updatedMac;

    @Column(name = "IEN", length = 20)
    private String ien;

    @Column(name = "MODEL", length = 255)
    private String model;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
