package com.bsep.securehome.service.contract;

import com.bsep.securehome.model.LockedAccount;

import java.util.List;

public interface ILockedAccountService {

    LockedAccount create(LockedAccount lockedAccount);

    LockedAccount update(String username);

    void delete(String username);

    LockedAccount findByUsername(String username);

    List<LockedAccount> findAll();
}
