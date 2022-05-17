package com.bsep.admin.app.exception;

public class DuplicateEntityException extends RuntimeException{

    private String message;

    public DuplicateEntityException() {

    }

    public DuplicateEntityException(String message) {
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
