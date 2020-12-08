package com.example.youdisenextlevel.ViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youdisenextlevel.Model.Carts;
import com.example.youdisenextlevel.Model.Database.ImageManage;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.R;

import java.util.ArrayList;

public class CartRecyclerViewAdapter  extends RecyclerView
        .Adapter<CartRecyclerViewAdapter
        .DataHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Carts> mDataset;
    private static MyClickListener myClickListener;

    public static class DataHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView nameCart, categoryCart, quantityCart, priceCart;
        ImageView imageCart;

        // liason tous les widegts de l'interface utilisateur(xml) avec la partie logique de java
        public DataHolder(View itemView) {
            super(itemView);
            nameCart = (TextView) itemView.findViewById(R.id.name_cart_item);
            categoryCart = (TextView) itemView.findViewById(R.id.category_cart_item);
            quantityCart = (TextView) itemView.findViewById(R.id.quantity_cart_item);
            priceCart = (TextView) itemView.findViewById(R.id.price_cart_item);
            imageCart = (ImageView) itemView.findViewById(R.id.image_cart_item);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public CartRecyclerViewAdapter(ArrayList<Carts> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_row, parent, false);
        DataHolder dataObjectHolder = new DataHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        // afficher les données sur le cardView
        holder.nameCart.setText(mDataset.get(position).getNameC());
        holder.categoryCart.setText(mDataset.get(position).getCategoryC());
        String price = mDataset.get(position).getPrice() + " €";
        holder.priceCart.setText(price);
        String quantity = "Quantité: " + mDataset.get(position).getQuantity();
        holder.quantityCart.setText(quantity);

        //recup image de Firebase Storage
        String image = mDataset.get(position).getImage();
        ImageManage.getImage(image, holder.imageCart);
    }

    public void addItem(Carts panier, int index) {
        mDataset.add(index, panier);
        notifyItemInserted(index);
    }

    //supprime un element du recycler View
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    //recup le nombre total de element du recylcer view
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
