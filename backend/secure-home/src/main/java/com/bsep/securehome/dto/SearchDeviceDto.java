package com.bsep.securehome.dto;

import java.time.LocalDate;

public class SearchDeviceDto {

    private Long realEstateId;

    private String type;

    // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    private String startDateTime;

    // @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]")
    private String endDateTime;

    private LocalDate startDate;

    private LocalDate endDate;

    public SearchDeviceDto() {}


    public SearchDeviceDto(Long realEstateId,String type, String startDateTime, String endDateTime) {
        this.realEstateId = realEstateId;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Long getRealEstateId() {
        return this.realEstateId;
    }

    public void setRealEstateId(Long realEstateId) {
        this.realEstateId = realEstateId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getStartDateTime() {
        return this.startDateTime;
    }

    public void setStartDateTime(String dateTime) {
        this.startDateTime = dateTime;
    }

    public String getEndDateTime() {
        return this.endDateTime;
    }

    public void setEndDateTime(String dateTime) {
        this.endDateTime = dateTime;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate dateTime) {
        this.startDate = dateTime;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate dateTime) {
        this.endDate = dateTime;
    }

    @Override
    public String toString() {
        return "UpdateUserDto{" +
                "type='" + type + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                '}';
    }
}
