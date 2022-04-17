package com.bsep.admin.app.dto;

public class SubjectDto {
    private String email;
    private String commonName;
    private String organization;
    private String organizationUnit;
    private String givenName;
    private String surname;
    private String country;
    private String userId;

    public SubjectDto(){}

    public SubjectDto(String email, String commonName, String organization, String organizationUnit, String givenName, String surname, String country, String userId) {
        this.email = email;
        this.commonName = commonName;
        this.organization = organization;
        this.organizationUnit = organizationUnit;
        this.givenName = givenName;
        this.surname = surname;
        this.country = country;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}