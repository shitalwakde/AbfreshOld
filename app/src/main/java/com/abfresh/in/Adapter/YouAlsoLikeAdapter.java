package com.abfresh.in.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class YouAlsoLikeAdapter extends RecyclerView.Adapter<YouAlsoLikeAdapter.ViewHolder> {

    private java.util.ArrayList<ArrayList> productList;
    //------------------------------------Define OnItemClickListner (step 2)-------------------------------------------------------------------------

    private YouAlsoLikeAdapter.OnItemClickListner mListner;
    //------------------------------------Creating Interface for clicklistner(step 1)-------------------------------------------------------------------------

    public interface OnItemClickListner{
        void onCatProductClick(int position);
    }
    //------------------------------------Creating method that we call from main page (step 3)-------------------------------------------------------------------------

    public void setOnItemClickListner(YouAlsoLikeAdapter.OnItemClickListner listner){
        mListner = listner;
    }
    public YouAlsoLikeAdapter(java.util.ArrayList<ArrayList> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public YouAlsoLikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collapse_also_like_layout,parent,false);
//------------------------------------Define mListner in return (step 4)-------------------------------------------------------------------------
        return new YouAlsoLikeAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull YouAlsoLikeAdapter.ViewHolder holder, int position) {
        holder.yalproductName.setText(productList.get(position).getProductName());
        holder.yalproductPrice.setText(productList.get(position).getProductPrice());
        holder.yalproductImage.setImageResource(productList.get(position).getProductImage());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView yalproductImage;
        TextView yalproductName,yalproductPrice;

        //------------------------------------Define OnItemClickListner inside View Holder (step 5)-------------------------------------------------------------------------

        public ViewHolder(@NonNull View itemView,final YouAlsoLikeAdapter.OnItemClickListner listner) {
            super(itemView);

            yalproductImage = (ImageView)itemView.findViewById(R.id.also_like_item_image);
            yalproductName = (TextView)itemView.findViewById(R.id.also_like_item_name);
            yalproductPrice = (TextView)itemView.findViewById(R.id.also_like_item_price);
//------------------------------------item click start (step 6)-------------------------------------------------------------------------
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCatProductClick(position);
                        }
                    }
                }
            });
            //------------------------------------item click end-------------------------------------------------------------------------

        }
    }
}
