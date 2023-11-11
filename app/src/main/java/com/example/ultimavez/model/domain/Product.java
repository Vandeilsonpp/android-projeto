package com.example.ultimavez.model.domain;

import com.example.ultimavez.model.enums.CategoryEnum;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class Product implements Serializable {
    private long id;
    private String name;
    private CategoryEnum category;
    private String description;
    private Double price;
    private byte[] image;
    private long seller;

    public Product(String name, CategoryEnum category, String description, double price, byte[] image) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public Product(long id, String name, CategoryEnum category, String description, Double price, byte[] image, long sellerId) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.image = image;
        this.seller = sellerId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", image=" + Arrays.toString(image) +
                ", seller=" + seller +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
