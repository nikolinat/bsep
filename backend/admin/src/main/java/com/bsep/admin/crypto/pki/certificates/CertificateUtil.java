package com.bsep.admin.crypto.pki.certificates;

import com.bsep.admin.crypto.pki.data.IssuerData;
import com.bsep.admin.crypto.pki.data.SubjectData;
import com.bsep.admin.crypto.pki.keystores.KeyStoreReader;
import com.bsep.admin.crypto.pki.keystores.KeyStoreWriter;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CertificateUtil {
    KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
    KeyStoreReader keyStoreReader = new KeyStoreReader();
    CertificateGenerator certificateGenerator = new CertificateGenerator();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private final String password = "bsep";

    public CertificateUtil() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public String makeFilePath() {
        return "src/main/java/files/keystores/bsep.jks";
    }

    public void createNewKeyStore() {
        keyStoreWriter.loadKeyStore(null, password.toCharArray());
        keyStoreWriter.saveKeyStore(makeFilePath(), password.toCharArray());
    }

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }

    private X500NameBuilder generateBuilder() {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Pera Peric");
        builder.addRDN(BCStyle.SURNAME, "Peric");
        builder.addRDN(BCStyle.GIVENNAME, "Pera");
        builder.addRDN(BCStyle.O, "SIIT");
        builder.addRDN(BCStyle.OU, "BSEP");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "tim4@uns.ac.rs");

        builder.addRDN(BCStyle.UID, "123456789");
        return builder;
    }

    public void createCACertificate() throws NoSuchAlgorithmException, NoSuchProviderException, ParseException, IOException {
        KeyPair issuerPair = generateKeyPair();
        Date startDate = formatter.parse("2022-04-10");
        Date endDate = formatter.parse("2023-04-10");

        String sn = UUID.randomUUID().toString();
        X500NameBuilder builder = generateBuilder();
        SubjectData subjectData = new SubjectData(issuerPair.getPublic(), builder.build(), sn, startDate, endDate);
        IssuerData issuerData = new IssuerData(issuerPair.getPrivate(), builder.build());

        Certificate certificate = certificateGenerator.generateCACertificate(subjectData, issuerData);

        String keystoreFileName = makeFilePath();
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.writeRoot("root", issuerPair.getPrivate(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());
    }

    public void createIntermediateCertificate() throws NoSuchAlgorithmException, NoSuchProviderException, ParseException, IOException {
        KeyPair subjectPair = generateKeyPair();
        Date startDate = formatter.parse("2022-04-10");
        Date endDate = formatter.parse("2023-04-10");

        String sn = UUID.randomUUID().toString();


        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Steva Stevic");
        builder.addRDN(BCStyle.SURNAME, "Stevic");
        builder.addRDN(BCStyle.GIVENNAME, "Steva");
        builder.addRDN(BCStyle.O, "SIIT");
        builder.addRDN(BCStyle.OU, "BSEP");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "tim4@uns.ac.rs");

        builder.addRDN(BCStyle.UID, "987654321");
        SubjectData subjectData = new SubjectData(subjectPair.getPublic(), builder.build(), sn, startDate, endDate);


        IssuerData issuerData = keyStoreReader.readIssuerFromStore(makeFilePath(), "root", password.toCharArray(), password.toCharArray());
        Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData);

        String keystoreFileName = makeFilePath();
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.write("intermediate", "root", subjectPair.getPrivate(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());

    }

}
