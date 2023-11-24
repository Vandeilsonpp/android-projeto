package com.example.ultimavez.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.ultimavez.factories.ProductFactory;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.enums.CategoryEnum;
import com.example.ultimavez.persistence.ProductPersistence;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductPersistence database;

    @InjectMocks ProductService productService;

    private ProductFactory genericProduct;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        genericProduct = ProductFactory.validProduct();
    }

    // ----------- SAVE ----------

    @Test
    public void saveValidProduct_shouldReturnValidResult() {
        Product product = genericProduct.build();
        Mockito.when(database.save(product, 0)).thenReturn(Result.valid(product));
        Mockito.when(database.existsByName(Mockito.anyString())).thenReturn(false);

        Result<Product> result = productService.saveProduct(product, 0);
        assertTrue(result.isValid());
    }

    @Test
    @Parameters({
            "null, Cupcake de morango delicioso, 7.5",
            "Morango, null, 7.5",
            "Morango, Cupcake de morango delicioso, 0.0"
    })
    public void saveProduct_withInvalidInput_shouldReturnInvalidResult(String name, String description, Double price) {
        name = "null".equals(name) ? null : name;
        description = "null".equals(description) ? null : description;

        Product product = genericProduct
            .withName(name)
            .withDescription(description)
            .withPrice(price)
            .build();

        Result<Product> result = productService.saveProduct(product, 0);
        assertFalse(result.isValid());
    }

    @Test
    public void saveRepeatedProduct_shouldReturnResultInvalid() {
        Product product = genericProduct.build();
        Mockito.when(database.save(product, 0)).thenReturn(Result.valid(product));
        Mockito.when(database.existsByName(Mockito.anyString())).thenReturn(true);

        Result<Product> result = productService.saveProduct(product, 0);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Já existe um produto com esse nome", result.getErrors().get(0));
    }

    @Test
    public void saveProduct_withInternalDbError_shouldReturnResultInvalid() {
        Product product = genericProduct.build();
        Mockito.when(database.existsByName(Mockito.anyString())).thenReturn(false);
        Mockito.when(database.save(product, 0)).thenReturn(Result.invalid(""));

        Result<Product> result = productService.saveProduct(product, 0);

        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Ocorreu um problema interno. Tente novamente mais tarde", result.getErrors().get(0));
    }

    // ----------- UPDATE ----------

    @Test
    public void updateValidProduct_shouldReturnValidResult() {
        Product product = genericProduct.build();
        Mockito.when(database.update(product)).thenReturn(Result.valid(product));

        Result<Product> result = productService.updateProduct(product);
        assertTrue(result.isValid());
    }

    @Test
    public void updateInvalidProduct_shouldReturnInvalidResult() {
        Product product = genericProduct.build();
        Mockito.when(database.update(product)).thenReturn(Result.invalid(""));

        Result<Product> result = productService.updateProduct(product);
        assertFalse(result.isValid());
        assertEquals(1, result.getErrors().size());
        assertEquals("Ocorreu um problema interno. Tente novamente mais tarde", result.getErrors().get(0));
    }

    @Test
    @Parameters({
            "null, Cupcake de morango delicioso, 7.5",
            "Morango, null, 7.5",
            "Morango, Cupcake de morango delicioso, 0.0"
    })
    public void updateProduct_withInvalidInput_shouldReturnInvalidResult(String name, String description, Double price) {
        name = "null".equals(name) ? null : name;
        description = "null".equals(description) ? null : description;

        Product product = genericProduct
                .withName(name)
                .withDescription(description)
                .withPrice(price)
                .build();

        Result<Product> result = productService.updateProduct(product);
        assertFalse(result.isValid());
    }

    @Test
    public void whenGetAllProducts_shouldReturnListOfProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(genericProduct.build());
        productList.add(genericProduct.build());

        Mockito.when(database.findAllProducts(1)).thenReturn(Optional.of(productList));

        Result<List<Product>> result = productService.getAllProducts(1);
        assertTrue(result.isValid());
        assertEquals(2, result.getResultObject().size());
    }

    @Test
    public void whenGetAllProducts_shouldReturnEmptyList() {
        Mockito.when(database.findAllProducts(1)).thenReturn(Optional.empty());

        Result<List<Product>> result = productService.getAllProducts(1);
        assertFalse(result.isValid());
        assertEquals("Não há produtos cadastrados", result.getErrors().get(0));
    }

    @Test
    public void whenGetByCategory_shouldReturnProduct() {
        Product product = genericProduct
                .withCategory(CategoryEnum.PREMIUM)
                .build();

        List<Product> productList = new ArrayList<>();
        productList.add(product);

        Mockito.when(database.findProductByCategory(CategoryEnum.valueOf("PREMIUM"))).thenReturn(Optional.of(productList));

        Result<List<Product>> result = productService.getProductByCategory("PREMIUM");
        assertTrue(result.isValid());
    }

    @Test
    public void whenGetByCategory_shouldNotReturnProduct() {
        Mockito.when(database.findProductByCategory(CategoryEnum.valueOf("PREMIUM"))).thenReturn(Optional.empty());

        Result<List<Product>> result = productService.getProductByCategory("PREMIUM");
        assertFalse(result.isValid());
        assertEquals("Não há produtos cadastrados nesta categoria", result.getErrors().get(0));

    }

}
