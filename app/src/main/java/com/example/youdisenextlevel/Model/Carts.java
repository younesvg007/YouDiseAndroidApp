package com.example.youdisenextlevel.Model;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Database.YouDise;

public class Carts {

    private String idCart, nameC, categoryC,  image;
    private int price ,quantity, idUser, idProduct;

    public Carts() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Carts(String nameP, String categoryC, String image, int quantity, int price, int idProduct, int idUser) {
        this.nameC = nameP;
        this.categoryC = categoryC;
        this.image = image;
        this.quantity = quantity;
        this.price = price;
        this.idUser = idUser;
        this.idProduct = idProduct;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getCategoryC() {
        return categoryC;
    }

    public void setCategoryC(String categoryC) {
        this.categoryC = categoryC;
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

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean insertCart(){
        boolean isAdded = Myapplication.getYdDatabaseAdapter().insertCart(nameC, categoryC, image, quantity, price, idProduct, idUser);
        return isAdded;
    }

    public boolean checkProductUser(){
        String idUserS = String.valueOf(idUser);
        String idProductS = String.valueOf(idProduct);
        boolean isExist = Myapplication.getYdDatabaseAdapter().checkProductUser(idProductS, idUserS);
        return isExist;
    }

    public Integer deleteSingleCart(){
        Integer row = Myapplication.getYdDatabaseAdapter().deleteSingleCart(idCart);
        return row;
    }

    public Integer deleteAllCartOfUser(){
        String idUserDelete = String.valueOf(idUser);
        Integer rowDeleted = Myapplication.getYdDatabaseAdapter().deleteAllCartOfUser(idUserDelete);
        return rowDeleted;
    };
}
