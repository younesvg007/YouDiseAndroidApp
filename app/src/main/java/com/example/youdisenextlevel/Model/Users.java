package com.example.youdisenextlevel.Model;

import com.example.youdisenextlevel.Application.Myapplication;

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

    /*public String getIdUser() {
        return idUser;
    }*/

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
}
