package com.example.ultimavez.service;

import static org.junit.Assert.assertTrue;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.model.enums.PedidoStatusEnum;
import com.example.ultimavez.model.enums.TiposPagamentoEnum;
import com.example.ultimavez.persistence.PedidoPersistence;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

public class PagamentoServiceTest {

    @Mock
    private PedidoPersistence database;

    @InjectMocks
    private PagamentoService service;

    private Pedido pedido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        pedido = new Pedido();
        pedido.setIdComprador(1);
        pedido.setDesconto(0);
        pedido.setStatus(PedidoStatusEnum.CONFIRMADO);
        pedido.setCriadoEm(LocalDateTime.now());
        pedido.setValorOriginal(5);
        pedido.setTemCupomAplicado(false);
        pedido.setCodigoCupom(null);
    }

    @Test
    public void whenPayWithPix_shouldReturnValid() {
        pedido.setTipoPaagamento(TiposPagamentoEnum.PIX);
        Mockito.when(database.savePedido(pedido)).thenReturn(Result.valid(pedido));

        Result<Pedido> result = service.pagar(pedido);
        assertTrue(result.isValid());
    }

    @Test
    public void whenPayWithCredit_shouldReturnValid() {
        pedido.setTipoPaagamento(TiposPagamentoEnum.CREDITO);
        Mockito.when(database.savePedido(pedido)).thenReturn(Result.valid(pedido));

        Result<Pedido> result = service.pagar(pedido);
        assertTrue(result.isValid());
    }
}
