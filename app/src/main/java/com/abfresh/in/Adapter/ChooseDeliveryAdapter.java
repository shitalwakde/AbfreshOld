package com.abfresh.in.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;
import com.abfresh.in.Model.CartList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChooseDeliveryAdapter extends RecyclerView.Adapter<ChooseDeliveryAdapter.ViewHolder>  {
    private ArrayList<CartList> CartProductList;
    Context Context;
    public int num = 0;
 //   private OnItemClickListner mListner;

    public ChooseDeliveryAdapter(Context context, ArrayList<CartList> cartProductList) {
        CartProductList = cartProductList;
        Context = context;
    }

    @NonNull
    @Override
    public ChooseDeliveryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_slot_adapter_layout,parent,false);
        return new ChooseDeliveryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChooseDeliveryAdapter.ViewHolder holder, int position) {

        holder.ds_product_quantity.setText(CartProductList.get(position).getPro_quantity());

        holder.dsProductName.setText(CartProductList.get(position).getProduct_name());
//        holder.dsProductweight.setText(CartProductList.get(position).getProductWeight());

        holder.ds_product_price.setText("₹"+CartProductList.get(position).getProduct_amt());
        holder.ds_price_gross_amount.setText("₹"+CartProductList.get(position).getProduct_gross_amt());
        holder.ds_price_gross_amount.setPaintFlags(holder.ds_price_gross_amount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if(CartProductList.get(position).getProduct_discount().equals("0")){
            holder.ds_price_gross_amount.setVisibility(View.GONE);
            holder.ds_price_slash.setVisibility(View.GONE);
        }else{
            holder.ds_price_gross_amount.setVisibility(View.VISIBLE);
            holder.ds_price_slash.setVisibility(View.VISIBLE);
        }

        Picasso.with(Context).load(CartProductList.get(position).getPro_image()).fit().into(holder.ds_Image);
//        holder.ds_Image.setImageResource(Integer.parseInt(CartProductList.get(position).getPro_image()));

        holder.dsProductweight.setText(CartProductList.get(position).getPro_wt());

        Log.w("CDATAG",(CartProductList.get(position).getPro_quantity()+CartProductList.get(position).getProduct_name()+CartProductList.get(position).getProduct_amt() + CartProductList.get(position).getPro_image()+CartProductList.get(position).getPro_wt()+""));

//        holder.cart_color_tv.setSolidColor("#3F51B5");

    }

    @Override
    public int getItemCount() {
        return CartProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout instock_ll;
        ImageView ds_Image;

        TextView dsProductName,dsProductweight,ds_product_price,ds_product_quantity,ds_price_gross_amount,ds_price_slash;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dsProductName = (TextView)itemView.findViewById(R.id.ds_brand_name);
            dsProductweight = (TextView)itemView.findViewById(R.id.ds_weight);
            ds_product_price = (TextView)itemView.findViewById(R.id.ds_price);
            ds_product_quantity = (TextView)itemView.findViewById(R.id.ds_qnty_value);
            ds_Image = (ImageView)itemView.findViewById(R.id.ds_cart_image);
            ds_price_gross_amount = (TextView) itemView.findViewById(R.id.ds_price_gross);
            ds_price_slash = (TextView) itemView.findViewById(R.id.ds_price_slash);

//            cart_color_tv = (CircularTextView)itemView.findViewById(R.id.cart_colore_tv);
        }
    }
}
