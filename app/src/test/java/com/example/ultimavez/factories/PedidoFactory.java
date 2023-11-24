package com.example.ultimavez.factories;

import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.enums.PedidoStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public class PedidoFactory {
    private List<Product> listaDeProdutos;
    private long idComprador;
    private PedidoStatusEnum status;
    private double valorOriginal;
    private double desconto;
    private double valorFinal;
    private LocalDateTime criadoEm;
    private Cupom cupom;
    private boolean temCupomAplicado;

    public PedidoFactory() {
    }

    public static PedidoFactory createPedido() {
        return new PedidoFactory();
    }

    public PedidoFactory withListaDeProdutos(List<Product> listaDeProdutos) {
        this.listaDeProdutos = listaDeProdutos;
        return this;
    }

    public PedidoFactory withIdComprador(long idComprador) {
        this.idComprador = idComprador;
        return this;
    }

    public PedidoFactory withStatus(PedidoStatusEnum status) {
        this.status = status;
        return this;
    }

    public PedidoFactory withValorOriginal(double valorOriginal) {
        this.valorOriginal = valorOriginal;
        return this;
    }

    public PedidoFactory withDesconto(double desconto) {
        this.desconto = desconto;
        setValorFinal();
        return this;
    }

    public PedidoFactory withValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
        return this;
    }

    private void setValorFinal() {
        this.valorFinal = this.valorOriginal - this.desconto;
    }

    public PedidoFactory withCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
        return this;
    }

    public PedidoFactory withCupom(Cupom cupom) {
        this.cupom = cupom;
        this.temCupomAplicado = true;
        return this;
    }

    public Pedido build() {
        Pedido pedido = new Pedido();
        pedido.setIdComprador(idComprador);
        pedido.setStatus(status);
        pedido.setValorOriginal(valorOriginal);
        pedido.setDesconto(desconto);
        pedido.setCriadoEm(criadoEm);
        return pedido;
    }

}
