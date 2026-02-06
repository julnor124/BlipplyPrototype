// ui/cart/CartAdapter.java
package com.example.blipplyprototype.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.model.CartItem;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface Listener {
        void onIncrease(CartItem item);
        void onDecrease(CartItem item);
        void onRemove(CartItem item);
    }

    private final List<CartItem> items;
    private final Listener listener;

    public CartAdapter(List<CartItem> items, Listener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_product, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = items.get(position);

        holder.name.setText(item.getProduct().getName());
        holder.unitPrice.setText(String.format(Locale.US, "KSh %.2f", item.getProduct().getPriceCents() / 100.0));
        holder.quantity.setText(String.valueOf(item.getQuantity()));

        double lineTotal = (item.getProduct().getPriceCents() * item.getQuantity()) / 100.0;
        holder.lineTotal.setText(String.format(Locale.US, "KSh %.2f", lineTotal));

        holder.plus.setOnClickListener(v -> {
            if (listener != null) listener.onIncrease(item);
        });

        holder.minus.setOnClickListener(v -> {
            if (listener != null) listener.onDecrease(item);
        });

        holder.trash.setOnClickListener(v -> {
            if (listener != null) listener.onRemove(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView unitPrice;
        final TextView quantity;
        final TextView lineTotal;
        final Button minus;
        final Button plus;
        final ImageButton trash;

        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            unitPrice = itemView.findViewById(R.id.textUnitPrice);
            quantity = itemView.findViewById(R.id.textQuantity);
            lineTotal = itemView.findViewById(R.id.textLineTotal);
            minus = itemView.findViewById(R.id.buttonMinus);
            plus = itemView.findViewById(R.id.buttonPlus);
            trash = itemView.findViewById(R.id.buttonTrash);
        }
    }
}
