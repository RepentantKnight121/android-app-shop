package com.example.android_app_shop.Model;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    List<String> ImageURL;
    int id;
    String productName;
    double productPrice;
    boolean isChecked;
    int value = 1; // Số lượng sản phẩm

    public Cart(int id, List<String> ImageURL, String productName, double productPrice, boolean isChecked) {
        this.id = id;
        this.ImageURL = ImageURL;
        this.productName = productName;
        this.productPrice = productPrice;
        this.isChecked = isChecked;
        this.value = 1;
    }

    public Cart(){
        ImageURL = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getImageURL() {
        return ImageURL;
    }

    public void setImageURL(List<String> imageURL) {
        ImageURL = imageURL;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addImageURL(String imageURL) {
        ImageURL.add(imageURL);
    }
}
