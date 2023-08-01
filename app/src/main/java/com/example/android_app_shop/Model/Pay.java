package com.example.android_app_shop.Model;

import java.util.ArrayList;
import java.util.List;

public class Pay {
    List<String> ImageURL;
    int id;
    String productName;
    String color;
    String storage;
    double productPrice;
    int value = 1;

    public Pay(int id, List<String> ImageURL, String productName, String color, String storage, double productPrice) {
        this.id = id;
        this.ImageURL = ImageURL;
        this.productName = productName;
        this.color = color;
        this.storage = storage;
        this.productPrice = productPrice;
        this.value = 1;
    }

    public Pay(){
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

    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }

    public String getStorage() {
        return storage;
    }
    public void setStorage(String storage) {
        this.storage = storage;
    }



    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
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
