package com.example.youdisenextlevel.Model;

public class Users extends Person{

    private String idUser, phoneNum, country;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String idUser, String username, String email, String phoneNum, String password,  String country) {
        super(username, email, password);
        this.idUser = idUser;
        this.phoneNum = phoneNum;
        this.country = country;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNum() { return phoneNum; }

    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }
}
