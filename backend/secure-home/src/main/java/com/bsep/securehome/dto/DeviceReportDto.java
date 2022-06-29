package com.bsep.securehome.dto;

import com.bsep.securehome.model.enums.DeviceType;

public class DeviceReportDto {
    private DeviceType type;
    private Integer number;
    private String id;

    public DeviceReportDto() {
    }

    public DeviceReportDto(DeviceType type, Integer number, String id) {
        this.type = type;
        this.number = number;
        this.id = id;
    }

    public DeviceType getType() {
        return this.type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
