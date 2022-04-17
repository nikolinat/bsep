package com.bsep.admin.crypto.pki.certificates;

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
            if(generateCertificateDto.getKeyUsagesExtension() != null) {
                addKeyUsage(generateCertificateDto.getKeyUsagesExtension());
            }

            // dodavanje authorityKeyIdentifier
            if(generateCertificateDto.getGeneralNamesForAuthorityKeyIdentifier() != null) {
                addAuthorityKeyIdentifier(subjectData.getPublicKey().getEncoded(), generateCertificateDto.getGeneralNamesForAuthorityKeyIdentifier(),
                        new BigInteger(subjectData.getSerialNumber().getBytes()));
            }

            // Dodavanje subject alternateive names
            if(generateCertificateDto.getSubjectAlternativeNames() != null) {
                addSubjectAlternativeNameExtension(generateCertificateDto.getSubjectAlternativeNames());
            }

            // Dodavanje extended key usages
            if(generateCertificateDto.getExtendedKeyUsages() != null) {
                addExtendedKeyUsageExtension(generateCertificateDto.getExtendedKeyUsages());
            }

            // Dodavanje policy constraints extenstion
            if(generateCertificateDto.getInhibitPolicyMapping() != null && generateCertificateDto.getRequireExplicitPolicy() != null) {
                addPolicyConstraintsExtension(generateCertificateDto.getRequireExplicitPolicy(), generateCertificateDto.getInhibitPolicyMapping());
            }

            // Dodavanje name constraint extension
            // Ne mogu da potrefim formate general name preko postmana..
//            if(generateCertificateDto.getPermitedSubtrees() != null || generateCertificateDto.getExcludedSubtrees() != null) {
//                List<GeneralSubtree> permitedSubtrees = new ArrayList<>();
//                List<GeneralSubtree> excludedSubtrees = new ArrayList<>();
//                generateCertificateDto.getExcludedSubtrees().forEach(element -> excludedSubtrees.add(new GeneralSubtree(
//                        new GeneralName(element.getTag(), element.getObj()), element.getMinimin(), element.getMaximum()
//                )));
//                generateCertificateDto.getPermitedSubtrees().forEach(element -> permitedSubtrees.add(new GeneralSubtree(
//                        new GeneralName(element.getTag(), element.getObj()), element.getMinimin(), element.getMaximum()
//                )));
//                addNameConstraintsExtension(permitedSubtrees, excludedSubtrees);
//            }

            if(generateCertificateDto.getIssuerAlternativeNames() != null) {
                addIssuerAlternativeNameExtension(generateCertificateDto.getIssuerAlternativeNames());
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
            keyUsages.add(KeyUsage.keyCertSign);
            keyUsages.add(KeyUsage.cRLSign);
            addKeyUsage(keyUsages);

            List<GeneralSubtree> permittedSubtrees = new ArrayList<>();
            List<GeneralSubtree> excludedSubtrees = new ArrayList<>();

            GeneralName generalName = new GeneralName(7, "0.0.0.0");
            GeneralName generalName1 = new GeneralName(7, "1.1.1.1");
            GeneralSubtree g = new GeneralSubtree(generalName, new BigInteger("1"), new BigInteger("2"));
            GeneralSubtree s = new GeneralSubtree(generalName1, new BigInteger("3"), new BigInteger("4"));
            permittedSubtrees.add(g);
            excludedSubtrees.add(s);

            addNameConstraintsExtension(permittedSubtrees, excludedSubtrees);

            Map<SubjectAlternativeName, String> generalNames = new HashMap<>();
            //OVO JE OBLIK
            generalNames.put(SubjectAlternativeName.DirectoryName, "CN=r,OU=r,O=r,L=r,ST=r,C=rs");
            addAuthorityKeyIdentifier(subjectData.getPublicKey().getEncoded(), generalNames, new BigInteger(subjectData.getSerialNumber().getBytes()));

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


