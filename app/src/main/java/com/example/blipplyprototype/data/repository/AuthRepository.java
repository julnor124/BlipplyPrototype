package com.example.blipplyprototype.data.repository;

public class AuthRepository {

    public boolean login(String email, String password) {
        return !isBlank(email) && !isBlank(password);
    }

    public boolean createAccount(String email, String password) {
        return !isBlank(email) && !isBlank(password);
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
