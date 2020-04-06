package com.example.youdisenextlevel.Model;

import com.example.youdisenextlevel.Application.Myapplication;

public class Products {
    private String name, category, description, dateTime, imagePath;
    private int price ,img;

    public Products() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Products(String name, String category, String description, int price, String dateTime, String imagePath) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.dateTime = dateTime;
        this.imagePath = imagePath;
    }

    public Products(String name, String description, int price, String imagePath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
    }

    public boolean insertProduct(){
        boolean isAdded = Myapplication.getYdDatabaseAdapter().insertProduct(name, category, description, price, dateTime, imagePath);
        return isAdded;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
