package com.example.btl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.model.CartHelper;
import com.example.btl.model.CartModel;
import com.example.btl.model.Product;
import com.example.btl.model.Respone;
import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetail extends AppCompatActivity {

    ImageButton imageButtonBack,imageButtonCart;
    ImageButton buttonMinus,buttonPlus;
    TextView product_quantity,product_title,product_price,
            textViewBattery,textViewRAM,textViewInternalMem,
            textViewSreenSize,textViewSreenTech,textViewProcessor
            ,textViewDescription,textViewSoldOut;
    ImageView product_image;
    Button buttonAddToCart;
    ProductService productService;
    int quantity = 1;
    Product product;
    CartModel cart = new CartModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        InitWidget();
        productService = ApiUtils.getProductService();
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        productService.getProductById(id).enqueue(new Callback<Respone>() {
            @Override
            public void onResponse(Call<Respone> call, Response<Respone> response) {
                if (response.isSuccessful())
                {
                    product = response.body().getData().getDocs();
                    cart.setId(product.getId());
                    cart.setName(product.getName());
                    cart.setImage(product.getImage());
                    cart.setPrice(product.getPrice());
                    cart.setQuantity(Integer.parseInt(product_quantity.getText().toString()));
                    product_title.setText(product.getName());
                    product_price.setText(String.valueOf(product.getPrice()));
                    String imageName = product.getImage();
                    int imageId = getResources().getIdentifier(imageName.substring(0,imageName.lastIndexOf('.')), "drawable", getPackageName());
                    product_image.setImageResource(imageId);
                    textViewDescription.setText(product.getDescription());
                    textViewBattery.setText(product.getBattery_capacity());
                    textViewRAM.setText(product.getRam());
                    textViewProcessor.setText(product.getProcessor());
                    textViewInternalMem.setText(product.getInternal_memory());
                    textViewSreenSize.setText(product.getScreen_size());
                    textViewSreenTech.setText(product.getScreen_tech());
                    updateProductUI();
                }
            }

            @Override
            public void onFailure(Call<Respone> call, Throwable t) {

            }
        });
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(product_quantity.getText().toString());
                if (quantity >1)
                {
                    quantity--;
                    product_quantity.setText(String.valueOf(quantity));
                }
                else {
                    Toast.makeText(ProductDetail.this, "Tối thiếu 1 sản phẩm", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity = Integer.parseInt(product_quantity.getText().toString());
                quantity++;
                product_quantity.setText(String.valueOf(quantity));
            }
        });
        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.setQuantity(Integer.parseInt(product_quantity.getText().toString()));
                CartHelper.addToCart(getApplicationContext(), cart);
                Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
        imageButtonCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CartModel> cartItems = CartHelper.getCartItems(getApplicationContext());
                Intent intent1 = new Intent(ProductDetail.this,Cart.class);
                intent1.putExtra("cart",cartItems);
                startActivity(intent1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        product_quantity.setText("1");
    }
    private void updateProductUI() {
        if (product.isIs_stock()==true) {
            buttonAddToCart.setEnabled(true);
        } else {
            textViewSoldOut.setVisibility(View.VISIBLE);
            buttonAddToCart.setVisibility(View.GONE);
        }
    }

    private void InitWidget()
    {
        imageButtonBack = (ImageButton) findViewById(R.id.imageButtonBack);
        imageButtonCart = (ImageButton) findViewById(R.id.imageButtonCart);
        buttonMinus = (ImageButton) findViewById(R.id.buttonMinus);
        buttonPlus = (ImageButton) findViewById(R.id.buttPlus);
        product_quantity = (TextView) findViewById(R.id.product_quantity);
        product_title = (TextView) findViewById(R.id.product_title);
        product_price = (TextView) findViewById(R.id.product_price);
        product_image = (ImageView) findViewById(R.id.product_image);
        textViewBattery = (TextView) findViewById(R.id.textViewBattery);
        textViewRAM = (TextView) findViewById(R.id.textViewRAM);
        textViewInternalMem = (TextView) findViewById(R.id.textViewInternalMem);
        textViewSreenSize= (TextView) findViewById(R.id.textViewScreenSize);
        textViewSreenTech = (TextView) findViewById(R.id.textViewScreenTech);
        textViewProcessor = (TextView) findViewById(R.id.textViewProcessor);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
        buttonAddToCart = (Button) findViewById(R.id.buttonAddToCart);
        textViewSoldOut = (TextView) findViewById(R.id.textViewSoldOut);
    }
}