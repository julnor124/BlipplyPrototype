// ui/catalog/ProductCatalogActivity.java
package com.example.blipplyprototype.ui.catalog;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.Product;
import com.example.blipplyprototype.data.repository.CatalogRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductCatalogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        RecyclerView recycler = findViewById(R.id.rvProducts);
        FloatingActionButton fabCart = findViewById(R.id.fabCart);

        fabCart.setVisibility(FloatingActionButton.GONE);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        CatalogRepository catalogRepository = new CatalogRepository(new MockDataSource());
        List<Product> products = catalogRepository.getProducts();

        recycler.setAdapter(new ProductAdapter(products));
    }
}
