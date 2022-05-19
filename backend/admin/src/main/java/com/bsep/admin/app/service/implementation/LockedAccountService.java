package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.model.LockedAccount;
import com.bsep.admin.app.repository.LockedAccountRepository;
import com.bsep.admin.app.service.contract.ILockedAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.locks.Lock;

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
        count = count +1 ;
        System.out.println(count);
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
