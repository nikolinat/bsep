package com.bsep.admin.app.dto;

public class ExtensionDto {

    private String extensionName;
    private String value;

    public ExtensionDto() {
    }

    public ExtensionDto(String extensionName, String value) {
        this.extensionName = extensionName;
        this.value = value;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
