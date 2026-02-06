package com.example.blipplyprototype.ui.orders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.model.CartItem;

import java.util.List;
import java.util.Locale;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.VH> {

    private final List<CartItem> items;

    public OrderItemAdapter(List<CartItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        CartItem item = items.get(position);

        holder.name.setText(item.getProduct().getName());
        holder.qty.setText("Qty " + item.getQuantity());

        double lineTotal = (item.getProduct().getPriceCents() * item.getQuantity()) / 100.0;
        holder.lineTotal.setText(String.format(Locale.US, "KSh %.2f", lineTotal));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView qty;
        final TextView lineTotal;

        VH(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textItemName);
            qty = itemView.findViewById(R.id.textItemQty);
            lineTotal = itemView.findViewById(R.id.textItemLineTotal);
        }
    }
}
