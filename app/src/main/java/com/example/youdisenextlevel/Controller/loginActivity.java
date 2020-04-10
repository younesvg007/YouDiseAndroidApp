package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.youdisenextlevel.Model.Database.DatabaseHelper;
import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Admins;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;

public class loginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private TextView adminLink, notAdminLink;
    private Button loginBtn;
    private ProgressDialog loadingBar;
    private CheckBox rememberChck;

    private String tableName = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initiatizeField();

        //mini-memoire
        initRemember();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authentification();
            }
        });

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginBtn.setText("Connexion Admin");
                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);

                tableName = "Admins";
                Toast.makeText(loginActivity.this, tableName, Toast.LENGTH_SHORT).show();
            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBtn.setText(getString(R.string.app_login));
                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);

                tableName = "Users";
                Toast.makeText(loginActivity.this, tableName, Toast.LENGTH_SHORT).show();
            }
        });

        rememberChck.setOnCheckedChangeListener((buttonView, isChecked) -> {

            /*if (rememberChck.isChecked()){
                SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("remember", "true");
                editor.apply();
                Toast.makeText(loginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
            }
            else{
                SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("remember", "false");
                editor.apply();
                Toast.makeText(loginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
            }*/
        });


    }

    private void initRemember() {
        SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = prefs.getString("remember", "");
        if (checkbox.equals("true")){
            sendUserToHome();
        }
        else if (checkbox.equals("false")){
            Toast.makeText(this, "Connectez vous", Toast.LENGTH_SHORT).show();
        }
    }

    // Phase de Connexion
    private void authentification(){
        final String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            inputEmail.setError(getString(R.string.empty_email));
            inputEmail.requestFocus();
        }
        if (TextUtils.isEmpty(password)){
            inputPassword.setError(getString(R.string.empty_password));
            inputPassword.requestFocus();
        }
        else{
            loadingBarLogin();

            if (adminLink.getVisibility() == View.VISIBLE){
                signInUser(email, password);
            }
            else if(notAdminLink.getVisibility() == View.VISIBLE){
                signInAdmin(email, password);
            }
        }
    }

    private void signInUser(String mail, String pwd){

        Users user = new Users(mail, pwd);

        boolean checkUser = user.loginUser();
        if (checkUser){
            loadingBar.dismiss();

            Intent homeIntent = new Intent(loginActivity.this, HomeActivity.class);
            homeIntent.putExtra("email", user.getEmail());
            startActivity(homeIntent);

            Toast.makeText(loginActivity.this, getString(R.string.logged_User_msg), Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.dismiss();
            Toast.makeText(loginActivity.this, getString(R.string.wrong_logged_msg), Toast.LENGTH_SHORT).show();
        }
    }

    private void signInAdmin(String mail, String pwd) {

        Admins admin = new Admins(mail, pwd);
        boolean checkAdmin = admin.loginAdmin(); //= db.checkAdmin(mail, pwd);
        if (checkAdmin){
            //sendUserToHome();
            loadingBar.dismiss();
            sendAdminToAdminCategory();

            Toast.makeText(loginActivity.this, getString(R.string.logged_Admin_msg), Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.dismiss();
            Toast.makeText(loginActivity.this, getString(R.string.wrong_logged_msg), Toast.LENGTH_SHORT).show();
        }
    }

    //methode pour phase d'attente
    private void loadingBarLogin() {
        loadingBar.setTitle(getString(R.string.login_account));
        loadingBar.setMessage(getString(R.string.hold_on_msg));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

    //direction vers Home Activity
    private void sendUserToHome() {
        Intent homeIntent = new Intent(loginActivity.this, HomeActivity.class);
        //homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//permet de refresh lactivity
        startActivity(homeIntent);
    }

    //direction vers AdminCategory Activity
    private void sendAdminToAdminCategory() {
        Intent mainIntent = new Intent(loginActivity.this, CategoryAdminActivity.class);
        startActivity(mainIntent);
    }

    //Initialiastion des champs
    private void initiatizeField() {
        //db = new DatabaseHelper(this);

        loginBtn = (Button) findViewById(R.id.login_btn);
        inputEmail = (EditText) findViewById(R.id.login_email_input);
        inputPassword = (EditText) findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        rememberChck = (CheckBox) findViewById(R.id.remember_me_checkbox);
        adminLink = (TextView) findViewById(R.id.admin_login_link);
        notAdminLink = (TextView) findViewById(R.id.not_admin_login_link);

        inputEmail.setText("soolking@mail.com");
        inputPassword.setText("banana");
    }
}
