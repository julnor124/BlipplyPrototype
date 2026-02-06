package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartRepository {

    private static final CartRepository INSTANCE = new CartRepository();

    private final List<CartItem> items = new ArrayList<>();

    private CartRepository() {
    }

    public static CartRepository getInstance() {
        return INSTANCE;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        items.add(new CartItem(product, 1));
    }

    public void removeProduct(Product product) {
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getProduct().getId().equals(product.getId())) {
                items.remove(i);
                return;
            }
        }
    }

    public void setQuantity(Product product, int quantity) {
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            if (item.getProduct().getId().equals(product.getId())) {
                if (quantity <= 0) {
                    items.remove(i);
                } else {
                    item.setQuantity(quantity);
                }
                return;
            }
        }
        if (quantity > 0) {
            items.add(new CartItem(product, quantity));
        }
    }

    public int getTotalCents() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPriceCents() * item.getQuantity();
        }
        return total;
    }

    public void clear() {
        items.clear();
    }
}
