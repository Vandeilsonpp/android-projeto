package com.example.ultimavez.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ultimavez.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        textView.setVisibility(View.INVISIBLE);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(2000);
        textView.startAnimation(fadeIn);

        textView.setVisibility(View.VISIBLE);

        new Handler().postDelayed(this::abrirAuth, 3000);
    }

    private void abrirAuth() {
        Intent i = new Intent(MainActivity.this, AuthActivity.class);
        startActivity(i);
        finish();
    }
}