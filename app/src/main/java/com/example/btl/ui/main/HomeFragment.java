package com.example.btl.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.btl.R;
import com.example.btl.adapter.FeaturedAdapter;
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

public class HomeFragment extends Fragment {

    RecyclerView recycleViewFeatured,recyclerViewNew;
    ArrayList<Product> products = new ArrayList<>(),products2 = new ArrayList<>();
    ProductService productService;
    FeaturedAdapter adapter,adapter2;
    boolean check = false;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recycleViewFeatured = (RecyclerView) view.findViewById(R.id.recycleViewFeatured);
        recyclerViewNew = (RecyclerView) view.findViewById(R.id.recyclerViewNew);
        productService = ApiUtils.getProductService();
        productService.get_product_service(true,6).enqueue(new Callback<LResponse>() {
            @Override
            public void onResponse(Call<LResponse> call, Response<LResponse> response) {
                if (response.isSuccessful())
                {
                    products = response.body().getData().getDocs();
                    adapter = new FeaturedAdapter(getContext(),products,"Sản phẩm nổi bật");

                    productService.get_product_with_created(6).enqueue(new Callback<LResponse>() {
                        @Override
                        public void onResponse(Call<LResponse> call, Response<LResponse> response) {
                            if (response.isSuccessful())
                            {
                                int size = products.size();
                                products2 = response.body().getData().getDocs();
                                adapter2 = new FeaturedAdapter(getContext(),products2,"Sản phẩm mới");
                                ConcatAdapter concatAdapter = new ConcatAdapter(adapter,adapter2);
                                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                                    @Override
                                    public int getSpanSize(int position) {
                                        if (concatAdapter.getItemViewType(position)==0 || concatAdapter.getItemViewType(position)==2) {
                                            return 2;
                                        } else if (concatAdapter.getItemViewType(position)==1) {
                                            return 1;
                                        }
                                        return 1;
                                    }
                                });
                                recycleViewFeatured.setLayoutManager(gridLayoutManager);

                                recycleViewFeatured.setAdapter(concatAdapter);
                            }
                            else {

                            }
                        }

                        @Override
                        public void onFailure(Call<LResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Lỗi server", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<LResponse> call, Throwable t) {

            }
        });

        return view;

    }

}