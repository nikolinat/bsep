package com.bsep.admin.app.controller;

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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) throws NoSuchAlgorithmException, InvalidKeySpecException {
        UserTokenState userTokenState = this.authenticationService.authenticate(authenticationRequest);
      
        if(userTokenState == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
    public ResponseEntity<InvalidToken> logout(@RequestBody String token) throws Exception {
        return new ResponseEntity<>(this.invalidTokenService.create(token), HttpStatus.OK);
    }
}
