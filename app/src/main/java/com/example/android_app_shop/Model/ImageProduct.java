package com.example.android_app_shop.Model;

public class ImageProduct {
    int ID, ID_Product;
    String URL;

    public ImageProduct(int ID, int ID_Product, String URL) {
        this.ID = ID;
        this.ID_Product = ID_Product;
        this.URL = URL;
    }

    public ImageProduct(){}


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID_Product() {
        return ID_Product;
    }

    public void setID_Product(int ID_Product) {
        this.ID_Product = ID_Product;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
