package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.model.Product;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CartRepositoryTest {

    private CartRepository repository;

    @Before
    public void setUp() {
        repository = CartRepository.getInstance();
        repository.clear();
    }

    @Test
    public void totals_include_vat_and_delivery_fee() {
        Product product = new Product("p1", "Test Product", 10000, "per unit", "Test");
        repository.addProduct(product);
        repository.addProduct(product);

        assertEquals(20000, repository.getSubtotalCents());
        assertEquals(3000, repository.getVatCents());
        assertEquals(5000, repository.getDeliveryCents());
        assertEquals(28000, repository.getGrandTotalCents());
    }

    @Test
    public void delivery_fee_is_zero_over_threshold() {
        Product product = new Product("p2", "Expensive", 60000, "per unit", "Test");
        repository.addProduct(product);

        assertEquals(60000, repository.getSubtotalCents());
        assertEquals(9000, repository.getVatCents());
        assertEquals(0, repository.getDeliveryCents());
        assertEquals(69000, repository.getGrandTotalCents());
    }

    @Test
    public void clear_empties_cart() {
        Product product = new Product("p3", "To Clear", 1000, "per unit", "Test");
        repository.addProduct(product);
        repository.clear();
        assertEquals(0, repository.getItems().size());
    }

    @Test
    public void set_quantity_zero_removes_item() {
        Product product = new Product("p4", "Remove", 1000, "per unit", "Test");
        repository.addProduct(product);
        repository.setQuantity(product, 0);
        assertEquals(0, repository.getItems().size());
    }
}
