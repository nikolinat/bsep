package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.JwtAuthenticationRequest;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.model.UserTokenState;
import com.bsep.admin.app.service.contract.IAuthenticationService;
import com.bsep.admin.app.utils.CookieUtil;
import com.bsep.admin.app.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class AuthenticationService implements IAuthenticationService {
    private TokenUtils tokenUtils;
    private CookieUtil cookieUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(TokenUtils tokenUtils,CookieUtil cookieUtil,
                                    AuthenticationManager authenticationManager){
        this.tokenUtils = tokenUtils;
        this.authenticationManager  = authenticationManager;
        this.cookieUtil = cookieUtil;
    }

    @Override
    public UserTokenState authenticate(JwtAuthenticationRequest jwtAuthenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String role = user.getRoles().get(0).getName();
        Integer id = user.getId();
        //String cookieContent = tokenUtils.generateCookieContent();
        HttpCookie cookie = cookieUtil.createAccessTokenCookie(tokenUtils.generateCookieContent(), 3600L);
        String jwt = tokenUtils.generateToken(user.getUsername(), role, id,  cookie.getValue());
        int expiresIn = tokenUtils.getExpiredIn();
        return new UserTokenState(jwt, expiresIn, role, id,  cookie);
    }
}
