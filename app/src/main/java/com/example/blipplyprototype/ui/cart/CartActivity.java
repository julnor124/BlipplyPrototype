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
    private static final double VAT_RATE = 0.15;
    private static final int DELIVERY_FEE_CENTS = 5000; // KSh 50.00
    private static final int FREE_DELIVERY_THRESHOLD_CENTS = 50000; // KSh 500.00

    private CartAdapter adapter;
    private LinearLayout emptyState;
    private TextView textClearAll;
    private LinearLayout bottomSection;
    private TextView textSubtotal;
    private TextView textVat;
    private TextView textDelivery;
    private TextView textGrandTotal;
    private RecyclerView rv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ImageButton back = findViewById(R.id.buttonBack);
        rv = findViewById(R.id.rvCart);
        emptyState = findViewById(R.id.emptyState);
        textClearAll = findViewById(R.id.textClearAll);
        bottomSection = findViewById(R.id.bottomSection);
        textSubtotal = findViewById(R.id.textSubtotal);
        textVat = findViewById(R.id.textVat);
        textDelivery = findViewById(R.id.textDelivery);
        textGrandTotal = findViewById(R.id.textGrandTotal);
        Button checkout = findViewById(R.id.buttonCheckout);
        Button browse = findViewById(R.id.buttonBrowse);

        back.setOnClickListener(v -> finish());
        textClearAll.setOnClickListener(v -> {
            cartRepository.clear();
            refresh();
        });
        browse.setOnClickListener(v -> finish());

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
            emptyState.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
            textClearAll.setVisibility(View.GONE);
            bottomSection.setVisibility(View.GONE);
        } else {
            emptyState.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            textClearAll.setVisibility(View.VISIBLE);
            bottomSection.setVisibility(View.VISIBLE);

            int subtotalCents = cartRepository.getTotalCents();
            int vatCents = (int) Math.round(subtotalCents * VAT_RATE);
            int deliveryCents = subtotalCents > FREE_DELIVERY_THRESHOLD_CENTS ? 0 : DELIVERY_FEE_CENTS;
            int grandTotalCents = subtotalCents + vatCents + deliveryCents;

            textSubtotal.setText(String.format(Locale.US, "KSh %.2f", subtotalCents / 100.0));
            textVat.setText(String.format(Locale.US, "KSh %.2f", vatCents / 100.0));
            textDelivery.setText(String.format(Locale.US, "KSh %.2f", deliveryCents / 100.0));
            textGrandTotal.setText(String.format(Locale.US, "KSh %.2f", grandTotalCents / 100.0));
        }

        adapter.notifyDataSetChanged();
    }
}
