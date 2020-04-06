package com.example.youdisenextlevel.ViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youdisenextlevel.Model.Database.ImageStorage;
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

        TextView namePro, descPro, pricePro;
        ImageView imagePro;

        public DataObjectHolder(View itemView) {
            super(itemView);
            namePro = (TextView) itemView.findViewById(R.id.name_product_item);
            descPro = (TextView) itemView.findViewById(R.id.description_product_item);
            pricePro = (TextView) itemView.findViewById(R.id.price_product_item);
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
        holder.namePro.setText(mDataset.get(position).getName());
        holder.descPro.setText(mDataset.get(position).getDescription());

        String price = mDataset.get(position).getPrice() + "  € ";
        holder.pricePro.setText(price);

        String image = mDataset.get(position).getImagePath();
        ImageStorage.getImage(image, holder.imagePro);
    }
    public void addItem(Products produit, int index) {
        mDataset.add(index, produit);
        notifyItemInserted(index);
    }
    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}