package com.example.blipplyprototype.data.repository;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthRepositoryTest {

    @Test
    public void login_requires_non_empty_email_and_password() {
        AuthRepository repository = new AuthRepository();
        assertFalse(repository.login("", "password"));
        assertFalse(repository.login("user@example.com", ""));
        assertFalse(repository.login(" ", " "));
        assertTrue(repository.login("user@example.com", "password"));
    }

    @Test
    public void create_account_requires_all_fields() {
        AuthRepository repository = new AuthRepository();
        assertFalse(repository.createAccount("Business", "", "0700000000", "password"));
        assertFalse(repository.createAccount("", "user@example.com", "0700000000", "password"));
        assertFalse(repository.createAccount("Business", "user@example.com", "", "password"));
        assertFalse(repository.createAccount("Business", "user@example.com", "0700000000", ""));
        assertTrue(repository.createAccount("Business", "user@example.com", "0700000000", "password"));
    }
}
