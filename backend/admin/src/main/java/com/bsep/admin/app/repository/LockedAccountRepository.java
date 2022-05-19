package com.bsep.admin.app.repository;


import com.bsep.admin.app.model.LockedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockedAccountRepository extends JpaRepository<LockedAccount, String> {

    LockedAccount findByUsername(String username);
}
