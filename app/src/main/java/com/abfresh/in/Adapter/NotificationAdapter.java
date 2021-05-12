package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private java.util.ArrayList<ArrayList> productList;
    Context nContext;

    public NotificationAdapter(Context applicationContext, java.util.ArrayList<ArrayList> productList) {
        nContext = applicationContext;
        this.productList = productList;
    }


    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_msg_layout,parent,false);
        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        holder.notHead.setText(productList.get(position).getName());
        holder.notMsg.setText(productList.get(position).getQuantity());
        Picasso.with(nContext).load(productList.get(position).getWeight()).fit().centerInside().into(holder.nmsg_iv);

//        holder.productPrice.setText(productList.get(position % productList.size()).getProductPrice());
//        holder.notMsg.setImageResource(productList.get(position).getImage());
//        holder.cat_ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TabActivity.tabCurrentItem =position;
//
//                Intent intent = new Intent(v.getContext(),TabActivity.class);
//                v.getContext().startActivity(intent);
//            }
//        });
//        if(TabActivity.tabCurrentItem==position){
//
//        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView notHead,notMsg;
        LinearLayout cat_ll;
        ImageView nmsg_iv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notHead = (TextView)itemView.findViewById(R.id.not_head);
            notMsg = (TextView)itemView.findViewById(R.id.not_msg);
            nmsg_iv = (ImageView) itemView.findViewById(R.id.nmsg_iv);

        }
    }
}
