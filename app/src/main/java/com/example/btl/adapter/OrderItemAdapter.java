package com.example.btl.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.ProductDetail;
import com.example.btl.R;
import com.example.btl.model.Order;
import com.example.btl.model.OrderDetail;
import com.example.btl.model.OrderItem;
import com.example.btl.model.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.ViewHolder> {

    private Context context;
    private List<OrderItem> orders;
    private List<Product> products;
    private List<OrderDetail> orderDetails;

    public OrderItemAdapter(Context context, List<OrderItem> orders, List<Product> products, List<OrderDetail> orderDetails) {
        this.context = context;
        this.orders = orders;
        this.products = products;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public OrderItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_order_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemAdapter.ViewHolder holder, int position) {
        OrderItem currenOrder = orders.get(position);
        holder.textViewOrderId.setText(String.valueOf(currenOrder.getId()));
        holder.textViewDate.setText(currenOrder.getCreated_at());
        String state = currenOrder.isState() == false ? "Chưa xử lý" : "Đã xử lý";
        holder.textViewState.setText(state);
        holder.textViewTotalPrice.setText(String.valueOf(currenOrder.getPrice_total()));
        ProductOrderAdapter adapter;
        List<OrderDetail> ord = new ArrayList<>();
        for (OrderDetail o: orderDetails) {
            if(o.getOrder_id()==currenOrder.getId())
            {
                ord.add(o);
            }
        }
        adapter = new ProductOrderAdapter(context,products,ord);
        holder.recyclerViewProduct.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerViewProduct.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId,textViewDate,textViewState,textViewTotalPrice;
        RecyclerView recyclerViewProduct;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTotalPrice = itemView.findViewById(R.id.textViewTotalPrice);
            textViewState = itemView.findViewById(R.id.textViewState);
            recyclerViewProduct = itemView.findViewById(R.id.recyclerViewProduct);
        }
    }
}
