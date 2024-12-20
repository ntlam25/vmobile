package com.example.btl.retrofit;

import com.example.btl.service.CartService;
import com.example.btl.service.CategoryService;
import com.example.btl.service.CustomerService;
import com.example.btl.service.ProductService;

public class ApiUtils {
    public static final String BASE_URL = "http://10.0.2.2:5000";
    public static ProductService getProductService()
    {
        return RetrofitClient.getClient(BASE_URL).create(ProductService.class);
    }
    public static CartService getCartService()
    {
        return RetrofitClient.getClient(BASE_URL).create(CartService.class);
    }
    public static CategoryService getCategoryService()
    {
        return RetrofitClient.getClient(BASE_URL).create(CategoryService.class);
    }
    public static CustomerService getCustomerService()
    {
        return RetrofitClient.getClient(BASE_URL).create(CustomerService.class);
    }
}
