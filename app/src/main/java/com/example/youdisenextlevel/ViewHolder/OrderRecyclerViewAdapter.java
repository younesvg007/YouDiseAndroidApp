package com.example.youdisenextlevel.ViewHolder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.youdisenextlevel.Model.Carts;
import com.example.youdisenextlevel.Model.Database.ImageManage;
import com.example.youdisenextlevel.Model.Orders;
import com.example.youdisenextlevel.R;

import java.util.ArrayList;

public class OrderRecyclerViewAdapter extends RecyclerView
        .Adapter<OrderRecyclerViewAdapter
        .DataHolder> {

    private static String LOG_TAG = "OrderRecyclerViewAdapter";
    private ArrayList<Orders> mDataset;
    private static MyClickListener myClickListener;
    public static class DataHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {

        TextView idOrder, adressOrder, countryOrder, cardBankOrder, totalAmountOrder, dateTimeOrder;

        public DataHolder(View itemView) {
            super(itemView);
            idOrder = (TextView) itemView.findViewById(R.id.order_id);
            adressOrder = (TextView) itemView.findViewById(R.id.adress_order_item);
            countryOrder = (TextView) itemView.findViewById(R.id.country_order_item);
            cardBankOrder = (TextView) itemView.findViewById(R.id.card_bank_item);
            totalAmountOrder = (TextView) itemView.findViewById(R.id.total_amount_item);
            dateTimeOrder = (TextView) itemView.findViewById(R.id.date_time_order_item);

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

    public OrderRecyclerViewAdapter(ArrayList<Orders> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_row, parent, false);
        DataHolder dataObjectHolder = new DataHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        String nCommande = "Commande n° " + mDataset.get(position).getIdOrder();
        holder.idOrder.setText(nCommande);
        holder.adressOrder.setText(mDataset.get(position).getAdress());
        holder.countryOrder.setText(mDataset.get(position).getCountry());
        String nCardBank = "Carte Bancaire : " + mDataset.get(position).getBankCard();
        holder.cardBankOrder.setText(nCardBank);
        String totalPrix = "Prix Total : " + mDataset.get(position).getTotalAmount();
        holder.totalAmountOrder.setText(totalPrix);
        String timeOrder = "Commande faite le " + mDataset.get(position).getDateTime();
        holder.dateTimeOrder.setText(timeOrder);
    }

    public void addItem(Orders commande, int index) {
        mDataset.add(index, commande);
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
