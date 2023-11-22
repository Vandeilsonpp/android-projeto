package com.example.ultimavez.model.domain;

import java.time.LocalDateTime;
import java.util.Map;

public class Encomenda {
   private double valor;
   private double desconto;
   private double valorFinal;
   private int quantidade;
   private LocalDateTime criadoEm;
   private long consumerId;

   public Encomenda(Map<Product, Integer> productsQuantityMap, double valor) {
      this.valor = valor;
      this.quantidade = calcularQuantidadeTotal(productsQuantityMap);
      this.desconto = calcularDesconto();
      this.valorFinal = this.valor - this.desconto;
   }

   private int calcularQuantidadeTotal(Map<Product, Integer> productsQuantityMap) {
      int sum = 0;

      for (int value : productsQuantityMap.values()) {
         sum += value;
      }

      return sum;
   }

   private double calcularDesconto() {
      if (this.quantidade > 50) {
         return this.valor * 0.3;
      } else if (this.quantidade > 25) {
         return this.valor * 0.15;
      } else {
         return 0;
      }
   }

   public double getValor() {
      return valor;
   }

   public double getDesconto() {
      return desconto;
   }

   public double getValorFinal() {
      return valorFinal;
   }

   public int getQuantidade() {
      return quantidade;
   }

   public void setCriadoEm(LocalDateTime criadoEm) {
      this.criadoEm = criadoEm;
   }

   public void setConsumerId(long consumerId) {
      this.consumerId = consumerId;
   }

   public LocalDateTime getCriadoEm() {
      return criadoEm;
   }

   public long getConsumerId() {
      return consumerId;
   }
}


