package com.bsep.securehome.repository;

import com.bsep.securehome.model.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long>{
}