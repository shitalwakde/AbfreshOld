package com.abfresh.in.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.abfresh.in.Custom.MyBounceInterpolator;
import com.abfresh.in.R;

import java.util.ArrayList;

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.ViewHolder>  {
    private ArrayList<com.abfresh.in.Model.ArrayList>CartProductList;
//    private java.util.ArrayList<com.tbd.abFresh.model.ArrayList> CartProductList;
    Context Context;
    public int num = 0;
    private OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onOfferClick(int position, ImageView offer_image);
    }
    //   private OnItemClickListner mListner;

//    public OfferAdapter(Context context, java.util.ArrayList<ArrayList>  cartProductList) {
//        CartProductList = cartProductList;
//        Context = context;
//    }

    public OfferAdapter(Context applicationContext, ArrayList<com.abfresh.in.Model.ArrayList> offerList) {
        CartProductList = offerList;
        Context = applicationContext;
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }
    @NonNull
    @Override
    public OfferAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_adapter_layout,parent,false);
        return new OfferAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferAdapter.ViewHolder holder, int position) {

//        holder.ds_product_quantity.setText(CartProductList.get(position).getPro_quantity());
//
        holder.offer_brand_name.setText(CartProductList.get(position).getQuantity());
////        holder.dsProductweight.setText(CartProductList.get(position).getProductWeight());
//
//        holder.ds_product_price.setText("₹."+CartProductList.get(position).getProduct_amt());
//
        Log.w("OFFTAG","Offer Image Adapter====>"+CartProductList.get(position).getWeight());
        Picasso.with(Context).load(CartProductList.get(position).getWeight()).fit().into(holder.offer_image);
////        holder.ds_Image.setImageResource(Integer.parseInt(CartProductList.get(position).getPro_image()));
//
//        holder.dsProductweight.setText(CartProductList.get(position).getPro_wt());
//
//        Log.w("CDATAG",(CartProductList.get(position).getPro_quantity()+CartProductList.get(position).getProduct_name()+CartProductList.get(position).getProduct_amt() + CartProductList.get(position).getPro_image()+CartProductList.get(position).getPro_wt()+""));

//        holder.cart_color_tv.setSolidColor("#3F51B5");
        final Animation myAnim = AnimationUtils.loadAnimation(Context, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        holder.offer_adpter_main_rl.startAnimation(myAnim);
    }

    @Override
    public int getItemCount() {
        return CartProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout offer_adpter_main_rl;
        ImageView offer_image;

        TextView offer_brand_name,offer_weight,offer_price,ds_product_quantity;

        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);

            offer_brand_name = (TextView)itemView.findViewById(R.id.offer_brand_name);
//            offer_weight = (TextView)itemView.findViewById(R.id.offer_weight);
//            offer_price = (TextView)itemView.findViewById(R.id.offer_price);
            offer_image = (ImageView)itemView.findViewById(R.id.offer_image);
            offer_adpter_main_rl = (RelativeLayout) itemView.findViewById(R.id.offer_adpter_main_rl);
         itemView.setOnClickListener(new View.OnClickListener() {
          @Override
         public void onClick(View v) {
              if(listner != null){
                  int position = getAdapterPosition();
                  if(position != RecyclerView.NO_POSITION){
                      listner.onOfferClick(position,offer_image);
                  }
              }
          }
        });
//            cart_color_tv = (CircularTextView)itemView.findViewById(R.id.cart_colore_tv);
        }
    }
}

