package com.example.ultimavez.model.domain;

public class Cupom {
    private long id;
    private String codigo;
    private boolean eValido;
    private double valorDoDesconto;

    public Cupom(long id, String codigo, boolean eValido, double valorDoDesconto) {
        this.id = id;
        this.codigo = codigo;
        this.eValido = eValido;
        this.valorDoDesconto = valorDoDesconto;
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
}
