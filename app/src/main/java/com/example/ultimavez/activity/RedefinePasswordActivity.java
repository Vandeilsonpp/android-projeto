package com.example.ultimavez.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.R;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.fragment.SuccessFragment;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.DTO.RedefinePasswordDTO;
import com.example.ultimavez.service.UserService;

public class RedefinePasswordActivity extends AppCompatActivity {

    private EditText email, novaSenha, confirmarNovaSenha;
    private Button redefinir;
    private final UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redefine);
        getSupportActionBar().hide();

        inicializarComponentes();
        redefinir.setOnClickListener(it -> updatePassword());

    }

    private void inicializarComponentes() {
        email = findViewById(R.id.txtEmailVerify);
        novaSenha = findViewById(R.id.txtNewPassword);
        confirmarNovaSenha = findViewById(R.id.txtSamePassword);
        redefinir = findViewById(R.id.btnRedefine);
    }

    private void updatePassword() {
        RedefinePasswordDTO passwordDTO = new RedefinePasswordDTO(
                email.getText().toString(),
                novaSenha.getText().toString(),
                confirmarNovaSenha.getText().toString()
                );

        Result<RedefinePasswordDTO> result = userService.updatePassword(passwordDTO);

        if (!result.isValid()) {
            ErrorInflator.showErrors(result.getErrors(), this);
        } else {
            SuccessFragment successDialog = new SuccessFragment("Senha atualizada com sucesso", AuthActivity.class);
            successDialog.show(getSupportFragmentManager(), null);
        }
    }

}
