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
import java.util.Scanner;
import java.util.UUID;

public class CertificateUtil {
    // stavila sam da mi je metoda za issuera static pa su mi trebala static polja. Ako bude problema promenicemo.
    static KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
    static KeyStoreReader keyStoreReader = new KeyStoreReader();
    static CertificateGenerator certificateGenerator = new CertificateGenerator();
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final String password = "bsep";

    public CertificateUtil() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String makeFilePath() {
        return "src/main/java/files/keystores/bsep.jks";
    }

    public static void createNewKeyStore() {
        keyStoreWriter.loadKeyStore(null, password.toCharArray());
        keyStoreWriter.saveKeyStore(makeFilePath(), password.toCharArray());
    }

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }

    private static X500NameBuilder generateBuilder() {
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

    public static String createCACertificate() throws NoSuchAlgorithmException, NoSuchProviderException, ParseException, IOException {
        KeyPair issuerPair = generateKeyPair();
        Date startDate = formatter.parse("2022-04-10");
        Date endDate = formatter.parse("2023-04-10");

        String serialNumber = UUID.randomUUID().toString();
        X500NameBuilder builder = generateBuilder();
        SubjectData subjectData = new SubjectData(issuerPair.getPublic(), builder.build(), serialNumber, startDate, endDate);
        IssuerData issuerData = new IssuerData(issuerPair.getPrivate(), builder.build());

        Certificate certificate = certificateGenerator.generateCACertificate(subjectData, issuerData);

        String keystoreFileName = makeFilePath();
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.writeRoot(serialNumber, issuerPair.getPrivate(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());
        return serialNumber;
    }

    public static String createIntermediateCertificate(String issuerAlias) throws NoSuchAlgorithmException, NoSuchProviderException, ParseException, IOException {
        KeyPair subjectPair = generateKeyPair();
        Date startDate = formatter.parse("2022-04-10");
        Date endDate = formatter.parse("2023-04-10");

        String serialNumber = UUID.randomUUID().toString();


        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "Steva Stevic");
        builder.addRDN(BCStyle.SURNAME, "Stevic");
        builder.addRDN(BCStyle.GIVENNAME, "Steva");
        builder.addRDN(BCStyle.O, "SIIT");
        builder.addRDN(BCStyle.OU, "BSEP");
        builder.addRDN(BCStyle.C, "RS");
        builder.addRDN(BCStyle.E, "tim4@uns.ac.rs");

        builder.addRDN(BCStyle.UID, "987654321");
        SubjectData subjectData = new SubjectData(subjectPair.getPublic(), builder.build(), serialNumber, startDate, endDate);


        IssuerData issuerData = keyStoreReader.readIssuerFromStore(makeFilePath(), issuerAlias, password.toCharArray(), password.toCharArray(), true);
        Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData);

        String keystoreFileName = makeFilePath();
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.write(serialNumber, issuerAlias, subjectPair.getPrivate(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());
        return serialNumber;
    }

    public static IssuerData getIntermediateCertificateDetails() {
        return keyStoreReader.readIssuerFromStore(makeFilePath(), "intermediate", password.toCharArray(), password.toCharArray(), false);
    }

    public static String getKeyStorePassword() {
        return password;
    }

    public static void createNewIssuedCertificate(String issuerAlias) throws ParseException, NoSuchAlgorithmException, NoSuchProviderException, IOException {

        KeyPair keyPair = generateKeyPair();

        Date startDate = formatter.parse("2022-05-05");
        Date endDate = formatter.parse("2022-06-05");

        String sn = UUID.randomUUID().toString();

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, "mika".concat(" ").concat("mikic"));
        builder.addRDN(BCStyle.SURNAME, "mikic");
        builder.addRDN(BCStyle.GIVENNAME, "mika");
        builder.addRDN(BCStyle.O, "ftn");
        builder.addRDN(BCStyle.OU, "ftn");
        builder.addRDN(BCStyle.C, "rs");
        builder.addRDN(BCStyle.E, "mika@");
        builder.addRDN(BCStyle.UID, "123");

        SubjectData subjectData = new SubjectData(keyPair.getPublic(), builder.build(), sn, startDate, endDate);

        IssuerData issuerData = keyStoreReader.readIssuerFromStore(makeFilePath(), issuerAlias,getKeyStorePassword().toCharArray(), getKeyStorePassword().toCharArray(), false);
        Certificate certificate = certificateGenerator.generateCertificate(subjectData,issuerData);
        String keystoreFileName = makeFilePath();
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.write(sn,issuerAlias, issuerData.getPrivateKey(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());

    }
}
