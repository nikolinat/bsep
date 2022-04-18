package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.GenerateCertificateDto;
import com.bsep.admin.app.exception.BadLogicException;
import com.bsep.admin.app.exception.MissingEntityException;
import com.bsep.admin.app.model.CACertificateAlias;
import com.bsep.admin.app.model.CertificateSigningRequest;
import com.bsep.admin.app.repository.CertificateSigningRequestRepository;
import com.bsep.admin.app.service.contract.ICertificateSigningRequestService;
import com.bsep.admin.crypto.pki.certificates.CertificateGenerator;
import com.bsep.admin.crypto.pki.certificates.CertificateUtil;
import com.bsep.admin.crypto.pki.data.IssuerData;
import com.bsep.admin.crypto.pki.data.SubjectData;
import com.bsep.admin.crypto.pki.keystores.KeyStoreWriter;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.List;
import java.util.UUID;

@Service
public class CertificateSignRequestService implements ICertificateSigningRequestService {
    private CertificateSigningRequestRepository certificateSigningRequestRepository;
    private CertificateGenerator certificateGenerator;
    private KeyStoreWriter keyStoreWriter;
    private CaCertificateAliasService caCertificateAliasService;

    @Autowired
    public CertificateSignRequestService(CertificateSigningRequestRepository certificateSigningRequestRepository,
                                         CertificateGenerator certificateGenerator,
                                         KeyStoreWriter keyStoreWriter,
                                         CaCertificateAliasService caCertificateAliasService) {
        this.certificateSigningRequestRepository = certificateSigningRequestRepository;
        this.certificateGenerator = certificateGenerator;
        this.keyStoreWriter = keyStoreWriter;
        this.caCertificateAliasService = caCertificateAliasService;
    }

    @Override
    public List<CertificateSigningRequest> findAll() {
        return certificateSigningRequestRepository.findAll();
    }

    @Override
    public CertificateSigningRequest findById(Integer id) {
        CertificateSigningRequest csr = certificateSigningRequestRepository.findById(id).orElse(null);

        if(csr == null) {
            throw new MissingEntityException("CSR with given id does not exist in the system.");
        }

        return csr;
    }

    @Override
    public CertificateSigningRequest create(CertificateSigningRequest entity) throws Exception {
        entity.setAccepted(false);
        return certificateSigningRequestRepository.save(entity);
    }

    @Override
    public void declineCertificateSigningRequest(Integer id, String reason) {
        CertificateSigningRequest csr = this.findById(id);

        if(csr.isAccepted()) {
            throw new BadLogicException("The given csr is already declined.");
        }

        csr.setAccepted(false);
        csr.setDeleted(true);
        csr.setReasonForDeclining(reason);

        certificateSigningRequestRepository.save(csr);
    }

    @Override
    public void acceptCertificateSigningRequest(Integer id, GenerateCertificateDto generateCertificateDto) throws Exception {
        CertificateSigningRequest csr = this.findById(id);

        CACertificateAlias caCertificateAlias = caCertificateAliasService.getActiveIntermediate();
        IssuerData issuerData = CertificateUtil.getIntermediateCertificateDetails(caCertificateAlias.getAlias());
        KeyPair keyPair = CertificateUtil.generateKeyPair();

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, csr.getCommonName());
        builder.addRDN(BCStyle.SURNAME, csr.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, csr.getGivenName());
        builder.addRDN(BCStyle.O, csr.getOrganization());
        builder.addRDN(BCStyle.OU, csr.getOrganizationUnit());
        builder.addRDN(BCStyle.C, csr.getCountry());
        builder.addRDN(BCStyle.E, csr.getEmail());
        builder.addRDN(BCStyle.UID, csr.getUserId());

        String serialNumber = UUID.randomUUID().toString();

        SubjectData subjectData = new SubjectData(keyPair.getPublic(), builder.build(), serialNumber, generateCertificateDto.getStartDate(),
                generateCertificateDto.getEndDate());

        Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData, generateCertificateDto);

        String keystoreFileName = CertificateUtil.makeFilePath();

        System.out.println(certificate);
        keyStoreWriter.loadKeyStore(keystoreFileName, CertificateUtil.getKeyStorePassword().toCharArray());
        keyStoreWriter.write(serialNumber, caCertificateAlias.getAlias(), issuerData.getPrivateKey(),
                CertificateUtil.getKeyStorePassword().toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, CertificateUtil.getKeyStorePassword().toCharArray());

        csr.setAccepted(true);
        csr.setDeleted(true);
        certificateSigningRequestRepository.save(csr);
    }
}
