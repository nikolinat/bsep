package com.bsep.admin.app.model;

import javax.persistence.*;

@Entity
@Table(name = "certificates")
public class RevokedCertificate {

    @Id
    @Column(name = "serialNumber", nullable = false)
    private Long serialNumber;

    @Column(name = "reason", nullable = false)
    private String reason;

    public RevokedCertificate() {

    }

    public RevokedCertificate(Long serialNumber, String reason) {
        this.serialNumber = serialNumber;
        this.reason = reason;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

