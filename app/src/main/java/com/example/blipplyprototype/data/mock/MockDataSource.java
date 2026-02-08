package com.example.blipplyprototype.data.mock;

import com.example.blipplyprototype.data.model.Product;

import java.util.List;

public class MockDataSource {

    // Mock credit data
    private final int availableCreditCents = 500000; // KSh 5000.00
    private final int usedCreditCents = 125000;      // KSh 1250.00

    public List<Product> getProducts() {
        return MockProducts.getProducts();
    }

    public int getAvailableCreditCents() {
        return availableCreditCents;
    }

    public int getUsedCreditCents() {
        return usedCreditCents;
    }

}
