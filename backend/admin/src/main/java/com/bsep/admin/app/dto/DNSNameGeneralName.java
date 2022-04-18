package com.bsep.admin.app.dto;

import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;
import sun.security.x509.DNSName;

import javax.validation.constraints.Pattern;

public class DNSNameGeneralName {
    private SubjectAlternativeName subjectAlternativeName;
    @Pattern(regexp =
            "\"^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\\\-]*[a-zA-Z0-9])\\\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\\\-]*[A-Za-z0-9])$\"",
            message = "Invalid DNS Name.")
    private String value;

    public DNSNameGeneralName(SubjectAlternativeName subjectAlternativeName, String value) {
        this.subjectAlternativeName = subjectAlternativeName;
        this.value = value;
    }

    public DNSNameGeneralName() {

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
