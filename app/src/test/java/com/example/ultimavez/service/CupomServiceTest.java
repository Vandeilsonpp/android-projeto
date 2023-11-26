package com.example.ultimavez.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.persistence.CupomPersistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CupomServiceTest {

    @Mock
    private CupomPersistence database;

    @InjectMocks CupomService service;

    private Cupom defaultCupom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        defaultCupom = new Cupom("ABC", true, 5, 1);
    }

    @Test
    public void whenAttCupom_shouldReturnValidCupom() {
        Mockito.when(database.saveCupom(defaultCupom)).thenReturn(Result.valid(defaultCupom));

        Result<Cupom> result = service.attCupom(defaultCupom);

        assertEquals(defaultCupom.getCodigo(), result.getResultObject().getCodigo());
    }

    @Test
    public void whenAttCupom_withNullValues_shouldReturnInvalidCupom() {
        defaultCupom = new Cupom(null, false, 0, 1);

        Result<Cupom> result = service.attCupom(defaultCupom);

        assertFalse(result.isValid());
        assertEquals(2, result.getErrors().size());
        assertEquals("Código é obrigatório", result.getErrors().get(0));
        assertEquals("Desconto precisa ser maior que 0", result.getErrors().get(1));
    }

    @Test
    public void whenAttCupom_withDbError_shouldReturnInvalidCupom() {
        Mockito.when(database.saveCupom(defaultCupom)).thenReturn(Result.invalid(""));

        Result<Cupom> result = service.attCupom(defaultCupom);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Ocorreu um problema interno. Tente novamente mais tarde", result.getErrors().get(0));
    }
}
