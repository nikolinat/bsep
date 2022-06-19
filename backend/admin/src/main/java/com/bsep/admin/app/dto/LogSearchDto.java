package com.bsep.admin.app.dto;

import com.bsep.admin.app.model.enums.LogType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class LogSearchDto {
    private LogType logType;
    private String messageRegex;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    public LogSearchDto(LogType logType, String messageRegex, LocalDateTime dateTime) {
        this.logType = logType;
        this.messageRegex = messageRegex;
        this.dateTime = dateTime;
    }

    public LogSearchDto() {

    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getMessageRegex() {
        return messageRegex;
    }

    public void setMessageRegex(String messageRegex) {
        this.messageRegex = messageRegex;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
