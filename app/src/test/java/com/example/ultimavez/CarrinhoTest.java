package com.example.ultimavez;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.ultimavez.factories.ProductFactory;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Product;

import org.junit.Test;

import java.util.List;

public class CarrinhoTest {

    private Carrinho carrinho = new Carrinho();
    private Product defaultProduct = ProductFactory.validProduct().build();

    @Test
    public void whenAddProduct_thenKartShouldAddProduct() {
        carrinho.addProduct(defaultProduct);

        assertEquals(1, carrinho.getListOfProducts().size());
        assertTrue(carrinho.productExists(defaultProduct));
    }

    @Test
    public void whenAddProductThatAlreadyExists_thenDoNothing() {
        carrinho.addProduct(defaultProduct);
        carrinho.addProduct(defaultProduct);

        assertEquals(1, carrinho.getListOfProducts().size());
    }

    @Test
    public void whenRemoveProductThatExists_thenRemoveIt() {
        carrinho.addProduct(defaultProduct);

        carrinho.removeProduct(defaultProduct);
        assertEquals(0, carrinho.getListOfProducts().size());
    }

    @Test
    public void whenRemoveProductThatDoesNotExists_thenDoNothing() {
        carrinho.removeProduct(defaultProduct);
        assertEquals(0, carrinho.getListOfProducts().size());
    }

    @Test
    public void whenIncrementProduct_thenAddByOne() {
        carrinho.addProduct(defaultProduct);
        carrinho.incrementProduct(defaultProduct);

        int quantity = carrinho.getProductQuantity(defaultProduct);
        assertEquals(2, quantity);
    }

    @Test
    public void whenDecrementProduct_thenSubtractByOne() {
        carrinho.addProduct(defaultProduct);
        carrinho.incrementProduct(defaultProduct);
        carrinho.decrementProduct(defaultProduct);

        int quantity = carrinho.getProductQuantity(defaultProduct);
        assertEquals(1, quantity);
    }

    @Test
    public void whenDecrementProduct_withOneAmount_thenRemoveIt() {
        carrinho.addProduct(defaultProduct);
        carrinho.decrementProduct(defaultProduct);

        assertFalse(carrinho.productExists(defaultProduct));
    }

    @Test
    public void calculateTotalPrice_shouldReturnTotalKartValue() {
        Product customProduct1 = ProductFactory
                .validProduct()
                .withName("Produto 1")
                .withPrice(3.34)
                .build();

        Product customProduct2 = ProductFactory
                .validProduct()
                .withName("Produto 2")
                .withPrice(7.87)
                .build();

        carrinho.addProduct(customProduct1);
        carrinho.addProduct(customProduct2);
        carrinho.incrementProduct(customProduct1);
        carrinho.incrementProduct(customProduct2);

        double totalPrice = customProduct1.getPrice() * 2 + customProduct2.getPrice() * 2;

        assertEquals(totalPrice, carrinho.calculateTotalPrice(), 0);
    }

    @Test
    public void whenCleanKart_shouldReturnEmptyKart() {
        carrinho.addProduct(defaultProduct);

        carrinho.cleanCarrinho();

        assertEquals(0, carrinho.getListOfProducts().size());
    }

    @Test
    public void whenGetListOfProducts_shouldReturnTheListProperly() {
        Product customProduct1 = ProductFactory
                .validProduct()
                .withName("Produto 1")
                .withPrice(3.34)
                .build();

        Product customProduct2 = ProductFactory
                .validProduct()
                .withName("Produto 2")
                .withPrice(3.34)
                .build();

        carrinho.addProduct(defaultProduct);
        carrinho.addProduct(customProduct1);
        carrinho.addProduct(customProduct2);

        List<Product> productList = carrinho.getListOfProducts();
        assertEquals(3, productList.size());
    }

    @Test
    public void whenGetProductAmount_shouldReturnCorrectAmount() {
        carrinho.addProduct(defaultProduct);
        carrinho.incrementProduct(defaultProduct);
        carrinho.incrementProduct(defaultProduct);

        int productQuantity = carrinho.getProductQuantity(defaultProduct);
        assertEquals(3, productQuantity);
    }

    @Test
    public void whenGetProductAmount_ThatNotExist_shouldReturnZero() {
        carrinho.addProduct(defaultProduct);
        carrinho.decrementProduct(defaultProduct);

        int productQuantity = carrinho.getProductQuantity(defaultProduct);
        assertEquals(0, productQuantity);
    }

}
