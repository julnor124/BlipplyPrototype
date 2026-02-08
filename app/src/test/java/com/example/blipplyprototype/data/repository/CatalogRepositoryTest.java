package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.mock.MockDataSource;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class CatalogRepositoryTest {

    @Test
    public void returns_products_from_data_source() {
        CatalogRepository repository = new CatalogRepository(new MockDataSource());
        assertFalse(repository.getProducts().isEmpty());
    }
}
