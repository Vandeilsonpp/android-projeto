package com.example.ultimavez.service;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.persistence.CupomPersistence;

import java.util.Optional;

public class PedidoService {

    private Pedido pedido;
    private CupomPersistence database;

    public PedidoService() {
        database = MyCustomApplication.getCupomPersistence();
        pedido = MyCustomApplication.getPedido();
    }

    // Criado para fins de teste unitário
    protected PedidoService(CupomPersistence database) {
        this.database = database;
        this.pedido = new Pedido();
    }

    public Pedido buildFromCarrinho(Carrinho carrinho) {
        pedido.setValorOriginal(carrinho.calculateTotalPrice());
        pedido.setDesconto(0);
        pedido.setValorFinal();
        pedido.setTemCupomAplicado(false);

        return pedido;
    }

    public Result<Cupom> aplicarCupom(String codigoCupom) {
        if (pedido.temCupomAplicado()) {
            return Result.invalid("Já existe um cupom aplicado neste pedido");
        }

        Optional<Cupom> result = database.findActiveByCodigo(codigoCupom);
        if (!result.isPresent()) {
            return Result.invalid("Não existe cupom ativo com esse código");
        }

        pedido.setCodigoCupom(result.get().getCodigo());
        pedido.setDesconto(result.get().getValorDoDesconto());
        return Result.valid(result.get());
    }
}
