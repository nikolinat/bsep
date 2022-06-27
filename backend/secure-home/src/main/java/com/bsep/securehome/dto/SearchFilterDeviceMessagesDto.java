package com.bsep.securehome.dto;

import com.bsep.securehome.model.enums.DeviceType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class SearchFilterDeviceMessagesDto {
    private DeviceType type;

    private String message;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    private Boolean alarm;

    public SearchFilterDeviceMessagesDto(DeviceType type, String message, LocalDateTime dateTime, Boolean alarm) {
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
        this.alarm = alarm;
    }

    public SearchFilterDeviceMessagesDto() {

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

    public Boolean isAlarm() {
        return alarm;
    }

    public void setAlarm(Boolean alarm) {
        this.alarm = alarm;
    }
}
