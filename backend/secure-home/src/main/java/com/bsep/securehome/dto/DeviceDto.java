package com.bsep.securehome.dto;

import com.bsep.securehome.utils.DeviceType;

public class DeviceDto {

    private Long realEstateId;
    private String id;
    private String name;
    private DeviceType type;
    private Integer period;
    private String filter;


    public DeviceDto() {
    }

    public DeviceDto(Long realEstateId, String id, String name, DeviceType type, Integer period, String filter) {
        this.realEstateId = realEstateId;
        this.id = id;
        this.name = name;
        this.type = type;
        this.period = period;
        this.filter = filter;
    }

    public Long getRealEstateId() {
        return this.realEstateId;
    }

    public void setRealEstateId(Long realEstateId) {
        this.realEstateId = realEstateId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeviceType getType() {
        return this.type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getFilter() {
        return this.filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return getId() + "," +getRealEstateId() + "," 
             + getName() + "," + getType() + "," +
                getPeriod() + "," +
                getFilter() + System.lineSeparator();
    }
    
}
