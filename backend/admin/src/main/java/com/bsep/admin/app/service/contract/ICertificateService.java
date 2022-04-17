package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.dto.CertificateDto;
import com.bsep.admin.app.model.RevokedCertificate;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.List;

public interface ICertificateService {
    List<RevokedCertificate> findAll();

    RevokedCertificate findByAlias(String alias) throws Exception;

    RevokedCertificate create(RevokedCertificate entity) throws Exception;

    void revoke(CertificateDto certificate, String reason) throws Exception;

    List<CertificateDto> findAllValidCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException;

    void createAll() throws Exception;

    List<CertificateDto> findAllRevokedCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException;

    CertificateDto verifyIssuerCertificate(CertificateDto issuerCertificate) throws Exception;
}
