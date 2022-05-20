package com.bsep.securehome.service.contract;

import com.bsep.securehome.model.InvalidToken;

public interface IInvalidTokenService {
    void findByToken(String token) throws Exception;

    public InvalidToken create(String token) throws Exception;
}
