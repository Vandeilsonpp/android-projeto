package com.example.ultimavez.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.R;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.fragment.SuccessFragment;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Encomenda;
import com.example.ultimavez.service.EncomendaService;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;

public class EncomendaActivity extends AppCompatActivity {

    private EditText valor, quantidade, desconto, total;
    private Button encomendar;
    private Carrinho carrinho;
    private Encomenda encomenda;
    private EncomendaService service = new EncomendaService();
    SharedPreferences sharedPreferences;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encomenda);
        getSupportActionBar().hide();

        inicializarComponentes();
        buildEncomenda();
        encomendar.setOnClickListener(it -> salvarEncomenda());

    }

    private void inicializarComponentes() {
        valor = findViewById(R.id.encomendaTotal);
        quantidade = findViewById(R.id.enconmendaQuantidade);
        desconto = findViewById(R.id.encomendaDesconto);
        total = findViewById(R.id.encomendaValorFinal);
        encomendar = findViewById(R.id.finalizarEncomenda);
        carrinho = MyCustomApplication.getCarrinho();
        encomenda = buildResumoEncomenda(carrinho);
    }

    private Encomenda buildResumoEncomenda(Carrinho carrinho) {
        return new Encomenda(
                carrinho.getProductsQuantityMap(),
                carrinho.calculateTotalPrice());
    }

    private void buildEncomenda() {
        valor.setText(decimalFormat.format(encomenda.getValor()));
        quantidade.setText(String.valueOf(encomenda.getQuantidade()));
        desconto.setText(decimalFormat.format(encomenda.getDesconto()));
        total.setText(decimalFormat.format(encomenda.getValorFinal()));
    }

    private void salvarEncomenda() {
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        long sellerId = sharedPreferences.getLong("userId", 0);
        encomenda.setConsumerId(sellerId);
        encomenda.setCriadoEm(LocalDateTime.now());

        Result<Encomenda> result = service.salvarEncomenda(encomenda);

        if (!result.isValid()) {
            showErrors(result.getErrors());
        } else {
            showSuccess();
            carrinho.cleanCarrinho();
        }
    }

    private void showErrors(List<String> notifications) {
        ErrorInflator.showErrors(notifications, this);
    }

    private void showSuccess() {
        SuccessFragment successDialog = new SuccessFragment("Sua encomenda foi recebida com sucesso!", CustomerHomePageActivity.class);
        successDialog.show(getSupportFragmentManager(), null);
    }
}
