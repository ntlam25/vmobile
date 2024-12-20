package com.example.btl;

import static com.example.btl.Cart.textViewTongTien;
import static com.example.btl.CartFragment.textViewTongTien1;

import android.accounts.Account;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.model.CartHelper;
import com.example.btl.model.SignIn;
import com.example.btl.model.UpdateResponse;
import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.CustomerService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    CustomerService customerService;
    Gson gson = new Gson();
    public AccountFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Button buttonUpdate,buttonUpdatePassword,buttonSignOut,buttonSignIn,buttonHistoryOrder;
        buttonUpdate = (Button) view.findViewById(R.id.buttonUpdate);
        buttonSignOut = (Button) view.findViewById(R.id.buttonSignOut);
        buttonUpdatePassword = (Button) view.findViewById(R.id.updatePassword);
        buttonSignIn = (Button) view.findViewById(R.id.buttonSignIn);
        buttonHistoryOrder = (Button) view.findViewById(R.id.buttonHistoryOrder);
        LinearLayout layoutSignIn = (LinearLayout) view.findViewById(R.id.layoutSignIn);
        LinearLayout layoutButton = (LinearLayout) view.findViewById(R.id.layoutButton);

        customerService = ApiUtils.getCustomerService();
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (!isLoggedIn) {
            layoutSignIn.setVisibility(View.VISIBLE);
            layoutButton.setVisibility(View.GONE);
            buttonSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(),SignInRequestActivity.class);
                    startActivity(intent);
                }
            });
        }
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyDialog();
            }
        });
        buttonUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMyDialog1();
            }
        });
        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Đăng xuất");
                builder.setMessage("Bạn muốn đăng xuất?");

                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        CartHelper.clearCart(getContext());

                        myEdit.remove("isLoggedIn");
                        myEdit.remove("token");
                        myEdit.apply();

                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Tạo và hiển thị AlertDialog:
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        buttonHistoryOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHistoryOrder();
            }
        });
        return view;
    }

    private void viewHistoryOrder() {
        Intent intent = new Intent(getContext(),History_Order.class);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        String cus = sharedPreferences.getString("token", null);
        SignIn customer = gson.fromJson(cus,SignIn.class);
        intent.putExtra("id",customer.getId());
        startActivity(intent);
    }

    private void showMyDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_update_customer);
        EditText nameEditText = dialog.findViewById(R.id.nameUpdate);
        EditText addressEditText = dialog.findViewById(R.id.addressUpdate);
        EditText phoneEditText = dialog.findViewById(R.id.phoneUpdate);
        Button buttonUpdate = dialog.findViewById(R.id.buttonUpdate);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        String cus = sharedPreferences.getString("token", null);
        SignIn customer = gson.fromJson(cus,SignIn.class);
        nameEditText.setText(customer.getName());
        addressEditText.setText(customer.getAddress());
        phoneEditText.setText(customer.getPhone());
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;
                check = validateFields(nameEditText,addressEditText,phoneEditText);
                if (check == true)
                {
                    String name,address,phone;
                    name = nameEditText.getText().toString().trim();
                    address = addressEditText.getText().toString().trim();
                    phone = phoneEditText.getText().toString().trim();
                    customer.setName(name);
                    customer.setAddress(address);
                    customer.setPhone(phone);
                    customerService.updateAccount(customer.getEmail(),customer).enqueue(new Callback<UpdateResponse>() {
                        @Override
                        public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                            if (response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                String newCus = gson.toJson(customer);
                                myEdit.putString("token",newCus);
                                myEdit.apply();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Cập nhật không thành công! Lỗi server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    private boolean validateFields(EditText nameEditText,EditText addressEditText,EditText phoneEditText) {
        String name,address,phone;
        name = nameEditText.getText().toString().trim();
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
    private void showMyDialog1() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_update_password);
        EditText currentPassword = dialog.findViewById(R.id.currentPassword);
        EditText newPassword = dialog.findViewById(R.id.passwordNew);
        EditText confirmPassword = dialog.findViewById(R.id.confirmPassword);
        Button buttonUpdate = dialog.findViewById(R.id.buttonUpdate1);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MySharedPref", getContext().MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        String cus = sharedPreferences.getString("token", null);
        SignIn customer = gson.fromJson(cus,SignIn.class);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = false;
                check = validateFields1(customer.getPassword(),currentPassword,newPassword,confirmPassword);
                if(check==true)
                {
                    String newPass;
                    newPass = newPassword.getText().toString().trim();
                    customer.setPassword(newPass);
                    customerService.updatePassword(customer.getId(),customer).enqueue(new Callback<UpdateResponse>() {
                        @Override
                        public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                            if (response.isSuccessful())
                            {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                String newCus = gson.toJson(customer);
                                myEdit.putString("token",newCus);
                                myEdit.apply();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "Cập nhật không thành công! Lỗi server", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.dismiss();
                }
            }
        });
        dialog.show();

    }

    private boolean validateFields1(String oldPass,EditText currentPassword,EditText newPassword, EditText confirmPassword) {
        String password,newPass,cfPass;
        password = currentPassword.getText().toString().trim();
        newPass = newPassword.getText().toString().trim();
        cfPass = confirmPassword.getText().toString().trim();
        boolean isValid = true;


        // Validate password
        if (password.isEmpty()) {
            currentPassword.setError("Xin mời nhập mật khẩu.");
            isValid = false;
        } else if (password.length() < 6 ) {
            currentPassword.setError("Mật khẩu phải dài tối thiểu 6 kí tự.");
            isValid = false;
        } else if (!password.equals(oldPass)) {
            currentPassword.setError("Mật khẩu không khớp");
            isValid = false;
        } else {
            currentPassword.setError(null);
        }
        if (!newPass.isEmpty()) {
            if (newPass.length() < 6) {
                newPassword.setError("Mật khẩu phải dài tối thiểu 6 kí tự");
                isValid = false;
            } else {
                newPassword.setError(null);
            }
            if (cfPass.isEmpty()) {
                confirmPassword.setError("Nhập lại mật khẩu mới.");
                isValid = false;
            } else if (password.length() < 6) {
                confirmPassword.setError("Mật khẩu phải dài tối thiểu 6 kí tự.");
                isValid = false;
            } else if (!cfPass.equals(newPass)) {
                confirmPassword.setError("Mật khẩu không khớp.");
                isValid = false;
            } else {
                confirmPassword.setError(null);
            }
        }

        return isValid;
    }

}