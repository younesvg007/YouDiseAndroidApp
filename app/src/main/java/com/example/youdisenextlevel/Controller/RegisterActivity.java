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

        initializeFields();

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputName.setVisibility(View.INVISIBLE);
                inputName = (EditText) findViewById(R.id.register_fullname_input);
                inputName.setVisibility(View.VISIBLE);

                inputCountry.setVisibility(View.INVISIBLE);
                inputPhone.setVisibility(View.INVISIBLE);

                adminLink.setVisibility(View.INVISIBLE);
                notAdminLink.setVisibility(View.VISIBLE);
                tableName = "admins";
                Toast.makeText(RegisterActivity.this, tableName, Toast.LENGTH_SHORT).show();
            }
        });

        notAdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputName.setVisibility(View.INVISIBLE);
                inputName = (EditText) findViewById(R.id.register_username_input);
                inputName.setVisibility(View.VISIBLE);

                inputCountry.setVisibility(View.VISIBLE);
                inputPhone.setVisibility(View.VISIBLE);

                adminLink.setVisibility(View.VISIBLE);
                notAdminLink.setVisibility(View.INVISIBLE);
                tableName = "users";
                Toast.makeText(RegisterActivity.this, tableName, Toast.LENGTH_SHORT).show();
            }
        });

        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCountry.getVisibility() == View.VISIBLE){
                    createUserAccount();
                }
                else if(inputCountry.getVisibility() == View.INVISIBLE){
                    createAdminAccount();
                }
            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void initializeFields() {
        createAccountBtn = (Button) findViewById(R.id.register_btn);
        inputName = (EditText) findViewById(R.id.register_username_input);
        inputEmail = (EditText) findViewById(R.id.register_email_input);
        inputPhone = (EditText) findViewById(R.id.register_phone_input);
        inputPassword = (EditText) findViewById(R.id.register_password_input);
        inputCountry = (EditText) findViewById(R.id.register_country_input);
        adminLink = (TextView) findViewById(R.id.admin_register_link);
        notAdminLink = (TextView) findViewById(R.id.not_admin_register_link);
        loadingBar = new ProgressDialog(this);

        inputName.setText("soolking");
        inputEmail.setText("soolking@mail.com");
        inputPassword.setText("banana");
        inputPhone.setText("0123456100");
        inputCountry.setText("Belgique");
    }

    private void createUserAccount() {
        String name = inputName.getText().toString().trim(); //trim() remove les espaces du debut et fin du string
        String email = inputEmail.getText().toString().trim();
        String phone = inputPhone.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        String country = inputCountry.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            inputName.setError(getString(R.string.empty_name));
            inputName.requestFocus();
        }
        else if (TextUtils.isEmpty(email)){
            inputEmail.setError(getString(R.string.empty_email));
            inputEmail.requestFocus();
        }
        else if (!isEmailValid(email)){
            inputEmail.setError(getString(R.string.invalid_mail));
            inputEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            inputPassword.setError(getString(R.string.empty_password));
            inputPassword.requestFocus();
        }
        else if (password.length() < 6){
            inputPassword.setError(getString(R.string.not_enough_password));
            inputPassword.requestFocus();
        }
        else if (TextUtils.isEmpty(phone)) {
            inputPhone.setError(getString(R.string.empty_phone));
            inputPhone.requestFocus();
        }
        else if (phone.length() != 10) {
            inputPhone.setError(getString(R.string.not_enough_phone));
            inputPhone.requestFocus();
        }
        else if (TextUtils.isEmpty(country)) {
            inputCountry.setError(getString(R.string.empty_country));
            inputCountry.requestFocus();
        }
        else{
            loadingBarRegister();
            registerUser(name, email, password, phone, country);
        }
    }

    private void registerUser(String name, String email, String password, String phone, String country) {
        //String passwordCrypte = CryptePassword.encrypt(passwordEditText.getText().toString());
        Users user = new Users(name, email, password, phone, country);

        boolean isExist = user.checkMailUser();
        if (!isExist){
            boolean isInsertUser = user.insertUser();

            //si insert a fonctionné
            if (isInsertUser){
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.registrationUser_msg), Toast.LENGTH_SHORT).show();
                sendUserToLogin();
            }
            else{
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.error_register_msg), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else{
            loadingBar.dismiss();
            Toast.makeText(RegisterActivity.this, getString(R.string.already_registered_msg), Toast.LENGTH_SHORT).show();
        }

    }

    private void createAdminAccount() {

        String name = inputName.getText().toString().trim(); //trim() remove les espaces du debut et fin du string
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            inputName.setError(getString(R.string.empty_name));
            inputName.requestFocus();
        }
        else if (TextUtils.isEmpty(email)){
            inputEmail.setError(getString(R.string.empty_email));
            inputEmail.requestFocus();
        }
        else if (!isEmailValid(email)){
            inputEmail.setError(getString(R.string.invalid_mail));
            inputEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            inputPassword.setError(getString(R.string.empty_password));
            inputPassword.requestFocus();
        }
        else if (password.length() < 6){
            inputPassword.setError(getString(R.string.not_enough_password));
            inputPassword.requestFocus();
        }
        else{
                loadingBarRegister();
                registerAdmin(name, email, password);
        }

    }

    private void registerAdmin(String name, String mail, String pwd) {
        //String passwordCrypte = CryptePassword.encrypt(passwordEditText.getText().toString());
        Admins admin = new Admins(name, mail, pwd);

        boolean isExist = admin.checkMailAdmin();
        if (!isExist){
            boolean isInsertAdmin = admin.insertAdmin();

            //si insert a fonctionné
            if (isInsertAdmin){
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.registrationAdmin_msg), Toast.LENGTH_SHORT).show();
                sendUserToLogin();
            }
            else{
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this, getString(R.string.error_register_msg), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        else{
            loadingBar.dismiss();
            Toast.makeText(RegisterActivity.this, getString(R.string.already_registered_msg), Toast.LENGTH_SHORT).show();
        }
    }
    
    //direction vers login Activity
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
