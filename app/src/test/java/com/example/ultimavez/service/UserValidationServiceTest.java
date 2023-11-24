package com.example.ultimavez.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.example.ultimavez.factories.UserFactory;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class UserValidationServiceTest {

    private final UserValidatorService validatorService = new UserValidatorService();
    private UserFactory validCustomer = UserFactory.validCustomer();

    @Test
    public void userWithMissingDataShouldHaveErrorInValidation() {
        User testUser = new User(UserEnum.CUSTOMER, "", "", "", "", "", "", "", "", "");
        Result<User> result = validatorService.validateUser(testUser);

        assertEquals(15, result.getErrors().size());
    }

    @Test
    public void userWithInvalidCPFShouldReturnError() {
        validCustomer.withDocument("12345678909");
        Result<User> result = validatorService.validateUser(validCustomer.build());

        assertEquals(1, result.getErrors().size());
        assertEquals("CPF inválido", result.getErrors().get(0));
    }

    @Test
    public void userWithDivergentPasswordShouldReturnError() {
        validCustomer.withPassword("Password1!");
        validCustomer.withPasswordAgain("Password2!");
        Result<User> result = validatorService.validateUser(validCustomer.build());

        assertEquals(1, result.getErrors().size());
        assertEquals("As senhas são diferentes", result.getErrors().get(0));
    }

    @Test
    public void userWithInvalidPhoneNumberShouldReturnError() {
        validCustomer.withPhoneNumber("11987654321");
        Result<User> result = validatorService.validateUser(validCustomer.build());

        assertEquals(1, result.getErrors().size());
        assertEquals("Telefone inválido", result.getErrors().get(0));
    }

    @Test
    public void userWithInvalidZipCodeShouldReturnError() {
        validCustomer.withZipCode("09876543");
        Result<User> result = validatorService.validateUser(validCustomer.build());

        assertEquals(1, result.getErrors().size());
        assertEquals("CEP inválido", result.getErrors().get(0));
    }

    @Test
    public void userWithInvalidEmailShouldReturnError() {
        validCustomer.withEmail("emailemail.com");
        Result<User> result = validatorService.validateUser(validCustomer.build());

        assertEquals(1, result.getErrors().size());
        assertEquals("E-mail inválido", result.getErrors().get(0));
    }

    @Test
    public void validUserShouldReturnSuccess() {
        Result<User> result = validatorService.validateUser(validCustomer.build());

        assertTrue(result.getErrors().isEmpty());
    }

    @Test
    @Parameters({
            "Passw0rd$, true",
            "Short1$, false",
            "password1$, false",
            "Password123, false",
    })
    public void testPasswordValidation(String password, boolean expected) {
        validCustomer.withPassword(password);
        validCustomer.withPasswordAgain(password);

        Result<User> result = validatorService.validateUser(validCustomer.build());
        assertEquals(expected, result.isValid());
    }

}
