package com.bsep.admin.app.repository;

import com.bsep.admin.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmailAddress(String email);
}
