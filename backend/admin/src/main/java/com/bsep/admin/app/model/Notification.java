package com.bsep.admin.app.model;

public class Notification {
    private String id;

    private String message;

    private String title;

    public Notification() {
        super();
    }

    public Notification(String id, String title, String message) {
        super();
        this.id = id;
        this.message = message;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
