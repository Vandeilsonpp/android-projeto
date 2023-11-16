package com.example.ultimavez;

import android.app.Application;

import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.persistence.CupomPersistence;
import com.example.ultimavez.persistence.PedidoPersistence;
import com.example.ultimavez.persistence.ProductPersistence;
import com.example.ultimavez.persistence.UserPersistence;
import com.example.ultimavez.persistence.sqlite.CupomTableHelper;
import com.example.ultimavez.persistence.sqlite.PedidoTableHelper;
import com.example.ultimavez.persistence.sqlite.ProductTableHelper;
import com.example.ultimavez.persistence.sqlite.UserTableHelper;

import java.util.ArrayList;
import java.util.List;

public class MyCustomApplication extends Application {

    private static UserPersistence userTableHelper;
    private static ProductPersistence productTableHelper;
    private static CupomPersistence cupomPersistence;
    private static PedidoPersistence pedidoPersistence;
    public static final int DATABASE_VERSION = 23;
    private static List<Product> selectedProducts;
    private static Carrinho carrinho;
    private static Pedido pedido;

    @Override
    public void onCreate() {
        super.onCreate();

        userTableHelper = new UserTableHelper(this);
        productTableHelper = new ProductTableHelper(this);
        cupomPersistence = new CupomTableHelper(this);
        pedidoPersistence = new PedidoTableHelper(this);
        selectedProducts = new ArrayList<>();
        carrinho = new Carrinho();
        pedido = new Pedido();
    }

    public static UserPersistence getUserPersistence() {
        return userTableHelper;
    }
    public static ProductPersistence getProductPersistence() {
        return productTableHelper;
    }
    public static CupomPersistence getCupomPersistence() {
        return cupomPersistence;
    }
    public static PedidoPersistence getPedidoPersistence() {
        return pedidoPersistence;
    }
    public static List<Product> getSelectedProducts() {
        return selectedProducts;
    }
    public static Carrinho getCarrinho() {
        return carrinho;
    }
    public static Pedido getPedido() {
        return pedido;
    }

}
