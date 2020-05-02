package com.example.youdisenextlevel.Model;

import com.example.youdisenextlevel.Application.Myapplication;

public class Orders {

    private String idOrder, name, email, adress, country, bankCard, dateTime, totalAmount;
    private int idUser;

    public Orders() {

    }

    public Orders(String name, String email, String adress, String country, String bankCard, String totalAmount, String dateTime, int idUser) {
        this.name = name;
        this.email = email;
        this.adress = adress;
        this.country = country;
        this.bankCard = bankCard;
        this.totalAmount = totalAmount;
        this.dateTime = dateTime;
        this.idUser = idUser;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    /*public boolean insertOrder(){
        boolean isAdded = Myapplication.getYdDatabaseAdapter().insertOrder(name, email, adress, country, bankCard, totalAmount, dateTime, idUser);
        return isAdded;
    }*/

}
