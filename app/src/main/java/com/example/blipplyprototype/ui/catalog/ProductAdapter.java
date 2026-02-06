// ui/catalog/ProductAdapter.java
package com.example.blipplyprototype.ui.catalog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blipplyprototype.R;
import com.example.blipplyprototype.data.model.Product;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface OnAddClicked {
        void onAdd(Product product);
    }

    private final List<Product> products;
    private final OnAddClicked onAddClicked;

    public ProductAdapter(List<Product> products, OnAddClicked onAddClicked) {
        this.products = products;
        this.onAddClicked = onAddClicked;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);

        holder.name.setText(product.getName());
        holder.price.setText(String.format(Locale.US, "R %.2f", product.getPriceCents() / 100.0));

        holder.addButton.setOnClickListener(v -> {
            if (onAddClicked != null) onAddClicked.onAdd(product);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView price;
        final Button addButton;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_product_name);
            price = itemView.findViewById(R.id.text_product_price);
            addButton = itemView.findViewById(R.id.button_add);
        }
    }
}
