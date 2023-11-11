package com.example.ultimavez.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.UUID;

public class PasswordEncrypterTest {

    private String inputString = "P13fd$2d%$#@";

    @Test
    public void encryptStringShouldReturnUUIDEncryptedFormat() {
        String encryptedString = PasswordEncrypter.encryptPassword(inputString);

        boolean isValidUUID = true;
        try {
            UUID.fromString(encryptedString);
        } catch (IllegalArgumentException e) {
            isValidUUID = false;
        }

        assertTrue(isValidUUID);
        assertTrue(encryptedString.matches("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"));
    }

    @Test
    public void validateInvalidPasswordShouldReturnFalse() {
        String hashedPassword = PasswordEncrypter.encryptPassword(inputString);

        assertFalse(PasswordEncrypter.validatePassword("invalidPassword", hashedPassword));
    }

    @Test
    public void validateValidPasswordShouldReturnTrue() {
        String hashedPassword = PasswordEncrypter.encryptPassword(inputString);

        assertTrue(PasswordEncrypter.validatePassword(inputString, hashedPassword));
    }
}
