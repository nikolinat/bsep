package com.bsep.securehome.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.bsep.securehome.model.enums.DeviceType;


@Document
public class DeviceMessage {
    @Id
    private UUID id;

    private String deviceId;

    private DeviceType type;

    private String message;

    private LocalDateTime dateTime;

    private Boolean alarm;

    private Integer value;
    
    private Long realEstateId;

    public DeviceMessage() {}


    public DeviceMessage(UUID id, String deviceId, DeviceType type, String message, LocalDateTime dateTime, Boolean alarm) {
        this.id = id;
        this.deviceId = deviceId;
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
        this.alarm = alarm;
    }

    public DeviceMessage(UUID id, String deviceId, DeviceType type, String message, LocalDateTime dateTime, Boolean alarm, Integer value) {
        this.id = id;
        this.deviceId = deviceId;
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
        this.alarm = alarm;
        this.value = value;
    }

    public DeviceMessage(UUID id, String deviceId, DeviceType type, String message, LocalDateTime dateTime, Boolean alarm, Integer value, Long realEstateId) {
        this.id = id;
        this.deviceId = deviceId;
        this.type = type;
        this.message = message;
        this.dateTime = dateTime;
        this.alarm = alarm;
        this.value = value;
        this.realEstateId = realEstateId;
    }



    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Boolean isAlarm() {
        return this.alarm;
    }

    public void setAlarm(Boolean alarm) {
        this.alarm = alarm;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Long getRealEstateId() {
        return this.realEstateId;
    }

    public void setRealEstateId(Long realEstateId) {
        this.realEstateId = realEstateId;
    }

}
