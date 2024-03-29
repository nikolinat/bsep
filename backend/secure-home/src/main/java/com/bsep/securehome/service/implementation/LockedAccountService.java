package com.bsep.securehome.service.implementation;

import com.bsep.securehome.model.LockedAccount;
import com.bsep.securehome.repository.LockedAccountRepository;
import com.bsep.securehome.service.contract.ILockedAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LockedAccountService implements ILockedAccountService {

    private LockedAccountRepository lockedAccountRepository;

    @Autowired
    public LockedAccountService(LockedAccountRepository lockedAccountRepository) {
        this.lockedAccountRepository = lockedAccountRepository;
    }

    @Override
    public LockedAccount create(LockedAccount lockedAccount) {
        return this.lockedAccountRepository.save(lockedAccount);
    }

    @Override
    public LockedAccount update(String username) {
        LockedAccount lockedAccount = this.findByUsername(username);
        int count = lockedAccount.getLoginCounts();
        count = count + 1;
        lockedAccount.setLoginCounts(count);
        lockedAccount.setDateTime(LocalDateTime.now());
        return this.lockedAccountRepository.save(lockedAccount);
    }

    @Override
    public void delete(String username) {
        this.lockedAccountRepository.delete(this.findByUsername(username));
    }

    @Override
    public LockedAccount findByUsername(String username) {
        return this.lockedAccountRepository.findByUsername(username);
    }

    @Override
    public List<LockedAccount> findAll() {
        return this.lockedAccountRepository.findAll();
    }
}
