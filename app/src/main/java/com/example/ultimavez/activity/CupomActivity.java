package com.example.ultimavez.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.R;

public class CupomActivity extends AppCompatActivity {

    private EditText txtBuscarCupom, txtCodigoCupom, txtValorCupom;
    private Button buscarCupom, atualizarCupom;
    private Spinner ativarCupom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_home_page);
        getSupportActionBar().hide();

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        txtBuscarCupom = findViewById(R.id.editTextCupomSearchName);
        txtCodigoCupom = findViewById(R.id.editTextCupomCod);
        txtValorCupom = findViewById(R.id.editTextNewCupomValue);
        buscarCupom = findViewById(R.id.btnSearchUpdCupom);
        atualizarCupom = findViewById(R.id.btnUpdCupom);
        ativarCupom = findViewById(R.id.spActive);
    }
}
