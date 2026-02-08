// ui/auth/CreateAccountActivity.java
package com.example.blipplyprototype.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
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

        businessName.addTextChangedListener(clearErrorWatcher(businessName));
        email.addTextChangedListener(clearErrorWatcher(email));
        phone.addTextChangedListener(clearErrorWatcher(phone));
        password.addTextChangedListener(clearErrorWatcher(password));
        confirmPassword.addTextChangedListener(clearErrorWatcher(confirmPassword));

        create.setOnClickListener(v -> {
            String bn = businessName.getText().toString().trim();
            String em = email.getText().toString().trim();
            String ph = phone.getText().toString().trim();
            String pw = password.getText().toString();
            String cpw = confirmPassword.getText().toString();

            if (!validateCreateAccount(businessName, email, phone, password, confirmPassword, bn, em, ph, pw, cpw)) {
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

    private boolean validateCreateAccount(
            EditText businessName,
            EditText email,
            EditText phone,
            EditText password,
            EditText confirmPassword,
            String bn,
            String em,
            String ph,
            String pw,
            String cpw
    ) {
        if (bn.isEmpty()) {
            businessName.setError("Business name is required");
            businessName.requestFocus();
            return false;
        }
        if (em.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
            email.setError("Enter a valid email");
            email.requestFocus();
            return false;
        }
        if (ph.isEmpty()) {
            phone.setError("Phone number is required");
            phone.requestFocus();
            return false;
        }
        if (ph.replaceAll("\\D", "").length() < 8) {
            phone.setError("Enter a valid phone number");
            phone.requestFocus();
            return false;
        }
        if (pw.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return false;
        }
        if (pw.length() < 6) {
            password.setError("Password must be at least 6 characters");
            password.requestFocus();
            return false;
        }
        if (cpw.isEmpty()) {
            confirmPassword.setError("Confirm your password");
            confirmPassword.requestFocus();
            return false;
        }
        if (!pw.equals(cpw)) {
            confirmPassword.setError("Passwords do not match");
            confirmPassword.requestFocus();
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
