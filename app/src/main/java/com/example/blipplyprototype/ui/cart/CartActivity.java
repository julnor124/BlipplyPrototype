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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private final CartRepository cartRepository = CartRepository.getInstance();

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
            showClearAllConfirmation();
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
                showRemoveConfirmation(item);
            }
        });

        rv.setAdapter(adapter);

        checkout.setOnClickListener(v -> {
            startActivity(new Intent(this, CheckoutActivity.class));
        });

        refresh();
    }

    private void showRemoveConfirmation(CartItem item) {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Remove item")
                .setMessage("Remove " + item.getProduct().getName() + " from your cart?")
                .setPositiveButton("Remove", (dialog, which) -> {
                    cartRepository.setQuantity(item.getProduct(), 0);
                    refresh();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showClearAllConfirmation() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Clear cart")
                .setMessage("Remove all items from your cart?")
                .setPositiveButton("Clear", (dialog, which) -> {
                    cartRepository.clear();
                    refresh();
                })
                .setNegativeButton("Cancel", null)
                .show();
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

            int subtotalCents = cartRepository.getSubtotalCents();
            int vatCents = cartRepository.getVatCents();
            int deliveryCents = cartRepository.getDeliveryCents();
            int grandTotalCents = cartRepository.getGrandTotalCents();

            textSubtotal.setText(String.format(Locale.US, "KSh %.2f", subtotalCents / 100.0));
            textVat.setText(String.format(Locale.US, "KSh %.2f", vatCents / 100.0));
            textDelivery.setText(String.format(Locale.US, "KSh %.2f", deliveryCents / 100.0));
            textGrandTotal.setText(String.format(Locale.US, "KSh %.2f", grandTotalCents / 100.0));
        }

        adapter.notifyDataSetChanged();
    }
}
