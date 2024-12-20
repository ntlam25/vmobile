package com.example.btl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.btl.model.SignIn;
import com.example.btl.model.SignUpRequest;
import com.example.btl.model.SignUpRespond;

import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.CustomerService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpRequestActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, addressEditText, phoneEditText;
    private CheckBox policyCheckBox;
    private Button register_Button;
    private CustomerService CustomerService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_request);

        // Initialize views
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        addressEditText = findViewById(R.id.address);
        phoneEditText = findViewById(R.id.phone);
        policyCheckBox = findViewById(R.id.policy);
        register_Button = findViewById(R.id.registerButton);


        CustomerService = ApiUtils.getCustomerService();

        // Set click listener for register button
        register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    registerUser();
                }
            }
        });
    }

    private boolean validateFields() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String address = addressEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        boolean policy = policyCheckBox.isChecked();
        boolean isValid = true;

        // Validate name
        if (name.isEmpty()) {
            nameEditText.setError("Xin mời nhập tên.");
            isValid = false;
        } else if (!name.matches("[a-zA-ZÀ-ỹ]+((\\s)[a-zA-ZÀ-ỹ]+)?[a-zA-ZÀ-ỹ]*")) {
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

        // Validate password
        if (password.isEmpty()) {
            passwordEditText.setError("Xin mời nhập mật khẩu.");
            isValid = false;
        } else if (!password.matches("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}")) {
            passwordEditText.setError("Mật khẩu phải dài tối thiểu 6 kí tự, trong đó có it nhất 1 kí tự thường, 1 kí tự in hoa và 1 số.");
            isValid = false;
        } else {
            passwordEditText.setError(null);
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

        // Validate policy agreement
        if (!policy) {
            Toast.makeText(SignUpRequestActivity.this, "Mời bạn chấp nhận với điều khoản của chúng tôi.", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        return isValid;
    }

    private void registerUser() {
        // Create SignUpRequest object with input data
        SignUpRequest signUpRequest = new SignUpRequest(
                nameEditText.getText().toString().trim(),
                emailEditText.getText().toString().trim(),
                addressEditText.getText().toString().trim(),
                passwordEditText.getText().toString(),
                Integer.parseInt(phoneEditText.getText().toString().trim()) // Assuming phone is an integer
        );

        // Send POST request using Retrofit
        Call<SignUpRespond> call = CustomerService.registerUser(signUpRequest);
        call.enqueue(new Callback<SignUpRespond>() {
            @Override
            public void onResponse(Call<SignUpRespond> call, Response<SignUpRespond> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpRequestActivity.this, "Đăng ký thành công",Toast.LENGTH_SHORT).show();
                    SignUpRespond signUpRespond = response.body();
                    // Redirect to SignUpRespondActivity and pass the response data
                    Intent intent = new Intent(SignUpRequestActivity.this, SignInRequestActivity.class);
                    intent.putExtra("SignUpRespond", signUpRespond.toString());
                    startActivity(intent);
                    finish(); // Close current activity to prevent user from navigating back
                } else {
                    Toast.makeText(SignUpRequestActivity.this, "Đăng ký thất bại, mời thử lại một lúc sau.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpRespond> call, Throwable t) {
                Log.e("SignUpRequestActivity", "Failed to send request: " + t.getMessage());
                Toast.makeText(SignUpRequestActivity.this, "Vấn đề về kết nối, hãy xem lại đường truyền.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}


