package com.example.youdisenextlevel.Controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.ViewHolder.MyRecyclerViewAdapter;
import com.example.youdisenextlevel.R;

import java.util.ArrayList;
import java.util.Collections;

public class HomeActivity extends AppCompatActivity {

    private String email;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Products products;
    private ArrayList<String> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        email = getIntent().getExtras().get("email").toString();

        initFields();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        String quantityProduct = getDataSet().size() + "";
        Toast.makeText(this, quantityProduct + " Produits au Total", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Products> getDataSet() {

        ArrayList results = new ArrayList<Products>();
        idList = new ArrayList<>();

        Cursor cursor = Myapplication.getYdDatabaseAdapter().getAllProduct();
        int index = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(YouDise.PRODUCTS_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_NAME));
                String category = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_CATEGORY));
                String desc = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_DESCRIPTION));
                int price = cursor.getInt(cursor.getColumnIndex(YouDise.PRODUCTS_COL_PRICE));
                String dateTime = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_DATETIME));
                String image = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_IMAGE));
                //Toast.makeText(this, image, Toast.LENGTH_SHORT).show();

                //products = new Products(id + ": " + name, desc, price, image);
                products = new Products(id + ": " + name, category, desc, price, dateTime, image);
                results.add(index, products);
                //products.setId(id);

                String idProduct = String.valueOf(id);
                idList.add(idProduct);
                //Toast.makeText(this, products.getId()+"", Toast.LENGTH_SHORT).show();
                ++index;

            } while (cursor.moveToNext());
        }

        //pour afficher produit les plus recent d'abord
        Collections.reverse(results);
        Collections.reverse(idList);

        return results;
    }

    private void initFields() {
        //logoutBtn = (Button) findViewById(R.id.logout_btn);
    }

    private void cancelRemember() {
        SharedPreferences prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("remember", "false");
        editor.apply();
        Toast.makeText(HomeActivity.this, getString(R.string.logout_User_msg), Toast.LENGTH_SHORT).show();
    }

    private void sendUserToProfile() {
        Intent profileIntent = new Intent(HomeActivity.this, ProfileActivity.class);
        profileIntent.putExtra("mail", email);
        startActivity(profileIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener((position, v) -> {

            Intent profileIntent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
            String idProduct = idList.get(position);
            profileIntent.putExtra("id", idProduct);
            profileIntent.putExtra("email", email);
            startActivity(profileIntent);
            Toast.makeText(HomeActivity.this, idList.get(position)+"", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profil_item){
            sendUserToProfile();
        }
        return super.onOptionsItemSelected(item);
    }
}
