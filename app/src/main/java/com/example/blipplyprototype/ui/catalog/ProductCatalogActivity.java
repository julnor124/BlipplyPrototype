// ui/catalog/ProductCatalogActivity.java
package com.example.blipplyprototype.ui.catalog;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.Product;
import com.example.blipplyprototype.data.repository.CartRepository;
import com.example.blipplyprototype.data.repository.CatalogRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductCatalogActivity extends AppCompatActivity {

    // Simple prototype state holder (keeps cart while app process lives)
    private static final CartRepository cartRepository = new CartRepository();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        RecyclerView recycler = findViewById(R.id.rvProducts);
        FloatingActionButton fabCart = findViewById(R.id.fabCart);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        CatalogRepository catalogRepository = new CatalogRepository(new MockDataSource());
        List<Product> products = catalogRepository.getProducts();

        ProductAdapter adapter = new ProductAdapter(products, product -> {
            cartRepository.addProduct(product);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            updateCartFab(fabCart);
        });

        recycler.setAdapter(adapter);

        // For this commit: no cart screen yet
        fabCart.setOnClickListener(v -> {
            int count = cartRepository.getItems().size();
            Toast.makeText(this, "Cart items: " + count, Toast.LENGTH_SHORT).show();
        });

        updateCartFab(fabCart);
    }

    private void updateCartFab(FloatingActionButton fabCart) {
        int count = cartRepository.getItems().size();
        fabCart.setVisibility(count > 0 ? FloatingActionButton.VISIBLE : FloatingActionButton.GONE);
    }
}
