package com.abfresh.in.Adapter;

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

import com.squareup.picasso.Picasso;
import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private java.util.ArrayList<ArrayList> productList;
    private OnItemClickListner mListner;
    private Context mContext;

    public interface OnItemClickListner{

        void onRecipeClick(int position, TextView rec_name);

    }
    public RecipeAdapter(Context recipes, java.util.ArrayList<ArrayList> productList) {
        mContext = recipes;
        this.productList = productList;
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_collection_item_adapter,parent,false);
        return new RecipeAdapter.ViewHolder(view,mListner);
    }



    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {

        holder.rec_name.setText(productList.get(position).getRecipeName());
        holder.rec_type.setText(productList.get(position).getRecipeTypeName());
//        holder.rec_image.setImageResource(productList.get(position).getR());
        Picasso.with(mContext).load(productList.get(position).getRecipeImage()).fit().centerCrop().into(holder.rec_image);
//        holder.productPrice.setText(productList.get(position % productList.size()).getProductPrice());
        holder.recipe_time_tv.setText(productList.get(position).getRecipeDuration());
        holder.recipe_serves_tv.setText("Serves :" + productList.get(position).getRecipeServes());
        Log.w("RTAG",productList.get(position).getRecipeImage());
    }

    @Override
    public int getItemCount() {
        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView rec_image;
        TextView rec_name,rec_type,recipe_time_tv,recipe_serves_tv;
        RelativeLayout card_recipe;

        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);
            rec_image = (ImageView)itemView.findViewById(R.id.rec_image);
            rec_name = (TextView)itemView.findViewById(R.id.rec_name);
            rec_type = (TextView)itemView.findViewById(R.id.rec_type);
            recipe_time_tv = (TextView)itemView.findViewById(R.id.recipe_time_tv);
            recipe_serves_tv = (TextView)itemView.findViewById(R.id.recipe_serves_tv);
            card_recipe = (RelativeLayout) itemView.findViewById(R.id.card_recipe);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onRecipeClick(position,rec_name);
                        }
                    }
                }
            });

        }
    }
}


