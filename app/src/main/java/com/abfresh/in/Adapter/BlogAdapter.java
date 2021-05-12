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

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.ViewHolder> {

    private ArrayList<com.abfresh.in.Model.ArrayList> productList;

    public BlogAdapter(ArrayList<com.abfresh.in.Model.ArrayList> productList) {
        this.productList = productList;
    }


    @Override
    public BlogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_adapter_layout,parent,false);
        return new BlogAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.productName.setText(productList.get(position).getName());
//        holder.productPrice.setText(productList.get(position % productList.size()).getProductPrice());
        holder.productImage.setImageResource(productList.get(position).getImage());


    }

    @Override
    public int getItemCount() {
        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName,productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = (ImageView)itemView.findViewById(R.id.blog_adpt_image);
            productName = (TextView)itemView.findViewById(R.id.blog_adpt_name);
        }
    }
}

