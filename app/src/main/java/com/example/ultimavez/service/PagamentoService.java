package com.example.ultimavez.service;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.infra.GatewayPagamento;
import com.example.ultimavez.infra.PagamentoCredito;
import com.example.ultimavez.infra.PagamentoPix;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.model.enums.TiposPagamentoEnum;
import com.example.ultimavez.persistence.PedidoPersistence;

import java.time.LocalDateTime;

public class PagamentoService {

    private GatewayPagamento gatewayPagamento;
    private PedidoPersistence pedidoPersistence;

    public PagamentoService() {
        pedidoPersistence = MyCustomApplication.getPedidoPersistence();
    }

    public Result<Pedido> pagar(Pedido pedido) {
        pedido.setCriadoEm(LocalDateTime.now());

        definePaymentGateway(pedido.getTipoPaagamento());
        gatewayPagamento.pagar(pedido);

        pedidoPersistence.savePedido(pedido);

        return Result.valid(null);
    }

    private void definePaymentGateway(TiposPagamentoEnum tipoPaagamento) {
        switch (tipoPaagamento) {
            case PIX:
                gatewayPagamento = new PagamentoPix();
            case CREDITO:
                gatewayPagamento = new PagamentoCredito();
        }
    }
}
