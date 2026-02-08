// ui/catalog/ProductCatalogActivity.java (only the FAB click changes)
package com.example.blipplyprototype.ui.catalog;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.blipplyprototype.ui.profile.ProfileActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class ProductCatalogActivity extends AppCompatActivity {

    private final CartRepository cartRepository = CartRepository.getInstance();
    private FloatingActionButton fabCart;
    private TextView cartCount;
    private ProductAdapter adapter;
    private List<Product> allProducts;
    private String selectedCategory = "All";
    private String searchQuery = "";
    private LinearLayout categoryContainer;
    private EditText searchInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        RecyclerView recycler = findViewById(R.id.rvProducts);
        fabCart = findViewById(R.id.fabCart);
        cartCount = findViewById(R.id.textCartCount);
        categoryContainer = findViewById(R.id.categoryContainer);
        searchInput = findViewById(R.id.inputSearch);
        findViewById(R.id.buttonProfile)
                .setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));

        recycler.setLayoutManager(new LinearLayoutManager(this));

        CatalogRepository catalogRepository = new CatalogRepository(new MockDataSource());
        allProducts = catalogRepository.getProducts();

        adapter = new ProductAdapter(allProducts, new ProductAdapter.Listener() {
            @Override
            public void onQuantityChanged() {
                updateCartFab();
            }

            @Override
            public void onAdd(Product product) {
                // No toast feedback; handled by inline quantity UI.
            }
        });

        recycler.setAdapter(adapter);

        fabCart.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));

        setupCategories();
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchQuery = s.toString();
                updateFilter();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        updateCartFab();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCartFab();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void setupCategories() {
        Set<String> categories = new TreeSet<>();
        for (Product product : allProducts) {
            categories.add(product.getCategory());
        }

        List<String> chips = new ArrayList<>();
        chips.add("All");
        chips.addAll(categories);

        categoryContainer.removeAllViews();
        for (String category : chips) {
            Button button = new Button(this);
            button.setAllCaps(false);
            button.setText(category);
            button.setTextSize(14);
            button.setMinHeight(0);
            button.setMinimumHeight(0);
            button.setPadding(32, 16, 32, 16);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMarginEnd(12);
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                selectedCategory = category;
                updateCategoryStyles();
                updateFilter();
            });

            categoryContainer.addView(button);
        }

        updateCategoryStyles();
    }

    private void updateCategoryStyles() {
        int childCount = categoryContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = categoryContainer.getChildAt(i);
            if (!(view instanceof Button)) continue;
            Button button = (Button) view;
            boolean isSelected = button.getText().toString().equals(selectedCategory);
            if (isSelected) {
                button.setBackgroundTintList(getColorStateList(R.color.button_primary));
                button.setTextColor(getColor(R.color.blipply_green));
            } else {
                button.setBackgroundTintList(getColorStateList(R.color.blipply_green));
                button.setTextColor(getColor(R.color.black));
            }
        }
    }

    private void updateFilter() {
        String query = searchQuery == null ? "" : searchQuery.trim().toLowerCase(Locale.US);
        List<Product> filtered = new ArrayList<>();
        for (Product product : allProducts) {
            boolean matchesSearch = product.getName().toLowerCase(Locale.US).contains(query);
            boolean matchesCategory = selectedCategory.equals("All")
                    || product.getCategory().equals(selectedCategory);
            if (matchesSearch && matchesCategory) {
                filtered.add(product);
            }
        }
        adapter.updateProducts(filtered);
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
