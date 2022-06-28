package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.CertificateDto;
import com.bsep.admin.app.dto.ExtensionDto;
import com.bsep.admin.app.dto.SubjectDto;
import com.bsep.admin.app.model.CACertificateAlias;
import com.bsep.admin.app.model.RevokedCertificate;
import com.bsep.admin.app.repository.CertificateRepository;
import com.bsep.admin.app.service.contract.ICACertificateAliasService;
import com.bsep.admin.app.service.contract.ICertificateService;
import com.bsep.admin.app.utils.ExtensionsUtil;
import com.bsep.admin.app.utils.SubjectUtil;
import com.bsep.admin.crypto.pki.certificates.CertificateUtil;
import com.bsep.admin.crypto.pki.keystores.KeyStoreReader;
import org.bouncycastle.cert.X509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CertificateService implements ICertificateService {

    private CertificateRepository certificateRepository;
    private ICACertificateAliasService certificateAliasService;
    KeyStoreReader keyStoreReader = new KeyStoreReader();

    @Autowired
    public CertificateService(CertificateRepository certificateRepository, CaCertificateAliasService caCertificateAliasService) {
        this.certificateRepository = certificateRepository;
        this.certificateAliasService = caCertificateAliasService;
    }

    @Override
    public RevokedCertificate findByAlias(String alias) {
        return certificateRepository.findByAlias(alias);
    }

    @Override
    public RevokedCertificate create(RevokedCertificate entity) {
        return certificateRepository.save(entity);

    }
    @Override
    public void revoke(CertificateDto certificateDto, String reason) throws Exception {

        CACertificateAlias root = certificateAliasService.getActiveCA();
        CACertificateAlias intermediate = certificateAliasService.getActiveIntermediate();

        List<String> aliases = keyStoreReader.getAllAliases(CertificateUtil.makeFilePath(), CertificateUtil.getKeyStorePassword());
        if (certificateDto.isRoot()) {
            for (String a : aliases) {
                Certificate[] certificate = keyStoreReader.readCertificate(CertificateUtil.makeFilePath(), CertificateUtil.getKeyStorePassword(), a);
                for (Certificate c : certificate) {
                    BigInteger serialNumber = new X509CertificateHolder(c.getEncoded()).getSerialNumber();
                    String certificateAlias = new String(serialNumber.toByteArray(), StandardCharsets.UTF_8);
                    RevokedCertificate revokedCertificate = new RevokedCertificate(certificateAlias, reason);
                    create(revokedCertificate);
                }
            }

            certificateAliasService.update(root.getAlias(), false);
            certificateAliasService.update(intermediate.getAlias(), false);

            String caAlias = CertificateUtil.createCACertificate();
            CACertificateAlias caCertificate = new CACertificateAlias(caAlias, true, false, true);
            certificateAliasService.create(caCertificate);
            CACertificateAlias intermediateCertificate = new CACertificateAlias(CertificateUtil.createIntermediateCertificate(caAlias), false, true, true);
            certificateAliasService.create(intermediateCertificate);

        } else if (certificateDto.isIntermediate()) {
            for (String a : aliases) {
                Certificate[] certificate = keyStoreReader.readCertificate(CertificateUtil.makeFilePath(), CertificateUtil.getKeyStorePassword(), a);
                for (Certificate c : certificate) {
                    BigInteger serialNumber = new X509CertificateHolder(c.getEncoded()).getSerialNumber();
                    if (!serialNumber.equals(new BigInteger(root.getAlias().getBytes()))) {
                        String certificateAlias = new String(serialNumber.toByteArray(), StandardCharsets.UTF_8);
                        RevokedCertificate revokedCertificate = new RevokedCertificate(certificateAlias, reason);
                        create(revokedCertificate);
                    }
                }
            }
            certificateAliasService.update(intermediate.getAlias(), false);
            CertificateUtil.createIntermediateCertificate(root.getAlias());
            CACertificateAlias intermediateCertificate = new CACertificateAlias(CertificateUtil.createIntermediateCertificate(root.getAlias()), false, true, true);
            certificateAliasService.create(intermediateCertificate);
        } else {
            RevokedCertificate revokedCertificate = new RevokedCertificate(certificateDto.getAlias(), reason);
            create(revokedCertificate);
        }

    }

    public boolean check(List<CertificateDto> certificateDtos, String alias) {
        for (CertificateDto certificateDto : certificateDtos) {
            if (certificateDto.getAlias().equals(alias)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void createAll() throws Exception {
        CertificateUtil.createNewKeyStore();
        String CA = CertificateUtil.createCACertificate();
        CACertificateAlias caCertificate = new CACertificateAlias(CA, true, false, true);
        certificateAliasService.create(caCertificate);

        String Intermediate = CertificateUtil.createIntermediateCertificate(CA);
        CACertificateAlias intermediateCertificate = new CACertificateAlias(Intermediate, false, true, true);
        certificateAliasService.create(intermediateCertificate);
        CertificateUtil.createNewIssuedCertificate(Intermediate);

        CertificateUtil.createRoot();
    }

    public List<CertificateDto> findCertificates(boolean valid) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        List<CertificateDto> certificateDtos = new ArrayList<>();

        CACertificateAlias root = certificateAliasService.getActiveCA();
        CACertificateAlias intermediate = certificateAliasService.getActiveIntermediate();

        List<String> aliases = keyStoreReader.getAllAliases(CertificateUtil.makeFilePath(), CertificateUtil.getKeyStorePassword());
        for (String a : aliases) {
            if (!a.equals("root")) {
                Certificate[] certificate = keyStoreReader.readCertificate(CertificateUtil.makeFilePath(), CertificateUtil.getKeyStorePassword(), a);
                for (Certificate c : certificate) {
                    c.getPublicKey().getEncoded();
                    BigInteger serialNumber = new X509CertificateHolder(c.getEncoded()).getSerialNumber();
                    String certificateAlias = new String(serialNumber.toByteArray(), StandardCharsets.UTF_8);
                    String subjectString = new X509CertificateHolder(c.getEncoded()).getSubject().toString();
                    SubjectDto subject = SubjectUtil.extractSubject(subjectString);
                    if (!check(certificateDtos, certificateAlias)) {
                        List<ExtensionDto> extensions = ExtensionsUtil.convertToMap(new X509CertificateHolder(c.getEncoded()).getExtensions());
                        Date startDate = new X509CertificateHolder(c.getEncoded()).getNotBefore();
                        Date endDate = new X509CertificateHolder(c.getEncoded()).getNotAfter();
                        if ((!valid && findByAlias(certificateAlias) != null) || (valid && findByAlias(certificateAlias) == null)) {
                            if (verifyIssuerCertificate(endDate)) {
                                if (serialNumber.equals(new BigInteger(root.getAlias().getBytes()))) {
                                    CertificateDto certificateDto = new CertificateDto(serialNumber, certificateAlias, startDate, endDate, subject, extensions, true, false);
                                    certificateDtos.add(certificateDto);
                                } else if (serialNumber.equals(new BigInteger(intermediate.getAlias().getBytes()))) {
                                    CertificateDto certificateDto = new CertificateDto(serialNumber, certificateAlias, startDate, endDate, subject, extensions, false, true);
                                    certificateDtos.add(certificateDto);
                                } else {
                                    CertificateDto certificateDto = new CertificateDto(serialNumber, certificateAlias, startDate, endDate, subject, extensions, false, false);
                                    certificateDtos.add(certificateDto);
                                }

                            }
                        }
                    }
                }
            }
        }
        return certificateDtos;


    }

    @Override
    public List<CertificateDto> findAllRevokedCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        return findCertificates(false);

    }
    @Override
    public List<CertificateDto> findAllValidCertificates() throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        return findCertificates(true);

    }

    @Override
    public boolean verifyIssuerCertificate (Date endDate) {
        if (endDate.compareTo(new Date()) < 0)
            return false;
        return true;
    }

}

