package com.example.ultimavez.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ultimavez.R;
import com.example.ultimavez.model.domain.Cupom;

public class SellerHomePageActivity extends AppCompatActivity {

    private CardView cadastrarProduto, listarProduto, cadastrarCupom, listarCupom, deslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_home_page);
        getSupportActionBar().hide();

        inicializarComponentes();

        cadastrarProduto.setOnClickListener(it -> abrirCadastroProduto());
        listarProduto.setOnClickListener(it -> abrirListarProduto());
        cadastrarCupom.setOnClickListener(it -> abrirGerenciarCupons());
        deslogar.setOnClickListener(it -> deslogar());
        listarCupom.setOnClickListener(it -> abrirListarCupom());
    }

    private void abrirListarCupom() {
        Intent intent = new Intent(SellerHomePageActivity.this, ListCupomActivity.class);
        startActivity(intent);
    }

    private void inicializarComponentes() {
        cadastrarProduto = findViewById(R.id.cardCadastrar);
        listarProduto = findViewById(R.id.cardListar);
        cadastrarCupom = findViewById(R.id.cardManageCupom);
        deslogar = findViewById(R.id.cardDeslogarSeller);
        listarCupom = findViewById(R.id.listarCupons);
    }

    private void deslogar() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    private void abrirListarProduto() {
        Intent intent = new Intent(SellerHomePageActivity.this, SellerListOfProductsActivity.class);
        startActivity(intent);
    }


    private void abrirCadastroProduto() {
        Intent intent = new Intent(SellerHomePageActivity.this, RegisterProductActivity.class);
        startActivity(intent);
    }

    private void abrirAtualizarProduto() {
        Intent intent = new Intent(SellerHomePageActivity.this, UpdateProductActivity.class);
        startActivity(intent);
    }

    private void abrirGerenciarCupons() {
        Intent intent = new Intent(SellerHomePageActivity.this, CupomActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
