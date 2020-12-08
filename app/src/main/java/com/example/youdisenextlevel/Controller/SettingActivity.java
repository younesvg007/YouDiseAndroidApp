package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;

public class SettingActivity extends AppCompatActivity {

    private TextView closeSetting, updateSetting;
    private EditText nameNew, emailNew, passwordNew;
    private ProgressDialog loadingBar;
    private Users user;
    private String oldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initField();

        oldEmail = getIntent().getExtras().get("email").toString();
        Toast.makeText(this, oldEmail, Toast.LENGTH_SHORT).show();

        //clique sur annuler pour revenir au profil
        closeSetting.setOnClickListener(v -> finish());

        //clique sur Enregistrer pour mettre a jour
        updateSetting.setOnClickListener(v -> validationUser());
    }

    private void initField() {
        closeSetting = (TextView) findViewById(R.id.close_setting);
        updateSetting = (TextView) findViewById(R.id.save_setting);
        nameNew = (EditText) findViewById(R.id.name_profile_setting);
        emailNew = (EditText) findViewById(R.id.email_profile_setting);
        passwordNew = (EditText) findViewById(R.id.password_profile_setting);
        loadingBar = new ProgressDialog(this);

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //validation des données entrées
    private void validationUser() {
        String name = nameNew.getText().toString().trim(); //trim() remove les espaces du debut et fin du string
        String newEmail = emailNew.getText().toString().trim();
        String password = passwordNew.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            nameNew.setError(getString(R.string.empty_name));
            nameNew.requestFocus();
        }
        else if (TextUtils.isEmpty(newEmail)){
            emailNew.setError(getString(R.string.empty_email));
            emailNew.requestFocus();
        }
        else if (!isEmailValid(newEmail)){
            emailNew.setError(getString(R.string.invalid_mail));
            emailNew.requestFocus();
        }
        else if (newEmail.equals(oldEmail)){
            emailNew.setError(getString(R.string.already_registered_msg));
            emailNew.requestFocus();
        }
        else if (TextUtils.isEmpty(password)){
            passwordNew.setError(getString(R.string.empty_password));
            passwordNew.requestFocus();
        }
        else if (password.length() < 6){
            passwordNew.setError(getString(R.string.not_enough_password));
            passwordNew.requestFocus();
        }
        else{
            loadingBarUpdate();
            updateUser(name, newEmail, password);
        }
    }

    //permet de mettre a jour les données du user dans La BDD
    private void updateUser(String name, String newEmail, String password) {

        try {
            boolean isUpdated = Myapplication.getYdDatabaseAdapter().updateUser(name, newEmail, password, oldEmail);
            if (isUpdated){
                loadingBar.dismiss();
                Toast.makeText(this, getString(R.string.user_updated), Toast.LENGTH_SHORT).show();
                finish();
                Intent loginIntent = new Intent(SettingActivity.this, loginActivity.class);
                startActivity(loginIntent);
            }
        }
        catch(Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }
    }

    private void loadingBarUpdate() {
        loadingBar.setTitle(getString(R.string.time_update_account));
        loadingBar.setMessage(getString(R.string.hold_on_msg));
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
    }

}
