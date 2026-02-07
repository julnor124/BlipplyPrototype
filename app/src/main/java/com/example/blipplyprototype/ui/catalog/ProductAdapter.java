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
import com.example.blipplyprototype.data.repository.CartRepository;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public interface Listener {
        void onAdd(Product product);
        void onQuantityChanged();
    }

    private final List<Product> products;
    private final Listener listener;
    private final CartRepository cartRepository = CartRepository.getInstance();

    public ProductAdapter(List<Product> products, Listener listener) {
        this.products = products;
        this.listener = listener;
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
        holder.price.setText(String.format(Locale.US, "KSh %.2f", product.getPriceCents() / 100.0));

        int quantity = getQuantity(product);
        boolean hasQuantity = quantity > 0;
        holder.addButton.setVisibility(hasQuantity ? View.GONE : View.VISIBLE);
        holder.quantityContainer.setVisibility(hasQuantity ? View.VISIBLE : View.GONE);
        holder.quantityText.setText(String.valueOf(Math.max(quantity, 1)));

        holder.addButton.setOnClickListener(v -> {
            cartRepository.addProduct(product);
            if (listener != null) {
                listener.onAdd(product);
                listener.onQuantityChanged();
            }
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.plusButton.setOnClickListener(v -> {
            int current = getQuantity(product);
            cartRepository.setQuantity(product, current + 1);
            if (listener != null) listener.onQuantityChanged();
            notifyItemChanged(holder.getAdapterPosition());
        });

        holder.minusButton.setOnClickListener(v -> {
            int current = getQuantity(product);
            cartRepository.setQuantity(product, current - 1);
            if (listener != null) listener.onQuantityChanged();
            notifyItemChanged(holder.getAdapterPosition());
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
        final View quantityContainer;
        final TextView quantityText;
        final TextView minusButton;
        final TextView plusButton;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_product_name);
            price = itemView.findViewById(R.id.text_product_price);
            addButton = itemView.findViewById(R.id.button_add);
            quantityContainer = itemView.findViewById(R.id.layout_quantity);
            quantityText = itemView.findViewById(R.id.text_quantity);
            minusButton = itemView.findViewById(R.id.button_minus);
            plusButton = itemView.findViewById(R.id.button_plus);
        }
    }

    private int getQuantity(Product product) {
        for (com.example.blipplyprototype.data.model.CartItem item : cartRepository.getItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                return item.getQuantity();
            }
        }
        return 0;
    }
}
