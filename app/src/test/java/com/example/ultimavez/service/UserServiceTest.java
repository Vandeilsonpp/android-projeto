package com.example.ultimavez.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.ultimavez.factories.UserFactory;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.DTO.RedefinePasswordDTO;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;
import com.example.ultimavez.persistence.UserPersistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @Mock
    private UserPersistence database;

    @Mock
    private UserValidatorService validator;

    @InjectMocks
    private UserService userService;

    private User genericUser;
    private RedefinePasswordDTO userData;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        userData = new RedefinePasswordDTO("email.gmail.com", "123", "123");
        genericUser = UserFactory.validCustomer().build();
    }

    @Test
    public void saveUser_ValidUser_ReturnsValidResult() {
        Mockito.when(validator.validateUser(genericUser)).thenReturn(Result.valid(genericUser));
        Mockito.when(database.existsByEmail(genericUser.getEmail())).thenReturn(false);
        Mockito.when(database.saveUser(genericUser)).thenReturn(Result.valid(genericUser));

        Result<User> result = userService.saveUser(genericUser);
        assertTrue(result.isValid());
    }

    @Test
    public void saveUser_WithInvalidCEP_ReturnsInvalidResult() {
        Mockito.when(validator.validateUser(genericUser)).thenReturn(Result.invalid("CEP é obrigatório"));

        Result<User> result = userService.saveUser(genericUser);
        assertFalse(result.isValid());
        assertEquals("CEP é obrigatório", result.getErrors().get(0));
    }

    @Test
    public void saveUser_WithRepeatedEmail_ReturnsInalidResult() {
        Mockito.when(validator.validateUser(genericUser)).thenReturn(Result.valid(genericUser));
        Mockito.when(database.existsByEmail(genericUser.getEmail())).thenReturn(true);

        Result<User> result = userService.saveUser(genericUser);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("E-mail já cadastrado", result.getErrors().get(0));
    }

    @Test
    public void saveUser_WhenDBHasError_ReturnsInvalidResult() {
        Mockito.when(validator.validateUser(genericUser)).thenReturn(Result.valid(genericUser));
        Mockito.when(database.existsByEmail(genericUser.getEmail())).thenReturn(false);
        Mockito.when(database.saveUser(genericUser)).thenReturn(Result.invalid(""));

        Result<User> result = userService.saveUser(genericUser);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Ocorreu um problema interno. Tente novamente mais tarde", result.getErrors().get(0));
    }

    @Test
    public void updatePassword_WithValidData_ReturnsValidResult() {
        Mockito.when(database.existsByEmail(userData.getEmail())).thenReturn(true);
        Mockito.doNothing().when(database).updatePassword(Mockito.eq(userData.getEmail()), Mockito.anyString());

        Result<RedefinePasswordDTO> result = userService.updatePassword(userData);

        assertTrue(result.isValid());
    }

    @Test
    public void updatePassword_WithInvalidEmail_ReturnsInvalidResult() {
        Mockito.when(database.existsByEmail(userData.getEmail())).thenReturn(false);

        Result<RedefinePasswordDTO> result = userService.updatePassword(userData);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Usuário não encontrado", result.getErrors().get(0));
    }

    @Test
    public void updatePassword_WithInvalidPassword_ReturnsInvalidResult() {
        userData = new RedefinePasswordDTO("email@gmail.com", "123", "1234");
        Mockito.when(database.existsByEmail(userData.getEmail())).thenReturn(true);
        Mockito.doNothing().when(database).updatePassword(Mockito.eq(userData.getEmail()), Mockito.anyString());

        Result<RedefinePasswordDTO> result = userService.updatePassword(userData);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Senha com dados divergentes", result.getErrors().get(0));
    }
}
