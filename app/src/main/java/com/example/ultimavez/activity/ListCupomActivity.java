package com.example.ultimavez.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimavez.R;
import com.example.ultimavez.adapter.CupomAdapter;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.listener.CupomClickListener;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.service.CupomService;

import java.util.List;

public class ListCupomActivity extends AppCompatActivity implements CupomClickListener {

    private final CupomService cupomService = new CupomService();
    private CupomAdapter cupomAdapter;
    private RecyclerView recyclerView;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_list_all_cupom);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        long sellerId = sharedPreferences.getLong("userId", 0);

        Result<List<Cupom>> result = cupomService.getAllCupomById(sellerId);

        if (!result.isValid()) {
            ErrorInflator.showErrors(result.getErrors(), this);
        } else {
            recyclerView = findViewById(R.id.cupom_recycler_item_view);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
            cupomAdapter = new CupomAdapter(result.getResultObject(), this, this::onCupomClick);
            recyclerView.setAdapter(cupomAdapter);
        }
    }

    @Override
    public void onCupomClick(Cupom cupom) {
        Intent intent = new Intent(this, CupomActivity.class);
        intent.putExtra("updatedCupom", cupom);
        startActivity(intent);
    }
}
