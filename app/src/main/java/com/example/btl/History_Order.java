package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.adapter.OrderItemAdapter;
import com.example.btl.model.OrderDetail;
import com.example.btl.model.OrderItem;
import com.example.btl.model.OrderItem.OrderResponse;
import com.example.btl.model.Product;
import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.CartService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History_Order extends AppCompatActivity {
    TextView textViewEmptyOrder;
    ImageButton imageButtonBack;
    RecyclerView recyclerViewOrderHistory;
    OrderItemAdapter adapter;
    OrderResponse orderResponse;
    List<Product> products;
    List<OrderItem> orderItems;
    List<OrderDetail> orderDetails;
    CartService cartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);
        imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBackOrder);
        textViewEmptyOrder = (TextView) findViewById(R.id.textViewEmptyOrder);
        cartService = ApiUtils.getCartService();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",1);
        recyclerViewOrderHistory = (RecyclerView) findViewById(R.id.recyclerViewOrderHistory);
        cartService.getOrderByCusId(id).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful())
                {
                    orderResponse = response.body();
                    products = orderResponse.getProducts();
                    orderDetails = orderResponse.getOrder_details();
                    orderItems = orderResponse.getOrders();
                    adapter = new OrderItemAdapter(History_Order.this,orderItems,products,orderDetails);
                    recyclerViewOrderHistory.setLayoutManager(new LinearLayoutManager(History_Order.this));
                    recyclerViewOrderHistory.setAdapter(adapter);
                    if (products.isEmpty())
                    {
                        textViewEmptyOrder.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}