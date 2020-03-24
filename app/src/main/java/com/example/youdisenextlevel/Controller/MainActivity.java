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
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lier button
        InitializeFields();



        //creer un evenement (click sur button)
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //methode qui permet la direction de la activite
                //envoi lutilisateur vers le login activity
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //envoi lutilisateur vers le register activity
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
        //homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//permet de refresh lactivity
        startActivity(homeIntent);
    }

    private void loadingBarLogin() {
        loadingBar.setTitle(getString(R.string.already_logged_in));
        loadingBar.setMessage(getString(R.string.hold_on_msg));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

    }

    private void InitializeFields() {
        loginButton = (Button) findViewById(R.id.login_main_btn);
        registerButton = (Button) findViewById(R.id.register_main_btn);

    }
}
