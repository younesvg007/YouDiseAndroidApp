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
        //permet de lier le layout avec la classe Activity de java
        setContentView(R.layout.activity_login);

        // methode qui permet de lier tous les widegts de l'interface utilisateur(xml) avec la partie logique
        initiatizeField();

        //initiatialisation pour la fonctionalité "se souvenir de moi"
        initRemember();

        loginBtn.setOnClickListener(v -> authentification());

        adminLink.setOnClickListener(v -> {

            loginBtn.setText(R.string.app_login_admin);
            adminLink.setVisibility(View.INVISIBLE);
            notAdminLink.setVisibility(View.VISIBLE);

            tableName = "Admins";
            Toast.makeText(loginActivity.this, tableName, Toast.LENGTH_SHORT).show();
        });

        notAdminLink.setOnClickListener(v -> {

            loginBtn.setText(getString(R.string.app_login));
            adminLink.setVisibility(View.VISIBLE);
            notAdminLink.setVisibility(View.INVISIBLE);

            tableName = "Users";
            Toast.makeText(loginActivity.this, tableName, Toast.LENGTH_SHORT).show();
        });

        //click sur le lien se Souvenir
        //mais je lai mis en commentaire car lorsque j'ai avancer dans le developpement de l'application, un crash de l'application survient
        rememberChck.setOnCheckedChangeListener((buttonView, isChecked) -> {

            //Utilisation de shared preferences qui permet aux activités et aux applications de conserver leurs préférences
            //sous la forme de paires de valeurs clés qui persistera même lorsque l'utilisateur fermera l'application

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
        Users users = new Users();
        SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = prefs.getString("remember", "");
        if (checkbox.equals("true")){
            sendUserToHome(users);
        }
        else if (checkbox.equals("false")){
            Toast.makeText(this, "Connectez vous", Toast.LENGTH_SHORT).show();
        }
    }

    //validation des données entrées
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
                //authentification en tant que User
                signInUser(email, password);
            }
            else if(notAdminLink.getVisibility() == View.VISIBLE){
                //authentification en tant que Admin
                signInAdmin(email, password);
            }
        }
    }

    ///// PHASE CONNEXION User
    private void signInUser(String mail, String pwd){

        Users user = new Users(mail, pwd);

        boolean checkUser = user.loginUser(); //renvoit boolean si les données correspond aux infos de la BDD
        if (checkUser){
            loadingBar.dismiss();
            sendUserToHome(user);
            Toast.makeText(loginActivity.this, getString(R.string.logged_User_msg), Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.dismiss();
            Toast.makeText(loginActivity.this, getString(R.string.wrong_logged_msg), Toast.LENGTH_SHORT).show();
        }
    }

    ///// PHASE CONNEXION Admin
    private void signInAdmin(String mail, String pwd) {

        Admins admin = new Admins(mail, pwd);
        boolean checkAdmin = admin.loginAdmin(); //= db.checkAdmin(mail, pwd);
        if (checkAdmin){
            loadingBar.dismiss();
            sendAdminToListProduct();
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
    private void sendUserToHome(Users user) {
        Intent homeIntent = new Intent(loginActivity.this, HomeActivity.class);
        homeIntent.putExtra("email", user.getEmail()); //transfert de l'email du user vers le home Activity
        startActivity(homeIntent);
    }

    //direction vers List Product Admin Activity
    private void sendAdminToListProduct() {
        Intent mainIntent = new Intent(loginActivity.this, ListProductAdminActivity.class);
        startActivity(mainIntent);
    }

    //Initialiastion des champs
    private void initiatizeField() {

        loginBtn = (Button) findViewById(R.id.login_btn);
        inputEmail = (EditText) findViewById(R.id.login_email_input);
        inputPassword = (EditText) findViewById(R.id.login_password_input);
        rememberChck = (CheckBox) findViewById(R.id.remember_me_checkbox);
        adminLink = (TextView) findViewById(R.id.admin_login_link);
        notAdminLink = (TextView) findViewById(R.id.not_admin_login_link);
        loadingBar = new ProgressDialog(this);

    }
}
