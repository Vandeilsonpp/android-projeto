package com.example.ultimavez.model.domain;

import com.example.ultimavez.model.enums.PedidoStatusEnum;
import com.example.ultimavez.model.enums.TiposPagamentoEnum;

import java.time.LocalDateTime;

public class Pedido {

    private long id;
    private long idComprador;
    private PedidoStatusEnum status;
    private double valorOriginal;
    private double desconto;
    private double valorFinal;
    private LocalDateTime criadoEm;
    private String codigoCupom;
    private TiposPagamentoEnum tipoPaagamento;
    private boolean temCupomAplicado;


    public void setTemCupomAplicado(boolean temCupomAplicado) {
        this.temCupomAplicado = temCupomAplicado;
    }

    public Pedido() {
    }

    public double getValorOriginal() {
        return valorOriginal;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorFinal() {
        return valorFinal;
    }

    public void setIdComprador(long idComprador) {
        this.idComprador = idComprador;
    }

    public void setStatus(PedidoStatusEnum status) {
        this.status = status;
    }

    public void setValorOriginal(double valorOriginal) {
        this.valorOriginal = valorOriginal;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
        setValorFinal();
    }

    public void setValorFinal() {
        if (this.desconto >= this.valorOriginal) {
            this.valorFinal = 0;
            this.desconto = this.valorOriginal;
            return;
        }
        this.valorFinal = this.valorOriginal - this.desconto;

    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public void setCodigoCupom(String cupom) {
        this.codigoCupom = cupom;
        this.temCupomAplicado = true;
    }

    public void setTipoPaagamento(TiposPagamentoEnum tipoPaagamento) {
        this.tipoPaagamento = tipoPaagamento;
    }

    public boolean temCupomAplicado() {
        return temCupomAplicado;
    }

    public TiposPagamentoEnum getTipoPaagamento() {
        return tipoPaagamento;
    }

    public long getId() {
        return id;
    }

    public long getIdComprador() {
        return idComprador;
    }

    public PedidoStatusEnum getStatus() {
        return status;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public String getCodigoCupom() {
        return codigoCupom;
    }
}
