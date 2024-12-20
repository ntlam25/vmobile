package com.example.btl.service;

import com.example.btl.model.Category;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryService {
    @GET("/api/v1/categories")
    Call<Category.CateResponse> getAllCategories();

    @GET("/api/v1/categories/{id}")
    Call<Category> getCategoryById(@Path("id") int id);
}
