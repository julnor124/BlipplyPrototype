package com.example.blipplyprototype.data.model;

public class Product {
    private final String id;
    private final String name;
    private final int priceCents;

    public Product(String id, String name, int priceCents) {
        this.id = id;
        this.name = name;
        this.priceCents = priceCents;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriceCents() {
        return priceCents;
    }
}
