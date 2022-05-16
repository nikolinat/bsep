package com.bsep.admin.app.exception;

public class InvalidTokenException extends RuntimeException {
    private String message;

    public InvalidTokenException() {

    }

    public InvalidTokenException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
