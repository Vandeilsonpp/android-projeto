package com.example.ultimavez.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ultimavez.R;
import com.example.ultimavez.model.enums.CategoryEnum;

public class CustomerHomePageActivity extends AppCompatActivity {

    private CardView cPremium, cGourmet, cVegano, cEspeciais, cDeslogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_home_page);
        getSupportActionBar().hide();

        inicializarComponentes();

        cPremium.setOnClickListener(view -> openListOfProducts(CategoryEnum.PREMIUM));
        cGourmet.setOnClickListener(view -> openListOfProducts(CategoryEnum.GOURMET));
        cVegano.setOnClickListener(view -> openListOfProducts(CategoryEnum.VEGANO));
        cEspeciais.setOnClickListener(view -> openListOfProducts(CategoryEnum.ESPECIAIS));
        cDeslogar.setOnClickListener(vire -> deslogar());
    }

    private void deslogar() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
    }

    private void openListOfProducts(CategoryEnum categoria) {
        Intent intent = new Intent(this, CustomerListOfProductsActivity.class);
        intent.putExtra("category", categoria.toString());
        startActivity(intent);
    }

    private void inicializarComponentes() {
        cPremium = findViewById(R.id.cardPremium);
        cGourmet = findViewById(R.id.cardGourmet);
        cVegano = findViewById(R.id.cardVegano);
        cEspeciais = findViewById(R.id.cardEspeciais);
        cDeslogar = findViewById(R.id.cardDeslogarUser);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
