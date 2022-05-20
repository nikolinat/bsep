package com.bsep.securehome.exception;

import java.time.LocalDateTime;

public class ExceptionResponse {
    private String errorMessage;
    private LocalDateTime dateTime;

    public String getMessage() {
        return errorMessage;
    }
    public void setMessage(String message) {
        this.errorMessage = message;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
