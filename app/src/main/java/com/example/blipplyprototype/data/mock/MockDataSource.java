package com.example.blipplyprototype.data.mock;

import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.model.Order;
import com.example.blipplyprototype.data.model.OrderStatus;
import com.example.blipplyprototype.data.model.PaymentMethod;
import com.example.blipplyprototype.data.model.Product;

import java.util.List;
import java.util.UUID;

public class MockDataSource {

    public List<Product> getProducts() {
        return MockProducts.getProducts();
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
                status
        );
    }
}
