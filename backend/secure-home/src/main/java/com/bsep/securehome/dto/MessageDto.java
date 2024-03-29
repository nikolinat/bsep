package com.bsep.securehome.dto;

import java.time.LocalDateTime;

import com.bsep.securehome.model.enums.DeviceType;

public class MessageDto {
    private LocalDateTime dateTime;
    private String id;
    private DeviceType type;
    private String message;
    private Long realEstateId;
    private Integer value;

    public MessageDto() {
    }

    public MessageDto(LocalDateTime dateTime, String id, DeviceType type, String message, Long realEstateId,
            Integer value) {
        this.dateTime = dateTime;
        this.id = id;
        this.type = type;
        this.message = message;
        this.realEstateId = realEstateId;
        this.value = value;
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

    public Long getRealEstateId() {
        return this.realEstateId;
    }

    public void setRealEstateId(Long realEstateId) {
        this.realEstateId = realEstateId;
    }

    public Integer getValue() {
        return this.value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return dateTime + "," + id + "," + message + ","
                + realEstateId + "," + type + "," + value + System.lineSeparator();
    }
}
