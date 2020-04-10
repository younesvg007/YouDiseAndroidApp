package com.example.youdisenextlevel.Model;

import com.example.youdisenextlevel.Application.Myapplication;

public class Carts {

    private String idCart, nameP, image;
    private int price ,quantity, idUser, idProduct;

    public Carts() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Carts(String nameP, String image, int quantity, int price, int idProduct, int idUser) {
        this.nameP = nameP;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.idUser = idUser;
        this.idProduct = idProduct;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdCart() {
        return idCart;
    }

    public void setIdCart(String idCart) {
        this.idCart = idCart;
    }

    public boolean insertCart(){
        boolean isAdded = Myapplication.getYdDatabaseAdapter().insertCart(nameP, image, quantity, price, idProduct, idUser);
        return isAdded;
    }

    public boolean checkProductUser(){
        String idUserS = String.valueOf(idUser);
        String idProductS = String.valueOf(idProduct);
        boolean isExist = Myapplication.getYdDatabaseAdapter().checkProductUser(idProductS, idUserS);
        return isExist;
    }
}
