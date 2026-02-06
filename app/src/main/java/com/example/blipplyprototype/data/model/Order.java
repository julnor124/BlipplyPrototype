package com.example.blipplyprototype.data.model;

import java.util.List;

public class Order {
    private final String id;
    private final List<CartItem> items;
    private final int totalCents;
    private final PaymentMethod paymentMethod;
    private final OrderStatus status;

    public Order(
            String id,
            List<CartItem> items,
            int totalCents,
            PaymentMethod paymentMethod,
            OrderStatus status
    ) {
        this.id = id;
        this.items = items;
        this.totalCents = totalCents;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public int getTotalCents() {
        return totalCents;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
