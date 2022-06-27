package com.bsep.securehome.model;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bsep.securehome.model.enums.DeviceType;


@Document
public class DevicesLog {

    private Long realEstateId;

    private Long devicesId;

    private DeviceType type;

    private String message;

    private String dateTime;

    private boolean alarm; 


    public DevicesLog() {}


    public DevicesLog(Long devicesId,DeviceType type,Long realEstateId, String message, String dateTime, boolean alarm) {
        this.devicesId = devicesId;
        this.type = type;
        this.realEstateId = realEstateId;
        this.message = message;
        this.dateTime = dateTime;
        this.alarm = alarm;
    }

    public Long getRealEstateId() {
        return this.realEstateId;
    }

    public void setRealEstateId(Long id) {
        this.realEstateId = id;
    }

    public Long getDevicesId() {
        return this.devicesId;
    }

    public void setDevicesId(Long id) {
        this.devicesId = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setType(DeviceType type)
    {
        this.type = type;
    }

    public DeviceType getType()
    {
        return this.type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateTime() {
        return this.dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean getAlarm(){
        return this.alarm;
    }

    public void setAlarm(boolean alarm){
        this.alarm = alarm;
    }

    @Override
    public String toString() {
        return "LogDto{" +
                "id='" + devicesId + '\'' +
                ", DateTime='" + dateTime + '\'' +
                ", message='" + message + '\'' +
                ", alarm='" + alarm + '\'' +
                '}';
    }
}
