package com.example.android_app_shop.Model;

public class Bill {
    int id, idAccount, status;
    String fullnamecutomer;
    String gmail;
    String phoneNumber;
    String timeOder;


    public Bill(int id, int idAccount, int status, String fullnamecutomer, String gmail, String phoneNumber, String timeOder) {
        this.id = id;
        this.idAccount = idAccount;
        this.status = status;
        this.fullnamecutomer = fullnamecutomer;
        this.gmail = gmail;
        this.phoneNumber = phoneNumber;
        this.timeOder = timeOder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFullnamecutomer() {
        return fullnamecutomer;
    }

    public void setFullnamecutomer(String fullnamecutomer) {
        this.fullnamecutomer = fullnamecutomer;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTimeOder() {
        return timeOder;
    }

    public void setTimeOder(String timeOder) {
        this.timeOder = timeOder;
    }
}