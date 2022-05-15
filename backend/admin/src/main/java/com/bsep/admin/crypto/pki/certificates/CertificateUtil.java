package com.bsep.admin.crypto.pki.certificates;

import com.bsep.admin.app.dto.GenerateCertificateDto;
import com.bsep.admin.crypto.pki.data.IssuerData;
import com.bsep.admin.crypto.pki.data.SubjectData;
import com.bsep.admin.crypto.pki.keystores.KeyStoreReader;
import com.bsep.admin.crypto.pki.keystores.KeyStoreWriter;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CertificateUtil {
    // stavila sam da mi je metoda za issuera static pa su mi trebala static polja. Ako bude problema promenicemo.
    static KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
    static KeyStoreReader keyStoreReader = new KeyStoreReader();
    static CertificateGenerator certificateGenerator = new CertificateGenerator();
    static ZoneId defaultZoneId = ZoneId.systemDefault();
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

    public static String createCACertificate() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair issuerPair = generateKeyPair();

        LocalDate localStartDate = LocalDate.now();

        Date startDate = Date.from(localStartDate.atStartOfDay(defaultZoneId).toInstant());
        LocalDate localEndDate = localStartDate.plusYears(1);
        Date endDate = Date.from(localEndDate.atStartOfDay(defaultZoneId).toInstant());

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

    public static String createIntermediateCertificate(String issuerAlias) throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPair subjectPair = generateKeyPair();
        LocalDate localStartDate = LocalDate.now();

        Date startDate = Date.from(localStartDate.atStartOfDay(defaultZoneId).toInstant());
        LocalDate localEndDate = localStartDate.plusYears(1);
        Date endDate = Date.from(localEndDate.atStartOfDay(defaultZoneId).toInstant());

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
        Certificate certificate = certificateGenerator.generateCACertificate(subjectData, issuerData);

        String keystoreFileName = makeFilePath();
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.write(serialNumber, issuerAlias, subjectPair.getPrivate(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());
        return serialNumber;
    }

    public static IssuerData getIntermediateCertificateDetails(String intermediateAlias) {
        return keyStoreReader.readIssuerFromStore(makeFilePath(), intermediateAlias, password.toCharArray(), password.toCharArray(), false);
    }

    public static String getKeyStorePassword() {
        return password;
    }

    public static void createNewIssuedCertificate(String issuerAlias) throws NoSuchAlgorithmException, NoSuchProviderException, IOException {

        KeyPair keyPair = generateKeyPair();

        LocalDate localStartDate = LocalDate.now();

        Date startDate = Date.from(localStartDate.atStartOfDay(defaultZoneId).toInstant());
        LocalDate localEndDate = localStartDate.plusMonths(2);
        Date endDate = Date.from(localEndDate.atStartOfDay(defaultZoneId).toInstant());

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
        Certificate certificate = certificateGenerator.generateCertificate(subjectData,issuerData, new GenerateCertificateDto());
        String keystoreFileName = makeFilePath();
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.write(sn,issuerAlias, keyPair.getPrivate(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());

    }

    public static Certificate findCertificate(String alias) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        List<String> aliases = keyStoreReader.getAllAliases(CertificateUtil.makeFilePath(), CertificateUtil.getKeyStorePassword());
        for (String a : aliases) {
            Certificate[] certificates = keyStoreReader.readCertificate(CertificateUtil.makeFilePath(), CertificateUtil.getKeyStorePassword(), a);
            for (Certificate certificate : certificates) {
                BigInteger serialNumber = new X509CertificateHolder(certificate.getEncoded()).getSerialNumber();
                if (new String(serialNumber.toByteArray(), StandardCharsets.UTF_8).equals(alias)) {
                    return certificate;
                }
            }
        }
        return null;
    }

    public static String writeToFile(String alias) throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        Certificate certificate = findCertificate(alias);
        StringWriter sw = new StringWriter();
        try (PemWriter pw = new PemWriter(sw)) {
            PemObjectGenerator gen = new JcaMiscPEMGenerator(certificate);
            pw.writeObject(gen);
        }

        String fileName = "src/main/java/files/keystores/" + alias + ".cer";
        FileWriter fw = new FileWriter(fileName);
        fw.write(sw.toString());
        fw.close();

        return fileName;
    }
}
