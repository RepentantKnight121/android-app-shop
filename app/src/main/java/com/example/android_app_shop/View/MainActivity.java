package com.example.android_app_shop.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android_app_shop.Controller.ProductHandlder;
import com.example.android_app_shop.Model.Product;
import com.example.android_app_shop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameFragment;
    ImageView IconSearch, IconCart;
    EditText InputSearch;

    ProductHandlder productHandlder;
    List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ẩn đi toolbar mặc định
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        InitControl();
        Event();
    }
    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameFragment, fragment).commit();
    }

    public void Event(){
        IconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InputSearch.getVisibility() == View.VISIBLE) {
                    InputSearch.setText("");
                    InputSearch.setVisibility(View.GONE);
                } else {
                    InputSearch.setText("");
                    InputSearch.setVisibility(View.VISIBLE);
                }
            }
        });

        InputSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_NEXT) {
                    String query = textView.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("query", query);
                    Frag_Search fr = new Frag_Search();
                    fr.setArguments(bundle);
                    loadFragment(fr);
                    InputSearch.setText("");
                    InputSearch.setVisibility(View.GONE);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    return true;
                }
                return false;
            }

        });


        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    // Thay thế fragment tương ứng với FragShop
                    loadFragment(new FragHome());
                    return true;
                } else if (itemId == R.id.navigation_flashsale) {
                    loadFragment(new FragFlashSale());
                    return true;
                }   else if (itemId == R.id.navigation_shop) {
                    loadFragment(new ProductPage());
                    return true; // k có true này click item khác k đc
                }
                else if (itemId == R.id.navigation_newpaper) {
                    loadFragment(new FragNewPaper());
                    return true;
                }
                else if (itemId == R.id.navigation_account) {
                    loadFragment(new Frag_Login());
                    return true;
                }
                return false;
            }
        });
        // Mặc định chọn fragment Home khi mở ứng dụng
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        IconCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }



    public void InitControl(){
        InputSearch = findViewById(R.id.inputSearch);
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav);
        frameFragment=(FrameLayout)findViewById(R.id.frameFragment);
        IconSearch = findViewById(R.id.IconSearch);
        IconCart =  (ImageView) findViewById(R.id.IconCart);
    }
}