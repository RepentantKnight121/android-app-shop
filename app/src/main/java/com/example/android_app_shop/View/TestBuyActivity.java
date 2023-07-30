package com.example.android_app_shop.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android_app_shop.R;

import java.util.ArrayList;

public class TestBuyActivity extends AppCompatActivity {
    TextView tvShowId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_buy);
        // Initialize the tvShowId TextView
        tvShowId = (TextView) findViewById(R.id.tvShowId);
        Intent intent = getIntent();
        ArrayList<Integer> checkedProductIds = getIntent().getIntegerArrayListExtra("checkedProductIds");
        ArrayList<Integer> quantityProductId = getIntent().getIntegerArrayListExtra("quantity");
        double totalAmount = intent.getDoubleExtra("totalAmount", 0.0);

        // for IdProduct
        StringBuilder strIdProduct = new StringBuilder();
        for (int i = 0; i < checkedProductIds.size(); i++) {
            if (i > 0) {
                strIdProduct.append(", ");
            }
            strIdProduct.append(checkedProductIds.get(i));
        }
        String idsString = strIdProduct.toString();

        StringBuilder strQuantity = new StringBuilder();
        for (int i = 0; i < checkedProductIds.size(); i++) {
            if (i > 0) {
                strQuantity.append(", ");
            }
            strQuantity.append(quantityProductId.get(i));
        }
        String quantityString = strQuantity.toString();

        tvShowId.setText("Checked Product IDs: " + idsString + "\n"
                + "Quantity Product IDs: " + quantityString + "\n"
                + "Total Amount: " + totalAmount + "đ");
    }
}