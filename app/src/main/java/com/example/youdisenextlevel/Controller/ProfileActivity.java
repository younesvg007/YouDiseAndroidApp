package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youdisenextlevel.Model.Database.ImageStorage;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout logout;
    private Button backBtn;
    private TextView nameProfile, emailProfile;
    private ImageView imageProfile;
    private String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initFields();

        mail = getIntent().getExtras().get("mail").toString();
        emailProfile.setText(mail);
        //ImageStorage.getImage("20200406140351.jpg", imageProfile);

        logout.setOnClickListener(v -> {
            //cancelRemember();
            sendUserToMain();
        });

    }

    private void initFields() {
        logout = (LinearLayout) findViewById(R.id.linear_logout_layout);
        backBtn = (Button) findViewById(R.id.back_btn);
        imageProfile = (ImageView) findViewById(R.id.image_profile);
        nameProfile = (TextView) findViewById(R.id.name_profile);
        emailProfile = (TextView) findViewById(R.id.email_profile);
    }

    private void sendUserToMain() {
        Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void sendUserToHome() {
        Intent homeIntent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(homeIntent);
    }

    private void cancelRemember() {
        SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("remember", "false");
        editor.apply();
        Toast.makeText(ProfileActivity.this, getString(R.string.logout_User_msg), Toast.LENGTH_SHORT).show();
    }
}
