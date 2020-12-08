package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Database.ImageManage;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private LinearLayout cart, order, setting, logout;
    private Button backBtn;
    private TextView nameProfile, emailProfile;
    private CircleImageView imageProfile;
    private String mail;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initFields();

        mail = getIntent().getExtras().get("mail").toString();

        // permet d'afficher le logo du profil avec l'image recupéré dans Firebase Storage
        ImageManage.getImage("20200408201111.jpg", imageProfile);

        //permet d'afficher les infos du users
        setInfos(mail);

        //Direction vers Cart Activity
        cart.setOnClickListener(v -> sendUserToCart());

        //Direction vers Order List Activity
        order.setOnClickListener(v -> sendUserToOrderList());

        //Direction vers Setting Activity
        setting.setOnClickListener(v -> sendUserToSetting());

        //deconnexion
        logout.setOnClickListener(v -> {
            //cancelRemember();
            sendUserToMain();
        });

        //retour a la page d'accueil
        backBtn.setOnClickListener(v -> finish());

    }

    private void setInfos(String email) {
        try {
            user = new Users();
            user.setEmail(email);
            Cursor cursor = user.getDataUser();
            if (cursor.getCount() != 0){
                while (cursor.moveToNext()){
                    String name = cursor.getString(cursor.getColumnIndex(YouDise.USERS_COL_USERNAME));
                    nameProfile.setText(name);
                }
            }
            emailProfile.setText(email);
        }
        catch (Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }
    }

    private void initFields() {
        cart = (LinearLayout) findViewById(R.id.linear_cart_layout);
        order = (LinearLayout) findViewById(R.id.linear_order_layout);
        setting = (LinearLayout) findViewById(R.id.linear_setting_layout);
        logout = (LinearLayout) findViewById(R.id.linear_logout_layout);
        backBtn = (Button) findViewById(R.id.back_btn);
        imageProfile = (CircleImageView) findViewById(R.id.image_profile);
        nameProfile = (TextView) findViewById(R.id.name_profile);
        emailProfile = (TextView) findViewById(R.id.email_profile);
    }

    private void sendUserToCart() {
        Intent cartIntent = new Intent(ProfileActivity.this, CartActivity.class);
        cartIntent.putExtra("email", mail);
        startActivity(cartIntent);
    }

    private void sendUserToOrderList() {
        Intent orderListIntent = new Intent(ProfileActivity.this, OrderListActivity.class);
        orderListIntent.putExtra("mail", mail);
        startActivity(orderListIntent);
    }

    private void sendUserToSetting() {
        Intent settingIntent = new Intent(ProfileActivity.this, SettingActivity.class);
        settingIntent.putExtra("email", mail);
        startActivity(settingIntent);
    }

    //Retour au Main Activity
    private void sendUserToMain() {
        Intent mainIntent = new Intent(ProfileActivity.this, MainActivity.class);
        startActivity(mainIntent);
        Toast.makeText(this, getString(R.string.logout_User_msg), Toast.LENGTH_SHORT).show();
    }

    //permet de desactiver le "Remember me"
    private void cancelRemember() {
        SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("remember", "false");
        editor.apply();
        Toast.makeText(ProfileActivity.this, getString(R.string.logout_User_msg), Toast.LENGTH_SHORT).show();
    }
}
