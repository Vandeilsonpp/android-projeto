package com.example.ultimavez.persistence;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.enums.CategoryEnum;

import java.util.List;
import java.util.Optional;

public interface ProductPersistence {

    Result<Product> save(Product product, long sellerId);
    boolean existsByName(String name);
    Optional<Product> findByName(String productName);
    Result<Product> update(Product newProduct);
    Result<Product> deleteById(String id);
    Optional<List<Product>> findAllProducts(long sellerId);
    Optional<List<Product>> findProductByCategory(CategoryEnum valueOf);
}
