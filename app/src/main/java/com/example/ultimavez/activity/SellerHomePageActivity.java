package com.example.ultimavez.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ultimavez.R;
import com.example.ultimavez.model.domain.Cupom;

public class SellerHomePageActivity extends AppCompatActivity {

    private CardView cadastrar, atualizar, listar, cupom, deslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_home_page);
        getSupportActionBar().hide();

        inicializarComponentes();

        cadastrar.setOnClickListener(it -> abrirCadastroProduto());
        atualizar.setOnClickListener(it -> abrirAtualizarProduto());
        listar.setOnClickListener(it -> abrirListarProduto());
        cupom.setOnClickListener(it -> abrirGerenciarCupons());
        deslogar.setOnClickListener(it -> deslogar());
    }

    private void inicializarComponentes() {
        cadastrar = findViewById(R.id.cardCadastrar);
        atualizar = findViewById(R.id.cardAtualizar);
        listar = findViewById(R.id.cardListar);
        cupom = findViewById(R.id.cardManageCupom);
        deslogar = findViewById(R.id.cardDeslogarSeller);
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
