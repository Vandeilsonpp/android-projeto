package com.example.ultimavez.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.ultimavez.factories.ProductFactory;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.persistence.CupomPersistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class PedidoServiceTest {

    @Mock
    private CupomPersistence database;

    @InjectMocks
    private PedidoService service;

    private Carrinho carrinho;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        carrinho = new Carrinho();
        carrinho.addProduct(
                ProductFactory
                        .validProduct()
                        .withPrice(5.0)
                        .build());
    }

    @Test
    public void whenBuildFromCarrinho_shouldReturnCorrectCarrinho() {
        Pedido pedido = service.buildFromCarrinho(carrinho);

        assertFalse(pedido.temCupomAplicado());
        assertEquals(5.0, pedido.getValorFinal(), 0);
    }

    @Test
    public void whenApplyCupom_shouldReturnValidCupom() {
        Cupom cupom = new Cupom("ABC", true, 3.0);

        Mockito.when(database.findActiveByCodigo("ABC")).thenReturn(Optional.of(cupom));

        Result<Cupom> result = service.aplicarCupom("ABC");

        assertTrue(result.isValid());
        assertEquals("ABC", result.getResultObject().getCodigo());
        assertTrue(result.getResultObject().eValido());
    }

    @Test
    public void whenApplyCupom_shouldReturnNotExist() {
        Mockito.when(database.findActiveByCodigo("ABC")).thenReturn(Optional.empty());

        Result<Cupom> result = service.aplicarCupom("ABC");

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Não existe cupom ativo com esse código", result.getErrors().get(0));
    }
}
