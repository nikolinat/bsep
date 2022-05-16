package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.model.InvalidToken;

public interface IInvalidTokenService {
    void findByToken(String token) throws Exception;
    public InvalidToken create(String token) throws Exception;
}
