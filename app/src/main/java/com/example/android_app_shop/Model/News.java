package com.example.android_app_shop.Model;

import java.util.Date;

public class News {
    String ID, title,content, imageURL, productID, date;


    public News(String ID, String title, String content, String imageURL, String productID, String date) {
        this.ID = ID;
        this.title = title;
        this.content = content;
        this.imageURL = imageURL;
        this.productID = productID;
        this.date = date;
    }

    public  News(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
