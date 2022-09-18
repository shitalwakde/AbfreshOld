package com.abfresh.in.Abfresh.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.model.CouponListModel;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.R;

import java.util.ArrayList;


public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.MyViewHolder> {

    ArrayList<CouponListModel> mdata;
    SessionManagement sessionManagement;
    public static final String CARTDATA = "Cartdata";

    public PromoCodeAdapter(ArrayList<CouponListModel> mdata) {
        this.mdata=mdata;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.promocode_adapter, parent, false);

        sessionManagement=new SessionManagement(parent.getContext());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CouponListModel couponListModel = mdata.get(position);
        holder.tv_title.setText("USE CODE: "+couponListModel.getPromo_code());
        holder.tv_description.setText(couponListModel.getDiscription());
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title, tv_description, tv_apply_promo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=(TextView)itemView.findViewById(R.id.tv_title);
            tv_description=(TextView)itemView.findViewById(R.id.tv_description);
            tv_apply_promo=(TextView)itemView.findViewById(R.id.tv_apply_promo);


            tv_apply_promo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.couponCode = mdata.get(getAdapterPosition()).getPromo_code();
                    ((Activity) itemView.getContext()).finish();
//                    applyCouponCode(getAdapterPosition(), mdata);
                }
            });
        }


    }
}
