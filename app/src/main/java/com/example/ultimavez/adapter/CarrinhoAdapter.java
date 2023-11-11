package com.example.ultimavez.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.R;
import com.example.ultimavez.model.domain.Carrinho;
import com.example.ultimavez.model.domain.Product;

import java.util.List;

public class CarrinhoAdapter extends RecyclerView.Adapter<CarrinhoAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private Carrinho carrinho;
    private UpdateProductListListener updateProductListListener;
    private SubtractButtonClickListener subtractButtonClickListener;
    private AddButtonClickListener addButtonClickListener;

    public CarrinhoAdapter(List<Product> productList, Context context) {
        this.context = context;
        this.productList = productList;
        carrinho = MyCustomApplication.getCarrinho();
    }

    public interface SubtractButtonClickListener {
        void onSubtractButtonClick(Product product);
    }

    public interface AddButtonClickListener {
        void onAddButtonClick(Product product);
    }

    public interface UpdateProductListListener {
        void onDeleteElement();
    }

    public void setSubtractButtonClickListener(SubtractButtonClickListener listener) {
        this.subtractButtonClickListener = listener;
    }

    public void setAddButtonClickListener(AddButtonClickListener listener) {
        this.addButtonClickListener = listener;
    }

    public void setNotifyDeleteElementListener(UpdateProductListListener listener) {
        this.updateProductListListener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrinho, parent, false);
        return new ProductViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productPrice.setText("R$" + product.getPrice());

        Drawable productDrawable = byteToDrawable(product.getImage());
        holder.productImage.setImageDrawable(productDrawable);

        Button subtractButton = holder.itemView.findViewById(R.id.btnCarrinhoSubtractProduct);
        TextView quantityTextView = holder.itemView.findViewById(R.id.txtCarrinhoQtdProduto);
        Button addButton = holder.itemView.findViewById(R.id.btnCarrinhoAddProduct);

        quantityTextView.setText(carrinho.getProductQuantity(product).toString());
        subtractButton.setOnClickListener(view -> {
            carrinho.decrementProduct(product);
            subtractButtonClickListener.onSubtractButtonClick(product);

            if (carrinho.getProductQuantity(product) == 0) {
                productList.remove(product);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, carrinho.getListOfProducts().size());
            } else {
                quantityTextView.setText(carrinho.getProductQuantity(product).toString());
            }
        });

        addButton.setOnClickListener(view -> {
            carrinho.incrementProduct(product);
            quantityTextView.setText(carrinho.getProductQuantity(product).toString());
            addButtonClickListener.onAddButtonClick(product);
        });
    }

    private Drawable byteToDrawable(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }



    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.carrinhoImage);
            productName = itemView.findViewById(R.id.txtCarrinhoNomeProduct);
            productPrice = itemView.findViewById(R.id.txtCarrinhoPrecoProduto);
        }
    }
}
