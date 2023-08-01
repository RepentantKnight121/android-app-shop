package com.example.android_app_shop.Model;

public class Account {
    Integer id ;
    String username , password , displayName ;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Account(){}

    public Account(Integer ID , String Username , String Password , String DisplayName){
        id = ID ;
        username = Username ;
        password = Password;
        displayName = DisplayName;
    }
}
