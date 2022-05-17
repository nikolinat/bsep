package com.bsep.admin.app.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class CertificateDto {

    private BigInteger serialNumber;
    private String alias;
    private Date startDate;
    private Date endDate;
    private SubjectDto subject;
    private boolean isRoot;
    private boolean isIntermediate;
    private List<ExtensionDto> extensions;

    public CertificateDto() {}

    public CertificateDto(BigInteger serialNumber, String alias, Date startDate, Date endDate, SubjectDto subject, List<ExtensionDto> extensions, boolean isRoot, boolean isIntermediate) {
        this.serialNumber = serialNumber;
        this.alias = alias;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subject = subject;
        this.extensions = extensions;
        this.isRoot = isRoot;
        this.isIntermediate = isIntermediate;
    }

    public BigInteger getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(BigInteger serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public SubjectDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectDto subject) {
        this.subject = subject;
    }

    public List<ExtensionDto> getExtensions() {
        return extensions;
    }

    public void setExtensions(List<ExtensionDto> extensions) {
        this.extensions = extensions;
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