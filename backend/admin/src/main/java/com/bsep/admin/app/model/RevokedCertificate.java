package com.bsep.admin.app.model;

import javax.persistence.*;

@Entity
@Table(name = "certificates")
public class RevokedCertificate {

    @Id
    @Column(name = "alias", nullable = false)
    private String alias;

    @Column(name = "reason", nullable = false)
    private String reason;

    public RevokedCertificate() {

    }

    public RevokedCertificate(String alias, String reason) {
        this.alias = alias;
        this.reason = reason;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

