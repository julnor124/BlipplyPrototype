package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.model.Product;
import com.example.blipplyprototype.data.model.User;
import com.example.blipplyprototype.data.remote.FakeApiService;

import java.util.List;

public class BlipplyRepository {
    private static BlipplyRepository instance;
    private final FakeApiService apiService;

    private BlipplyRepository() {
        apiService = new FakeApiService();
    }

    public static synchronized BlipplyRepository getInstance() {
        if (instance == null) {
            instance = new BlipplyRepository();
        }
        return instance;
    }

    public List<Product> getProducts() {
        return apiService.getProducts();
    }

    public User login(String email, String password) {
        return apiService.login(email, password);
    }
}