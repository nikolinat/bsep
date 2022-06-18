package com.bsep.admin.app.controller;

import com.bsep.admin.app.annotation.LogAfterReturning;
import com.bsep.admin.app.annotation.LogAfterThrowing;
import com.bsep.admin.app.dto.JwtAuthenticationRequest;
import com.bsep.admin.app.model.InvalidToken;
import com.bsep.admin.app.model.UserTokenState;
import com.bsep.admin.app.service.contract.IAuthenticationService;
import com.bsep.admin.app.service.contract.IInvalidTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
    @LogAfterThrowing(message = "ERROR login")
    @LogAfterReturning(message = "SUCCESS login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(@Valid
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserTokenState userTokenState = this.authenticationService.authenticate(authenticationRequest);
      
        if(userTokenState == null){
            throw new BadCredentialsException("Bad credentials.");
        }

        HttpCookie cookie = userTokenState.getCookieSecureContent();
        //String cookie = "SecureContent=" + userTokenState.getCookieSecureContent() + "; HttpOnly; Path=/; Secure; SameSite=None";

        HttpHeaders headers = new HttpHeaders();
        //headers.add("Access-Control-Allow-Origin", "http://192.168.1.3:8081");
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        //headers.add("Set-Cookie", cookie);
      
        return ResponseEntity.ok().headers(headers).body(userTokenState);
    }

    @PostMapping("/logout")
    @LogAfterThrowing(message = "ERROR logout")
    @LogAfterReturning(message = "SUCCESS logout")
    public ResponseEntity<InvalidToken> logout(@RequestBody String token) throws Exception {
        return new ResponseEntity<>(this.invalidTokenService.create(token), HttpStatus.OK);
    }
}
