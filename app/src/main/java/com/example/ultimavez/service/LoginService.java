package com.example.ultimavez.service;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.PasswordEncrypter;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.DTO.LoginDTO;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.persistence.UserPersistence;

import java.util.Optional;

public class LoginService {

    private final UserPersistence database;

    public LoginService() {
        this.database = MyCustomApplication.getUserPersistence();
    }

    // Constructor for testing purposes
    protected LoginService(UserPersistence database) {
        this.database = database;
    }

    public Result<User> login(LoginDTO loginDTO) {
        Result<User> result = new Result<>(null);

        Optional<User> user = database.findByEmailAndType(loginDTO.getEmail(), loginDTO.getUserType());
        if (!user.isPresent()) {
            result.addError("Usuário não encontrado");
            return result;
        }

        if (!PasswordEncrypter.validatePassword(loginDTO.getPassword(), user.get().getPassword())) {
            result.addError("Senha incorreta");
        }

        result.setResultObject(user.get());
        return result;
    }
}
