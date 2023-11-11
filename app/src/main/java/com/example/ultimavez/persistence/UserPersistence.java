package com.example.ultimavez.persistence;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;

import java.util.Optional;

public interface UserPersistence {

    Result<User> saveUser(User user);
    Optional<User> findByEmailAndType(String email, UserEnum userType);
    void updatePassword(String email, String newPassword);
    boolean existsByEmail(String email);
}
