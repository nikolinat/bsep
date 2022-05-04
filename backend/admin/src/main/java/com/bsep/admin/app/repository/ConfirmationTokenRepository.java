package com.bsep.admin.app.repository;

import com.bsep.admin.app.model.CertificateSigningRequest;
import com.bsep.admin.app.model.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
    ConfirmationToken findByCertificateSigningRequest(CertificateSigningRequest csr);
}
