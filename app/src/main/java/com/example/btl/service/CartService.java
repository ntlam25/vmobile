package com.example.btl.service;

import com.example.btl.model.CartModel;
import com.example.btl.model.Order;
import com.example.btl.model.OrderItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartService {
    @POST("/api/v1/order")
    Call<Order> addOrder(@Body Order Order);

    @GET("/api/v1/customer/{id}/orders")
    Call<OrderItem.OrderResponse> getOrderByCusId(@Path("id") int id);
}
