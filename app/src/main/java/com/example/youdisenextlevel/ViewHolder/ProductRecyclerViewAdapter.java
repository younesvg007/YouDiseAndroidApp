package com.example.youdisenextlevel.ViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youdisenextlevel.Model.Database.ImageManage;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.R;

import java.util.ArrayList;

public class ProductRecyclerViewAdapter extends RecyclerView
        .Adapter<ProductRecyclerViewAdapter
        .DataClassHolder> {

    private static String LOG_TAG = "ProductRecyclerViewAdapter";
    private ArrayList<Products> mDataset;
    private static MyClickListener myClickListener;
    public static class DataClassHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView nameProAdmin, categoryProAdmin, descProAdmin, priceProAdmin, dateProAdmin;
        ImageView imageProAdmin;

        public DataClassHolder(View itemView) {
            super(itemView);
            nameProAdmin = (TextView) itemView.findViewById(R.id.name_product_admin_item);
            categoryProAdmin = (TextView) itemView.findViewById(R.id.category_product_admin_item);
            descProAdmin = (TextView) itemView.findViewById(R.id.description_product_admin_item);
            priceProAdmin = (TextView) itemView.findViewById(R.id.price_product_admin_item);
            dateProAdmin = (TextView) itemView.findViewById(R.id.datetime_product_admin_item);
            imageProAdmin = (ImageView) itemView.findViewById(R.id.image_product_admin_item);
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

    public ProductRecyclerViewAdapter(ArrayList<Products> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataClassHolder onCreateViewHolder(ViewGroup parent,
                                              int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_admin_row, parent, false);
        DataClassHolder dataObjectHolder = new DataClassHolder(view);
        return dataObjectHolder;
    }
    @Override
    public void onBindViewHolder(DataClassHolder holder, int position) {
        holder.nameProAdmin.setText(mDataset.get(position).getName());

        String category = "(" + mDataset.get(position).getCategory() + ").";
        holder.categoryProAdmin.setText(category);

        holder.descProAdmin.setText(mDataset.get(position).getDescription());

        String price = mDataset.get(position).getPrice() + " € ";
        holder.priceProAdmin.setText(price);

        String dateTime = "Publié le " + mDataset.get(position).getDateTime();
        holder.dateProAdmin.setText(dateTime);

        String image = mDataset.get(position).getImagePath();
        ImageManage.getImage(image, holder.imageProAdmin);
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
