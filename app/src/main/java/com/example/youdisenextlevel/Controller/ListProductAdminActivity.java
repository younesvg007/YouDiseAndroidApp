package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Carts;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.R;
import com.example.youdisenextlevel.ViewHolder.CartRecyclerViewAdapter;
import com.example.youdisenextlevel.ViewHolder.MyRecyclerViewAdapter;
import com.example.youdisenextlevel.ViewHolder.ProductRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class ListProductAdminActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Products products;
    private ArrayList<String> idList;
    private Button totalProduct;
    private String productFull;
    private int quantityProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product_admin);

        initRecyclerView();
        quantityProduct = getDataSet().size();

        productFull = "Total Produits : "+ quantityProduct;
        totalProduct.setText(productFull);

        //Toast.makeText(this, , Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        totalProduct = (Button) findViewById(R.id.total_product_btn);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_product_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ProductRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
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

                products = new Products(id + ": " + name, category, desc, price, dateTime, image);
                results.add(index, products);

                String idProduct = String.valueOf(id);
                idList.add(idProduct);
                ++index;

            } while (cursor.moveToNext());
        }

        //pour afficher produit les plus recent d'abord
        Collections.reverse(results);
        Collections.reverse(idList);

        return results;
    }

    private void sendAdminToCategory() {
        Intent mainIntent = new Intent(ListProductAdminActivity.this, CategoryAdminActivity.class);
        startActivity(mainIntent);
    }

    private void sendAdminToMain() {
        Intent mainIntent = new Intent(ListProductAdminActivity.this, MainActivity.class);
        startActivity(mainIntent);
        Toast.makeText(this, getString(R.string.logout_User_msg), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((ProductRecyclerViewAdapter) mAdapter).setOnItemClickListener(new ProductRecyclerViewAdapter.MyClickListener(){
            @Override
            public void onItemClick(int position, View v) {
                AlertDialog.Builder dialogDelete = new AlertDialog.Builder(ListProductAdminActivity.this);
                dialogDelete.setTitle(getString(R.string.deletion_product));
                dialogDelete.setMessage(getString(R.string.sure_to_delete));
                dialogDelete.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Products product = new Products();
                        String idProduct = idList.get(position);
                        product.setIdProduct(idProduct);
                        //Toast.makeText(ListProductAdminActivity.this, product.getIdProduct(), Toast.LENGTH_SHORT).show();
                        try {
                            Integer rowDeleted = product.deleteSingleProduct();
                            if (rowDeleted > 0){
                                Toast.makeText(ListProductAdminActivity.this, "Produit supprimer", Toast.LENGTH_SHORT).show();
                                //Toast.makeText(CartActivity.this, results.get(position).getPrice()+"", Toast.LENGTH_SHORT).show();

                                //cela supprimera un item va falloir rafraichir pour revenir a la page
                                ((ProductRecyclerViewAdapter) mAdapter).deleteItem(position);
                                --quantityProduct;

                                productFull = "Total Produits : "+ quantityProduct;
                                totalProduct.setText(productFull);
                            }
                            else{
                                Toast.makeText(ListProductAdminActivity.this, getString(R.string.refresh_activity), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception e){
                            String msgError = e.getMessage();
                            Toast.makeText(ListProductAdminActivity.this, msgError, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialogDelete.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialogDelete.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add_product_item:
                sendAdminToCategory();
                break;
            case R.id.erase_list_product_item:
                    clearListProduct();
                break;
            case R.id.logout_item:
                sendAdminToMain();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void clearListProduct() {

        Products produit = new Products();
        try {
            Integer countRowsDeleted = produit.deleteAllProduct();
            if (countRowsDeleted > 0){
                Toast.makeText(this, getString(R.string.empty_basket), Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(this, getString(R.string.already_empty_cart), Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }
    }
}
