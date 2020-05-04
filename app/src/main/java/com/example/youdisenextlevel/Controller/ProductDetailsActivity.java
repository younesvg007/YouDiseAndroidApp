package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Carts;
import com.example.youdisenextlevel.Model.Database.ImageManage;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailsActivity extends AppCompatActivity {

    private String idProduct, email, nameC, categoryC, price,imageKey;
    private TextView closeProduct, nameProductDetail, describeProductDetail, priceProductDetail;
    private ImageView imageProductDetail;
    private ElegantNumberButton quantityBtn;
    private Button addCart;
    private Products product;
    private Users user;
    private int priceI, idProductInt, quantity, idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        initField();

        idProduct = getIntent().getExtras().get("id").toString();
        email = getIntent().getExtras().get("email").toString();
        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        getInfosProduct();
        //Toast.makeText(this, nameP, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, quantityBtn.getNumber(), Toast.LENGTH_SHORT).show();

        getInfosUser();
        closeProduct.setOnClickListener(v -> finish());

        addCart.setOnClickListener(v -> addToCart());
    }

    private void addToCart() {
        try {
            priceI = Integer.parseInt(price);
            idProductInt = Integer.parseInt(idProduct);
            quantity = Integer.parseInt(quantityBtn.getNumber());
            Carts cart = new Carts(nameC, categoryC, imageKey, quantity, priceI, idProductInt, idUser);
            boolean isExist = cart.checkProductUser();
            //Toast.makeText(this, isExist+"", Toast.LENGTH_SHORT).show();
            if (!isExist){
                boolean isAdded = cart.insertCart();
                if (isAdded){
                    sendNotification();
                    //Toast.makeText(this, getString(R.string.added_to_cart), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, quantityBtn.getNumber(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(this, getString(R.string.error_addproduct), Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(this, getString(R.string.already_exist_in_cart), Toast.LENGTH_SHORT).show();
            }

        }
        catch(Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }

    }

    private void sendNotification() {
        String msg = "Un nouveau produit à été ajouté dans le panier";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ProductDetailsActivity.this, Myapplication.ID_CHANNEL)
                .setSmallIcon(R.drawable.cart)
                .setContentTitle("Succès")
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        notificationManager.notify(1, builder.build());
    }

    private void getInfosUser() {
        try {
            user = new Users();
            user.setEmail(email);
            Cursor cursor = user.getDataUser();
            if (cursor.getCount() != 0){
                while (cursor.moveToNext()){
                    //ne pas utiliser getter et setter sinon app crash
                    idUser = cursor.getInt(cursor.getColumnIndex(YouDise.USERS_COL_ID));
                }
            }
        }
        catch (Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }
    }

    private void getInfosProduct() {
        try {
            product = new Products();
            product.setIdProduct(idProduct);
            Cursor cursor = product.getDataProduct();
            if (cursor.getCount() != 0){
                while (cursor.moveToNext()){
                    //ne pas utiliser getter et setter sinon app crash
                    nameC = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_NAME));
                    String nameFull = idProduct + ". " + nameC;
                    categoryC = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_CATEGORY));
                    String describeP = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_DESCRIPTION));
                    price = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_PRICE));
                    String priceFull = price + " €";
                    imageKey = cursor.getString(cursor.getColumnIndex(YouDise.PRODUCTS_COL_IMAGE));
                    ImageManage.getImage(imageKey, imageProductDetail);

                    nameProductDetail.setText(nameFull);
                    describeProductDetail.setText(describeP);
                    priceProductDetail.setText(priceFull);
                }
            }
        }
        catch (Exception e){
            String msgError = e.getMessage();
            Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();
        }
    }

    private void initField() {
        closeProduct = (TextView) findViewById(R.id.close_product_detail);
        nameProductDetail = (TextView) findViewById(R.id.name_product_detail);
        describeProductDetail = (TextView) findViewById(R.id.description_product_detail);
        priceProductDetail = (TextView) findViewById(R.id.price_product_detail);
        imageProductDetail = (ImageView) findViewById(R.id.image_product_detail);
        quantityBtn = (ElegantNumberButton) findViewById(R.id.quantity_product_btn);
        addCart = (Button) findViewById(R.id.add_cart_btn);
    }
}
