package com.bsep.securehome.dto;

public class LogAlarm {
    private String message;

    private Boolean isAlerted = false;

    public LogAlarm(String message) {
        this.message = message;
    }

    public LogAlarm() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsAlerted() {
        return isAlerted;
    }

    public void setIsAlerted(Boolean isAlerted) {
        this.isAlerted = isAlerted;
    }
}
