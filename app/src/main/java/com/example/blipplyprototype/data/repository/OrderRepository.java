package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.model.Order;
import com.example.blipplyprototype.data.model.OrderStatus;
import com.example.blipplyprototype.data.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderRepository {

    private final MockDataSource dataSource;

    public OrderRepository(MockDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Order placeOrder(List<CartItem> items, int totalCents, PaymentMethod method) {
        int remainingCreditCents = dataSource.getAvailableCreditCents() - dataSource.getUsedCreditCents();

        OrderStatus status;
        Integer creditUsedCents = null;

        if (method == PaymentMethod.ADVANCE) {
            status = OrderStatus.CONFIRMED;
        } else if (method == PaymentMethod.ONSITE) {
            status = OrderStatus.PAY_ON_DELIVERY;
        } else {
            // CREDIT
            creditUsedCents = Math.min(totalCents, remainingCreditCents);
            status = (totalCents <= remainingCreditCents)
                    ? OrderStatus.CONFIRMED
                    : OrderStatus.INSUFFICIENT_CREDIT;
        }

        String orderId = String.format(Locale.US, "%06d", (int) (Math.random() * 1_000_000));
        return new Order(orderId, new ArrayList<>(items), totalCents, method, status, creditUsedCents);
    }
}
