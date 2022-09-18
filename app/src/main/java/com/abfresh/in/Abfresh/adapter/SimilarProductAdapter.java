package com.abfresh.in.Abfresh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.model.CartItemList;
import com.abfresh.in.R;

import java.util.ArrayList;


public class SimilarProductAdapter extends RecyclerView.Adapter<SimilarProductAdapter.MyViewHolder> {
    ArrayList<CartItemList> mdata;

//    public SimilarProductAdapter(ArrayList<CartItemList> mdata) {
//        this.mdata=mdata;
//    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.similar_product_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        /*CartItemList cartItemList=mdata.get(position);
        Picasso.with(holder.itemView.getContext()).load(cartItemList.getProduct_image()).into(holder.iv_prod_image);
        holder.tv_prodName.setText(cartItemList.getProduct_name());
        holder.tv_prodWeight.setText("Net wt: "+cartItemList.getProduct_weight());
        holder.price.setText("MRP: "+cartItemList.getProduct_amt());
        holder.tv_qty.setText("Qty: "+cartItemList.getQty());*/
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }


}
