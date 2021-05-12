package com.abfresh.in.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;

import java.util.ArrayList;

public class Search_Location_Adapter extends RecyclerView.Adapter<Search_Location_Adapter.ViewHolder>  {
    private ArrayList<com.abfresh.in.Model.ArrayList> mOrdertList;

    private Search_Location_Adapter.OnItemClickListner moListner;

    Context Context;


    public interface OnItemClickListner{
        void onMyOrderListtClick(int position);
        void onCancelOrderRequest(int position);
    }

    public void setOnItemClickListner(Search_Location_Adapter.OnItemClickListner listner){
        moListner = listner;
    }
    public Search_Location_Adapter(Context context,ArrayList<com.abfresh.in.Model.ArrayList> mOrdertList) {
        Context = context;
        this.mOrdertList = mOrdertList;

    }

    @NonNull
    @Override
    public Search_Location_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_layout,parent,false);
        return new Search_Location_Adapter.ViewHolder(view,moListner);
    }

    @Override
    public void onBindViewHolder(@NonNull Search_Location_Adapter.ViewHolder holder, int position) {

            holder.textView_search.setText(mOrdertList.get(position).getName());

            Log.w("MOATAG", "order list===> "+mOrdertList.get(position).getName());

//        Log.w("MOATAG===>order_status",order_status);

    }




    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView_search;
        CardView cl_cv;

        public ViewHolder(@NonNull View itemView,final Search_Location_Adapter.OnItemClickListner listner) {
            super(itemView);

            textView_search = (TextView)itemView.findViewById(R.id.textView_search);


//            mo_cancel_order.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if(position != RecyclerView.NO_POSITION){
//                        listner.onCancelOrderRequest(position);
//                    }
//                }
//            });
        }
    }
}
