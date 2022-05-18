package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.JwtAuthenticationRequest;
import com.bsep.admin.app.model.LockedAccount;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.model.UserTokenState;
import com.bsep.admin.app.service.contract.IAuthenticationService;
import com.bsep.admin.app.utils.Base64Utility;
import com.bsep.admin.app.utils.PasswordUtil;
import com.bsep.admin.app.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;

@Service
public class AuthenticationService implements IAuthenticationService {
    private TokenUtils tokenUtils;
    private AuthenticationManager authenticationManager;
    private LockedAccountService lockedAccountService;

    @Autowired
    public AuthenticationService(TokenUtils tokenUtils, LockedAccountService lockedAccountService,
                                 AuthenticationManager authenticationManager) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.lockedAccountService = lockedAccountService;
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
            String jwt = tokenUtils.generateToken(user.getUsername(), role, id);
            int expiresIn = tokenUtils.getExpiredIn();
            if (lockedAccountService.findByUsername(jwtAuthenticationRequest.getUsername()) != null && lockedAccountService.findByUsername(jwtAuthenticationRequest.getUsername()).getLoginCounts() < 3) {
                lockedAccountService.delete(jwtAuthenticationRequest.getUsername());
            }
            return new UserTokenState(jwt, expiresIn, role, id);

        } catch (AuthenticationException e) {
            String username = jwtAuthenticationRequest.getUsername();
            if (lockedAccountService.findByUsername(username) == null) {

                lockedAccountService.create(new LockedAccount(1, username, LocalDateTime.now()));
            } else {

                lockedAccountService.update(username);
            }

        }
        return null;

    }
}
