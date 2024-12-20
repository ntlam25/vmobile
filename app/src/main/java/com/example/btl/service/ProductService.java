package com.example.btl.service;

import com.example.btl.model.Data;
import com.example.btl.model.LResponse;
import com.example.btl.model.Respone;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("/api/v1/products")
    Call<LResponse> getAllProduct();

    @GET("/api/v1/products/{id}")
    Call<Respone> getProductById(@Path("id") int id);

    @GET("/api/v1/categories/{id}/products")
    Call<LResponse> getProductByCategory(@Path("id") int id);

    @GET("/api/v1/products")
    Call<LResponse> get_product_service(@Query("filter[is_featured]") boolean isFeatured, @Query("limit") int limit);
    @GET("/api/v1/products")
    Call<LResponse> get_product_with_created(@Query("limit") int limit);

    @GET("/api/v1/products")
    Call<LResponse> get_product_by_name(@Query("name") String name);
}
