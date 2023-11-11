package com.example.ultimavez.service;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidatorService {

    private static final String CPF_REGEX = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";
    private static final String ZIP_CODE_REGEX = "\\d{5}-\\d{3}";
    private static final String PHONE_NUMBER_REGEX = "\\(\\d{2}\\)\\d{5}-\\d{4}";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final Integer MINIMUM_PASSWORD_LENGTH = 8;

    public Result<User> validateUser(User user) {
        Result<User> result = new Result<>(user);

        verifyNullValues(user, result);
        verifyMasks(user, result);
        verifyPassword(user.getPassword(), result);

        return result;
    }

    private void verifyNullValues(User user, Result<User> result) {
        if (isEmpty(user.getEmail())) {
            result.addError("Email é obrigatório");
        }

        if (isEmpty(user.getPassword())) {
            result.addError("Senha é obrigatório");
        }

        if (isEmpty(user.getFullName())) {
            result.addError("Nome completo é obrigatório");
        }

        if (isEmpty(user.getDocument())) {
            result.addError("Documento é obrigatório");
        }

        if (isEmpty(user.getAddress())) {
            result.addError("Endereço é obrigatório");
        }

        if (isEmpty(user.getCity())) {
            result.addError("Cidade é obrigatório");
        }

        if (isEmpty(user.getZipCode())) {
            result.addError("CEP é obrigatório");
        }

        if (isEmpty(user.getPhoneNumber())) {
            result.addError("Telefone é obrigatório");
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void verifyMasks(User user, Result<User> result) {
        if (invalidPattern(user.getDocument(), CPF_REGEX)) {
            result.addError("CPF inválido");
        }

        if (invalidPattern(user.getPhoneNumber(), PHONE_NUMBER_REGEX)) {
            result.addError("Telefone inválido");
        }

        if (invalidPattern(user.getZipCode(), ZIP_CODE_REGEX)) {
            result.addError("CEP inválido");
        }

        if (invalidPattern(user.getEmail(), EMAIL_REGEX)) {
            result.addError("E-mail inválido");
        }
    }

    private boolean invalidPattern(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return !matcher.matches();
    }


    private void verifyPassword(String password, Result<User> result) {
        if (password.length() < MINIMUM_PASSWORD_LENGTH) {
            result.addError("Senha deve ter pelo menos 8 caracteres");
        }

        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }

        if (!hasUppercase | !hasSpecialChar | !hasDigit) {
            result.addError("Senha deve pelo menos 1 número, 1 caracter especial e 1 letra maiúscula");
        }
    }

}
