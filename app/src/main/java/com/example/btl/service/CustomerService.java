package com.example.btl.service;

import com.example.btl.model.SignIn;
import com.example.btl.model.SignUpRequest;
import com.example.btl.model.SignUpRespond;
import com.example.btl.model.UpdateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CustomerService {
    @POST("/api/v1/customer")
    Call<SignUpRespond> registerUser(@Body SignUpRequest signUpRequest);

    @GET("/api/v1/customer")
    Call<SignIn.SignInResponse> signIn(@Query("email") String email, @Query("password") String password);

    @PATCH("/api/v1/customer")
    Call<UpdateResponse> updateAccount(@Query("email") String email, @Body SignIn customer);

    @PATCH("/api/v1/customer/{id}")
    Call<UpdateResponse> updatePassword(@Path("id") int id, @Body SignIn customer);
}

