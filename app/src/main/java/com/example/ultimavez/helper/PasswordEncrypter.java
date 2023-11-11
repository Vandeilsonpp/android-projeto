package com.example.ultimavez.helper;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PasswordEncrypter {

    public static String encryptPassword(String password) {
        return UUID.nameUUIDFromBytes(password.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public static boolean validatePassword(String plainTextPassword, String hashedPassword) {
        return hashedPassword.equals(encryptPassword(plainTextPassword));
    }
}
