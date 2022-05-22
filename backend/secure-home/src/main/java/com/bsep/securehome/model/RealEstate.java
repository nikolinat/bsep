package com.bsep.securehome.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class RealEstate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "realestate_tenants",
            joinColumns = @JoinColumn(
                    name = "realestate_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "users_id", referencedColumnName = "id"))
    private Collection<User> tenants;


    public RealEstate() {
    }

    public RealEstate(String name) {
        this.name = name;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Collection<User> getTenants() {
        return tenants;
    }

    public void setTenants(Collection<User> tenants) {
        this.tenants = tenants;
    }
}
