package com.example.btl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.btl.model.SignIn;
import com.example.btl.model.SignIn.SignInResponse;
import com.example.btl.model.SignUpRequest;
import com.example.btl.model.SignUpRespond;
import com.google.gson.Gson;

public class SignInRequestActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private Button signUpLink;
    private SignIn customer;
    private CustomerService customerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_request);
        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        signUpLink = findViewById(R.id.signUpLink); // Added
        customerService = ApiUtils.getCustomerService();
        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateInputs();
            }
        });

        // Set click listener for signUpLink button
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // Redirect to activity_main2.xml
                    Intent intent = new Intent(SignInRequestActivity.this, SignUpRequestActivity.class);
                    startActivity(intent);
            }
        });

    }
    private boolean validateInputs() {
        // Get email and password from input fields
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        // Call signIn method in ApiService
        Call<SignIn.SignInResponse> call = customerService.signIn(email,password);
        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignIn.SignInResponse> call, Response<SignInResponse> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SignInRequestActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInRequestActivity.this, HomeActivity.class);
                    customer = response.body().getData();
                    SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String cusjson = gson.toJson(customer);
                    myEdit.putBoolean("isLoggedIn", true);
                    myEdit.putString("token", cusjson); // Giả sử bạn có một token
                    myEdit.apply();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(SignInRequestActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Toast.makeText(SignInRequestActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
            }
        });

        // Define email pattern for validation
        String emailPattern = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";

        // Initialize variable to track form validity
        boolean isValid = true;

        // Check email field
        if (email.isEmpty()) {
            emailEditText.setError("Mời bạn nhập email");
            isValid = false;
        } else if (!email.matches(emailPattern)) {
            emailEditText.setError("Địa chỉ email không hợp lệ.");
            isValid = false;
        } else {
            emailEditText.setError(null);
        }

        // Check password field
        if (password.isEmpty()) {
            passwordEditText.setError("Vui lòng nhập mật khẩu.");
            isValid = false;
        } else {
            if (password.length() < 6) {
                passwordEditText.setError("Mật khẩu phải có ít nhất hơn 6 kí tự.");
                isValid = false;
            } else {
                passwordEditText.setError(null);
            }
        }
        return isValid;

    }

}
