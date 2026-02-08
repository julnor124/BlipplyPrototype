package com.example.blipplyprototype.data.model;

public class Product {
    private final String id;
    private final String name;
    private final int priceCents;
    private final String unit;
    private final String category;

    public Product(String id, String name, int priceCents, String unit, String category) {
        this.id = id;
        this.name = name;
        this.priceCents = priceCents;
        this.unit = unit;
        this.category = category;
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

    public String getUnit() {
        return unit;
    }

    public String getCategory() {
        return category;
    }
}
