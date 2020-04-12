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

    public boolean insertOrder(){
        boolean isAdded = Myapplication.getYdDatabaseAdapter().insertOrder(name, email, adress, country, bankCard, totalAmount, dateTime, idUser);
        return isAdded;
    }

}
