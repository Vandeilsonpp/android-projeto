package com.example.ultimavez.service;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.PasswordEncrypter;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.DTO.RedefinePasswordDTO;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.persistence.UserPersistence;

public class UserService {

    private UserValidatorService validator;
    private final UserPersistence database;

    public UserService() {
        database = MyCustomApplication.getUserPersistence();
        validator = new UserValidatorService();
    }

    // Construtor para facilitar os testes
    protected UserService(UserPersistence database, UserValidatorService validator) {
        this.database = database;
        this.validator = validator;
    }

    public Result<User> saveUser(User user) {
        Result<User> validationResult =  validator.validateUser(user);
        if (!validationResult.isValid()) {
            return Result.invalid(validationResult.getErrors());
        }

        if (database.existsByEmail(user.getEmail())) {
            return Result.invalid("E-mail já cadastrado");
        }

        user.setPassword(encryptPassword(user.getPassword()));

        Result<User> dbResult = database.saveUser(user);
        if (!dbResult.isValid()) {
            return Result.invalid("Ocorreu um problema interno. Tente novamente mais tarde");
        }

        return Result.valid(user);
    }

    public Result<RedefinePasswordDTO> updatePassword(RedefinePasswordDTO userData) {
        Result<RedefinePasswordDTO> result = new Result<>(userData);

        if (!database.existsByEmail(userData.getEmail())) {
            result.addError("Usuário não encontrado");
        }

        if (!userData.getNewPassword().equals(userData.getNewPasswordConfirmation())) {
            result.addError("Senha com dados divergentes");
        }

        String encryptedPassword = encryptPassword(userData.getNewPassword());
        database.updatePassword(userData.getEmail(), encryptedPassword);

        return result;
    }

    private String encryptPassword(String password) {
        return PasswordEncrypter.encryptPassword(password);
    }

}
