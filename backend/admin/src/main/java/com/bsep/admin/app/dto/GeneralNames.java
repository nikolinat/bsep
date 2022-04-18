package com.bsep.admin.app.dto;

<<<<<<< HEAD
=======
import com.bsep.admin.app.validators.constraints.DirectoryNameConstraint;
>>>>>>> f7ddb8c3bdad9e8630f940c296305357f8049a8c
import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;

import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

public class GeneralNames {
<<<<<<< HEAD
    private String directoryName;
=======

    @DirectoryNameConstraint
    private String directoryName;

>>>>>>> f7ddb8c3bdad9e8630f940c296305357f8049a8c
    @Pattern(regexp =
            "\"^(([a-zA-Z0-9]|[a-zA-Z0-9][a-zA-Z0-9\\\\-]*[a-zA-Z0-9])\\\\.)*([A-Za-z0-9]|[A-Za-z0-9][A-Za-z0-9\\\\-]*[A-Za-z0-9])$\"",
            message = "Invalid DNS Name.")
    private String dnsName;
    @Pattern(regexp = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.\n" +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$", message = "Invalid IP Address.")
    private String ipAddress;
    @Pattern(regexp = "\"^[a-zA-Z0-9_-](?!.*::)[^%+\\\\\\\\/#'\\\"]+$\"", message = "Invalid Registered Id.")
    private String registeredId;
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?" +
            ":[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])" +
            "*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4]" +
            "[0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-" +
            "\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])",
            message = "Invalid Rfc822 Name.")
    private String rfc822Name;
    @Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]",
            message = "Invalid URI.")
    private String uri;

    public GeneralNames() {
    }

    public GeneralNames(String directoryName, String dnsName, String ipAddress, String registeredId, String rfc822Name, String uri) {
        this.directoryName = directoryName;
        this.dnsName = dnsName;
        this.ipAddress = ipAddress;
        this.registeredId = registeredId;
        this.rfc822Name = rfc822Name;
        this.uri = uri;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getDnsName() {
        return dnsName;
    }

    public void setDnsName(String dnsName) {
        this.dnsName = dnsName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRegisteredId() {
        return registeredId;
    }

    public void setRegisteredId(String registeredId) {
        this.registeredId = registeredId;
    }

    public String getRfc822Name() {
        return rfc822Name;
    }

    public void setRfc822Name(String rfc822Name) {
        this.rfc822Name = rfc822Name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<SubjectAlternativeName, String> getMapFromObject() {
        Map<SubjectAlternativeName, String> map = new HashMap<>();

<<<<<<< HEAD
        if(this.directoryName != null) {
            map.put(SubjectAlternativeName.DirectoryName, this.directoryName);
        }

        if(this.dnsName != null) {
            map.put(SubjectAlternativeName.DNSName, this.dnsName);
        }

        if(this.ipAddress != null) {
            map.put(SubjectAlternativeName.IPAddress, this.ipAddress);
        }

        if(this.rfc822Name != null) {
            map.put(SubjectAlternativeName.Rfc822Name, this.rfc822Name);
        }

        if(this.registeredId != null) {
            map.put(SubjectAlternativeName.RegisteredID, this.registeredId);
        }

        if(this.uri != null) {
=======
        if (this.directoryName != null) {
            map.put(SubjectAlternativeName.DirectoryName, this.directoryName);
        }

        if (this.dnsName != null) {
            map.put(SubjectAlternativeName.DNSName, this.dnsName);
        }

        if (this.ipAddress != null) {
            map.put(SubjectAlternativeName.IPAddress, this.ipAddress);
        }

        if (this.rfc822Name != null) {
            map.put(SubjectAlternativeName.Rfc822Name, this.rfc822Name);
        }

        if (this.registeredId != null) {
            map.put(SubjectAlternativeName.RegisteredID, this.registeredId);
        }

        if (this.uri != null) {
>>>>>>> f7ddb8c3bdad9e8630f940c296305357f8049a8c
            map.put(SubjectAlternativeName.UniformResourceIdentifier, this.uri);
        }

        return map;
    }
}
