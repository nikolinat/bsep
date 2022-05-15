package com.bsep.admin.crypto.pki.certificates;

import com.bsep.admin.app.dto.GeneralSubtreeElement;
import com.bsep.admin.app.dto.GenerateCertificateDto;
import com.bsep.admin.crypto.pki.data.IssuerData;
import com.bsep.admin.crypto.pki.data.SubjectData;
import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.asn1.x509.PolicyConstraints;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CertificateGenerator {
    private X509v3CertificateBuilder certificateGenerator;

    public CertificateGenerator() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }


    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData, GenerateCertificateDto generateCertificateDto) throws IOException {
        try {

            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");


            builder = builder.setProvider("BC");


            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            certificateGenerator = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber().getBytes()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(subjectData.getPublicKey());
            certificateGenerator.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);

            // dodavanje keyUsage
            if (generateCertificateDto.getKeyUsagesExtension() != null) {
                addKeyUsage(generateCertificateDto.getKeyUsagesExtension());
            }

            // dodavanje authorityKeyIdentifier
            if (generateCertificateDto.getGeneralNamesForAuthorityKeyIdentifier() != null) {
                addAuthorityKeyIdentifier(subjectData.getPublicKey().getEncoded(), generateCertificateDto.getGeneralNamesForAuthorityKeyIdentifier().getMapFromObject(),
                        new BigInteger(subjectData.getSerialNumber().getBytes()));
            }

            // Dodavanje subject alternateive names
            if (generateCertificateDto.getSubjectAlternativeNames() != null) {
                addSubjectAlternativeNameExtension(generateCertificateDto.getSubjectAlternativeNames().getMapFromObject());
            }

            // Dodavanje extended key usages
            if (generateCertificateDto.getExtendedKeyUsages() != null) {
                addExtendedKeyUsageExtension(generateCertificateDto.getExtendedKeyUsages());
            }

            // Dodavanje policy constraints extenstion
            if (generateCertificateDto.getInhibitPolicyMapping() != null && generateCertificateDto.getRequireExplicitPolicy() != null) {
                addPolicyConstraintsExtension(generateCertificateDto.getRequireExplicitPolicy(), generateCertificateDto.getInhibitPolicyMapping());
            }

            // Dodavanje name constraint extension
            if (generateCertificateDto.getPermitedSubtrees() != null || generateCertificateDto.getExcludedSubtrees() != null) {
                addNameConstraintsExtension(mapGeneralSubtree(generateCertificateDto.getPermitedSubtrees()),
                        mapGeneralSubtree(generateCertificateDto.getExcludedSubtrees()));
            }

            // Dodavanje issuera alternative name extension
            if (generateCertificateDto.getIssuerAlternativeNames() != null) {
                addIssuerAlternativeNameExtension(generateCertificateDto.getIssuerAlternativeNames().getMapFromObject());
            }

            // Generise se sertifikat
            X509CertificateHolder certHolder = certificateGenerator.build(contentSigner);

            // Builder generise sertifikat kao objekat klase X509CertificateHolder
            // Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            // Konvertuje objekat u sertifikat
            return certConverter.getCertificate(certHolder);

        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException | CertIOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addSubjectAlternativeNameExtension(Map<SubjectAlternativeName, String> names) throws CertIOException {
        List<GeneralName> alternativeNames = new ArrayList<>();
        names.forEach((key, value) -> alternativeNames.add(new GeneralName(key.getValue(), value)));
        GeneralNames subjectAlternativeNames = GeneralNames.getInstance(new DERSequence((GeneralName[]) alternativeNames.toArray(new GeneralName[]{})));
        certificateGenerator.addExtension(Extension.subjectAlternativeName, false, subjectAlternativeNames);
    }

    public void addExtendedKeyUsageExtension(List<String> extendedKeyUsages) throws CertIOException {
        List<KeyPurposeId> keyPurposeIds = new ArrayList<>();
        extendedKeyUsages.forEach(usage -> keyPurposeIds.add(new KeyPurposeId(usage)));
        ASN1EncodableVector purposes = new ASN1EncodableVector();
        keyPurposeIds.forEach(id -> purposes.add(id));

        certificateGenerator.addExtension(Extension.extendedKeyUsage, false, new DERSequence(purposes));
    }

    public void addKeyUsage(List<Integer> keyUsages) throws CertIOException {
        int usage = 0;
        for (Integer keyUsage : keyUsages) {
            usage = usage | keyUsage;
        }
        certificateGenerator.addExtension(Extension.keyUsage, false, new KeyUsage(usage));
    }

    public void addAuthorityKeyIdentifier(byte[] keyIdentifier, Map<SubjectAlternativeName, String> names, BigInteger bigInteger) throws CertIOException {
        List<GeneralName> alternativeNames = new ArrayList<>();
        names.forEach((key, value) -> alternativeNames.add(new GeneralName(key.getValue(), value)));
        GeneralNames generalNames = GeneralNames.getInstance(new DERSequence(alternativeNames.toArray(new GeneralName[]{})));
        certificateGenerator.addExtension(Extension.authorityKeyIdentifier, false, new AuthorityKeyIdentifier(keyIdentifier, generalNames, bigInteger));
    }

    public void addPolicyConstraintsExtension(BigInteger requireExplicitPolicy, BigInteger inhibitPolicyMapping) throws IOException {
        PolicyConstraints policyConstraintsExtension = new PolicyConstraints(requireExplicitPolicy, inhibitPolicyMapping);
        certificateGenerator.addExtension(Extension.policyConstraints, false, policyConstraintsExtension);
    }

    public void addNameConstraintsExtension(List<GeneralSubtree> permittedSubtrees, List<GeneralSubtree> excludedSubtrees) throws CertIOException {
        GeneralSubtree[] permittedSubtreesArray = permittedSubtrees.toArray(new GeneralSubtree[permittedSubtrees.size()]);
        GeneralSubtree[] excludedSubtreesArray = excludedSubtrees.toArray(new GeneralSubtree[excludedSubtrees.size()]);
        NameConstraints nameConstraints = new NameConstraints(permittedSubtreesArray, excludedSubtreesArray);

        certificateGenerator.addExtension(Extension.nameConstraints, false, nameConstraints);
    }

    public void addIssuerAlternativeNameExtension(Map<SubjectAlternativeName, String> names) throws CertIOException {
        List<GeneralName> alternativeNames = new ArrayList<>();
        names.forEach((key, value) -> alternativeNames.add(new GeneralName(key.getValue(), value)));

        certificateGenerator.addExtension(Extension.issuerAlternativeName, false, new DERSequence(alternativeNames.toArray(new GeneralName[]{})));
    }

    public List<GeneralSubtree> mapGeneralSubtree(List<GeneralSubtreeElement> generalSubtreeElements) {

        List<GeneralSubtree> generalSubtrees = new ArrayList<>();
        generalSubtreeElements.forEach(generalSubtreeElement -> generalSubtrees.add(new GeneralSubtree(
                new GeneralName(generalSubtreeElement.getTag(), generalSubtreeElement.getObj()),
                generalSubtreeElement.getMinimin(), generalSubtreeElement.getMaximum()
        )));

        return generalSubtrees;

    }

    public X509Certificate generateCACertificate(SubjectData subjectData, IssuerData issuerData) {
        try {

            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            certificateGenerator = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber().getBytes()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(subjectData.getPublicKey());
            certificateGenerator.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);

            certificateGenerator.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));

            List<Integer> keyUsages = new ArrayList<>();
            keyUsages.add(KeyUsage.digitalSignature);
            keyUsages.add(KeyUsage.keyEncipherment);
            keyUsages.add(KeyUsage.dataEncipherment);
            keyUsages.add(KeyUsage.nonRepudiation);
            keyUsages.add(KeyUsage.cRLSign);
            keyUsages.add(KeyUsage.keyCertSign);
            addKeyUsage(keyUsages);

            List<String> extendedKeyUsages = new ArrayList<>();
            extendedKeyUsages.add("1.3.6.1.5.5.7.3.1");
            extendedKeyUsages.add("1.3.6.1.5.5.7.3.2");
            addExtendedKeyUsageExtension(extendedKeyUsages);

            Map<SubjectAlternativeName, String> subjectAlternativeNames = new HashMap<>();
            subjectAlternativeNames.put(SubjectAlternativeName.DNSName, "localhost");
            subjectAlternativeNames.put(SubjectAlternativeName.IPAddress, "127.0.0.1");

            addSubjectAlternativeNameExtension(subjectAlternativeNames);
            X509CertificateHolder certHolder = certificateGenerator.build(contentSigner);

            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            return certConverter.getCertificate(certHolder);

        } catch (IllegalArgumentException | IllegalStateException | OperatorCreationException | CertificateException | CertIOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}


