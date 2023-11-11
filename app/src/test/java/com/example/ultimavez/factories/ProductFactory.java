package com.example.ultimavez.factories;

import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.enums.CategoryEnum;

import java.nio.charset.StandardCharsets;

public class ProductFactory {
    private String name;
    private CategoryEnum category;
    private String description;
    private Double price;
    private byte[] image;

    private ProductFactory(String name, CategoryEnum category, String description, Double price, byte[] image) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public static ProductFactory validProduct() {
        return new ProductFactory(
              "Chocolate Maravilhoso",
              CategoryEnum.ESPECIAIS,
              "Chocolate nobre meio amargo com cerejas",
              4.5,
              "standardImage.png".getBytes(StandardCharsets.UTF_8)
        );
    }

    public ProductFactory withName(String name) {
        this.name = name;
        return this;
    }

    public ProductFactory withCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public ProductFactory withDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductFactory withPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductFactory withImage(byte[] image) {
        this.image = image;
        return this;
    }

    public Product build() {
        return new Product(name, category, description, price, image);
    }

}
