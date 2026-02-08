package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartRepository {

    private static final CartRepository INSTANCE = new CartRepository();
    private static final double VAT_RATE = 0.15;
    private static final int DELIVERY_FEE_CENTS = 5000; // KSh 50.00
    private static final int FREE_DELIVERY_THRESHOLD_CENTS = 50000; // KSh 500.00

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

    public int getSubtotalCents() {
        int total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPriceCents() * item.getQuantity();
        }
        return total;
    }

    public int getVatCents() {
        return (int) Math.round(getSubtotalCents() * VAT_RATE);
    }

    public int getDeliveryCents() {
        return getSubtotalCents() > FREE_DELIVERY_THRESHOLD_CENTS ? 0 : DELIVERY_FEE_CENTS;
    }

    public int getGrandTotalCents() {
        return getSubtotalCents() + getVatCents() + getDeliveryCents();
    }

    public void clear() {
        items.clear();
    }
}
