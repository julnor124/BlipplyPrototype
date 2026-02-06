package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.model.Order;
import com.example.blipplyprototype.data.model.PaymentMethod;

import java.util.List;

public class OrderRepository {

    private final MockDataSource dataSource;
    private Order lastOrder;

    public OrderRepository(MockDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Order createOrder(List<CartItem> items, PaymentMethod paymentMethod, int totalCents) {
        lastOrder = dataSource.createOrder(items, paymentMethod, totalCents);
        return lastOrder;
    }

    public Order getLastOrder() {
        return lastOrder;
    }
}
