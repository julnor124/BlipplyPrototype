package com.example.blipplyprototype.data.model;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private String orderId;
    private List<Product> items;
    private double totalAmount;
    private String paymentMethod;
    private boolean creditApproved;
    private String status;

    public Order(String orderId, List<Product> items, double totalAmount, String paymentMethod, boolean creditApproved) {
        this.orderId = orderId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.creditApproved = creditApproved;
        this.status = "Pending";
    }

    public String getOrderId() { return orderId; }
    public List<Product> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public boolean isCreditApproved() { return creditApproved; }
    public String getStatus() { return status; }
}