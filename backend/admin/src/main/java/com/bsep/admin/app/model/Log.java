package com.bsep.admin.app.model;

import com.bsep.admin.app.model.enums.LogType;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Document
public class Log {
    private LogType logType;

    @Id
    private UUID id;

    private String message;

    private LocalDateTime dateTime = LocalDateTime.now();

    private String applicationName = "Admin";

    public Log(UUID id, String message, LogType logType) {
        this.id = id;
        this.message = message;
        this.logType = logType;
    }

    public Log(String message, LogType logType) {
        this.message = message;
        this.logType = logType;
    }

    private Log() {

    }

    @Override
    public String toString() {
        return "Log [applicationName=" + applicationName + ", dateTime=" + dateTime + ", id=" + id + ", logType="
                + logType + ", message=" + message + "]";
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
