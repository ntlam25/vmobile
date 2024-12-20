package com.example.btl;

import static com.example.btl.Cart.textViewTongTien;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btl.adapter.CartItemAdapter;
import com.example.btl.model.CartHelper;
import com.example.btl.model.CartModel;
import com.example.btl.model.Order;
import com.example.btl.model.SignIn;
import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.CartService;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    RecyclerView recyclerViewCartItem;
    CartItemAdapter adapter;
    ArrayList<CartModel> cartItems = new ArrayList<>();
    Button buttonMuaHang;
    TextView textViewGioHangTrong;
    CartService cartService;
    public static TextView textViewTongTien1;

    public CartFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        // InitWidget with view.findViewById
        recyclerViewCartItem = view.findViewById(R.id.recycleviewgiohang1);
        buttonMuaHang = view.findViewById(R.id.buttonMuaHang1);
        textViewTongTien1 = view.findViewById(R.id.textViewTongTien1);
        textViewGioHangTrong = view.findViewById(R.id.textViewEmptyCart1);

        buttonMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",getContext().MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                if (isLoggedIn == false) {
                    Intent intent = new Intent(getContext(), SignInRequestActivity.class);
                    startActivity(intent);
                }else
                {
                    showMyDialog();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        cartService = ApiUtils.getCartService();
        cartItems = CartHelper.getCartItems(getContext());
        adapter = new CartItemAdapter(getContext(), cartItems);
        recyclerViewCartItem.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewCartItem.setAdapter(adapter);

        long total = CartHelper.calculateTotal(cartItems);
        textViewTongTien1.setText(String.valueOf(total)+" vnd");
        updateCartUI();
    }

    private void MakeOrder(int id,String name, String email, String address, String phone) {
        Order order = new Order(1,id,name,email,address,phone,cartItems);
        cartService.addOrder(order).enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                Toast.makeText(getContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Toast.makeText(getContext(), "Đặt hàng thất bại: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.buy_layout);
        EditText nameEditText = dialog.findViewById(R.id.nameBuy);
        EditText emailEditText = dialog.findViewById(R.id.emailBuy);
        EditText addressEditText = dialog.findViewById(R.id.addressBuy);
        EditText phoneEditText = dialog.findViewById(R.id.phoneBuy);
        Button buttonBuy = dialog.findViewById(R.id.buttonBuy);

        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref",getContext().MODE_PRIVATE);
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

                    boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

                    if (!isLoggedIn) {
                        Intent intent = new Intent(getContext(), SignInRequestActivity.class);
                        startActivity(intent);
                    } else {
                        int id=customer.getId();
                        String name,email,address,phone;
                        name = nameEditText.getText().toString().trim();
                        email = emailEditText.getText().toString().trim();
                        address = addressEditText.getText().toString().trim();
                        phone = phoneEditText.getText().toString().trim();
                        MakeOrder(id,name,email,address,phone);
                        CartHelper.clearCart(getContext());
                        cartItems.clear();
                        adapter.notifyDataSetChanged();
                        updateCartUI();
                        textViewTongTien1.setText("0 vnd");
                        Intent intent = new Intent(getContext(),HomeActivity.class);
                        startActivity(intent);
                    }
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