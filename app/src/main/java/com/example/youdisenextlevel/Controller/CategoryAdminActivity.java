package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.youdisenextlevel.R;

public class CategoryAdminActivity extends AppCompatActivity {

    private ImageView mobiles, watches, laptops, headphones;
    private ImageView sweathers, femaleDress, sport, tshirts;
    private ImageView shoes, hats, pursesBag, glasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin);

        initViews();

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Smartphones");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Montres");
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "PC portable");
                startActivity(intent);
            }
        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Casques - Ecouteurs");
                startActivity(intent);
            }
        });

        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Manteaux");
                startActivity(intent);
            }
        });

        femaleDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Vetements Feminins");
                startActivity(intent);
            }
        });

        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Training");
                startActivity(intent);
            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "T-shirts");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Chaussures");
                startActivity(intent);
            }
        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Chapeaux - Casquette");
                startActivity(intent);
            }
        });

        pursesBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Sacs à mains");
                startActivity(intent);
            }
        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
                intent.putExtra("category", "Lunettes");
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        mobiles = (ImageView) findViewById(R.id.mobile1);
        watches = (ImageView) findViewById(R.id.watches1);
        laptops = (ImageView) findViewById(R.id.laptops1);
        headphones = (ImageView) findViewById(R.id.headphones1);

        sweathers = (ImageView) findViewById(R.id.sweathers1);
        femaleDress = (ImageView) findViewById(R.id.female_dress1);
        sport = (ImageView) findViewById(R.id.sport1);
        tshirts = (ImageView) findViewById(R.id.tshirts1);

        shoes = (ImageView) findViewById(R.id.shoes1);
        hats = (ImageView) findViewById(R.id.hats1);
        pursesBag = (ImageView) findViewById(R.id.purse_bag_wallet1);
        glasses = (ImageView) findViewById(R.id.glasses1);
    }
}
