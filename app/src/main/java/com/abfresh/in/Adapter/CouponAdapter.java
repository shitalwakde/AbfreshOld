package com.abfresh.in.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder>  {
    private java.util.ArrayList<ArrayList> mOrdertList;
    private OnItemClickListner moListner;

    public int num = 0;
    android.content.Context context;
    SessionManagement sessionManagement;
    String userid;
    HashMap<String, String> user;

    public interface OnItemClickListner{
        void onCouponClick(int position);

    }

        public void setOnItemClickListner(OnItemClickListner listner){
        moListner = listner;
    }
    public CouponAdapter(Context context, java.util.ArrayList<ArrayList> thList) {
        context = context;
        this.mOrdertList = thList;

    }



    @NonNull
    @Override
    public CouponAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coupon_adapter_layout,parent,false);
        return new CouponAdapter.ViewHolder(view,moListner);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull CouponAdapter.ViewHolder holder, int position) {

        holder.coupon_title_tv.setText(mOrdertList.get(position).getCopTitle());
        holder.coupon_dic_tv.setText(mOrdertList.get(position).getCopDis());
        holder.coupon_min_or_tv.setText(mOrdertList.get(position).getCopMinOrder());
        holder.coupon_code_tv.setText("USE CODE: "+"\""+mOrdertList.get(position).getPromoCode()+"\"");
        Log.w("IMTAG","adapter Image response====>" +mOrdertList.get(position).getCopImage());
        if(mOrdertList.get(position).getCopImage().length()==0){
            holder.image_coupon.setVisibility(View.GONE);
        }else{
            holder.image_coupon.setVisibility(View.VISIBLE);
//            Picasso.with(context).load( "http://abfresh.in"+mOrdertList.get(position).getCopImage()).fit().centerCrop().into(holder.image_coupon);
            Picasso.with(context).load(mOrdertList.get(position).getCopImage()).fit().centerCrop().into(holder.image_coupon);

        }
        holder.coupon_validity_tv.setText(mOrdertList.get(position).getCopValidity());


    }




    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        TextView coupon_title_tv,coupon_dic_tv,coupon_min_or_tv,coupon_validity_tv,apply_btn_cdl,coupon_code_tv;
        ImageView image_coupon;
        RelativeLayout cal_ll;
        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);

//            th_image = (ImageView) itemView.findViewById(R.id.th_image);
            coupon_title_tv = (TextView)itemView.findViewById(R.id.coupon_title_tv);
            coupon_dic_tv = (TextView)itemView.findViewById(R.id.coupon_dic_tv);
            coupon_min_or_tv = (TextView)itemView.findViewById(R.id.coupon_min_or_tv);
            coupon_code_tv = (TextView)itemView.findViewById(R.id.coupon_code_tv);
            coupon_validity_tv = (TextView)itemView.findViewById(R.id.coupon_validity_tv);
            apply_btn_cdl = (TextView)itemView.findViewById(R.id.apply_btn_cdl);
            image_coupon = (ImageView) itemView.findViewById(R.id.image_coupon);
            cal_ll = (RelativeLayout) itemView.findViewById(R.id.cal_ll);

            cal_ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCouponClick(position);
                        }
                    }
                }
            });

        }
    }
}


