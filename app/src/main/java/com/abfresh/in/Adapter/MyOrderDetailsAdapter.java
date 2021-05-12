package com.abfresh.in.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;
import com.abfresh.in.Model.CartList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderDetailsAdapter extends RecyclerView.Adapter<MyOrderDetailsAdapter.ViewHolder>  {
    private ArrayList<CartList> mOrdertList;

    public int num = 0;
    android.content.Context Context;
    SessionManagement sessionManagement;
    String userid;
    HashMap<String, String> user;


    public MyOrderDetailsAdapter(android.content.Context context, ArrayList<CartList> mOrdertList) {
        Context = context;
        this.mOrdertList = mOrdertList;

    }

    @NonNull
    @Override
    public MyOrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_details_adapter,parent,false);
        return new MyOrderDetailsAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyOrderDetailsAdapter.ViewHolder holder, int position) {

        holder.mod_brand_name.setText(mOrdertList.get(position).getCart_id());
        Picasso.with(Context).load(mOrdertList.get(position).getCart_qty()).fit().into(holder.mod_cart_image);
        holder.mod_weight.setText(mOrdertList.get(position).getProduct_name());
        holder.mod_price.setText("₹"+mOrdertList.get(position).getProduct_gross_amt());
        holder.mod_price_gross.setText("₹"+mOrdertList.get(position).getProduct_discount());
        holder.mod_price_gross.setPaintFlags(holder.mod_price_gross.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.mod_discount.setText(mOrdertList.get(position).getProduct_amt()+"%");
        holder.mod_qnty_value.setText(mOrdertList.get(position).getPro_wt());

        if(mOrdertList.get(position).getProduct_discount().equals("0")){
            holder.mod_price_gross.setVisibility(View.GONE);
            holder.mod_discount.setVisibility(View.GONE);
        }else{
            holder.mod_price_gross.setVisibility(View.VISIBLE);
            holder.mod_discount.setVisibility(View.VISIBLE);
        }
//        holder.mo_order_amount_tv.setText("₹"+ mOrdertList.get(position).getOrder_address());

    }




    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mod_cart_image;
        TextView mod_brand_name,mod_weight,mod_price,mod_price_gross,mod_discount,mod_qnty_value;
        CardView cl_cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mod_brand_name = (TextView)itemView.findViewById(R.id.mod_brand_name);
            mod_weight = (TextView)itemView.findViewById(R.id.mod_weight);
            mod_price = (TextView)itemView.findViewById(R.id.mod_price);
            mod_price_gross = (TextView)itemView.findViewById(R.id.mod_price_gross);
            mod_discount = (TextView)itemView.findViewById(R.id.mod_discount);
            mod_qnty_value = (TextView)itemView.findViewById(R.id.mod_qnty_value);
            mod_cart_image = (ImageView) itemView.findViewById(R.id.mod_cart_image);



        }
    }
}

