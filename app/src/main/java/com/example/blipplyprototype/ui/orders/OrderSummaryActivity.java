// ui/orders/OrderSummaryActivity.java
package com.example.blipplyprototype.ui.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.OnBackPressedCallback;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.Order;
import com.example.blipplyprototype.data.model.OrderStatus;
import com.example.blipplyprototype.data.model.PaymentMethod;
import com.example.blipplyprototype.data.repository.CartRepository;
import com.example.blipplyprototype.data.repository.OrderRepository;
import com.example.blipplyprototype.ui.catalog.ProductCatalogActivity;

import java.util.Locale;

public class OrderSummaryActivity extends AppCompatActivity {

    private static final CartRepository cartRepository = CartRepository.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        TextView orderId = findViewById(R.id.textOrderId);
        TextView statusText = findViewById(R.id.textStatus);
        LinearLayout statusCard = findViewById(R.id.cardStatus);

        TextView paymentMethodText = findViewById(R.id.textPaymentMethod);
        LinearLayout creditSection = findViewById(R.id.creditSection);
        TextView creditUsedText = findViewById(R.id.textCreditUsed);

        RecyclerView rv = findViewById(R.id.rvOrderItems);
        TextView subtotalText = findViewById(R.id.textOrderSubtotal);
        TextView vatText = findViewById(R.id.textOrderVat);
        TextView deliveryText = findViewById(R.id.textOrderDelivery);
        TextView totalText = findViewById(R.id.textOrderTotal);

        Button backToProducts = findViewById(R.id.buttonBackToProducts);

        String methodName = getIntent().getStringExtra("payment_method");
        PaymentMethod method = methodName != null ? PaymentMethod.valueOf(methodName) : PaymentMethod.ADVANCE;

        OrderRepository orderRepository = new OrderRepository(new MockDataSource());
        Order order = orderRepository.placeOrder(
                cartRepository.getItems(),
                cartRepository.getGrandTotalCents(),
                method
        );

        orderId.setText("#" + order.getId());

        statusText.setText(statusLabel(order.getStatus()));
        applyStatusStyling(statusCard, statusText, order.getStatus());

        paymentMethodText.setText(paymentLabel(order.getPaymentMethod()));

        if (order.getCreditUsedCents() != null) {
            creditSection.setVisibility(View.VISIBLE);
            creditUsedText.setText(String.format(Locale.US, "KSh %.2f", order.getCreditUsedCents() / 100.0));
        } else {
            creditSection.setVisibility(View.GONE);
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new OrderItemAdapter(order.getItems()));

        subtotalText.setText(String.format(Locale.US, "KSh %.2f", cartRepository.getSubtotalCents() / 100.0));
        vatText.setText(String.format(Locale.US, "KSh %.2f", cartRepository.getVatCents() / 100.0));
        deliveryText.setText(String.format(Locale.US, "KSh %.2f", cartRepository.getDeliveryCents() / 100.0));
        totalText.setText(String.format(Locale.US, "KSh %.2f", order.getTotalCents() / 100.0));

        // Optional: clear cart after order is placed
        cartRepository.clear();

        backToProducts.setOnClickListener(v -> navigateBackToProducts());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateBackToProducts();
            }
        });
    }

    private void navigateBackToProducts() {
        Intent intent = new Intent(this, ProductCatalogActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private String paymentLabel(PaymentMethod method) {
        switch (method) {
            case ADVANCE: return "Advance payment";
            case CREDIT: return "Credit";
            case ONSITE: return "On site payment";
            default: return method.name();
        }
    }

    private String statusLabel(OrderStatus status) {
        switch (status) {
            case CONFIRMED: return "Confirmed";
            case PAY_ON_DELIVERY: return "On site payment";
            case INSUFFICIENT_CREDIT: return "Pending payment";
            default: return status.name();
        }
    }

    private void applyStatusStyling(LinearLayout card, TextView statusText, OrderStatus status) {
        // Very simple styling that matches Figma intent
        if (status == OrderStatus.CONFIRMED) {
            card.setBackgroundColor(0x4DD4FF69); // #D4FF69/30 approx
            statusText.setTextColor(getColor(R.color.text_primary));
        } else if (status == OrderStatus.PAY_ON_DELIVERY) {
            card.setBackgroundColor(0xFFEFF6FF); // light blue
            statusText.setTextColor(0xFF1D4ED8); // blue-ish
        } else {
            card.setBackgroundColor(0xFFFFFBEB); // light yellow
            statusText.setTextColor(0xFFB45309); // yellow-ish
        }
    }
}
