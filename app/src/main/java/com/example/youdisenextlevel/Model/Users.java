package com.example.youdisenextlevel.Model;

import android.database.Cursor;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Database.YouDise;

public class Users{

    private String idUser;
    private String username, email, password,  phoneNum, country;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String username, String email, String password,  String phoneNum, String country) {
        //this.idUser = idUser;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.country = country;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNum() { return phoneNum; }

    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    public boolean insertUser(){
        boolean isAdded = Myapplication.getYdDatabaseAdapter().insertUser(username, email, password,  phoneNum, country);
        return isAdded;
    }

    public boolean loginUser(){
        boolean checkUser = Myapplication.getYdDatabaseAdapter().checkUser(email, password);
        return checkUser;
    }

    public boolean checkMailUser(){
        boolean isExist = Myapplication.getYdDatabaseAdapter().checkMailUser(email);
        return isExist;
    }

    public Cursor getDataUser(){
        Cursor cursor = Myapplication.getYdDatabaseAdapter().getDataOfUser(email);
        return cursor;
    }
}
