package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Carts;
import com.example.youdisenextlevel.Model.Database.DataObject;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.R;
import com.example.youdisenextlevel.ViewHolder.CartRecyclerViewAdapter;
import com.example.youdisenextlevel.ViewHolder.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class CartActivity extends AppCompatActivity {

    private String email;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Carts carts;
    private ArrayList<String> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        email = getIntent().getExtras().get("email").toString();

        mRecyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CartRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ((CartRecyclerViewAdapter) mAdapter).setOnItemClickListener(new CartRecyclerViewAdapter
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(CartActivity.this, "cest clair", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private ArrayList<Products> getDataSet() {
        ArrayList results = new ArrayList<Products>();
        idList = new ArrayList<>();

        Cursor cursor = Myapplication.getYdDatabaseAdapter().getAllProduct();
        int index = 0;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_NAME));

                //Toast.makeText(this, image, Toast.LENGTH_SHORT).show();

                //products = new Products(id + ": " + name, desc, price, image);
                Products products = new Products();
                products.setName(name);
                results.add(index, products);
                //products.setId(id);
                //Toast.makeText(this, products.getId()+"", Toast.LENGTH_SHORT).show();
                ++index;

            } while (cursor.moveToNext());
        }

        //pour afficher produit les plus recent d'abord

        return results;
    }
}
