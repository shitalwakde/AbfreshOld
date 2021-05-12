package com.abfresh.in.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;
import java.util.ArrayList;

public class RecipeAdapterInsideProDisc extends RecyclerView.Adapter<RecipeAdapterInsideProDisc.ViewHolder> {

    private ArrayList<com.abfresh.in.Model.ArrayList> productList;

    public RecipeAdapterInsideProDisc(ArrayList<com.abfresh.in.Model.ArrayList> productList) {
        this.productList = productList;
    }


    @Override
    public RecipeAdapterInsideProDisc.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_adapter,parent,false);
        return new RecipeAdapterInsideProDisc.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapterInsideProDisc.ViewHolder holder, int position) {
        holder.recipeImage.setImageResource(productList.get(position).getProductImage());
        holder.recipetName.setText(productList.get(position).getProductName());
//        holder.productPrice.setText(productList.get(position % productList.size()).getProductPrice());

        holder.recipeDisc.setText(productList.get(position).getProductdisc());


    }

    @Override
    public int getItemCount() {
        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView recipeImage;
        TextView recipetName,recipeDisc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = (ImageView)itemView.findViewById(R.id.rec_adpt_image);
            recipetName = (TextView)itemView.findViewById(R.id.recipie_adpt_name);
            recipeDisc = (TextView)itemView.findViewById(R.id.recipie_disc);
        }
    }
}

