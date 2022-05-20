package com.bsep.securehome.service.contract;

import com.bsep.securehome.dto.JwtAuthenticationRequest;
import com.bsep.securehome.model.UserTokenState;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface IAuthenticationService {
    UserTokenState authenticate(JwtAuthenticationRequest jwtAuthenticationRequest) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
