package com.bsep.admin.app.dto;

import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;

import javax.validation.constraints.Pattern;

public class RegisteredIDGeneralName {
    private SubjectAlternativeName subjectAlternativeName;
    @Pattern(regexp = "\"^[a-zA-Z0-9_-](?!.*::)[^%+\\\\\\\\/#'\\\"]+$\"", message = "Invalid Registered Id.")
    private String value;

    public RegisteredIDGeneralName(SubjectAlternativeName subjectAlternativeName, String value) {
        this.subjectAlternativeName = subjectAlternativeName;
        this.value = value;
    }

    public RegisteredIDGeneralName() {

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
