package com.bsep.securehome.dto;

import java.util.UUID;

import com.bsep.securehome.model.enums.LogType;

public class RuleLogDto {
    private String message;
    private LogType type;
    private UUID id;

    public RuleLogDto() {
    }

    public RuleLogDto(String message, LogType type, UUID id) {
        this.message = message;
        this.type = type;
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LogType getType() {
        return this.type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
