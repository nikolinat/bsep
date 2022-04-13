package com.bsep.admin.app.model;

import com.bsep.admin.app.model.enums.CertificateStatus;

import javax.persistence.*;

@Entity
@Table(name = "certificates")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "serialNumber", unique = true, nullable = false)
    private String serialNumber;

    private CertificateStatus certificateStatus;

    private boolean isRoot;

    public Certificate() {

    }


    public Certificate(String serialNumber, CertificateStatus certificateStatus, boolean isRoot) {
        this.serialNumber = serialNumber;
        this.certificateStatus = certificateStatus;
        this.isRoot = isRoot;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public CertificateStatus getCertificateStatus() {
        return certificateStatus;
    }

    public void setCertificateStatus(CertificateStatus certificateStatus) {
        this.certificateStatus = certificateStatus;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }
}

