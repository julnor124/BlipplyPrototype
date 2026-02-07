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
        products.add(new Product("p5", "Brown sugar 1kg", 2190));
        products.add(new Product("p6", "Black tea 250g", 1590));
        products.add(new Product("p7", "Rice 2kg", 4590));
        products.add(new Product("p8", "Wheat flour 2kg", 1990));
        products.add(new Product("p9", "Tomato paste 400g", 1290));
        products.add(new Product("p10", "Canned beans 400g", 1190));
        products.add(new Product("p11", "Laundry detergent 1L", 4990));
        products.add(new Product("p12", "Dish soap 750ml", 1490));
        products.add(new Product("p13", "Bottled water 6-pack", 990));
        products.add(new Product("p14", "Sugar-free soda 2L", 1790));
        products.add(new Product("p15", "Toilet paper 9-pack", 3990));
        products.add(new Product("p16", "Paper towels 6-pack", 3490));
        products.add(new Product("p17", "Eggs 30 pack", 5990));
        products.add(new Product("p18", "Cheddar cheese 500g", 5490));
        products.add(new Product("p19", "Cooking salt 1kg", 890));
        products.add(new Product("p20", "Sunflower oil 1L", 3790));

        return Collections.unmodifiableList(products);
    }
}
