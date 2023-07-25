package com.example.android_app_shop.Model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    String ID_Category, NameProduct, Color;
    List<String> ImageURL;
    int Storage, ID;
    float Price;

    public Product(String ID_Category, int ID, String nameProduct, String color, List<String> imageURL, int storage, float price) {
        this.ID_Category = ID_Category;
        this.ID = ID;
        NameProduct = nameProduct;
        Color = color;
        ImageURL = imageURL;
        Storage = storage;
        Price = price;
    }

    public Product() {
        ImageURL = new ArrayList<>();
    }

    public String getID_Category() {
        return ID_Category;
    }

    public void setID_Category(String ID_Category) {
        this.ID_Category = ID_Category;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNameProduct() {
        return NameProduct;
    }

    public void setNameProduct(String nameProduct) {
        NameProduct = nameProduct;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public List<String> getImageURL() {
        return ImageURL;
    }

    public void setImageURL(List<String> imageURL) {
        ImageURL = imageURL;
    }

    public int getStorage() {
        return Storage;
    }

    public void setStorage(int storage) {
        Storage = storage;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public void addImageURL(String imageURL) {
        ImageURL.add(imageURL);
    }
}
