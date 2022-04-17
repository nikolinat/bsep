package com.bsep.admin.app.repository;

import com.bsep.admin.app.model.RevokedCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<RevokedCertificate,Long> {

    RevokedCertificate findByAlias(String alias);
}
