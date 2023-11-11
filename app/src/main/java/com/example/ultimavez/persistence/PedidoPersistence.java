package com.example.ultimavez.persistence;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Pedido;

public interface PedidoPersistence {
    Result<Pedido> savePedido(Pedido pedido);
}
