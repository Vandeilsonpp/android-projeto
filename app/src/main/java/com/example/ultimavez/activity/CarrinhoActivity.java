package com.example.ultimavez.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.R;
import com.example.ultimavez.adapter.CarrinhoAdapter;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Product;

import java.text.DecimalFormat;


public class CarrinhoActivity extends AppCompatActivity {

    private Button continueShopping, finishOrder, encomenda;
    private EditText total;
    private RecyclerView recyclerView;
    private CarrinhoAdapter adapter;
    private Carrinho carrinho = MyCustomApplication.getCarrinho();
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carrinho_compra);
        getSupportActionBar().hide();

        inicializarComponentes();

        Product product = (Product) getIntent().getSerializableExtra("selectedProduct");

        if (product != null) {
            carrinho.addProduct(product);
        }
        
        renderSelectedProducts();
        continueShopping.setOnClickListener(it -> returnToListOfProducts());
        finishOrder.setOnClickListener(it -> proceedToCheckout());
        encomenda.setOnClickListener(it -> proceedToEncomenda());
        adapter.setSubtractButtonClickListener(updatedProduct -> {
            updateTotalPrice();
        });

        adapter.setAddButtonClickListener(updatedProduct -> {
            updateTotalPrice();
        });

    }

    private void inicializarComponentes() {
        total = findViewById(R.id.txtCarrinhoTotal);
        continueShopping = findViewById(R.id.addMaisProdutos);
        finishOrder = findViewById(R.id.finalizarPedido);
        recyclerView = findViewById(R.id.carrinho_recycler_item_view);
        encomenda = findViewById(R.id.encomenda);
    }

    private void renderSelectedProducts() {
        adapter = new CarrinhoAdapter(carrinho.getListOfProducts(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        updateTotalPrice();
    }

    private void returnToListOfProducts() {
        Intent intent = new Intent(this, CustomerHomePageActivity.class);
        startActivity(intent);
    }

    private void proceedToCheckout() {
        Intent intent = new Intent(this, CheckoutActivity.class);
        startActivity(intent);
    }

    private void proceedToEncomenda() {
        Intent intent = new Intent(this, EncomendaActivity.class);
        startActivity(intent);
    }



    @SuppressLint("SetTextI18n")
    private void updateTotalPrice() {
        double totalPrice = carrinho.calculateTotalPrice();
        total.setText("R$ " + decimalFormat.format(totalPrice));
    }

}
