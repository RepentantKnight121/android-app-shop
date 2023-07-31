package com.example.android_app_shop.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android_app_shop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameFragment;
    ImageView IconSearch ;
    EditText InputSearch;
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
                    // Ẩn EditText nếu đã hiển thị
                    InputSearch.setVisibility(View.GONE);
                } else {
                    // Hiển thị EditText nếu đã ẩn
                    InputSearch.setVisibility(View.VISIBLE);
                }
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
                    loadFragment(new FragShop());
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
    }

    public void InitControl(){
        InputSearch = findViewById(R.id.inputSearch);

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav);
        frameFragment=(FrameLayout)findViewById(R.id.frameFragment);

        IconSearch = findViewById(R.id.IconSearch);
    }
}