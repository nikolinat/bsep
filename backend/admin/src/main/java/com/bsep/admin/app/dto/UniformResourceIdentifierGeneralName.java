package com.bsep.admin.app.dto;

import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;

import javax.validation.constraints.Pattern;

public class UniformResourceIdentifierGeneralName {
    private SubjectAlternativeName subjectAlternativeName;
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
                message = "Invalid URI.")
    private String value;

    public UniformResourceIdentifierGeneralName(SubjectAlternativeName subjectAlternativeName, String value) {
        this.subjectAlternativeName = subjectAlternativeName;
        this.value = value;
    }

    public UniformResourceIdentifierGeneralName() {

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
