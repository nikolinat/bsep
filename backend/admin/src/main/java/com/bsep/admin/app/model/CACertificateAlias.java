package com.bsep.admin.app.model;

import javax.persistence.*;

@Entity
@Table(name = "cacertificatealiases")
public class CACertificateAlias {

    @Id
    @Column(name = "alias", unique = true, nullable = false)
    private String alias;

    private Boolean isRoot;

    private Boolean isIntermediate;

    private Boolean isActive;

    public CACertificateAlias() {

    }

    public CACertificateAlias(String alias, Boolean isRoot, Boolean isIntermediate, Boolean isActive) {
        this.alias = alias;
        this.isRoot = isRoot;
        this.isIntermediate = isIntermediate;
        this.isActive = isActive;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Boolean getRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public Boolean getIntermediate() {
        return isIntermediate;
    }

    public void setIntermediate(Boolean intermediate) {
        isIntermediate = intermediate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
