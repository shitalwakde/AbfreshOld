package com.abfresh.in.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Controller.AppController;
import com.abfresh.in.R;
import com.abfresh.in.TabActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FrontPageMenuBlackAdapter extends RecyclerView.Adapter<FrontPageMenuBlackAdapter.ViewHolder> {

    private ArrayList<com.abfresh.in.Model.ArrayList> productList;
    Context Context;
    public FrontPageMenuBlackAdapter(Context context, ArrayList<com.abfresh.in.Model.ArrayList> productList) {
        this.Context = context;
        this.productList = productList;
    }


    @Override
    public FrontPageMenuBlackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.front_page_menu_black,parent,false);
        return new FrontPageMenuBlackAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FrontPageMenuBlackAdapter.ViewHolder holder, int position) {
        holder.fpm_textView.setText(productList.get(position).getName());
//        holder.fpm_imgView.setImageResource(productList.get(position).getImage());
        Picasso.with(Context).load(productList.get(position).getCatImage()).fit().centerInside().into(holder.fpm_imgView);

        holder.rl_blackcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.tabCurrentItem =position;
                Log.w("FPA","Front Adapter current item===>"+AppController.tabCurrentItem);
                Intent intent = new Intent(v.getContext(),TabActivity.class);
                v.getContext().startActivity(intent);
//                notifyDataSetChanged();
            }
        });

//        if(TabActivity.tabCurrentItem==position){
//
//        }
//        holder.productPrice.setText(productList.get(position % productList.size()).getProductPrice());



    }

    @Override
    public int getItemCount() {
        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView fpm_textView,productPrice;
        ImageView fpm_imgView;
        RelativeLayout rl_blackcat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rl_blackcat = (RelativeLayout)itemView.findViewById(R.id.rl_blackcat);
            fpm_textView = (TextView)itemView.findViewById(R.id.fpm_textView);
            fpm_imgView = (ImageView) itemView.findViewById(R.id.icon_image);
        }
    }
}

