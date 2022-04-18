package com.bsep.admin.app.dto;

import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;

import javax.validation.constraints.Pattern;

public class IPAddressGeneralName {
    private SubjectAlternativeName subjectAlternativeName;
    @Pattern(regexp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.\n" +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$", message = "Invalid IP Address.")
    private String value;

    public IPAddressGeneralName(SubjectAlternativeName subjectAlternativeName, String value) {
        this.subjectAlternativeName = subjectAlternativeName;
        this.value = value;
    }

    public IPAddressGeneralName() {

    }

    public SubjectAlternativeName getSubjectAlternativeName() {
        return subjectAlternativeName;
    }

    public void setSubjectAlternativeName(SubjectAlternativeName subjectAlternativeName) {
        this.subjectAlternativeName = subjectAlternativeName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
