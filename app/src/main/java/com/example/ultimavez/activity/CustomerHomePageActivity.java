package com.example.ultimavez.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.ultimavez.R;
import com.example.ultimavez.model.enums.CategoryEnum;

public class CustomerHomePageActivity extends AppCompatActivity {

    private CardView cPremium, cGourmet, cVegano, cEspeciais, cEncomendar, cPedidos, cHistoria;

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
    }

    private void openListOfProducts(CategoryEnum categoria) {
        Intent intent = new Intent(this, CustomerListOfProductsActivity.class);
        intent.putExtra("category", categoria.toString()); // TODO: Verificar como esse Enum está sendo salvo no banco (uppercase etc.)
        startActivity(intent);
    }

    private void inicializarComponentes() {
        cPremium = findViewById(R.id.cardPremium);
        cGourmet = findViewById(R.id.cardGourmet);
        cVegano = findViewById(R.id.cardVegano);
        cEspeciais = findViewById(R.id.cardEspeciais);
        cEncomendar = findViewById(R.id.cardEncomenda);
        cPedidos = findViewById(R.id.cardPedidos);
        cHistoria = findViewById(R.id.cardOurHistory);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
