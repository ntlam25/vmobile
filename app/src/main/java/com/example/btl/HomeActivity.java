package com.example.btl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btl.model.CartHelper;
import com.example.btl.model.Category;
import com.example.btl.retrofit.ApiUtils;
import com.example.btl.service.CategoryService;
import com.example.btl.ui.main.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    CategoryService categoryService;
    ArrayList<Category> categories = new ArrayList<>();
    Menu menu ;
    MenuItem categoryItem;
    EditText editTextSearch;
    ImageButton buttonSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        categoryService = ApiUtils.getCategoryService();
        categoryService.getAllCategories().enqueue(new Callback<Category.CateResponse>() {
            @Override
            public void onResponse(Call<Category.CateResponse> call, Response<Category.CateResponse> response) {
                if (response.isSuccessful())
                {
                    categories = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<Category.CateResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
            }
        });
        toolbar = findViewById(R.id.toolbar_index);
        mDrawLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        buttonSearch = findViewById(R.id.buttonSearch);
        editTextSearch = findViewById(R.id.editTextSearch);
        menu = navigationView.getMenu();
        categoryItem = menu.findItem(R.id.nav_category);
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setTitle("Trang chủ");
        setSupportActionBar(toolbar);

        // Cài đặt ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        mDrawLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextSearch.getVisibility() == View.VISIBLE) {
                    editTextSearch.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(HomeActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else {
                    editTextSearch.setVisibility(View.VISIBLE);
                    editTextSearch.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(HomeActivity.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editTextSearch, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE ) {
                    String searchQuery = editTextSearch.getText().toString();
                    performSearch(searchQuery);
                    return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
            navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        }

    }

    private void performSearch(String query) {
        Intent intent = new Intent(HomeActivity.this,ProductByCate.class);
        intent.putExtra("name",query);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDrawLayout.closeDrawers();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(HomeActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    // Ẩn editTextSearch
                    editTextSearch.setVisibility(View.GONE);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, fragment); // ID của FrameLayout trong layout của bạn
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Xử lý khi một mục menu được chọn
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            toolbar.setTitle("Trang chủ");
            replaceFragment(new HomeFragment());
        }
        else if (id == R.id.nav_category)
        {
            showDropDownMenu(findViewById(R.id.nav_category));
            return true;
        }
        else if (id == R.id.nav_cart)
        {
            toolbar.setTitle("Giỏ hàng");
            replaceFragment(new CartFragment());
        }
        else if (id == R.id.nav_account)
        {
            toolbar.setTitle("Tài khoản");
            replaceFragment(new AccountFragment());
        }

        mDrawLayout.closeDrawers();
        return true;
    }
    private void showDropDownMenu(View anchor) {
        PopupMenu dropDownMenu = new PopupMenu(this, anchor);
        final Menu menu = dropDownMenu.getMenu();
        Map<String,Integer> cateMap = new HashMap<>();
        for(Category category : categories) {
            menu.add(Menu.NONE, View.generateViewId(), Menu.NONE, category.getTitle());
            cateMap.put(category.getTitle(),category.getId());
        }

        dropDownMenu.setOnMenuItemClickListener(item -> {
            int id = cateMap.get(item.getTitle());
            Intent intent = new Intent(HomeActivity.this, ProductByCate.class);
            intent.putExtra("id",id);
            startActivity(intent);
            return true;
        });
        dropDownMenu.show();
    }
}