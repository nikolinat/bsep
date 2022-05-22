package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.JwtAuthenticationRequest;
import com.bsep.admin.app.exception.BadLogicException;
import com.bsep.admin.app.exception.InvalidCredentialsException;
import com.bsep.admin.app.model.LockedAccount;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.model.UserTokenState;
import com.bsep.admin.app.service.EmailService;
import com.bsep.admin.app.service.contract.IAuthenticationService;
import com.bsep.admin.app.utils.CookieUtil;
import com.bsep.admin.app.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
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
            if (!role.equals("ROLE_ADMIN")) {
                throw new InvalidCredentialsException("Only admin can login in this application.");
            }
            Integer id = user.getId();
            HttpCookie cookie = cookieUtil.createAccessTokenCookie(tokenUtils.generateCookieContent(), 3600L);
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
                emailService.sendEmailForBlockedAccount(userService.findUser(username).getEmailAddress());
            } else {

                lockedAccountService.update(username);
            }

        }
        return null;
    }
}
