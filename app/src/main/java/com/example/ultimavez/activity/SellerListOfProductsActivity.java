package com.example.ultimavez.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimavez.R;
import com.example.ultimavez.adapter.ProductAdapter;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.listener.ProductClickListener;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.service.ProductService;

import java.util.List;

public class SellerListOfProductsActivity extends AppCompatActivity implements ProductClickListener {

    private final ProductService productService = new ProductService();
    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private TextView title;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_list_all_products);
        getSupportActionBar().hide();

        title = findViewById(R.id.list_title);
        title.setText("Meus produtos");

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        long sellerId = sharedPreferences.getLong("userId", 0);

        Result<List<Product>> result = productService.getAllProducts(sellerId);

        if (!result.isValid()) {
            ErrorInflator.showErrors(result.getErrors(), this);
        } else {
            recyclerView = findViewById(R.id.product_recycler_item_view);
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
            productAdapter = new ProductAdapter(result.getResultObject(), this, this::onProductClick);
            recyclerView.setAdapter(productAdapter);
        }
    }

    @Override
    public void onProductClick(Product product) {

    }
}