package com.example.blipplyprototype.data.remote;

import com.example.blipplyprototype.data.model.Product;
import com.example.blipplyprototype.data.model.User;

import java.util.ArrayList;
import java.util.List;

public class FakeApiService {

    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("1", "Wholesale Coffee Beans 1kg", 25.0, "Premium Arabica"));
        products.add(new Product("2", "Paper Cups 500pk", 45.0, "Eco-friendly 12oz"));
        products.add(new Product("3", "Oat Milk 6x1L", 18.0, "Barista Edition"));
        products.add(new Product("4", "Sugar Sachets 1000pk", 12.5, "White fine sugar"));
        return products;
    }

    public User login(String email, String password) {
        if ("merchant@example.com".equals(email) && "password123".equals(password)) {
            return new User(email, "Blipply Merchant");
        }
        return null;
    }
}