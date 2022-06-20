package com.bsep.securehome.dto;

import com.bsep.securehome.utils.DeviceType;

import java.time.LocalDateTime;

public class DeviceMessageDto {
    private String deviceId;

    private String deviceName;

    private DeviceType type;

    private String message;

    private LocalDateTime dateTime;

    private boolean alarm;

    public DeviceMessageDto(String deviceId, String deviceName, DeviceType type, String message, LocalDateTime dateTime, boolean alarm) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
        this.alarm = alarm;
    }

    public DeviceMessageDto() {

    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public DeviceType getType() {
        return type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(boolean alarm) {
        this.alarm = alarm;
    }
}
