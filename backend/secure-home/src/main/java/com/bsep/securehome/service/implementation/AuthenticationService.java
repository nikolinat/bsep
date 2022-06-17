package com.bsep.securehome.service.implementation;

import com.bsep.securehome.annotation.LogBefore;
import com.bsep.securehome.dto.JwtAuthenticationRequest;
import com.bsep.securehome.model.LockedAccount;
import com.bsep.securehome.model.User;
import com.bsep.securehome.model.UserTokenState;
import com.bsep.securehome.service.EmailService;
import com.bsep.securehome.service.contract.IAuthenticationService;
import com.bsep.securehome.utils.CookieUtil;
import com.bsep.securehome.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;

@Service
public class AuthenticationService implements IAuthenticationService {
    private TokenUtils tokenUtils;
    private CookieUtil cookieUtil;
    private AuthenticationManager authenticationManager;
    private LockedAccountService lockedAccountService;
    private EmailService emailService;
    private UserService userService;

    @Autowired
    public AuthenticationService(TokenUtils tokenUtils, LockedAccountService lockedAccountService,
                                 AuthenticationManager authenticationManager, EmailService emailService, UserService userService, CookieUtil cookieUtil) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.lockedAccountService = lockedAccountService;
        this.emailService = emailService;
        this.userService = userService;
        this.cookieUtil = cookieUtil;
    }


    @Override
    public UserTokenState authenticate(JwtAuthenticationRequest jwtAuthenticationRequest) throws NoSuchAlgorithmException, InvalidKeySpecException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    jwtAuthenticationRequest.getUsername(), jwtAuthenticationRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String role = user.getRoles().get(0).getName();
            Integer id = user.getId();
            HttpCookie cookie = cookieUtil.createAccessTokenCookie(tokenUtils.generateCookieContent());
            String jwt = tokenUtils.generateToken(user.getUsername(), role, id, cookie.getValue());
            int expiresIn = tokenUtils.getExpiredIn();
            if (lockedAccountService.findByUsername(jwtAuthenticationRequest.getUsername()) != null && lockedAccountService.findByUsername(jwtAuthenticationRequest.getUsername()).getLoginCounts() < 3) {
                lockedAccountService.delete(jwtAuthenticationRequest.getUsername());
            }
            return new UserTokenState(jwt, expiresIn, role, id, cookie);

        } catch (AuthenticationException e) {
            String username = jwtAuthenticationRequest.getUsername();
            if (lockedAccountService.findByUsername(username) == null) {

                lockedAccountService.create(new LockedAccount(1, username, LocalDateTime.now()));

            } else {
                LockedAccount l = lockedAccountService.update(username);
                if (l.getLoginCounts() >= 3) {
                    emailService.sendEmailForBlockedAccount(userService.findUser(username).getEmailAddress());
                }
            }

        }
        return null;
    }
}
