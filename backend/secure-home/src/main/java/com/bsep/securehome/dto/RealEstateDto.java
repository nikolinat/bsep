package com.bsep.securehome.dto;

import java.util.List;

public class RealEstateDto {
    private String name;
    private String address;
    private Long id;
    private List<UserDto> owners;
    private List<UserDto> tenants;

    public RealEstateDto() {
    }

    public RealEstateDto(String name, String address, Long id, List<UserDto> owners, List<UserDto> tenants) {
        this.name = name;
        this.address = address;
        this.id = id;
        this.owners = owners;
        this.tenants = tenants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<UserDto> getOwners() {
        return this.owners;
    }

    public void setOwners(List<UserDto> owners) {
        this.owners = owners;
    }
   
    public List<UserDto> getTenants() {
        return tenants;
    }

    public void setTenants(List<UserDto> tenants) {
        this.tenants = tenants;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}