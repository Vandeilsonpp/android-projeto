package com.example.ultimavez.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.R;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.fragment.SuccessFragment;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.service.CupomService;

import java.util.List;
import java.util.Optional;

public class CupomActivity extends AppCompatActivity {

    private EditText txtBuscarCupom, txtCodigoCupom, txtValorCupom;
    private Button buscarCupom, atualizarCupom;
    private Spinner ativarCupom;
    private Cupom cupomEditavel;

    private final CupomService cupomService = new CupomService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_cupom);
        getSupportActionBar().hide();

        inicializarComponentes();

        buscarCupom.setOnClickListener(it -> findCupom());
        atualizarCupom.setOnClickListener(it -> attCupom());
    }

    private void inicializarComponentes() {
        txtBuscarCupom = findViewById(R.id.editTextCupomSearchName);
        txtCodigoCupom = findViewById(R.id.editTextCupomCod);
        txtValorCupom = findViewById(R.id.editTextNewCupomValue);
        buscarCupom = findViewById(R.id.btnSearchUpdCupom);
        atualizarCupom = findViewById(R.id.btnUpdCupom);
        ativarCupom = findViewById(R.id.spActive);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.ativar, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ativarCupom.setAdapter(adapter);
    }

    private void attCupom() {
        // TODO: Verificar por null antes
        Cupom cupomAtualizado = buildFromInput();

        Result<Cupom> result = cupomService.attCupom(cupomAtualizado);

        if (!result.isValid()) {
            showErrors(result.getErrors());
        } else {
            showSuccess("Cupom registrado");
        }
    }

    private Cupom buildFromInput() {
        String codigo = txtCodigoCupom.getText().toString();
        double valor = Double.parseDouble(txtValorCupom.getText().toString());
        boolean ativado = ativarCupom.getSelectedItemId() == 0;

        return new Cupom(codigo, ativado, valor);
    }

    private void findCupom() {
        String codigoCupom = txtBuscarCupom.getText().toString();

        Optional<Cupom> cupomEncontrado = cupomService.findCupom(codigoCupom);
        if (!cupomEncontrado.isPresent()) {
            ErrorInflator.showErrors("Cupom não encontrado", this);
        } else {
            cupomEditavel = cupomEncontrado.get();
            mostrarCupomNaTela(cupomEditavel);
        }
    }

    private void mostrarCupomNaTela(Cupom cupomEditavel) {
        txtCodigoCupom.setText(cupomEditavel.getCodigo());
        txtValorCupom.setText(String.valueOf(cupomEditavel.getValorDoDesconto()));

        String habilitado = cupomEditavel.eValido() ? "Sim" : "Não";
        int index = getCategoryIndex(habilitado);
        ativarCupom.setSelection(index);
    }

    private int getCategoryIndex(String category) {
        String[] options = {"Sim", "Não"};

        for (int i = 0; i < options.length; i++) {
            if (options[i].equalsIgnoreCase(category)) {
                return i;
            }
        }

        return 0;
    }

    private void showErrors(List<String> notifications) {
        ErrorInflator.showErrors(notifications, this);
    }

    private void showSuccess(String message) {
        SuccessFragment successDialog = new SuccessFragment(message, SellerHomePageActivity.class);
        successDialog.show(getSupportFragmentManager(), null);
    }
}
