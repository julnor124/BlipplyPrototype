package com.example.blipplyprototype.data.repository;

import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.Product;

import java.util.List;

public class CatalogRepository {

    private final MockDataSource dataSource;

    public CatalogRepository(MockDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getProducts() {
        return dataSource.getProducts();
    }
}
