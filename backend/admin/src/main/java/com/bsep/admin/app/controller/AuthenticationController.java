package com.bsep.admin.app.controller;

import com.bsep.admin.app.dto.JwtAuthenticationRequest;
import com.bsep.admin.app.model.InvalidToken;
import com.bsep.admin.app.model.UserTokenState;
import com.bsep.admin.app.service.contract.IAuthenticationService;
import com.bsep.admin.app.service.contract.IInvalidTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
@RequestMapping(value="/api/v1/auth")
public class AuthenticationController {
    private IAuthenticationService authenticationService;
    private IInvalidTokenService invalidTokenService;

    @Autowired
    public AuthenticationController(IAuthenticationService authenticationService,
                                    IInvalidTokenService invalidTokenService) {
        this.authenticationService = authenticationService;
        this.invalidTokenService = invalidTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {
        return new ResponseEntity<UserTokenState>(this.authenticationService.authenticate(authenticationRequest), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<InvalidToken> logout(@RequestBody String token) throws Exception {
        return new ResponseEntity<>(this.invalidTokenService.create(token), HttpStatus.OK);
    }
}
