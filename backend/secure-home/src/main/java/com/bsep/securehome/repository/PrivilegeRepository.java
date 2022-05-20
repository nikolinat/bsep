package com.bsep.securehome.repository;

import com.bsep.securehome.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Integer> {
    Privilege findByName(String name);
}
