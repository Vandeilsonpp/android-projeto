package com.example.ultimavez;

import android.app.Application;

import com.example.ultimavez.helper.SeedsHelper;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.persistence.CupomPersistence;
import com.example.ultimavez.persistence.EncomendaPersistence;
import com.example.ultimavez.persistence.PedidoPersistence;
import com.example.ultimavez.persistence.ProductPersistence;
import com.example.ultimavez.persistence.UserPersistence;
import com.example.ultimavez.persistence.sqlite.CupomTableHelper;
import com.example.ultimavez.persistence.sqlite.EncomendaTableHelper;
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
    private static EncomendaPersistence encomendaPersistence;
    public static final int DATABASE_VERSION = 24;
    private static List<Product> selectedProducts;
    private static Carrinho carrinho;
    private static Pedido pedido;
    private static SeedsHelper seeds;

    @Override
    public void onCreate() {
        super.onCreate();

        userTableHelper = new UserTableHelper(this);
        productTableHelper = new ProductTableHelper(this);
        cupomPersistence = new CupomTableHelper(this);
        pedidoPersistence = new PedidoTableHelper(this);
        encomendaPersistence = new EncomendaTableHelper(this);
        selectedProducts = new ArrayList<>();
        carrinho = new Carrinho();
        pedido = new Pedido();

        // Popular banco de dados com dados reais
        seeds = new SeedsHelper(this);
        seeds.createSeeds();
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
    public static EncomendaPersistence getEncomendaPersistence() {
        return encomendaPersistence;
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
