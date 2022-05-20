package com.bsep.securehome.repository;

import com.bsep.securehome.model.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {
    InvalidToken findByToken(String token);
}
