package com.bsep.admin.app.dto;

import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;

public class DirectoryNameGeneralName {
    private SubjectAlternativeName subjectAlternativeName;
    private String value;

    public DirectoryNameGeneralName(SubjectAlternativeName subjectAlternativeName, String value) {
        this.subjectAlternativeName = subjectAlternativeName;
        this.value = value;
    }

    public DirectoryNameGeneralName() {

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
