package com.bsep.securehome.dto;

public class LogDto {
    private Long id;

    private String message;

    private String dateTime;


    public LogDto() {}


    public LogDto(Long id, String message, String dateTime) {
        this.id = id;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
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

    @Override
    public String toString() {
        return "LogDto{" +
                "id='" + id + '\'' +
                ", DateTime='" + dateTime + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
