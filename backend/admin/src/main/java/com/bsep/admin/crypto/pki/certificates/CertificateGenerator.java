package com.bsep.admin.crypto.pki.certificates;

import com.bsep.admin.crypto.pki.data.IssuerData;
import com.bsep.admin.crypto.pki.data.SubjectData;
import com.bsep.admin.crypto.pki.enums.SubjectAlternativeName;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.*;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.its.asn1.IssuerIdentifier;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.asn1.x509.PolicyConstraints;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CertificateGenerator {
    private X509v3CertificateBuilder certificateGenerator;
    public CertificateGenerator() {
    }


    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData) throws IOException {
        try {
            // Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
            // Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
            // Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");

            // Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
            builder = builder.setProvider("BC");

            // Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            // Postavljaju se podaci za generisanje sertifiakta
            certificateGenerator = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber().getBytes()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            SubjectKeyIdentifier subjectKeyIdentifier = new JcaX509ExtensionUtils().createSubjectKeyIdentifier(subjectData.getPublicKey());
            certificateGenerator.addExtension(Extension.subjectKeyIdentifier, false, subjectKeyIdentifier);

            certificateGenerator.addExtension(Extension.basicConstraints, true, new BasicConstraints(true));

            // dodavanje keyUsage
            List<Integer> keyUsages = new ArrayList<>();
            keyUsages.add(KeyUsage.keyCertSign);
            keyUsages.add(KeyUsage.keyCertSign);
            addKeyUsage(keyUsages);

            // dodavanje authorityKeyIdentifier
            Map<SubjectAlternativeName, String> generalNames = new HashMap<>();
            generalNames.put(SubjectAlternativeName.Rfc822Name, "user@mail.com");
            generalNames.put(SubjectAlternativeName.RegisteredID, "0.0.1");
            addAuthorityKeyIdentifier(subjectData.getPublicKey().getEncoded(), generalNames, new BigInteger(subjectData.getSerialNumber().getBytes()));


            // Za testiranje samo
            Map<SubjectAlternativeName, String> subjectAlternativeNames = new HashMap<>();
            subjectAlternativeNames.put(SubjectAlternativeName.Rfc822Name, "user@mail.com");
            subjectAlternativeNames.put(SubjectAlternativeName.DNSName, "test.com");
            subjectAlternativeNames.put(SubjectAlternativeName.IPAddress, "127.0.0.1");
            addSubjectAlternativeNameExtension(subjectAlternativeNames);



            // Za testiranje
            List<KeyPurposeId> extendedKeyUsages = new ArrayList<>();
            extendedKeyUsages.add(KeyPurposeId.anyExtendedKeyUsage);
            extendedKeyUsages.add(KeyPurposeId.id_kp_clientAuth);
            extendedKeyUsages.add(KeyPurposeId.id_kp_serverAuth);
            addExtendedKeyUsageExtension(extendedKeyUsages);

            addPolicyConstraintsExtension(new BigInteger("1"), new BigInteger("1"));

            List<GeneralSubtree> permitedSubtrees = new ArrayList<>();
            List<GeneralSubtree> excludedSubtrees = new ArrayList<>();
            permitedSubtrees.add(new GeneralSubtree(new GeneralName(GeneralName.rfc822Name, "nesti"), new BigInteger("1"), new BigInteger("5")));
            excludedSubtrees.add(new GeneralSubtree(new GeneralName(GeneralName.uniformResourceIdentifier, "nesto1"), new BigInteger("2"), new BigInteger("8")));
            addNameConstraintsExtension(permitedSubtrees, excludedSubtrees);

            Map<SubjectAlternativeName, String> issuerAlternativeNames = new HashMap<>();
            issuerAlternativeNames.put(SubjectAlternativeName.Rfc822Name, "user@mail.com");
            addIssuerAlternativeNameExtension(issuerAlternativeNames);


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

    public void addExtendedKeyUsageExtension(List<KeyPurposeId> extendedKeyUsages) throws CertIOException {
        ASN1EncodableVector purposes = new ASN1EncodableVector();
//        purposes.add(KeyPurposeId.id_kp_serverAuth);
//        purposes.add(KeyPurposeId.id_kp_clientAuth);
//        purposes.add(KeyPurposeId.anyExtendedKeyUsage);
        extendedKeyUsages.forEach(usage -> purposes.add(usage));

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
}


