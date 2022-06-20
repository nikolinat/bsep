package com.bsep.securehome.dto;

import com.bsep.securehome.model.enums.DeviceType;

public class AlarmDto {
    private Long realEstateId;
    private String id;
    private DeviceType type;
    private String alarmMessage;


    public AlarmDto() {
    }

    public AlarmDto(Long realEstateId, String id, DeviceType type, String alarmMessage) {
        this.realEstateId = realEstateId;
        this.id = id;
        this.type = type;
        this.alarmMessage = alarmMessage;
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

    public DeviceType getType() {
        return this.type;
    }

    public void setType(DeviceType type) {
        this.type = type;
    }

    public String getAlarmMessage() {
        return this.alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    @Override
    public String toString() {
        return getId() + "," +getRealEstateId() + "," 
             +  getType() + "," + getAlarmMessage() + System.lineSeparator();
    }

}
