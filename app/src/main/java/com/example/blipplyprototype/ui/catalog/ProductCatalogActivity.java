// ui/catalog/ProductCatalogActivity.java (only the FAB click changes)
package com.example.blipplyprototype.ui.catalog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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
import com.example.blipplyprototype.ui.cart.CartActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductCatalogActivity extends AppCompatActivity {

    private final CartRepository cartRepository = CartRepository.getInstance();
    private FloatingActionButton fabCart;
    private TextView cartCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        RecyclerView recycler = findViewById(R.id.rvProducts);
        fabCart = findViewById(R.id.fabCart);
        cartCount = findViewById(R.id.textCartCount);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        CatalogRepository catalogRepository = new CatalogRepository(new MockDataSource());
        List<Product> products = catalogRepository.getProducts();

        ProductAdapter adapter = new ProductAdapter(products, product -> {
            cartRepository.addProduct(product);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
            updateCartFab();
        });

        recycler.setAdapter(adapter);

        fabCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

        updateCartFab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartFab();
    }

    private void updateCartFab() {
        int count = 0;
        for (com.example.blipplyprototype.data.model.CartItem item : cartRepository.getItems()) {
            count += item.getQuantity();
        }

        boolean hasItems = count > 0;
        fabCart.setVisibility(hasItems ? FloatingActionButton.VISIBLE : FloatingActionButton.GONE);

        if (!hasItems) {
            cartCount.setVisibility(View.GONE);
            return;
        }

        cartCount.setVisibility(View.VISIBLE);
        cartCount.setText(count > 9 ? "9+" : String.valueOf(count));
    }
}
