package com.example.blipplyprototype.ui.auth;

import android.content.Intent;
import android.os.Bundle;
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

        loginButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

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
}
