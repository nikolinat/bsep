package com.bsep.admin.crypto.pki.enums;

public enum SubjectAlternativeNameEnum {
    OtherName(0),
    Rfc822Name(1),
    DNSName(2),
    X400Address(3),
    DirectoryName(4),
    EdiPartyName(5),
    UniformResourceIdentifier(6),
    IPAddress(7),
    RegisteredID(8);

    private final int value;
    SubjectAlternativeNameEnum(int value) { this.value = value; }
    public int getValue() { return value; }
}
