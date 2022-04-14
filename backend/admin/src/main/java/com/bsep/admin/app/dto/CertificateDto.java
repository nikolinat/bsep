package com.bsep.admin.app.dto;

import java.math.BigInteger;
import java.util.Date;

public class CertificateDto {

    private BigInteger serialNumber;
    private Date startDate;
    private Date endDate;
    private boolean isRoot;
    private boolean isIntermediate;

    public CertificateDto(BigInteger serialNumber,Date startDate, Date endDate, boolean isRoot, boolean isIntermediate) {
        this.serialNumber = serialNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isRoot = isRoot;
        this.isIntermediate = isIntermediate;
    }

    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public boolean isIntermediate() {
        return isIntermediate;
    }

    public void setIntermediate(boolean intermediate) {
        isIntermediate = intermediate;
    }
}
