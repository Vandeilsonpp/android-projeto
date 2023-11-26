package com.example.ultimavez.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.R;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.fragment.SuccessFragment;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;
import com.example.ultimavez.service.UserService;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword, txtFullName, txtCpf, txtPhone, txtAddress, txtZipCode, txtCity, txtSenhaNovamente;
    private Button btnSignup;
    private CheckBox verSenhas;
    private MaterialButtonToggleGroup toggleGroup;
    private UserEnum userType;

    private final UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        initComponents();

        btnSignup.setOnClickListener(it -> saveUser());

        txtPhone.addTextChangedListener(formatPhoneNumber());
        txtCpf.addTextChangedListener(formatDocument());
        txtZipCode.addTextChangedListener(formatCep());
        verSenhas.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int inputType = isChecked ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    : InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
            txtPassword.setInputType(inputType);
            txtPassword.setSelection(txtPassword.length());
            txtSenhaNovamente.setInputType(inputType);
            txtSenhaNovamente.setSelection(txtSenhaNovamente.length());
        });

        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                switch (checkedId) {
                    case R.id.btncliente2:
                        userType = UserEnum.CUSTOMER;
                        break;
                    case R.id.btnLojista2:
                        userType = UserEnum.SELLER;
                        break;

                }
            } else  {
                userType = null;
            }
        });

    }

    @NonNull
    private TextWatcher formatPhoneNumber() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();

                if (input.length() == 11) {
                    String formatted = String.format("(%s)%s-%s",
                            input.substring(0, 2),
                            input.substring(2, 7),
                            input.substring(7)
                    );
                    txtPhone.removeTextChangedListener(this);
                    txtPhone.setText(formatted);
                    txtPhone.setSelection(formatted.length());
                    txtPhone.addTextChangedListener(this);
                }
            }
        };
    }

    @NonNull
    private TextWatcher formatDocument() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();

                if (input.length() == 11) {
                    String formatted = String.format("%s.%s.%s-%s",
                            input.substring(0, 3),
                            input.substring(3, 6),
                            input.substring(6, 9),
                            input.substring(9)
                    );
                    txtCpf.removeTextChangedListener(this);
                    txtCpf.setText(formatted);
                    txtCpf.setSelection(formatted.length());
                    txtCpf.addTextChangedListener(this);
                }
            }
        };
    }

    @NonNull
    private TextWatcher formatCep() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();

                if (input.length() == 8) {
                    String formatted = String.format("%s-%s",
                            input.substring(0, 5),
                            input.substring(5)
                    );
                    txtZipCode.removeTextChangedListener(this);
                    txtZipCode.setText(formatted);
                    txtZipCode.setSelection(formatted.length());
                    txtZipCode.addTextChangedListener(this);
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initComponents() {
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtFullName = findViewById(R.id.txtFullName);
        txtCpf = findViewById(R.id.txtCpf);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtZipCode = findViewById(R.id.txtZipCode);
        txtCity = findViewById(R.id.txtCity);
        btnSignup = findViewById(R.id.btnSign);
        toggleGroup = findViewById(R.id.materialButtonToggleGroup2);
        txtSenhaNovamente = findViewById(R.id.txtPasswordAgain);
        verSenhas = findViewById(R.id.checkBoxShowPasswordSignUp);
    }

    private void saveUser() {
        if (this.userType == null) {
            Toast.makeText(this, "Selecione se você é Lojista ou Cliente", Toast.LENGTH_SHORT).show();
            return ;
        }
        User user = buildUserFromInput();
        Result<User> result = userService.saveUser(user);

        if (!result.isValid()) {
            showErrors(result.getErrors());
        } else {
            showSuccess();
        }
    }

    @NonNull
    private User buildUserFromInput() {
        String email = txtEmail.getText().toString().toLowerCase(Locale.ROOT);
        String password = txtPassword.getText().toString();
        String fullName = txtFullName.getText().toString().toLowerCase(Locale.ROOT);
        String cpf = txtCpf.getText().toString();
        String phone = txtPhone.getText().toString();
        String address = txtAddress.getText().toString().toLowerCase(Locale.ROOT);
        String zipCode = txtZipCode.getText().toString();
        String city = txtCity.getText().toString().toLowerCase(Locale.ROOT);
        UserEnum userType = this.userType;
        String passwordAgain = txtSenhaNovamente.getText().toString();

        return new User(userType, email,password,fullName, cpf, phone, address, zipCode, city, passwordAgain);
    }

    private void showErrors(List<String> notifications) {
        ErrorInflator.showErrors(notifications, this);
    }

    private void showSuccess() {
        SuccessFragment successDialog = new SuccessFragment("Cadastro realizado com sucesso", AuthActivity.class);
        successDialog.show(getSupportFragmentManager(), null);
    }
}
