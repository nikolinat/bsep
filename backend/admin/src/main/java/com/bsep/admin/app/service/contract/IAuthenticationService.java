package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.dto.JwtAuthenticationRequest;
import com.bsep.admin.app.model.UserTokenState;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IAuthenticationService {
    UserTokenState authenticate(JwtAuthenticationRequest jwtAuthenticationRequest) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
