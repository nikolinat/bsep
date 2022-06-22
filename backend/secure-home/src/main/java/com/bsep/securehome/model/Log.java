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

    public Log(UUID id, String message, LogType logType) {
        this.id = id;
        this.message = message;
        this.logType = logType;
    }

    public Log(String message, LogType logType) {
        this.message = message;
        this.logType = logType;
    }

    private Log() {}

    @Override
    public String toString() {
        return "Log{" +
                "logType=" + logType +
                ", id=" + id +
                ", message='" + message + '\'' +
                ", dateTime=" + dateTime +
                '}';
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
}
