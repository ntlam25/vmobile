package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.adapter.CartItemAdapter;
import com.example.btl.adapter.ProductAdapter;
import com.example.btl.model.CartHelper;
import com.example.btl.model.CartModel;
import com.example.btl.model.Order;
import com.example.btl.model.Product;
import com.example.btl.model.SignIn;
import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.CartService;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerViewCartItem;
    CartItemAdapter adapter;
    ArrayList<CartModel> cartItems = new ArrayList<>();
    Button buttonMuaHang;
    ImageButton imageButtonBack;
    TextView textViewGioHangTrong;
    CartService cartService;
    public static TextView textViewTongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        InitWidget();
        Intent intent = getIntent();
        cartItems = intent.getParcelableArrayListExtra("cart");
        cartService = ApiUtils.getCartService();
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }
        adapter = new CartItemAdapter(Cart.this, cartItems);
        recyclerViewCartItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCartItem.setAdapter(adapter);
        long total = CartHelper.calculateTotal(cartItems);
        textViewTongTien.setText(String.valueOf(total)+" vnd");
        updateCartUI();
        buttonMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                if (isLoggedIn == false) {
                    Intent intent = new Intent(Cart.this, SignInRequestActivity.class);
                    startActivity(intent);
                }else
                {
                    showMyDialog();
                }
            }
        });
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void MakeOrder(int id,String name,String email,String address,String phone) {
        Order order = new Order(1,id,name,email,address,phone,cartItems);
        cartService.addOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Toast.makeText(Cart.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(Cart.this, "Đặt hàng thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void InitWidget() {
        recyclerViewCartItem = findViewById(R.id.recycleviewgiohang);
        buttonMuaHang = findViewById(R.id.buttonMuaHang);
        textViewTongTien = findViewById(R.id.textViewTongTien);
        textViewGioHangTrong = findViewById(R.id.textViewEmptyCart);
        imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
    }

    private void updateCartUI() {
        if (cartItems.size() == 0) {
            textViewGioHangTrong.setVisibility(View.VISIBLE);
            recyclerViewCartItem.setVisibility(View.GONE);
            buttonMuaHang.setEnabled(false);
        } else {
            textViewGioHangTrong.setVisibility(View.GONE);
            recyclerViewCartItem.setVisibility(View.VISIBLE);
            buttonMuaHang.setEnabled(true);
        }
    }
    private void showMyDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.buy_layout);
        EditText nameEditText = dialog.findViewById(R.id.nameBuy);
        EditText emailEditText = dialog.findViewById(R.id.emailBuy);
        EditText addressEditText = dialog.findViewById(R.id.addressBuy);
        EditText phoneEditText = dialog.findViewById(R.id.phoneBuy);
        Button buttonBuy = dialog.findViewById(R.id.buttonBuy);

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        String cus = sharedPreferences.getString("token", null);
        SignIn customer = gson.fromJson(cus,SignIn.class);
        nameEditText.setText(customer.getName());
        addressEditText.setText(customer.getAddress());
        phoneEditText.setText(customer.getPhone());
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;
                check = validateFields(nameEditText,emailEditText,addressEditText,phoneEditText);
                if (check == true)
                {
                    int id = customer.getId();
                    String name,email,address,phone;
                    name = nameEditText.getText().toString().trim();
                    email = emailEditText.getText().toString().trim();
                    address = addressEditText.getText().toString().trim();
                    phone = phoneEditText.getText().toString().trim();
                    MakeOrder(id,name,email,address,phone);
                    CartHelper.clearCart(Cart.this);
                    cartItems.clear();
                    adapter.notifyDataSetChanged();
                    updateCartUI();
                    textViewTongTien.setText("0 vnd");
                    finish();
                    Intent intent = new Intent(Cart.this,HomeActivity.class);
                    startActivity(intent);
                }

            }
        });
        dialog.show();

    }

    private boolean validateFields(EditText nameEditText,EditText emailEditText,EditText addressEditText,EditText phoneEditText) {
        String name,email,address,phone;
        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        address = addressEditText.getText().toString().trim();
        phone = phoneEditText.getText().toString().trim();
        boolean isValid = true;

        // Validate name
        if (name.isEmpty()) {
            nameEditText.setError("Xin mời nhập tên.");
            isValid = false;
        } else if (!name.matches("^[a-zA-ZÀ-ỹ]+([\\s][a-zA-ZÀ-ỹ]+)*$")) {
            nameEditText.setError("Tên chỉ có thể nhập chữ cái và dấu cách.");
            isValid = false;
        } else {
            nameEditText.setError(null);
        }
        // Validate email
        if (email.isEmpty()) {
            emailEditText.setError("Xin mời nhập địa chỉ email.");
            isValid = false;
        } else if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            emailEditText.setError("Xin mời nhập địa chỉ email hợp lệ.");
            isValid = false;
        } else {
            emailEditText.setError(null);
        }
        // Validate address
        if (address.isEmpty()) {
            addressEditText.setError("Xin mời bạn nhập địa chỉ.");
            isValid = false;
        } else {
            addressEditText.setError(null);
        }
        //validate phone
        if (phone.isEmpty()) {
            phoneEditText.setError("Xin mời số điện thoại");
            isValid = false;
        } else if (!phone.matches( "(09|03|07|08|05)+([0-9]{8})\\b")) {
            phoneEditText.setError("Điện thoại chưa đúng định dạng, đầu số 09,03,07,08,05");
            isValid = false;
        } else {
            phoneEditText.setError(null);
        }

        return isValid;
    }
}