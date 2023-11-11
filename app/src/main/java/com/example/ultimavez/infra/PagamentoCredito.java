package com.example.ultimavez.infra;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.model.enums.PedidoStatusEnum;

public class PagamentoCredito implements GatewayPagamento{
    @Override
    public Result<?> pagar(Pedido pedido) {
        pedido.setStatus(PedidoStatusEnum.CONFIRMADO);
        return Result.valid(pedido);
    }
}
