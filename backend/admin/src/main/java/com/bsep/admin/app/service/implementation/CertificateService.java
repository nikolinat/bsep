package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.model.Certificate;
import com.bsep.admin.app.model.enums.CertificateStatus;
import com.bsep.admin.app.repository.CertificateRepository;
import com.bsep.admin.app.service.contract.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.cert.X509Certificate;
import java.util.List;

@Service
public class CertificateService implements ICertificateService {

    private CertificateRepository certificateRepository;

    @Autowired
    public CertificateService(CertificateRepository certificateRepository){
        this.certificateRepository = certificateRepository;
    }

    @Override
    public List<Certificate> findAll() {
        return null;
    }

    @Override
    public Certificate findBySerialNumber(String serialNumber) {
        return null;
    }

    @Override
    public Certificate create(Certificate entity) {
        return null;
    }

    @Override
    public Certificate update(Certificate entity, String id) {
        return null;
    }

    @Override
    public Certificate verifyIssuerCertificate(Certificate issuerCertificate) {
        Certificate certificate = certificateRepository.findById(issuerCertificate.getId()).orElse(null);

        if(certificate==null || certificate.getCertificateStatus()==CertificateStatus.UNKNOWN || certificate.getCertificateStatus()== CertificateStatus.REVOKED)
            return null;

        return certificate;
    }

}

