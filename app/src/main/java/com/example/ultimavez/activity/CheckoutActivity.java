package com.example.ultimavez.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.R;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.fragment.SuccessFragment;
import com.example.ultimavez.helper.Notifications;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.model.domain.Pedido;
import com.example.ultimavez.model.enums.TiposPagamentoEnum;
import com.example.ultimavez.service.PagamentoService;
import com.example.ultimavez.service.PedidoService;

import java.util.List;

public class CheckoutActivity extends AppCompatActivity {

    private EditText valor, desconto, valorTotal, cupom;
    private Button aplicarCupom, finalizarCompra;
    private Carrinho carrinho;
    private Pedido pedido;
    private PedidoService pedidoService = new PedidoService();
    private PagamentoService pagamentoService = new PagamentoService();
    private RadioGroup tiposDePagamento;
    SharedPreferences sharedPreferences;
    private TiposPagamentoEnum tiposPagamentoEnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        getSupportActionBar().hide();

        inicializarComponentes();
        buildResumoPedido();

        aplicarCupom.setOnClickListener(it -> applyCupom());
        finalizarCompra.setOnClickListener(it -> realizarPagamento());
        tiposDePagamento.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton selectedRadioButton = findViewById(checkedId);
            if (selectedRadioButton != null) {
                String selectedPaymentMethod = selectedRadioButton.getText().toString();
                if (selectedPaymentMethod.equals("Pix")) {
                    tiposPagamentoEnum = TiposPagamentoEnum.PIX;
                } else if (selectedPaymentMethod.equals("Crédito")) {
                    tiposPagamentoEnum = TiposPagamentoEnum.CREDITO;
                }
            }
        });
    }

    private void inicializarComponentes() {
        valor = findViewById(R.id.compraTotal);
        desconto = findViewById(R.id.compraDesconto);
        valorTotal = findViewById(R.id.compraValorTotal);
        cupom = findViewById(R.id.cupom);
        aplicarCupom = findViewById(R.id.aplicarCupomDesconto);
        finalizarCompra = findViewById(R.id.finalizarCompra);
        carrinho = MyCustomApplication.getCarrinho();
        tiposDePagamento = findViewById(R.id.paymentMethodRadioGroup);
        pedido = pedidoService.buildFromCarrinho(carrinho);
    }

    private void buildResumoPedido() {
        valor.setText(String.valueOf(pedido.getValorOriginal()));
        desconto.setText(String.valueOf(pedido.getDesconto()));
        valorTotal.setText(String.valueOf(pedido.getValorFinal()));
    }

    private void applyCupom() { // Testar Só Depois que implementar o Save de cupom por parte do seller
        String codigoCupom = cupom.getText().toString();
        Result<Cupom> result = pedidoService.aplicarCupom(codigoCupom);

        if (!result.isValid()) {
            showErrors(result.getErrors());
        } else {
            recalcularPreco(result.getResultObject());
        }
    }

    private void recalcularPreco(Cupom resultObject) {
        pedido.setDesconto(resultObject.getValorDoDesconto());
        buildResumoPedido();
    }

    private void realizarPagamento() {
        if (tiposPagamentoEnum != null) {
            sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
            long sellerId = sharedPreferences.getLong("userId", 0);

            pedido.setTipoPaagamento(tiposPagamentoEnum);
            pedido.setIdComprador(sellerId);

            Result<Pedido> result = pagamentoService.pagar(pedido);

            if (!result.isValid()) {
                showErrors(result.getErrors());
            } else {
                showSuccess("Seu pedido foi recebido e pago com sucesso!", CustomerHomePageActivity.class);
                carrinho.cleanCarrinho();
            }
        }
    }

    private void showErrors(List<String> notifications) {
        ErrorInflator.showErrors(notifications, this);
    }

    private void showSuccess(String message, Class clazz) {
        SuccessFragment successDialog = new SuccessFragment(message, clazz);
        successDialog.show(getSupportFragmentManager(), null);
    }

}