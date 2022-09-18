package com.abfresh.in.Abfresh.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.model.OrderModel;
import com.abfresh.in.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.MyViewHolder> {



    ArrayList<OrderModel> mdata;


    public OrderDetailAdapter(ArrayList<OrderModel> mdata) {
        this.mdata=mdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        OrderModel orderModel=mdata.get(position);
        Picasso.with(holder.itemView.getContext()).load(orderModel.getProduct_image()).into(holder.iv_prod_image);
        holder.tv_prodName.setText(orderModel.getProduct_name());
        holder.tv_prodWeight.setText("Net wt: "+orderModel.getProduct_weight()+""+orderModel.getProduct_type());
        holder.price.setText("MRP: "+orderModel.getAmt());
        holder.tv_qty.setText("Qty: "+orderModel.getQty());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_close, iv_prod_image, iv_minus, iv_plus;
        TextView price, tv_qty, tv_prodName, tv_prodWeight;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_prod_image=(ImageView)itemView.findViewById(R.id.iv_prod_image);
            price=(TextView)itemView.findViewById(R.id.price);
            tv_prodName=(TextView)itemView.findViewById(R.id.tv_prodName);
            tv_prodWeight=(TextView)itemView.findViewById(R.id.tv_prodWeight);
            tv_qty=(TextView)itemView.findViewById(R.id.tv_qty);
        }
    }
}
