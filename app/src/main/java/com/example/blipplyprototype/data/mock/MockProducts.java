package com.example.blipplyprototype.data.mock;

import com.example.blipplyprototype.data.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MockProducts {

    private MockProducts() {
        // no instances
    }

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product("p1", "Coffee beans 1kg", 12900));
        products.add(new Product("p2", "Milk 1L", 1790));
        products.add(new Product("p3", "Pasta 500g", 2490));
        products.add(new Product("p4", "Olive oil 500ml", 8990));

        return Collections.unmodifiableList(products);
    }
}
