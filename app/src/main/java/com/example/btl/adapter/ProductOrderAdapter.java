package com.example.btl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.R;
import com.example.btl.model.OrderDetail;
import com.example.btl.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductOrderAdapter extends RecyclerView.Adapter<ProductOrderAdapter.ViewHolder> {
    Context context;
    List<Product> products;
    List<OrderDetail> orderDetails;


    public ProductOrderAdapter(Context context, List<Product> products, List<OrderDetail> orderDetails) {
        this.context = context;
        this.products = products;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public ProductOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_product_order,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductOrderAdapter.ViewHolder holder, int position) {
        OrderDetail orderDetail = orderDetails.get(position);
        for (Product product: products ) {
            if (product.getId() == orderDetail.getPrd_id())
            {
                holder.textViewNameOrder.setText(product.getName());
                holder.textViewQuantityOrder.setText(String.valueOf(orderDetail.getQty()));
                holder.textViewPriceOrder.setText(String.valueOf(orderDetail.getPrice()));
            }
        }

    }

    @Override
    public int getItemCount() {
        return orderDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNameOrder,textViewQuantityOrder,textViewPriceOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameOrder = itemView.findViewById(R.id.textViewNameOrder);
            textViewQuantityOrder = itemView.findViewById(R.id.textViewQuantityOrder);
            textViewPriceOrder = itemView.findViewById(R.id.textViewPriceOrder);
        }
    }
}
