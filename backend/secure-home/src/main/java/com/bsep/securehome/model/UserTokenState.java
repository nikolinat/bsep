package com.bsep.securehome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpCookie;

public class UserTokenState {
    private String accessToken;
    private Long expiresIn;
    private String role;
    private Integer id;
    @JsonIgnore
    private HttpCookie cookieSecureContent;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(String accessToken, long expiresIn, String role, Integer id, HttpCookie cookieSecureContent) {
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

    public HttpCookie getCookieSecureContent() {
        return cookieSecureContent;
    }

    public void setCookieSecureContent(HttpCookie cookieSecureContent) {
        this.cookieSecureContent = cookieSecureContent;
    }
}
