package com.example.btl.adapter;

import static com.example.btl.Cart.textViewTongTien;
import static com.example.btl.CartFragment.textViewTongTien1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.Cart;
import com.example.btl.ProductDetail;
import com.example.btl.R;
import com.example.btl.model.CartHelper;
import com.example.btl.model.CartModel;
import com.example.btl.model.Product;

import java.util.ArrayList;
import java.util.List;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.ViewHolder> {

    private Context mContext;
    private List<Product> mProducts;
    private String title;
    private static final int VIEW_TYPE_TITLE = 0;
    private static final int VIEW_TYPE_PRODUCT = 1;


    public FeaturedAdapter(Context context, ArrayList<Product> products, String title) {
        this.mContext = context;
        this.mProducts = products;
        this.title = title;
    }


    @NonNull
    @Override
    public FeaturedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_TITLE) {
            View titleView = LayoutInflater.from(mContext).inflate(R.layout.title_layout, parent, false);
            return new TitleViewHolder(titleView);
        } else {
            View productView = LayoutInflater.from(mContext).inflate(R.layout.product_featured, parent, false);
            return new ProductViewHolder(productView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_TITLE;
        } else {
            return VIEW_TYPE_PRODUCT;
        }
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull FeaturedAdapter.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_TITLE) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.titleTextView.setText(title);
        } else {
            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
            Product product = mProducts.get(position - 1);
            String imageName = product.getImage();
            int imageId = mContext.getResources().getIdentifier(imageName.substring(0,imageName.lastIndexOf('.')), "drawable", mContext.getPackageName());
            productViewHolder.imageView.setImageResource(imageId);
            productViewHolder.textViewTitle.setText(product.getName());
            productViewHolder.textViewPrice.setText(String.valueOf(product.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size() + 1;
    }
    public class TitleViewHolder extends ViewHolder {
        TextView titleTextView;

        public TitleViewHolder(View view) {
            super(view);
            titleTextView = view.findViewById(R.id.textViewFeatured);
        }
    }
    public class ProductViewHolder extends ViewHolder {
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewPrice;

        public ProductViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.product_image1);
            textViewTitle = view.findViewById(R.id.product_title1);
            textViewPrice = view.findViewById(R.id.product_price1);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    int id = mProducts.get(position-1).getId();
                    Intent intent = new Intent(mContext,ProductDetail.class);
                    intent.putExtra("id",id);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textViewTitle;
        TextView textViewPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.product_image1);
            textViewTitle = itemView.findViewById(R.id.product_title1);
            textViewPrice = itemView.findViewById(R.id.product_price1);
        }
    }
}
