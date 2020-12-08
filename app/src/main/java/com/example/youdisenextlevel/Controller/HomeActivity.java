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

        //recuperation de la donnée (adresse email) de l'utilisateur
        email = getIntent().getExtras().get("email").toString();

        //Mise en place du recycler View
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(getDataSet()); //recoit l'arraylist produit
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<Products> getDataSet() {

        //declaration Arraylist pour stocker tous les produits qui viennent de la BDD
        ArrayList results = new ArrayList<Products>();

        //declaration Arraylist pour stocker les id du produit
        idList = new ArrayList<>();

        // recoit curseur qui permet de recup les produits de la BDD
        Cursor cursor = Myapplication.getYdDatabaseAdapter().getAllProduct();

        //c'est l'index pour chaque produit l'arraylist car on peut pas utiliser l'id du produit parce que il commence a 1.
        int index = 0;

        // si curseur de la table PRoduit commence par la premiere ligne
        if (cursor.moveToFirst()) {
            //parcours de toutes les lignes de la table Produits
            do {
                // Chaque variable recoit une donnee de la table Produit
                int id = cursor.getInt(cursor.getColumnIndex(YouDise.PRODUCTS_COL_ID));
                String name = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_NAME));
                String category = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_CATEGORY));
                String desc = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_DESCRIPTION));
                int price = cursor.getInt(cursor.getColumnIndex(YouDise.PRODUCTS_COL_PRICE));
                String dateTime = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_DATETIME));
                String image = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_IMAGE));

                products = new Products(id + ": " + name, category, desc, price, dateTime, image);
                results.add(index, products);

                String idProduct = String.valueOf(id);
                idList.add(idProduct);
                ++index;

            } while (cursor.moveToNext());
        }

        //pour afficher les produit les plus recent d'abord
        Collections.reverse(results);
        //vu que cest les plus recent dabord afficher, on inverse les id aussi pour eviter la confusion du systeme lors du click sur chaque item
        Collections.reverse(idList);
        return results;
    }

    //permet de desactiver le "Remember me"
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

    //lorsqu'on clique sur un item produit
    @Override
    protected void onResume() {
        super.onResume();
        ((MyRecyclerViewAdapter) mAdapter).setOnItemClickListener((position, v) -> {

            //transfert id du produit et email du user vers Product Detail Activity
            Intent profileIntent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
            String idProduct = idList.get(position);
            profileIntent.putExtra("id", idProduct);
            profileIntent.putExtra("email", email);
            startActivity(profileIntent);
        });
    }

    //permet de lier le widget Option menu avec la partie logique (java)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_profil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //presente les different option. Ici il ya que le profil
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profil_item){
            sendUserToProfile();
        }
        return super.onOptionsItemSelected(item);
    }
}
