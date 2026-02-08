package com.example.blipplyprototype.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.mock.MockDataSource;
import com.example.blipplyprototype.data.model.PaymentMethod;
import com.example.blipplyprototype.data.repository.CartRepository;
import com.example.blipplyprototype.ui.orders.OrderSummaryActivity;

import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private static final CartRepository cartRepository = CartRepository.getInstance();
    private final MockDataSource dataSource = new MockDataSource();

    private PaymentMethod selectedMethod = null;

    private int remainingCreditCents;

    private Button placeOrder;
    private LinearLayout optionAdvance;
    private LinearLayout optionCredit;
    private LinearLayout optionOnsite;
    private RadioButton radioAdvance;
    private RadioButton radioCredit;
    private RadioButton radioOnsite;

    private Button getSuggestion;
    private LinearLayout aiResultContainer;
    private TextView aiRecommendationText;
    private TextView aiExplanationText;
    private Button applyRecommendation;

    private boolean showAI = false;
    private PaymentMethod aiRecommendation = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        if (cartRepository.getItems().isEmpty()) {
            finish();
            return;
        }

        int availableCreditCents = dataSource.getAvailableCreditCents();
        int usedCreditCents = dataSource.getUsedCreditCents();
        remainingCreditCents = availableCreditCents - usedCreditCents;

        ImageButton back = findViewById(R.id.buttonBack);
        TextView totalText = findViewById(R.id.textTotal);

        optionAdvance = findViewById(R.id.optionAdvance);
        optionCredit = findViewById(R.id.optionCredit);
        optionOnsite = findViewById(R.id.optionOnsite);

        radioAdvance = findViewById(R.id.radioAdvance);
        radioCredit = findViewById(R.id.radioCredit);
        radioOnsite = findViewById(R.id.radioOnsite);

        TextView availableCredit = findViewById(R.id.textAvailableCredit);
        TextView usedCredit = findViewById(R.id.textUsedCredit);
        TextView remainingCredit = findViewById(R.id.textRemainingCredit);

        getSuggestion = findViewById(R.id.buttonGetSuggestion);
        aiResultContainer = findViewById(R.id.aiResultContainer);
        aiRecommendationText = findViewById(R.id.textAiRecommendation);
        aiExplanationText = findViewById(R.id.textAiExplanation);
        applyRecommendation = findViewById(R.id.buttonApplyRecommendation);

        placeOrder = findViewById(R.id.buttonPlaceOrder);

        back.setOnClickListener(v -> finish());

        int totalCents = cartRepository.getGrandTotalCents();
        totalText.setText(String.format(Locale.US, "KSh %.2f", totalCents / 100.0));

        availableCredit.setText(String.format(Locale.US, "KSh %.2f", availableCreditCents / 100.0));
        usedCredit.setText(String.format(Locale.US, "KSh %.2f", usedCreditCents / 100.0));
        remainingCredit.setText(String.format(Locale.US, "KSh %.2f", remainingCreditCents / 100.0));

        optionAdvance.setOnClickListener(v -> select(PaymentMethod.ADVANCE));
        optionCredit.setOnClickListener(v -> select(PaymentMethod.CREDIT));
        optionOnsite.setOnClickListener(v -> select(PaymentMethod.ONSITE));

        radioAdvance.setOnClickListener(v -> select(PaymentMethod.ADVANCE));
        radioCredit.setOnClickListener(v -> select(PaymentMethod.CREDIT));
        radioOnsite.setOnClickListener(v -> select(PaymentMethod.ONSITE));

        getSuggestion.setOnClickListener(v -> {
            if (showAI) return;
            showAI = true;
            getSuggestion.setEnabled(false);

            // simulate AI delay
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                int total = cartRepository.getGrandTotalCents();
                aiRecommendation = (total <= remainingCreditCents) ? PaymentMethod.CREDIT : PaymentMethod.ADVANCE;

                aiResultContainer.setVisibility(View.VISIBLE);
                aiRecommendationText.setText(
                        aiRecommendation == PaymentMethod.CREDIT
                                ? "✓ Credit recommended"
                                : "✓ Advance payment recommended"
                );
                aiExplanationText.setText(buildAiExplanation(aiRecommendation, total));

                applyRecommendation.setEnabled(true);
            }, 500);
        });

        applyRecommendation.setOnClickListener(v -> {
            if (aiRecommendation != null) select(aiRecommendation);
        });

        placeOrder.setOnClickListener(v -> {
            if (selectedMethod == null || cartRepository.getItems().isEmpty()) return;

            Intent intent = new Intent(this, OrderSummaryActivity.class);
            intent.putExtra("payment_method", selectedMethod.name());
            startActivity(intent);
            finish();
        });

        updatePlaceOrderButton();
    }

    private void select(PaymentMethod method) {
        selectedMethod = method;

        radioAdvance.setChecked(method == PaymentMethod.ADVANCE);
        radioCredit.setChecked(method == PaymentMethod.CREDIT);
        radioOnsite.setChecked(method == PaymentMethod.ONSITE);

        updatePlaceOrderButton();
    }

    private void updatePlaceOrderButton() {
        boolean enabled = selectedMethod != null && !cartRepository.getItems().isEmpty();
        placeOrder.setEnabled(enabled);

        if (enabled) {
            placeOrder.setBackgroundTintList(getColorStateList(R.color.button_primary));
            placeOrder.setTextColor(getColor(R.color.button_primary_text));
        } else {
            placeOrder.setBackgroundTintList(getColorStateList(android.R.color.darker_gray));
            placeOrder.setTextColor(getColor(android.R.color.white));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (cartRepository.getItems().isEmpty()) {
            finish();
            return;
        }

        int totalCents = cartRepository.getGrandTotalCents();
        TextView totalText = findViewById(R.id.textTotal);
        totalText.setText(String.format(Locale.US, "KSh %.2f", totalCents / 100.0));
        updatePlaceOrderButton();
    }

    private String buildAiExplanation(PaymentMethod recommendation, int totalCents) {
        double total = totalCents / 100.0;
        double remaining = remainingCreditCents / 100.0;

        if (recommendation == PaymentMethod.CREDIT) {
            return String.format(
                    Locale.US,
                    "Your total is KSh %.2f and you have KSh %.2f credit available. Using credit keeps your cash flow flexible and you have sufficient coverage.",
                    total, remaining
            );
        } else {
            return String.format(
                    Locale.US,
                    "Your total is KSh %.2f and you have KSh %.2f credit available. I recommend advance payment to avoid partial credit coverage and simplify reconciliation. If you prefer to use credit, expect an insufficient credit status and a remaining amount due.",
                    total, remaining
            );
        }
    }
}
