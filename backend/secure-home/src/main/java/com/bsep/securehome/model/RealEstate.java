package com.bsep.securehome.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "realestate_owners",
            joinColumns = @JoinColumn(
                    name = "realestate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "users_id", referencedColumnName = "id"))
    private Collection<User> owners;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "realestate_tenants",
            joinColumns = @JoinColumn(
                    name = "realestate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "users_id", referencedColumnName = "id"))
    private Collection<User> tenants;


    public RealEstate() {
    }

    public RealEstate(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<User> getOwners() {
        return this.owners;
    }

    public void setOwners(Collection<User> owners) {
        this.owners = owners;
    }
    

    public Collection<User> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<User> tenants) {
        this.tenants = tenants;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}