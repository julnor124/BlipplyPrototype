// ui/cart/CartActivity.java
package com.example.blipplyprototype.ui.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.model.CartItem;
import com.example.blipplyprototype.data.repository.CartRepository;
import com.example.blipplyprototype.ui.checkout.CheckoutActivity;

import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private final CartRepository cartRepository = CartRepository.getInstance();

    private CartAdapter adapter;
    private TextView textEmpty;
    private LinearLayout bottomSection;
    private TextView textTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ImageButton back = findViewById(R.id.buttonBack);
        RecyclerView rv = findViewById(R.id.rvCart);
        textEmpty = findViewById(R.id.textEmpty);
        bottomSection = findViewById(R.id.bottomSection);
        textTotal = findViewById(R.id.textTotal);
        Button checkout = findViewById(R.id.buttonCheckout);

        back.setOnClickListener(v -> finish());

        rv.setLayoutManager(new LinearLayoutManager(this));

        List<CartItem> items = cartRepository.getItems();
        adapter = new CartAdapter(items, new CartAdapter.Listener() {
            @Override
            public void onIncrease(CartItem item) {
                cartRepository.setQuantity(item.getProduct(), item.getQuantity() + 1);
                refresh();
            }

            @Override
            public void onDecrease(CartItem item) {
                cartRepository.setQuantity(item.getProduct(), item.getQuantity() - 1);
                refresh();
            }

            @Override
            public void onRemove(CartItem item) {
                cartRepository.setQuantity(item.getProduct(), 0);
                refresh();
            }
        });

        rv.setAdapter(adapter);

        checkout.setOnClickListener(v -> {
            // Next commit will implement the checkout screen details
            startActivity(new Intent(this, CheckoutActivity.class));
        });

        refresh();
    }

    private void refresh() {
        int count = cartRepository.getItems().size();

        if (count == 0) {
            textEmpty.setVisibility(View.VISIBLE);
            bottomSection.setVisibility(View.GONE);
        } else {
            textEmpty.setVisibility(View.GONE);
            bottomSection.setVisibility(View.VISIBLE);

            textTotal.setText(String.format(Locale.US, "R %.2f", cartRepository.getTotalCents() / 100.0));
        }

        adapter.notifyDataSetChanged();
    }
}
