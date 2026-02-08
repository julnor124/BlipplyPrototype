package com.example.blipplyprototype.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.repository.AuthRepository;
import com.example.blipplyprototype.ui.catalog.ProductCatalogActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailInput = findViewById(R.id.input_email);
        EditText passwordInput = findViewById(R.id.input_password);
        Button loginButton = findViewById(R.id.button_login);
        TextView createAccount = findViewById(R.id.text_create_account);

        AuthRepository authRepository = new AuthRepository();

        emailInput.addTextChangedListener(clearErrorWatcher(emailInput));
        passwordInput.addTextChangedListener(clearErrorWatcher(passwordInput));

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (!validateLogin(emailInput, passwordInput, email, password)) {
                return;
            }

            boolean success = authRepository.login(email, password);

            if (!success) {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, ProductCatalogActivity.class);
            startActivity(intent);
            finish();
        });

        createAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateLogin(EditText emailInput, EditText passwordInput, String email, String password) {
        if (email.isEmpty()) {
            emailInput.setError("Email is required");
            emailInput.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Enter a valid email");
            emailInput.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            passwordInput.setError("Password is required");
            passwordInput.requestFocus();
            return false;
        }
        if (password.length() < 6) {
            passwordInput.setError("Password must be at least 6 characters");
            passwordInput.requestFocus();
            return false;
        }
        return true;
    }

    private TextWatcher clearErrorWatcher(EditText input) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
    }
}
