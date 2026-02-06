package com.example.blipplyprototype.data.mock;

import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.model.Order;
import com.example.blipplyprototype.data.model.OrderStatus;
import com.example.blipplyprototype.data.model.PaymentMethod;
import com.example.blipplyprototype.data.model.Product;

import java.util.List;
import java.util.UUID;

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

    public Order createOrder(
            List<CartItem> items,
            PaymentMethod paymentMethod,
            int totalCents
    ) {
        OrderStatus status;

        switch (paymentMethod) {
            case CREDIT:
                status = OrderStatus.CONFIRMED;
                break;
            case ONSITE:
                status = OrderStatus.PAY_ON_DELIVERY;
                break;
            case ADVANCE:
            default:
                status = OrderStatus.PENDING_PAYMENT;
                break;
        }

        return new Order(
                UUID.randomUUID().toString(),
                items,
                totalCents,
                paymentMethod,
                status,
                null
        );
    }
}
