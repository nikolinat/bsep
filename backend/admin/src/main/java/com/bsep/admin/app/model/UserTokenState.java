package com.bsep.admin.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private String role;
    private Integer id;
    @JsonIgnore
    private String cookieSecureContent;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(String accessToken, long expiresIn,String role, Integer id, String cookieSecureContent) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.role = role;
        this.id = id;
        this.cookieSecureContent = cookieSecureContent;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCookieSecureContent() {
        return cookieSecureContent;
    }

    public void setCookieSecureContent(String cookieSecureContent) {
        this.cookieSecureContent = cookieSecureContent;
    }
}
