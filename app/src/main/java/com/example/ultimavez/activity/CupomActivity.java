package com.example.ultimavez.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.R;

public class CupomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_home_page);
        getSupportActionBar().hide();
    }
}
