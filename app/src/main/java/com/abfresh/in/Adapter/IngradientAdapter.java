package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;
import java.util.ArrayList;

public class IngradientAdapter extends RecyclerView.Adapter<IngradientAdapter.ViewHolder> {

    private ArrayList<com.abfresh.in.Model.ArrayList> productList;

    public IngradientAdapter(Context applicationContext, ArrayList<com.abfresh.in.Model.ArrayList> productList) {
        this.productList = productList;
    }


    @Override
    public IngradientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingradient_layout,parent,false);
        return new IngradientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngradientAdapter.ViewHolder holder, int position) {
        holder.ingradient_name.setText(productList.get(position).getName());
        holder.ingradient_weight.setText(productList.get(position).getCatImage());

//        holder.productPrice.setText(productList.get(position % productList.size()).getProductPrice());


    }

    @Override
    public int getItemCount() {
        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ingradient_name,ingradient_weight,ingradient_quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingradient_name = (TextView)itemView.findViewById(R.id.ingradient_name);
            ingradient_weight = (TextView)itemView.findViewById(R.id.ingradient_weight);
            ingradient_quantity = (TextView)itemView.findViewById(R.id.ingradient_quantity);
        }
    }
}


