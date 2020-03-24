package com.example.youdisenextlevel.Model;

public class Admins extends Person{

    private String idAdmin;

    public Admins() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Admins(String idAdmin, String username, String email, String password) {
        super(username, email, password);
        this.idAdmin = idAdmin;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }


}
