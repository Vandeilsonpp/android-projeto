package com.example.ultimavez.model.domain;

import java.io.Serializable;

public class Cupom implements Serializable {
    private long id;
    private String codigo;
    private boolean eValido;
    private double valorDoDesconto;
    private long sellerId;

    public Cupom(long id, String codigo, boolean eValido, double valorDoDesconto, long sellerId) {
        this.id = id;
        this.codigo = codigo;
        this.eValido = eValido;
        this.valorDoDesconto = valorDoDesconto;
        this.sellerId = sellerId;
    }
    public Cupom(String codigo, boolean eValido, double valorDoDesconto, long sellerId) {
        this.codigo = codigo;
        this.eValido = eValido;
        this.valorDoDesconto = valorDoDesconto;
        this.sellerId = sellerId;
    }

    public boolean eValido() {
        return eValido;
    }

    public long getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getValorDoDesconto() {
        return valorDoDesconto;
    }

    public long getSellerId() {
        return sellerId;
    }
}
