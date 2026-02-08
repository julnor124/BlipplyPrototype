package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.model.Order;
import com.example.blipplyprototype.data.model.OrderStatus;
import com.example.blipplyprototype.data.model.PaymentMethod;
import com.example.blipplyprototype.data.model.Product;

import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrderRepositoryTest {

    @Test
    public void advance_payment_is_confirmed() {
        OrderRepository repository = new OrderRepository(new MockDataSource());
        Order order = repository.placeOrder(Collections.emptyList(), 10000, PaymentMethod.ADVANCE);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
    }

    @Test
    public void onsite_payment_is_pay_on_delivery() {
        OrderRepository repository = new OrderRepository(new MockDataSource());
        Order order = repository.placeOrder(Collections.emptyList(), 10000, PaymentMethod.ONSITE);
        assertEquals(OrderStatus.PAY_ON_DELIVERY, order.getStatus());
    }

    @Test
    public void credit_over_limit_is_insufficient() {
        OrderRepository repository = new OrderRepository(new MockDataSource());
        Order order = repository.placeOrder(Collections.emptyList(), 999999, PaymentMethod.CREDIT);
        assertEquals(OrderStatus.INSUFFICIENT_CREDIT, order.getStatus());
        assertNotNull(order.getCreditUsedCents());
    }
}
