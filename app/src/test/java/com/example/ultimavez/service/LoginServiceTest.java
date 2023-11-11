package com.example.ultimavez.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.DTO.LoginDTO;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;
import com.example.ultimavez.persistence.UserPersistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


public class LoginServiceTest {

    @Mock
    private UserPersistence database;

    @InjectMocks
    private LoginService loginService;

    private User genericUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        genericUser = new User(UserEnum.CUSTOMER, "email@gmail.com", "password", "Fulano da Silva", "123.456.789-90", "(11)91234-5678", "Rua das vitórias, 765", "09876-543", "São Paulo");
    }

    @Test
    public void loginWithUserFoundAndPasswordCorrect() {
        LoginDTO loginDTO = new LoginDTO("user@example.com", "password", UserEnum.CUSTOMER);

        when(database.findByEmailAndType("user@example.com", UserEnum.CUSTOMER)).thenReturn(Optional.of(genericUser));

        Result<User> result = loginService.login(loginDTO);

        assertEquals("Fulano da Silva", result.getResultObject().getFullName());
    }

    @Test
    public void loginWithUserNotFoundShouldReturnUserNotFound() {
        LoginDTO loginDTO = new LoginDTO("user@example.com", "password", UserEnum.CUSTOMER);

        when(database.findByEmailAndType("user@example.com", UserEnum.CUSTOMER)).thenReturn(Optional.empty());

        Result<User> result = loginService.login(loginDTO);

        assertEquals(1, result.getErrors().size());
        assertEquals("Usuário não encontrado", result.getErrors().get(0));
    }

    @Test
    public void loginWithIncorrectCredentialsShouldReturnInvalidPassword() {
        LoginDTO loginDTO = new LoginDTO("user@example.com", "password2", UserEnum.CUSTOMER);

        when(database.findByEmailAndType("user@example.com", UserEnum.CUSTOMER)).thenReturn(Optional.of(genericUser));

        Result<User> result = loginService.login(loginDTO);

        assertEquals(1, result.getErrors().size());
        assertEquals("Senha incorreta", result.getErrors().get(0));
    }
}
