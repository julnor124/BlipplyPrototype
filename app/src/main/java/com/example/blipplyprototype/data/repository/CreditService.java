package com.example.blipplyprototype.data.repository;

public class CreditService {
    private static final double CREDIT_LIMIT = 200.0;

    public boolean checkCreditApproval(double totalAmount) {
        // Logic: Approves credit if total is less than limit
        return totalAmount <= CREDIT_LIMIT;
    }
}