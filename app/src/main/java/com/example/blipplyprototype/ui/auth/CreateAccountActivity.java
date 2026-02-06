// ui/auth/CreateAccountActivity.java
package com.example.blipplyprototype.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.repository.AuthRepository;
import com.example.blipplyprototype.ui.catalog.ProductCatalogActivity;

public class CreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageButton back = findViewById(R.id.buttonBack);

        EditText businessName = findViewById(R.id.inputBusinessName);
        EditText email = findViewById(R.id.inputEmail);
        EditText phone = findViewById(R.id.inputPhone);
        EditText password = findViewById(R.id.inputPassword);
        EditText confirmPassword = findViewById(R.id.inputConfirmPassword);

        Button create = findViewById(R.id.buttonCreateAccount);

        AuthRepository authRepository = new AuthRepository();

        back.setOnClickListener(v -> finish());

        create.setOnClickListener(v -> {
            String bn = businessName.getText().toString().trim();
            String em = email.getText().toString().trim();
            String ph = phone.getText().toString().trim();
            String pw = password.getText().toString();
            String cpw = confirmPassword.getText().toString();

            if (bn.isEmpty() || em.isEmpty() || ph.isEmpty() || pw.isEmpty() || cpw.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pw.equals(cpw)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Mock “create account”
            boolean success = authRepository.createAccount(bn, em, ph, pw);

            if (!success) {
                Toast.makeText(this, "Could not create account", Toast.LENGTH_SHORT).show();
                return;
            }

            // Go to catalog (or back to login — your choice)
            Intent intent = new Intent(this, ProductCatalogActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
