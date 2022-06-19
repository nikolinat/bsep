package com.bsep.securehome.dto;

import java.time.LocalDateTime;

import com.bsep.securehome.utils.DeviceType;

public class MessageDto {
    private LocalDateTime dateTime;
    private String id;
    private DeviceType type;
    private String message;


    public MessageDto() {
    }


    public MessageDto(LocalDateTime dateTime, String id, DeviceType type, String message) {
        this.dateTime = dateTime;
        this.id = id;
        this.type = type;
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    
}
