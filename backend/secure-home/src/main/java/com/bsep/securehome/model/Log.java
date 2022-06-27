package com.bsep.securehome.model;

import com.bsep.securehome.model.enums.LogType;
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

    private String applicationName;

    public Log(UUID id, String message, LogType logType, String applicationName) {
        this.id = id;
        this.message = message;
        this.logType = logType;
        this.applicationName = applicationName;
    }

    public Log(String message, LogType logType, String applicationName) {
        this.message = message;
        this.logType = logType;
        this.applicationName = applicationName;
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
