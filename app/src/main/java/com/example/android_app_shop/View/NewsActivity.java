package com.example.android_app_shop.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android_app_shop.R;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    TextView tvTitle, tvContent, tvDate;
    ImageView imageView;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        addControls();
        loadData();
        addEvents();
    }

    private void loadData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String title = bundle.getString("title");
        String content = bundle.getString("content");
        String date = bundle.getString("date");
        String imageURL = bundle.getString("image");

        tvTitle.setText(title);
        tvContent.setText(content);
        tvDate.setText(date);
        Picasso.get().load(imageURL).into(imageView);
    }

    private void addControls(){
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvContent = (TextView) findViewById(R.id.tvContent);
        tvDate = (TextView) findViewById(R.id.tvDate);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnBack = (Button) findViewById(R.id.btnBack);
    }

    private void addEvents(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}