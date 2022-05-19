package com.bsep.admin.app.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lockedaccounts")
public class LockedAccount {

    @Id
    @Column(unique = true, nullable = false)
    private String username;


    private Integer loginCounts;

    private LocalDateTime dateTime;

    public LockedAccount() {
    }

    public LockedAccount(Integer loginCounts, String username, LocalDateTime dateTime) {
        this.loginCounts = loginCounts;
        this.username = username;
        this.dateTime = dateTime;
    }


    public Integer getLoginCounts() {
        return loginCounts;
    }

    public void setLoginCounts(Integer loginCounts) {
        this.loginCounts = loginCounts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
