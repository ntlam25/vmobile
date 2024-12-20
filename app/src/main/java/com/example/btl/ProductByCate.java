package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.btl.adapter.ProductAdapter;
import com.example.btl.model.CartHelper;
import com.example.btl.model.LResponse;
import com.example.btl.model.Product;
import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.ProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductByCate extends AppCompatActivity {

    ListView listViewProduct;
    ImageButton buttonBack;
    ProductAdapter adapter;
    ProductService productService;
    ArrayList<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cate);
        listViewProduct = (ListView) findViewById(R.id.listViewProduct);
        buttonBack = (ImageButton) findViewById(R.id.imageButtonBack);
        productService = ApiUtils.getProductService();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        if (id == -1)
        {
            String name = intent.getStringExtra("name");
            productService.get_product_by_name(name).enqueue(new Callback<LResponse>() {
                @Override
                public void onResponse(Call<LResponse> call, Response<LResponse> response) {
                    if (response.isSuccessful())
                    {
                        LResponse res = response.body();
                        products = res.getData().getDocs();
                        adapter = new ProductAdapter(ProductByCate.this,R.layout.product_item,products);
                        listViewProduct.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<LResponse> call, Throwable t) {
                }
            });
        }
        else {
            productService.getProductByCategory(id).enqueue(new Callback<LResponse>() {
                @Override
                public void onResponse(Call<LResponse> call, Response<LResponse> response) {
                    if (response.isSuccessful())
                    {
                        LResponse res = response.body();
                        products = res.getData().getDocs();
                        adapter = new ProductAdapter(ProductByCate.this,R.layout.product_item,products);
                        listViewProduct.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<LResponse> call, Throwable t) {
                }
            });
        }
        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ProductByCate.this, ProductDetail.class);
                int id = products.get(i).getId();
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}