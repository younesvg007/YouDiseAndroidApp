package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youdisenextlevel.R;

import org.w3c.dom.Text;

public class CategoryAdminActivity extends AppCompatActivity {

    private ImageView mobiles, watches, laptops, headphones;
    private ImageView sweathers, femaleDress, sport, tshirts;
    private ImageView shoes, hats, pursesBag, glasses;
    private TextView getBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_admin);

        //initialisation des widgets avec la partie logique
        initViews();

        //retour
        getBack.setOnClickListener(v -> finish());

        //cliquer sur la categories des smartphones
        mobiles.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Smartphones");
            startActivity(intent);
        });

        //cliquer sur la categories des montres
        watches.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Montres");
            startActivity(intent);
        });

        //cliquer sur la categories des pc
        laptops.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "PC portable");
            startActivity(intent);
        });

        //cliquer sur la categories des casques/ecouteurs
        headphones.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Casques - Ecouteurs");
            startActivity(intent);
        });

        //cliquer sur la categories des manteaux
        sweathers.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Manteaux");
            startActivity(intent);
        });

        //cliquer sur la categories des vetements pour femmes
        femaleDress.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Vetements Feminins");
            startActivity(intent);
        });

        //cliquer sur la categories des vetement sport
        sport.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Training");
            startActivity(intent);
        });

        //cliquer sur la categories des tshirt
        tshirts.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "T-shirts");
            startActivity(intent);
        });

        //cliquer sur la categories des chaussures
        shoes.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Chaussures");
            startActivity(intent);
        });

        //cliquer sur la categories des chapeaux
        hats.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Chapeaux - Casquette");
            startActivity(intent);
        });

        //cliquer sur la categories des sacs
        pursesBag.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Sacs à mains");
            startActivity(intent);
        });

        //cliquer sur la categories des lunettes
        glasses.setOnClickListener(v -> {
            Intent intent = new Intent(CategoryAdminActivity.this, AddProductActivity.class);
            intent.putExtra("category", "Lunettes");
            startActivity(intent);
        });
    }

    private void initViews() {
        getBack = (TextView) findViewById(R.id.close_admin);

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
