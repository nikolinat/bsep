package com.bsep.securehome.dto;

import java.util.List;

public class RealEstateDto {
    private String name;
    private Long id;
    private UserDto owner;
    private List<UserDto> tenants;

    public RealEstateDto() {
    }

    public RealEstateDto(String name, Long id, UserDto owner, List<UserDto> tenants) {
        this.name = name;
        this.id = id;
        this.owner = owner;
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

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public List<UserDto> getTenants() {
        return tenants;
    }

    public void setTenants(List<UserDto> tenants) {
        this.tenants = tenants;
    }
}
