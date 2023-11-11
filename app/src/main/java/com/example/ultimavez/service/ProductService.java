package com.example.ultimavez.service;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.enums.CategoryEnum;
import com.example.ultimavez.persistence.ProductPersistence;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class ProductService {

    private ProductPersistence database;

    public ProductService() {
        this.database = MyCustomApplication.getProductPersistence();
    }

    // Criado para fins de testes unitários
    protected ProductService(ProductPersistence database) {
        this.database = database;
    }

    public Result<Product> saveProduct(Product product, long sellerId) {
        Result<Product> result = new Result<>(product);
        verifyNullValues(product, result);

        if (!result.isValid()) {
            return Result.invalid(result.getErrors());
        }

        if (database.existsByName(product.getName())) {
            return Result.invalid("Já existe um produto com esse nome");
        }

        Result<Product> dbResult = database.save(product, sellerId);
        if (!dbResult.isValid()) {
            return Result.invalid("Ocorreu um problema interno. Tente novamente mais tarde");
        }

        return Result.valid(product);
    }

    public Optional<Product> findProduct(String name) {
        return database.findByName(name);
    }

    public Result<List<Product>> getAllProducts(long sellerId) {
        Optional<List<Product>> productList = database.findAllProducts(sellerId);

        return productList.isPresent() ? Result.valid(productList.get()) : Result.invalid("Não há produtos cadastrados");
    }

    public Result<Product> updateProduct(Product product) {
        Result<Product> result = new Result<>(product);
        verifyNullValues(product, result);

        if (!result.isValid()) {
            return Result.invalid(result.getErrors());
        }

        Result<Product> dbResult = database.update(product);
        if (!dbResult.isValid()) {
            return Result.invalid("Ocorreu um problema interno. Tente novamente mais tarde");
        }

        return Result.valid(product);
    }

    public Result<Product> deleteProduct(long id) {
        return database.deleteById(String.valueOf(id));
    }

    private void verifyNullValues(Product product, Result<Product> result) {

        if (isEmpty(product.getName())) {
            result.addError("Nome é obrigatório");
        }

        if (isEmpty(product.getDescription())) {
            result.addError("Descrição é obrigatória");
        }

        if (product.getPrice() == 0.0) {
            result.addError("Preço é obrigatório");
        }
    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public Result<List<Product>> getProductByCategory(String category) {
        Optional<List<Product>> result = database.findProductByCategory(CategoryEnum.valueOf(category.toUpperCase(Locale.ROOT)));
        return result.isPresent() ? Result.valid(result.get()) : Result.invalid("Não há produtos cadastrados nesta categoria");
    }
}
