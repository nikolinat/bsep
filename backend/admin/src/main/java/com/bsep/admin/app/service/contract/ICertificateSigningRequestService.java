package com.bsep.admin.app.service.contract;

import com.bsep.admin.app.dto.GenerateCertificateDto;
import com.bsep.admin.app.model.CertificateSigningRequest;

import java.util.List;

public interface ICertificateSigningRequestService {
    List<CertificateSigningRequest> findAll();
    CertificateSigningRequest findById(Integer id) throws Exception;
    CertificateSigningRequest create(CertificateSigningRequest entity) throws Exception;
    CertificateSigningRequest update(CertificateSigningRequest entity, Integer id) throws Exception;
    void declineCertificateSigningRequest(Integer id, String reason) throws Exception;
    void acceptCertificateSigningRequest(Integer id, GenerateCertificateDto generateCertificateDto) throws Exception;
}
