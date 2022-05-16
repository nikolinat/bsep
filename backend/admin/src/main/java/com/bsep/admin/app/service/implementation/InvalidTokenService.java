package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.exception.InvalidTokenException;
import com.bsep.admin.app.model.InvalidToken;
import com.bsep.admin.app.repository.InvalidTokenRepository;
import com.bsep.admin.app.service.contract.IInvalidTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvalidTokenService implements IInvalidTokenService {
    private InvalidTokenRepository invalidTokenRepository;

    @Autowired
    public InvalidTokenService(InvalidTokenRepository invalidTokenRepository) {
        this.invalidTokenRepository = invalidTokenRepository;
    }

    public InvalidToken create(String token) throws Exception {
        InvalidToken invalidToken = new InvalidToken(token);
        return invalidTokenRepository.save(invalidToken);
    }

    @Override
    public void findByToken(String token) throws Exception {
        InvalidToken invalidToken = invalidTokenRepository.findByToken(token);
        if(invalidToken != null) {
            throw new InvalidTokenException("Token is invalid.");
        }
    }
}
