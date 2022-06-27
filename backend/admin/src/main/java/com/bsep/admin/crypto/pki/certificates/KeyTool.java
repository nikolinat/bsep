package com.bsep.admin.crypto.pki.certificates;

import com.bsep.admin.app.dto.GenerateCertificateDto;
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
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class KeyTool {
    KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
    KeyStoreReader keyStoreReader = new KeyStoreReader();
    CertificateGenerator certificateGenerator = new CertificateGenerator();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public KeyTool() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static void main(String[] args) throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, NoSuchProviderException, ParseException {
        KeyTool kt = new KeyTool();
        System.out.println("===== Konzolna aplikacija za upravljanje sertifikatima i kljucevima =====");
        Scanner keyboard = new Scanner(System.in);
        String seralNumber = "";
        int choice = 0;
        do {
            kt.menu();
            choice = keyboard.nextInt();
            switch (choice) {
                case 1: {
                    kt.createNewKeyStore();
                    break;
                }
                case 2: {
                    kt.showKeyStoreContent();
                    break;
                }
                case 3: {
                    kt.createNewSelfSignedCertificate();
                    break;
                }
                case 4: {
                    kt.createNewIssuedCertificate();
                    break;
                }
                case 5: {
                    seralNumber = CertificateUtil.createCACertificate();
                    break;
                }
                case 6: {
                    CertificateUtil.createIntermediateCertificate(seralNumber);
                    break;
                }
            }
        } while (choice != 7);
        keyboard.close();
    }

    public String makeFilePath(String fileName) {
        return "src/main/java/files/keystores/" + fileName + ".jks";
    }

    private void createNewKeyStore() {
        // TODO: Upotrebom klasa iz primeri/pki paketa, implementirati funkciju gde korisnik unosi ime keystore datoteke i ona se kreira
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter name for keystore:");
        String keystoreName = keyboard.nextLine();
        System.out.println("Enter password for keystore:");
        String password = keyboard.nextLine();
        keyStoreWriter.loadKeyStore(null, password.toCharArray());
        keyStoreWriter.saveKeyStore(makeFilePath(keystoreName), password.toCharArray());
    }

    private void showKeyStoreContent() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, NoSuchProviderException {
        // TODO: Upotrebom klasa iz primeri/pki paketa, prikazati sadrzaj keystore-a, gde korisnik unosi ime i lozinku
        // Dozvoljeno je menjati klase iz primeri/pki paketa
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter name for keystore:");
        String keystore = keyboard.nextLine();
        System.out.println("Enter password for keystore:");
        String password = keyboard.nextLine();
        String keystoreFileName = makeFilePath(keystore);
        List<String> aliases = keyStoreReader.getAllAliases(keystoreFileName, password);
        // OVDE SAM IZBACILA ROOT I INTERMEDIATE JER SE U OKVIRU POSLEDNJEG U NIZU UCITAJU I ONI, MISLIM DA JE TO OK
        aliases.removeIf(a -> a.equals("root") || a.equals("intermediate"));
        for (String a : aliases) {
            Certificate[] certificate = keyStoreReader.readCertificate(keystoreFileName, password, a);
            for (Certificate c : certificate) {
                System.out.println(c);
            }
        }


    }

    private KeyPair generateKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(2048, random);
        return keyGen.generateKeyPair();
    }

    private void createNewSelfSignedCertificate() throws ParseException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        // TODO: Upotrebom klasa iz primeri/pki paketa, prikazati sadrzaj keystore-a, gde korisnik unosi sve potrebne podatke
        // Radi ustede hardkodovati podatke vezane za subjekta sertifikata
        KeyPair keyPair = generateKeyPair();

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = keyboard.nextLine();
        System.out.println("Enter surname:");
        String surname = keyboard.nextLine();
        System.out.println("Enter organization:");
        String organization = keyboard.nextLine();
        System.out.println("Enter organization unit name:");
        String organizationUnit = keyboard.nextLine();
        System.out.println("Enter country code:");
        String countryCode = keyboard.nextLine();
        System.out.println("Enter email address:");
        String email = keyboard.nextLine();
        System.out.println("Enter user id:");
        String uid = keyboard.nextLine();
        System.out.println("Enter start date(yyyy-MM-dd):");
        String start = keyboard.nextLine();
        System.out.println("Enter end date(yyyy-MM-dd):");
        String end = keyboard.nextLine();

        System.out.println("Enter keystore name:");
        String keystore = keyboard.nextLine();
        System.out.println("Enter password:");
        String password = keyboard.nextLine();

        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);

        String sn = UUID.randomUUID().toString();

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, name.concat(" ").concat(surname));
        builder.addRDN(BCStyle.SURNAME, surname);
        builder.addRDN(BCStyle.GIVENNAME, name);
        builder.addRDN(BCStyle.O, organization);
        builder.addRDN(BCStyle.OU, organizationUnit);
        builder.addRDN(BCStyle.C, countryCode);
        builder.addRDN(BCStyle.E, email);
        builder.addRDN(BCStyle.UID, uid);

        SubjectData subjectData = new SubjectData(keyPair.getPublic(), builder.build(), sn, startDate, endDate);

        IssuerData issuerData = new  IssuerData(keyPair.getPrivate(), builder.build());
        Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData, new GenerateCertificateDto());

        String keystoreFileName = makeFilePath(keystore);
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.write("certificate" + sn, "intermediate", keyPair.getPrivate(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());

    }


    private void createNewIssuedCertificate() throws ParseException, NoSuchAlgorithmException, NoSuchProviderException, IOException {
        // TODO: Upotrebom klasa iz primeri/pki paketa, prikazati sadrzaj keystore-a, gde korisnik unosi sve potrebne podatke
        // Radi ustede vremena hardkodovati podatke vezane za subjekta sertifikata
        KeyPair keyPair = generateKeyPair();

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter name:");
        String name = keyboard.nextLine();
        System.out.println("Enter surname:");
        String surname = keyboard.nextLine();
        System.out.println("Enter organization:");
        String organization = keyboard.nextLine();
        System.out.println("Enter organization unit name:");
        String organizationUnit = keyboard.nextLine();
        System.out.println("Enter country code:");
        String countryCode = keyboard.nextLine();
        System.out.println("Enter email address:");
        String email = keyboard.nextLine();
        System.out.println("Enter user id:");
        String uid = keyboard.nextLine();
        System.out.println("Enter start date(yyyy-MM-dd):");
        String start = keyboard.nextLine();
        System.out.println("Enter end date(yyyy-MM-dd):");
        String end = keyboard.nextLine();

        System.out.println("Enter keystore name:");
        String keystore = keyboard.nextLine();
        System.out.println("Enter password:");
        String password = keyboard.nextLine();

        Date startDate = formatter.parse(start);
        Date endDate = formatter.parse(end);

        String sn = UUID.randomUUID().toString();

        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, name.concat(" ").concat(surname));
        builder.addRDN(BCStyle.SURNAME, surname);
        builder.addRDN(BCStyle.GIVENNAME, name);
        builder.addRDN(BCStyle.O, organization);
        builder.addRDN(BCStyle.OU, organizationUnit);
        builder.addRDN(BCStyle.C, countryCode);
        builder.addRDN(BCStyle.E, email);
        builder.addRDN(BCStyle.UID, uid);

        SubjectData subjectData = new SubjectData(keyPair.getPublic(), builder.build(), sn, startDate, endDate);

        String alias = "intermediate";
        String pass = "bsep";

        IssuerData issuerData = keyStoreReader.readIssuerFromStore(makeFilePath(pass), alias, pass.toCharArray(), pass.toCharArray(), false);
        Certificate certificate = certificateGenerator.generateCertificate(subjectData, issuerData, new GenerateCertificateDto());

        String keystoreFileName = makeFilePath(keystore);
        keyStoreWriter.loadKeyStore(keystoreFileName, password.toCharArray());
        keyStoreWriter.write(sn, "intermediate", issuerData.getPrivateKey(), password.toCharArray(), certificate);
        keyStoreWriter.saveKeyStore(keystoreFileName, password.toCharArray());

    }

    private void menu() {
        System.out.println("==================================");
        System.out.println("1.	Create new key store");
        System.out.println("2.	Show key store content");
        System.out.println("3.	Create new self signed certificate");
        System.out.println("4.	Create new issued certificate");
        System.out.println("5.  Create CA certificate");
        System.out.println("6.  Create intermediate certificate");
        System.out.println("7.	Exit");
        System.out.print(">>>");
    }

}
