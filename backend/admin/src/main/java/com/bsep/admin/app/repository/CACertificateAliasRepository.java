package com.bsep.admin.app.repository;

import com.bsep.admin.app.model.CACertificateAlias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CACertificateAliasRepository extends JpaRepository<CACertificateAlias,Integer> {
    @Query("select c from CACertificateAlias c where c.isRoot = true and c.isActive = true")
    CACertificateAlias findActiveCA();

    @Query("select c from CACertificateAlias c where c.isIntermediate = true and c.isActive = true")
    CACertificateAlias findActiveIntermediate();

    CACertificateAlias findByAlias(String alias);
}
