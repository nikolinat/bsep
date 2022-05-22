package com.bsep.admin.app.exception;

public class InvalidCredentialsException extends RuntimeException {
    private String errorMessage;

    public InvalidCredentialsException() {

    }

    public InvalidCredentialsException(String message) {
        this.errorMessage = message;
    }

    @Override
    public String getMessage() {
        return errorMessage;
    }

    public void setMessage(String message) {
        this.errorMessage = message;
    }
}
