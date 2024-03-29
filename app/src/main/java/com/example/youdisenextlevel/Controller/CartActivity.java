package com.example.youdisenextlevel.Controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youdisenextlevel.Application.Myapplication;
import com.example.youdisenextlevel.Model.Carts;
import com.example.youdisenextlevel.Model.Database.YouDise;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.Model.Users;
import com.example.youdisenextlevel.R;
import com.example.youdisenextlevel.ViewHolder.CartRecyclerViewAdapter;
import com.example.youdisenextlevel.ViewHolder.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class CartActivity extends AppCompatActivity {

    private String email, idUserS, priceBtn;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Carts cart;
    private Users user;
    private int idUser, priceTotal;
    private ArrayList<Integer> cartList;
    private ArrayList<Integer> idList;
    private ArrayList<Carts> results;
    private Button toCheckout;
    private TextView closeCart, refreshCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        email = getIntent().getExtras().get("email").toString();

        initField();
        initRecyclerView();

        priceBtn = priceTotal + " €"; // prix total du panier en euro
        toCheckout.setText(priceBtn); //affiche le prix total du panier

        closeCart.setOnClickListener(v -> finish());

        refreshCart.setOnClickListener(v -> clearBasket());

        //cliquer sur le bouton affichant le prix pour ensuite se diriger vers le formulaire de commande
        toCheckout.setOnClickListener(v -> {

            if (!toCheckout.getText().equals("0 €")){

                sendNotification(); //methode permettant d'envoyer une Notification

                sendUserToOrder(); // direction vers le fomulaire de commande => Order activity
            }
            else{
                //si le panier est vide => impossible de passer une commande
                Toast.makeText(CartActivity.this, getString(R.string.impossible_to_checkout ), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CartRecyclerViewAdapter(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void sendNotification() {
        String msg = "Le prix Total est de "+toCheckout.getText()+ ". Passez votre commande.";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(CartActivity.this, Myapplication.ID_CHANNEL)
                .setSmallIcon(R.drawable.add_cart)
                .setContentTitle("Panier validé !")
                .setContentText(msg)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE
        );
        notificationManager.notify(0, builder.build());
    }

    private void sendUserToOrder() {
        Intent orderIntent = new Intent(CartActivity.this, OrderActivity.class);
        orderIntent.putExtra("mail", email);
        orderIntent.putExtra("totalprice", toCheckout.getText()); // transfert du montant total du panier pour la commande
        startActivity(orderIntent);
    }

    // methode permettant de vider tout le panier
    private void clearBasket() {

        Carts panier = new Carts();
        panier.setIdUser(idUser);
        Toast.makeText(this, results.size()+"", Toast.LENGTH_SHORT).show();
        try {
            Integer countRowsDeleted = panier.deleteAllCartOfUser(); // recoit un integer pour verifier si la requete a supprimer toute la table panier
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

    private void initField() {
        toCheckout = (Button) findViewById(R.id.checkout_btn);
        closeCart = (TextView) findViewById(R.id.close_cart_tv);
        refreshCart = (TextView) findViewById(R.id.refresh_cart_tv);
    }

    //envoit arraylist contenant tous les element du panier
    private ArrayList<Carts> getDataSet() {
        results = new ArrayList<>();
        cartList = new ArrayList<>();
        idList = new ArrayList<>();
        getIdUser();

        Cursor cursor = Myapplication.getYdDatabaseAdapter().getCartOfUser(idUserS);
        int index = 0;
        int idCartItem = 1;
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(YouDise.CART_COL_ID));
                String name =  id + ". " + cursor.getString(cursor.getColumnIndex(YouDise.CART_COL_NAME));
                String category = cursor.getString(cursor.getColumnIndex(YouDise.CART_COL_CATEGORY));
                String image = cursor.getString(cursor.getColumnIndex(YouDise.CART_COL_IMAGE));
                int quantity = cursor.getInt(cursor.getColumnIndex(YouDise.CART_COL_QUANTITY));
                int price = cursor.getInt(cursor.getColumnIndex(YouDise.CART_COL_PRICE));
                int priceItemTotal = quantity * price;

                cart = new Carts();
                cart.setNameC(name);
                cart.setCategoryC(category);
                cart.setImage(image);
                cart.setQuantity(quantity);
                cart.setPrice(priceItemTotal);

                results.add(index, cart);
                idList.add(id);
                cartList.add(idCartItem);

                ++index;
                ++idCartItem;
                priceTotal += priceItemTotal;

            } while (cursor.moveToNext());
        }

        Collections.reverse(results);
        Collections.reverse(cartList);
        Collections.reverse(idList);

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

    //lorque on clique sur un item, un alert dialogue souvre pour confirmer la suppression du produit du panier
    @Override
    protected void onResume() {
        super.onResume();
        ((CartRecyclerViewAdapter) mAdapter).setOnItemClickListener((position, v) -> {

            //creation d'un alert dialog
            AlertDialog.Builder dialogDelete = new AlertDialog.Builder(CartActivity.this);
            dialogDelete.setTitle(getString(R.string.deletion_product) + cartList.get(position));
            dialogDelete.setMessage(getString(R.string.sure_to_delete));

            //si oui , supression du produit du panier
            dialogDelete.setPositiveButton("Oui", (dialog, which) -> {

                Carts carts = new Carts();
                String idCart = idList.get(position)+"";
                carts.setIdCart(idCart);
                try {

                    // appel a la requete qui va supprimer uniquement le produit cliquer en utilisant id du produit et id de luser
                    Integer rowDeleted = carts.deleteSingleCart();
                    if (rowDeleted > 0){

                        Toast.makeText(CartActivity.this, getString(R.string.product_deleted), Toast.LENGTH_SHORT).show();

                        //mise a jour du prix en temps reel (diminution des qu un produit est supprimer)
                        priceTotal -= results.get(position).getPrice();
                        priceBtn = priceTotal + " €";
                        toCheckout.setText(priceBtn);

                        //cela supprimera un item va falloir rafraichir pour revenir a la page
                        ((CartRecyclerViewAdapter) mAdapter).deleteItem(position);
                        if (priceTotal == 0){
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(CartActivity.this, getString(R.string.refresh_activity), Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e){
                    String msgError = e.getMessage();
                    Toast.makeText(CartActivity.this, msgError, Toast.LENGTH_SHORT).show();
                }
            });

            dialogDelete.setNegativeButton("Non", (dialog, which) -> dialog.dismiss());
            dialogDelete.show();
        });

    }

}
