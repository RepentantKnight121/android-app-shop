package com.example.android_app_shop.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.example.android_app_shop.R;

public class HomePageTesting extends AppCompatActivity {

    FrameLayout fragLayout;
    EditText edtId;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_paget_testing);
        addControls();
        loadFragment(new ProductPage());
        addEvent();
    }

    private void addControls(){
        edtId = (EditText) findViewById(R.id.edtId);
        btnSend = (Button) findViewById(R.id.btnSend);
        fragLayout = (FrameLayout) findViewById(R.id.fragLayout);
    }

    private void addEvent(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePageTesting.this, ProductDetails.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", Integer.parseInt(edtId.getText().toString()));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragLayout, fragment);
        fragmentTransaction.commit();
    }

}