package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Carts;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Orders;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;
import com.example.youdisenextlevel.ViewHolder.CartRecyclerViewAdapter;
import com.example.youdisenextlevel.ViewHolder.OrderRecyclerViewAdapter;
import com.example.youdisenextlevel.ViewHolder.ProductRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class OrderListActivity extends AppCompatActivity {

    private String email, idUserS;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Orders order;
    private Users user;
    private int idUser;
    private ArrayList<Orders> results;
    private TextView cancelOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        email = getIntent().getExtras().get("mail").toString();
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        initField();
        initRecyclerView();

        cancelOrder.setOnClickListener(v -> finish());
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list_order_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrderRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initField() {
        cancelOrder = (TextView) findViewById(R.id.cancel_order_tv);
    }

    private ArrayList<Orders> getDataSet() {
        results = new ArrayList<>();
        getIdUser();

        //recupere seulement les commandes qui appartiennent à l'user
        Cursor cursor = Myapplication.getYdDatabaseAdapter().getOrderOfUser(idUserS);
        int index = 0;
        int indexOrder = 1; // pour le
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String adress =  cursor.getString(cursor.getColumnIndex(YouDise.CHECKOUT_COL_ADRESS));
                String country = cursor.getString(cursor.getColumnIndex(YouDise.CHECKOUT_COL_COUNTRY));
                String cardBank = cursor.getString(cursor.getColumnIndex(YouDise.CHECKOUT_COL_CARDBANK));
                String montantTotal = cursor.getString(cursor.getColumnIndex(YouDise.CHECKOUT_COL_TOTALAMOUNT));
                String dateTime = cursor.getString(cursor.getColumnIndex(YouDise.CHECKOUT_COL_DATETIME));

                order = new Orders();
                order.setIdOrder(indexOrder + "");
                order.setAdress(adress);
                order.setCountry(country);
                order.setBankCard(cardBank);
                order.setTotalAmount(montantTotal);
                order.setDateTime(dateTime);

                results.add(index, order);

                ++index;
                ++indexOrder;

            } while (cursor.moveToNext());
        }

        Collections.reverse(results);

        return results;
    }

    private void getIdUser() {
        try {
            user = new Users();
            user.setEmail(email);

            Cursor cursor = user.getDataUser();
            if (cursor.getCount() != 0){
                while (cursor.moveToNext()){
                    idUser = cursor.getInt(cursor.getColumnIndex(YouDise.USERS_COL_ID));
                    idUserS = String.valueOf(idUser);
                }
            }
        }
        catch (Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((OrderRecyclerViewAdapter) mAdapter).setOnItemClickListener((position, v) -> {

        });
    }
}
