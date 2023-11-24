package com.example.ultimavez.model.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Carrinho {

    private Map<Product, Integer> productsQuantityMap;

    public Map<Product, Integer> getProductsQuantityMap() {
        return productsQuantityMap;
    }

    public Carrinho() {
        productsQuantityMap = new HashMap<>();
    }

    public void addProduct(Product product) {
        if (productExists(product)) {
            return;
        }
        this.productsQuantityMap.put(product, 1);
    }

    public void removeProduct(Product product) {
        productsQuantityMap.remove(product);
    }

    public void incrementProduct(Product product) {
        if (productExists(product)) {
            int actualValue = productsQuantityMap.get(product);
            actualValue++;
            productsQuantityMap.put(product, actualValue);
        }
    }

    public void decrementProduct(Product product) {
        if (productExists(product)) {
            int actualValue = productsQuantityMap.get(product);
            actualValue--;
            if (actualValue == 0) {
                removeProduct(product);
            } else {
                productsQuantityMap.put(product, actualValue);
            }
        }
    }

    public double calculateTotalPrice() {
        AtomicReference<Double> totalPrice = new AtomicReference<>(0d);
        productsQuantityMap.forEach((product, quantity) -> {
            double totalProductPrice = product.getPrice() * quantity;
            totalPrice.updateAndGet(v -> v + totalProductPrice);
        });
        return totalPrice.get();
    }

    public void cleanCarrinho() {
        productsQuantityMap.clear();
    }

    public Integer getProductQuantity(Product product) {
        if (null == productsQuantityMap.get(product)) {
            return 0;
        } else {
            return productsQuantityMap.get(product);
        }
    }

    public List<Product> getListOfProducts() {
        List<Product> listOfProducts = new ArrayList<>();
        productsQuantityMap.forEach((product, amount) -> {
            listOfProducts.add(product);
        });
        return listOfProducts;
    }

    public boolean productExists(Product product) {
        return productsQuantityMap.containsKey(product);
    }
}
