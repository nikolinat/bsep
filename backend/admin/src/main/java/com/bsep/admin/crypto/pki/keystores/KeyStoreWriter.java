package com.bsep.admin.crypto.pki.keystores;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyStoreWriter {

    private KeyStore keyStore;

    public KeyStoreWriter() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public void loadKeyStore(String fileName, char[] password) {
        try {
            if (fileName != null) {
                keyStore.load(new FileInputStream(fileName), password);
            } else {
                keyStore.load(null, password);
            }
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveKeyStore(String fileName, char[] password) {
        try {
            keyStore.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String alias, String chain, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            List<Certificate> certificateList = new ArrayList<>(Arrays.asList(findCertificateChain(chain)));
            certificateList.add(certificate);
            Certificate[] certificates = certificateList.toArray(findCertificateChain(chain));
            keyStore.setKeyEntry(alias, privateKey, password, certificates);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public void writeRoot(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[]{certificate});
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }


    public Certificate[] findCertificateChain(String alias) throws KeyStoreException {
        return keyStore.getCertificateChain(alias);
    }
}
