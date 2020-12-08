package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.youdisenextlevel.Model.Database.DatabaseHelper;
import com.example.youdisenextlevel.Model.Admins;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;


public class RegisterActivity extends AppCompatActivity {

    private Button createAccountBtn;
    private EditText inputName, inputEmail, inputPhone, inputPassword, inputCountry;
    private TextView adminLink, notAdminLink;
    private ProgressDialog loadingBar;
    private String tableName = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // methode qui permet de lier tous les widegts de l'interface utilisateur(xml) avec la programation de java
        initFields();

        //click sur le lien AdminLink pour s'inscrire en tant que Admin
        adminLink.setOnClickListener(v -> {

            //l'inscription d'un admin constitue uniquement le fullname, adresse email, mot de passe
            //les autres editTexts passent à l'etat invisible
            inputName.setVisibility(View.INVISIBLE);
            inputName = (EditText) findViewById(R.id.register_fullname_input); //la variable inputName est desormais lier avec l'edit text fullname
            inputName.setVisibility(View.VISIBLE);
            inputCountry.setVisibility(View.INVISIBLE);
            inputPhone.setVisibility(View.INVISIBLE);
            adminLink.setVisibility(View.INVISIBLE);
            notAdminLink.setVisibility(View.VISIBLE);

            // indique que on est sur la page d'inscription en tant que admin
            tableName = "admins";
            Toast.makeText(RegisterActivity.this, tableName, Toast.LENGTH_SHORT).show();
        });

        //click sur le lien notAdminLink pour s'inscrire en tant que User
        notAdminLink.setOnClickListener(v -> {

            //l'inscription d'un user constitue la totale: le username, adresse email, mot de passe, pays, numero de tel
            //les autres editTexts passent à l'etat invisible
            inputName.setVisibility(View.INVISIBLE);
            inputName = (EditText) findViewById(R.id.register_username_input); //la variable inputName est desormais lier avec l'edit text username
            inputName.setVisibility(View.VISIBLE);
            inputCountry.setVisibility(View.VISIBLE);
            inputPhone.setVisibility(View.VISIBLE);
            adminLink.setVisibility(View.VISIBLE);
            notAdminLink.setVisibility(View.INVISIBLE);

            // indique que on est sur la page d'inscription en tant que user
            tableName = "users";
            Toast.makeText(RegisterActivity.this, tableName, Toast.LENGTH_SHORT).show();
        });

        //click sur le bouton "Creer un compte"
        createAccountBtn.setOnClickListener(v -> {
            if (inputCountry.getVisibility() == View.VISIBLE) {
                //si l'edit Text pour le pays est invisble c'est d'office une inscription en tant que Admin
                createUserAccount();
            } else if (inputCountry.getVisibility() == View.INVISIBLE) {
                //si l'edit Text pour le pays est visible c'est d'office une inscription en tant que user
                createAdminAccount();
            }
        });

    }

    //methode qui permet de verifier si l'addresse email entré est valide
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void initFields() {
        createAccountBtn = (Button) findViewById(R.id.register_btn);
        inputName = (EditText) findViewById(R.id.register_username_input);
        inputEmail = (EditText) findViewById(R.id.register_email_input);
        inputPhone = (EditText) findViewById(R.id.register_phone_input);
        inputPassword = (EditText) findViewById(R.id.register_password_input);
        inputCountry = (EditText) findViewById(R.id.register_country_input);
        adminLink = (TextView) findViewById(R.id.admin_register_link);
        notAdminLink = (TextView) findViewById(R.id.not_admin_register_link);

        //Creation d'un composant de base des interfaces graphiques
        // qui permettent d'indiquer à l'utilisateur l'état d'avancement d'un travail qu'est en train d'effectuer l'ordinateur.
        loadingBar = new ProgressDialog(this);

    }

    //validation des données entrées
    private void createUserAccount() {
        //recuperation des données entrées par l'utilisateur
        String name = inputName.getText().toString().trim(); //trim() remove les espaces du debut et fin du string
        String email = inputEmail.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String country = inputCountry.getText().toString().trim();

        //is.empty => si string est vide
        if (TextUtils.isEmpty(name)) {
            inputName.setError(getString(R.string.empty_name)); //message d'erreur
            inputName.requestFocus(); //focus sur cet edittext
        } else if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.empty_email));
            inputEmail.requestFocus();
        } else if (!isEmailValid(email)) {
            inputEmail.setError(getString(R.string.invalid_mail));
            inputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            inputPassword.setError(getString(R.string.empty_password));
            inputPassword.requestFocus();
        } else if (password.length() < 6) {
            inputPassword.setError(getString(R.string.not_enough_password));
            inputPassword.requestFocus();
        } else if (TextUtils.isEmpty(phone)) {
            inputPhone.setError(getString(R.string.empty_phone));
            inputPhone.requestFocus();
        } else if (phone.length() != 10) {
            inputPhone.setError(getString(R.string.not_enough_phone));
            inputPhone.requestFocus();
        } else if (TextUtils.isEmpty(country)) {
            inputCountry.setError(getString(R.string.empty_country));
            inputCountry.requestFocus();
        } else {

            //affichage du composant chargement
            loadingBarRegister();

            //methode qui permet d'inserer les données dans la base de donnée
            registerUser(name, email, password, phone, country);
        }
    }

    //Insrciption en tant que User
    private void registerUser(String name, String email, String password, String phone, String country) {
        //Instanciation de la classe Users(Model)
        Users user = new Users(name, email, password, phone, country);

        //renvoit un boolean pour verifier si il existe un compte avec la meme l'adresse email
        boolean isExist = user.checkMailUser();
        if (!isExist) {

            //renvoit un boolean pour verifier si l'insertion s'est bien effectuer
            boolean isInsertUser = user.insertUser();

            if (isInsertUser) {
                // le composant de chargement se stoppe
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.registrationUser_msg), Toast.LENGTH_SHORT).show();

                //envoi l'utilisateur vers login activity
                sendUserToLogin();
            } else {
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.error_register_msg), Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            loadingBar.dismiss();
            Toast.makeText(RegisterActivity.this, getString(R.string.already_registered_msg), Toast.LENGTH_SHORT).show();
        }

    }

    //validation des données entrées
    private void createAdminAccount() {
        //recuperation des données entrées par l'utilisateur
        String name = inputName.getText().toString().trim(); //trim() remove les espaces du debut et fin du string
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            inputName.setError(getString(R.string.empty_name));
            inputName.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            inputEmail.setError(getString(R.string.empty_email));
            inputEmail.requestFocus();
        } else if (!isEmailValid(email)) {
            inputEmail.setError(getString(R.string.invalid_mail));
            inputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            inputPassword.setError(getString(R.string.empty_password));
            inputPassword.requestFocus();
        } else if (password.length() < 6) {
            inputPassword.setError(getString(R.string.not_enough_password));
            inputPassword.requestFocus();
        } else {

            loadingBarRegister();
            registerAdmin(name, email, password);
        }

    }

    //Insrciption en tant que Admin
    private void registerAdmin(String name, String mail, String pwd) {
        //Instanciation de la classe Users(Model)
        Admins admin = new Admins(name, mail, pwd);

        boolean isExist = admin.checkMailAdmin();
        if (!isExist) {
            boolean isInsertAdmin = admin.insertAdmin();

            //si insert a fonctionné
            if (isInsertAdmin) {
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.registrationAdmin_msg), Toast.LENGTH_SHORT).show();
                sendUserToLogin();
            } else {
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.error_register_msg), Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            loadingBar.dismiss();
            Toast.makeText(RegisterActivity.this, getString(R.string.already_registered_msg), Toast.LENGTH_SHORT).show();
        }
    }

    private void sendUserToLogin() {
        Intent loginIntent = new Intent(RegisterActivity.this, loginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //permet de refresh lactivity
        startActivity(loginIntent);
    }

    private void loadingBarRegister() {
        loadingBar.setTitle(getString(R.string.creation_account));
        loadingBar.setMessage(getString(R.string.hold_on_msg));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }


}
