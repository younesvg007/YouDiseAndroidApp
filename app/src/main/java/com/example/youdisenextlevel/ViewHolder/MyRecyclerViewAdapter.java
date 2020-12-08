package com.example.youdisenextlevel.ViewHolder;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youdisenextlevel.Controller.HomeActivity;
import com.example.youdisenextlevel.Controller.MainActivity;
import com.example.youdisenextlevel.Model.Database.ImageManage;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.R;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {

    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private ArrayList<Products> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView namePro, categoryPro, descPro, pricePro, datePro;
        ImageView imagePro;

        // liason tous les widegts de l'interface utilisateur(xml) avec la partie logique de java
        public DataObjectHolder(View itemView) {
            super(itemView);

            namePro = (TextView) itemView.findViewById(R.id.name_product_item);
            categoryPro = (TextView) itemView.findViewById(R.id.category_product_item);
            descPro = (TextView) itemView.findViewById(R.id.description_product_item);
            pricePro = (TextView) itemView.findViewById(R.id.price_product_item);
            datePro = (TextView) itemView.findViewById(R.id.datetime_product_item);
            imagePro = (ImageView) itemView.findViewById(R.id.image_product_item);
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

    public MyRecyclerViewAdapter(ArrayList<Products> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        // afficher les données sur le cardView
        holder.namePro.setText(mDataset.get(position).getName());

        String category = "(" + mDataset.get(position).getCategory() + ").";
        holder.categoryPro.setText(category);

        holder.descPro.setText(mDataset.get(position).getDescription());

        String price = mDataset.get(position).getPrice() + " € ";
        holder.pricePro.setText(price);

        String dateTime = "Publié le " + mDataset.get(position).getDateTime();
        holder.datePro.setText(dateTime);

        //recup image de Firebase Storage
        String image = mDataset.get(position).getImagePath();
        ImageManage.getImage(image, holder.imagePro);

    }

    public void addItem(Products produit, int index) {
        mDataset.add(index, produit);
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