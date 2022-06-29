package com.bsep.securehome.dto;

import java.util.UUID;

import com.bsep.securehome.model.enums.DeviceType;

public class RuleDto {
    private Integer number;
    private String sign;
    private String message;
    private DeviceType type;
    private UUID id;

    public RuleDto() {
    }

    public RuleDto(Integer number, String sign, String message, DeviceType type, UUID id) {
        this.number = number;
        this.sign = sign;
        this.message = message;
        this.type = type;
        this.id = id;
    }


    public Integer getNumber() {
        return this.number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getSign() {
        return this.sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DeviceType getType() {
        return this.type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


}
