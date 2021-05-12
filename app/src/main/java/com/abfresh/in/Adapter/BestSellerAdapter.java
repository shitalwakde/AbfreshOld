package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.ViewHolder> {

    private java.util.ArrayList<ArrayList> productList;
    Context context;

    private OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onBsClick(int position, ImageView productImage);

    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }
    public BestSellerAdapter(Context context, java.util.ArrayList<ArrayList> productList) {
        this.productList = productList;
        this.context = context;
    }


    @Override
    public BestSellerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.best_seller_adapter,parent,false);
        return new BestSellerAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(BestSellerAdapter.ViewHolder holder, int position) {
        holder.heading_bsa.setText(productList.get(position).getRecipeImage());
        holder.amount_bsa.setText("₹ "+productList.get(position).getRecipeDuration());
        Picasso.with(context).load(productList.get(position).getRecipeServes()).into(holder.bsa_iv);



    }

    @Override
    public int getItemCount() {
        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView bsa_iv;
        CardView bs_cv;
        TextView amount_bsa,heading_bsa;
        Button btn_bsa;
//        TextView recipetName,recipeDisc;

        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);
            bsa_iv = (ImageView)itemView.findViewById(R.id.bsa_iv);
            bs_cv = (CardView) itemView.findViewById(R.id.bs_cv);
            amount_bsa = (TextView) itemView.findViewById(R.id.amount_bsa);
            heading_bsa = (TextView) itemView.findViewById(R.id.heading_bsa);
            btn_bsa = (Button) itemView.findViewById(R.id.btn_bsa);

            btn_bsa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onBsClick(position,bsa_iv);
                        }
                    }
                }
            });

        }
    }
}

