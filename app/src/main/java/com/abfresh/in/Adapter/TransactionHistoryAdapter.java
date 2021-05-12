package com.abfresh.in.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.ViewHolder>  {
    private ArrayList<com.abfresh.in.Model.ArrayList> mOrdertList;
//    private OnItemClickListner moListner;

    public int num = 0;
    android.content.Context Context;
    SessionManagement sessionManagement;
    String userid;
    HashMap<String, String> user;

//    public interface OnItemClickListner{
//        void onMyOrderListtClick(int position);
//        void onCancelOrderRequest(int position);
//    }

//    public void setOnItemClickListner(OnItemClickListner listner){
//        moListner = listner;
//    }
    public TransactionHistoryAdapter(Context context, ArrayList<com.abfresh.in.Model.ArrayList> thList) {
        Context = context;
        this.mOrdertList = thList;

    }



    @NonNull
    @Override
    public TransactionHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history_adapter,parent,false);
        return new TransactionHistoryAdapter.ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryAdapter.ViewHolder holder, int position) {

        holder.th_transaction_amount.setText("₹" + mOrdertList.get(position).getPayAmount());
        holder.th_transaction_status.setText("Status : " + mOrdertList.get(position).getPayType());
        holder.th_transaction_name.setText(mOrdertList.get(position).getPayName());
        holder.th_transaction_date.setText(mOrdertList.get(position).getPayTime());


        String name = mOrdertList.get(position).getPayName();
        String firstLetter = String.valueOf(name.charAt(0));

        Log.d("FLTAG",firstLetter);


        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getColor(position);
        Log.d("fist color=========>", String.valueOf(color));
//        int color = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color); // radius in px

       holder.th_image.setImageDrawable(drawable);

    }




    @Override
    public int getItemCount() {
        return mOrdertList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView th_image;
        TextView th_transaction_name,th_transaction_status,th_transaction_amount,th_transaction_time,th_transaction_date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            th_image = (ImageView) itemView.findViewById(R.id.th_image);
            th_transaction_name = (TextView)itemView.findViewById(R.id.th_transaction_name);
            th_transaction_status = (TextView)itemView.findViewById(R.id.th_transaction_status);
            th_transaction_amount = (TextView)itemView.findViewById(R.id.th_transaction_amount);
            th_transaction_time = (TextView)itemView.findViewById(R.id.th_transaction_time);
            th_transaction_date = (TextView)itemView.findViewById(R.id.th_transaction_date);



        }
    }
}

