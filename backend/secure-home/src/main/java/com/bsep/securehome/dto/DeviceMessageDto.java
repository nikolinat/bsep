package com.bsep.securehome.dto;

import java.time.LocalDateTime;

import com.bsep.securehome.utils.DeviceType;

public class DeviceMessageDto {
    
    private String deviceId;

    private DeviceType type;

    private String message;

    private LocalDateTime dateTime;


    public DeviceMessageDto() {}


    public DeviceMessageDto(String deviceId, DeviceType type, String message, LocalDateTime dateTime) {
        this.deviceId = deviceId;
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceType getType() {
        return this.type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
