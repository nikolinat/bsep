package com.bsep.admin.app.repository;

import com.bsep.admin.app.model.CertificateSigningRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CertificateSigningRequestRepository extends JpaRepository<CertificateSigningRequest, Integer> {
}
