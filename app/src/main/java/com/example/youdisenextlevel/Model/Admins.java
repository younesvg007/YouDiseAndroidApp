package com.example.youdisenextlevel.Model;

import com.example.youdisenextlevel.Application.Myapplication;

public class Admins{

    //private String idAdmin;
    private String fullname, email, password;

    public Admins() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Admins(String fullname, String email, String password) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
    }

    public Admins(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //methode faisant appel a la requete d'insertion Admin dans la BDD
    public boolean insertAdmin(){
        boolean isAdded = Myapplication.getYdDatabaseAdapter().insertAdmin(fullname, email, password);
        return isAdded;
    }

    //methode faisant appel a la requete de verification si le mail Admin existe deja dans la table
    public boolean checkMailAdmin(){
        boolean isExist = Myapplication.getYdDatabaseAdapter().checkMailAdmin(email);
        return isExist;
    }

    //methode faisant appel a la requete de authentification de Admin
    public boolean loginAdmin(){
        boolean checkAdmin = Myapplication.getYdDatabaseAdapter().checkAdmin(email, password);
        return checkAdmin;
    }
}
