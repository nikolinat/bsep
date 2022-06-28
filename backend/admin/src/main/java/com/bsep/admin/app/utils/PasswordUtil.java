package com.bsep.admin.app.utils;

import com.github.nbaars.pwnedpasswords4j.client.PwnedPasswordChecker;
import com.github.nbaars.pwnedpasswords4j.client.PwnedPasswordClient;
import okhttp3.OkHttpClient;


public class PasswordUtil {

    private static PwnedPasswordClient client = new PwnedPasswordClient(new OkHttpClient(), "https://api.pwnedpasswords.com/range", "");
    private static PwnedPasswordChecker checker = new PwnedPasswordChecker(client);

    public static boolean check(String password){
        return checker.check(password);
    }
}
