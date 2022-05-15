package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.dto.JwtAuthenticationRequest;
import com.bsep.admin.app.model.UserTokenState;

public interface IAuthenticationService {
    UserTokenState authenticate(JwtAuthenticationRequest jwtAuthenticationRequest);
}
