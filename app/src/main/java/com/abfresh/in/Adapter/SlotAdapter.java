package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;
import com.abfresh.in.Model.CartList;

import java.util.ArrayList;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder>  {
    private ArrayList<CartList> CartProductList;
    android.content.Context Context;
    public int num = 0;
    //   private OnItemClickListner mListner;
    private OnItemClickListner mListner;

    public interface OnItemClickListner{
        void onSlotClick(int position, TextView textView);

    }
    public SlotAdapter(Context context, ArrayList<CartList> cartProductList) {
        CartProductList = cartProductList;
        Context = context;
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }
    @NonNull
    @Override
    public SlotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_adapter,parent,false);
        return new SlotAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotAdapter.ViewHolder holder, int position) {

        holder.slot1.setText(CartProductList.get(position).getDelivery_location_id());


    }

    @Override
    public int getItemCount() {
        return CartProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView slot1;
        LinearLayout time_ll_adap;

        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);

            slot1 = (TextView)itemView.findViewById(R.id.slot1);
            time_ll_adap = (LinearLayout) itemView.findViewById(R.id.time_ll_adap);
            time_ll_adap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onSlotClick(position,slot1);
                        }
                    }
                }
            });
        }
    }
}
