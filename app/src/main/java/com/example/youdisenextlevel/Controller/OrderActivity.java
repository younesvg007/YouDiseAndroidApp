package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Orders;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class OrderActivity extends AppCompatActivity {

    private String mail, totalAmount;
    private TextView cancelOrder, adressOrder, countryOrder, bankCardOrder;
    private Button orderBtn;
    private Users user = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        mail = getIntent().getExtras().get("mail").toString();
        totalAmount = getIntent().getExtras().get("totalprice").toString();

        user.setEmail(mail);

        initField();

        cancelOrder.setOnClickListener(v -> finish());

        orderBtn.setOnClickListener(v -> validateOrder());
    }

    private void validateOrder() {
        String adress = adressOrder.getText().toString().trim(); //trim() remove les espaces du debut et fin du string
        String country = countryOrder.getText().toString().trim();
        String bankCard = bankCardOrder.getText().toString().trim();

        if (TextUtils.isEmpty(adress)){
            adressOrder.setError(getString(R.string.empty_adress));
            adressOrder.requestFocus();
        }
        else if (TextUtils.isEmpty(country)){
            countryOrder.setError(getString(R.string.empty_country));
            countryOrder.requestFocus();
        }
        else if (TextUtils.isEmpty(bankCard)){
            bankCardOrder.setError(getString(R.string.empty_bank));
            bankCardOrder.requestFocus();
        }
        else if (TextUtils.isEmpty(bankCard)){
            bankCardOrder.setError(getString(R.string.empty_bank));
            bankCardOrder.requestFocus();
        }
        else if (bankCard.length() != 16){
            bankCardOrder.setError(getString(R.string.invalid_bank_card));
            bankCardOrder.requestFocus();
        }
        else{

            //methode permetant d'inserer les donnees de la commande dans la BDD
            addOrder(adress, country, bankCard);
        }
    }

    private void addOrder(String adress, String country, String bankCard) {

        getDataUser();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dateTime =  currentDate.format(calendar.getTime());

        try {
            // appel a la requete qui va inserer une commandde dans la Table ORDERS
            boolean isAdded = Myapplication.getYdDatabaseAdapter().insertOrder(user.getUsername(), user.getEmail(), adress, country, bankCard, totalAmount, dateTime, user.getIdUser());
            Toast.makeText(this, isAdded+"", Toast.LENGTH_SHORT).show();
            if (isAdded){
                Toast.makeText(this, getString(R.string.order_sent), Toast.LENGTH_SHORT).show();
                sendNotification();
                finish();
            }
            else{
                Toast.makeText(this, getString(R.string.error_addproduct), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }

    }

    private void sendNotification() {
        String msg = "Votre colis vous sera envoyé. Au plaisir de vous revoir";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(OrderActivity.this, Myapplication.ID_CHANNEL)
                .setSmallIcon(R.drawable.orders)
                .setContentTitle("Commande confirmé !")
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        notificationManager.notify(2, builder.build());
    }

    private void getDataUser() {
        try {
            Cursor cursor = user.getDataUser();
            if (cursor.getCount() != 0){
                while (cursor.moveToNext()){

                    int idUser = cursor.getInt(cursor.getColumnIndex(YouDise.USERS_COL_ID));
                    String name = cursor.getString(cursor.getColumnIndex(YouDise.USERS_COL_USERNAME));
                    String country = cursor.getString(cursor.getColumnIndex(YouDise.USERS_COL_COUNTRY));

                    user.setIdUser(idUser);
                    user.setUsername(name);
                    user.setCountry(country);

                }
            }
        }
        catch (Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }
    }

    private void initField() {
        cancelOrder = (TextView) findViewById(R.id.close_order);
        adressOrder = (TextView) findViewById(R.id.adress_order);
        countryOrder = (TextView) findViewById(R.id.country_order);
        bankCardOrder = (TextView) findViewById(R.id.bank_card_order);
        orderBtn = (Button) findViewById(R.id.valid_order_btn);

    }
}
