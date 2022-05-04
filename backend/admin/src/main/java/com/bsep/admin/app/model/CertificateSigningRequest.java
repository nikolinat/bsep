package com.bsep.admin.app.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@SQLDelete(sql = "UPDATE certificate_signing_request SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class CertificateSigningRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    private String email;
    private String commonName;
    private String organization;
    private String organizationUnit;
    private String givenName;
    private String surname;
    private String country;
    private String userId;
    private boolean isAccepted;
    private String reasonForDeclining;
    private boolean deleted = Boolean.FALSE;
    private boolean isEmailVerified = Boolean.FALSE;

    public CertificateSigningRequest(String email, String commonName, String organization,
                                     String organizationUnit, String givenName, String surname, String country, String userId) {
        this.email = email;
        this.commonName = commonName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.givenName = givenName;
        this.surname = surname;
        this.country = country;
        this.userId = userId;
        this.isAccepted = false;
    }

    public CertificateSigningRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getReasonForDeclining() {
        return reasonForDeclining;
    }

    public void setReasonForDeclining(String reasonForDeclining) {
        this.reasonForDeclining = reasonForDeclining;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }
}
