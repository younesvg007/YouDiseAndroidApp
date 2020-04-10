package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.youdisenextlevel.R;

public class MainActivity extends AppCompatActivity {

    //declaration des button
    private Button loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lier button
        InitializeFields();

        //creer un evenement (click sur button)
        loginButton.setOnClickListener(v -> {
            //envoi lutilisateur vers le login activity
            sendUserToLogin();
        });

        registerButton.setOnClickListener(v -> {
            //envoi lutilisateur vers le register activity
            sendUserToRegister();
        });

    }

    private void InitializeFields() {
        loginButton = (Button) findViewById(R.id.login_main_btn);
        registerButton = (Button) findViewById(R.id.register_main_btn);
    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, loginActivity.class);
        //homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//permet de refresh lactivity
        startActivity(loginIntent);
    }

    private void sendUserToRegister() {
        Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }

}
