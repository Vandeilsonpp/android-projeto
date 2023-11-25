package com.example.ultimavez.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;

import com.example.ultimavez.R;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.DTO.LoginDTO;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;
import com.example.ultimavez.service.LoginService;

public class AuthActivity extends AppCompatActivity {

    private Button btnAcessar, btnCadastro, btnRedefinirSenha;
    private EditText campoEmail, camposenha;
    private Switch tipoAcesso;
    private CheckBox verSenha;
    private final LoginService loginService = new LoginService();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        getSupportActionBar().hide();

        inicializarComponentes();
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        btnAcessar.setOnClickListener(it -> realizarLogin());
        btnCadastro.setOnClickListener(view -> abrirCadastro());
        btnRedefinirSenha.setOnClickListener(view -> abrirRedefinirSenha());
        verSenha.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int inputType = isChecked ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            camposenha.setInputType(inputType);

            camposenha.setSelection(camposenha.length());
        });
    }
    private void realizarLogin() {
        LoginDTO loginDTO = new LoginDTO(
                campoEmail.getText().toString(),
                camposenha.getText().toString(),
                tipoAcesso.isChecked() ? UserEnum.SELLER : UserEnum.CUSTOMER);

        Result<User> result = loginService.login(loginDTO);

        if (!result.isValid()) {
            ErrorInflator.showErrors(result.getErrors(), this);
        } else {
            storeUserSession(result.getResultObject());

            Intent intent = new Intent(AuthActivity.this, defineHomePage(result.getResultObject().getType()));
            startActivity(intent);
        }
    }

    private Class<? extends AppCompatActivity> defineHomePage(UserEnum userType) {
        return userType == UserEnum.SELLER ? SellerHomePageActivity.class : CustomerHomePageActivity.class;
    }

    private void inicializarComponentes() {
        campoEmail = findViewById(R.id.txtEmail);
        camposenha = findViewById(R.id.txtPassword);
        btnCadastro = findViewById(R.id.btnSignup);
        btnAcessar = findViewById(R.id.btnLogin);
        tipoAcesso = findViewById(R.id.switchAcesso);
        btnRedefinirSenha = findViewById(R.id.btnForgotPassword);
        verSenha = findViewById(R.id.checkBoxShowPassword);
    }

    private void abrirCadastro() {
        Intent intent = new Intent(AuthActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void abrirRedefinirSenha() {
        Intent intent = new Intent(AuthActivity.this, RedefinePasswordActivity.class);
        startActivity(intent);
    }

    private void storeUserSession(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong("userId", user.getId());
        editor.apply();
    }
}