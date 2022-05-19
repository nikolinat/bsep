package com.bsep.admin.app.utils;

import com.github.nbaars.pwnedpasswords4j.client.PwnedPasswordChecker;
import com.github.nbaars.pwnedpasswords4j.client.PwnedPasswordClient;
import okhttp3.OkHttpClient;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class PasswordUtil {

    private static PwnedPasswordClient client = new PwnedPasswordClient(new OkHttpClient(), "https://api.pwnedpasswords.com/range", "");
    private static PwnedPasswordChecker checker = new PwnedPasswordChecker(client);

    public static byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static byte[] hashPassword(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        int iterations = 1000;
        char[] chars = password.toCharArray();

        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        byte[] hash = skf.generateSecret(spec).getEncoded();
        return hash;
    }

    public static boolean check(String password){
        return checker.check(password);
    }
}
