package com.example.ultimavez.infra;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Pedido;

public interface GatewayPagamento {
    Result<?> pagar(Pedido pedido);
}
