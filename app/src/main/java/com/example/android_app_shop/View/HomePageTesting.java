package com.example.android_app_shop.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android_app_shop.R;

public class HomePageTesting extends AppCompatActivity {

    EditText edtId;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_paget_testing);
        addControls();
        addEvent();
    }

    private void addControls(){
        edtId = (EditText) findViewById(R.id.edtId);
        btnSend = (Button) findViewById(R.id.btnSend);
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

}