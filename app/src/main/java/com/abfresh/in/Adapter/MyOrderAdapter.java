package com.abfresh.in.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.R;
import com.abfresh.in.Model.CartList;

import java.util.ArrayList;
import java.util.HashMap;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder>  {
    private ArrayList<CartList> mOrdertList;
    private OnItemClickListner moListner;

    public int num = 0;
    android.content.Context Context;
    SessionManagement sessionManagement;
    String userid;
    HashMap<String, String> user;

    public interface OnItemClickListner{
        void onMyOrderListtClick(int position);
        void onCancelOrderRequest(int position);
    }

    public void setOnItemClickListner(OnItemClickListner listner){
        moListner = listner;
    }
    public MyOrderAdapter(Context context, ArrayList<CartList> mOrdertList) {
        Context = context;
       this.mOrdertList = mOrdertList;

    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_adapter,parent,false);
        return new MyOrderAdapter.ViewHolder(view,moListner);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder holder, int position) {
        try{
            holder.mo_orderno.setText(mOrdertList.get(position).getCart_qty());
            String order_status = mOrdertList.get(position).getProduct_name();
//            Log.w("MOATAG===>order_status",order_status);
//            Log.w("MOATAG", "order_status "+ mOrdertList.get(position).getProduct_name());

//        holder.mo_add_tv.setText(mOrdertList.get(position).getOrder_address());
            holder.mo_order_status_tv.setText(mOrdertList.get(position).getProduct_name());
            holder.mo_order_pay_status_tv.setText(mOrdertList.get(position).getProduct_gross_amt());
            holder.mo_order_amount_tv.setText("₹"+ mOrdertList.get(position).getProduct_discount());
            holder.mo_on_date.setText(mOrdertList.get(position).getProduct_amt());
            holder.deliveryslot_mo.setText(Utility.deliverySlot);

            Log.w("MOATAG", "getOrder_number "+mOrdertList.get(position).getCart_qty());
            Log.w("MOATAG", "getOrder_status "+mOrdertList.get(position).getProduct_name());
            Log.w("MOATAG", "getOrder_payment_type "+mOrdertList.get(position).getProduct_gross_amt());
            Log.w("MOATAG", "getOrder_amount "+mOrdertList.get(position).getProduct_discount());
            Log.w("MOATAG", "getOrder_date "+mOrdertList.get(position).getProduct_amt());

//        Log.w("MOATAG===>order_status",order_status);


            if((order_status.trim().length())==0){

                holder.mo_order_status_tv.setTextColor(Context.getResources().getColor(R.color.newYellow));
            }else{
                if(order_status.trim().equals("Cancelled")){
                    holder.mo_cancel_order.setVisibility(View.GONE);
                    holder.mo_order_status_tv.setTextColor(Context.getResources().getColor(R.color.falert_red));
                }else if(order_status.trim().equals("Delivered")){
                    holder.mo_cancel_order.setVisibility(View.GONE);
                    holder.mo_order_status_tv.setTextColor(Context.getResources().getColor(R.color.newYellow));
                }else if(order_status.trim().equals("Processing")){
                    holder.mo_cancel_order.setVisibility(View.GONE);
                    holder.mo_order_status_tv.setTextColor(Context.getResources().getColor(R.color.white));
                }else{
                    holder.mo_cancel_order.setVisibility(View.VISIBLE);
                    holder.mo_order_status_tv.setTextColor(Context.getResources().getColor(R.color.colorGreen));
                }


            }

        }catch (Error e){

        }

    }




    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout instock_ll,mo_cancel_order;

        TextView mo_orderno,mo_on_date,mo_add_tv,mo_order_status_tv,mo_order_pay_status_tv,mo_order_amount_tv,deliveryslot_mo;
        CardView my_order_cv;
        LinearLayout main_my_order;
        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);

            mo_orderno = (TextView)itemView.findViewById(R.id.mo_orderno);
            mo_on_date = (TextView)itemView.findViewById(R.id.mo_on_date);
            deliveryslot_mo = (TextView)itemView.findViewById(R.id.deliveryslot_mo);
//            mo_add_tv = (TextView)itemView.findViewById(R.id.mo_add_tv);
            mo_order_status_tv = (TextView)itemView.findViewById(R.id.mo_order_status_tv);
            mo_order_pay_status_tv = (TextView)itemView.findViewById(R.id.mo_order_pay_status_tv);
            mo_order_amount_tv = (TextView)itemView.findViewById(R.id.mo_order_amount_tv);
            mo_cancel_order = (LinearLayout) itemView.findViewById(R.id.mo_cancel_order);
            my_order_cv = (CardView) itemView.findViewById(R.id.my_order_cv);
            main_my_order = (LinearLayout) itemView.findViewById(R.id.main_my_order);

            main_my_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onMyOrderListtClick(position);
                        }
                    }
                }
            });

            mo_cancel_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listner.onCancelOrderRequest(position);
                    }
                }
            });
        }
    }
}
