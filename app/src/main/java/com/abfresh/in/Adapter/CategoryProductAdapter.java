package com.abfresh.in.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.IncrementCart;
import com.abfresh.in.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryProductAdapter extends RecyclerView.Adapter<CategoryProductAdapter.ViewHolder> {

private ArrayList<com.abfresh.in.Model.CatModel> productList;
 //------------------------------------Define OnItemClickListner (step 2)-------------------------------------------------------------------------
Context cContext;
private OnItemClickListner mListner;
    private int clastPosition = -1;
    int count=0;
    String productId;
    IncrementCart increment = null;
    SessionManagement sessionManagement;
    String userid;
    //------------------------------------Creating Interface for clicklistner(step 1)-------------------------------------------------------------------------

    public interface OnItemClickListner{
        void onCatProductClick(int position, ImageView productImage);
        void onAddCartClick(int position,TextView textView,LinearLayout linearLayout);
        void onCheckOutClick(int position);
        void onCartIncrement(int position, TextView increment_tv,TextView textView,TextView cpa_big_price);
        void onCartdecrement(int position, TextView increment_tv,TextView textView,LinearLayout linearLayout,Button buy_button,TextView cpa_big_price);
    }
 //------------------------------------Creating method that we call from main page (step 3)-------------------------------------------------------------------------

    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }


    public CategoryProductAdapter(Context context, ArrayList<com.abfresh.in.Model.CatModel> productList) {
        cContext = context;
        this.productList = productList;
        this.increment = increment;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_product_layout,parent,false);
//------------------------------------Define mListner in return (step 4)-------------------------------------------------------------------------
        return new CategoryProductAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.productName.setText(productList.get(position).getCatName());
        holder.gross_price_cpl.setText("₹"+productList.get(position).getCatGrossAmountNew());
        holder.gross_price_cpl.setPaintFlags(holder.gross_price_cpl.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.productPrice.setText("₹"+ productList.get(position).getCatproduct_amt());

        Picasso.with(cContext).load(productList.get(position).getCatImage()).fit().into(holder.productImage);
//        Animation animation2 = AnimationUtils.loadAnimation(cContext, R.anim.fade_in);
//        holder.productImage.startAnimation(animation2);
        holder.productGross.setPaintFlags(holder.productGross.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.pro_dic_name_tv.setText(productList.get(position).getCatdiscName());
        holder.pro_brief.setText(productList.get(position).getCatbrief());
        holder.tvDeliveryTime.setText(productList.get(position).getDelivery_time());

        if (productList.get(position).getDelivery_time().equals("")){
            holder.llDeliveryTime.setVisibility(View.GONE);
        }else {
            holder.llDeliveryTime.setVisibility(View.VISIBLE);

        }

        holder.item_gross_weight.setText(productList.get(position).getCatProductGwt());
        holder.item_net_weight.setText(productList.get(position).getCatProductwt());
        holder.nop_tv_cpl.setText(productList.get(position).getCatProduct_nop());

//        Log.d("CAtAdp","Name"+productList.get(position).getProName());
//        Log.d("CAtAdp","price"+productList.get(position).getProPrice());
//        Log.d("CAtAdp","image"+productList.get(position).getProImage());
//        Log.d("CAtAdp","gross amount"+productList.get(position).getProGross());
//        Log.d("CAtAdp","discount"+productList.get(position).getProDiscount());
//        Log.d("CAtAdp","gross wt"+productList.get(position).getProductGrosswt());
//        Log.d("CAtAdp","net wt"+productList.get(position).getProductwt());




        holder.cat_pro_adp_pb.setVisibility(View.GONE);
        if(productList.get(position).getCatProductStock().equals("0")){
            holder.buy_button.setVisibility(View.GONE);
            holder.outOfStock_button.setVisibility(View.VISIBLE);
            holder.inCart_ll.setVisibility(View.GONE);
        }else{
            holder.outOfStock_button.setVisibility(View.GONE);
            if(productList.get(position).getIs_in_cart().equals("No")){

                holder.inCart_ll.setVisibility(View.GONE);
                holder.checkout_button.setVisibility(View.GONE);
//                holder.inCart_ll.setVisibility(View.VISIBLE);
                holder.buy_button.setVisibility(View.VISIBLE);
            }else{
                holder.inCart_ll.setVisibility(View.VISIBLE);
                holder.buy_button.setVisibility(View.GONE);
                holder.checkout_button.setVisibility(View.GONE);
//                holder.buy_button.setVisibility(View.GONE);

                if(productList.get(position).getCart_qty().equals("")){
                    holder.increment_tv.setText("1");
                }else{
                    String Number = productList.get(position).getCart_qty();
                    holder.increment_tv.setText(Number);
                }
            }
        }
        if(productList.get(position).getCart_qty().length()!=0){
            int productAmount = Integer.parseInt(productList.get(position).getCatproduct_amt());
            int cartQuantity = Integer.parseInt(productList.get(position).getCart_qty());

            String totalPrice = String.valueOf(productAmount * cartQuantity);
            holder.cpa_big_price.setText("₹"+ totalPrice);
        }else{
            holder.cpa_big_price.setText("₹"+ productList.get(position).getCatproduct_amt());
        }
        if(productList.get(position).getCatDiscountNew().trim().equals("0")){
            holder.gross_price_cpl.setVisibility(View.GONE);
            holder.between_line_tv.setVisibility(View.GONE);
        }else{

            holder.gross_price_cpl.setVisibility(View.VISIBLE);
            holder.between_line_tv.setVisibility(View.VISIBLE);
        }
//        int color = holder.cpl_cv.getContext().getResources().getColor(R.color.colorPrimaryDark);
//        holder.cpl_cv.setCardBackgroundColor(color);
//        holder.item_gross_weight.setText(productList.get(position).getProductId());
//        Toast.makeText(cContext, productId, Toast.LENGTH_SHORT).show();
//        if(productList.get(position).getProGross().length()==0){
//
//            holder.productGross.setVisibility(View.GONE);
//            holder.ProductDiscount.setVisibility(View.GONE);
//        }else {
//            holder.productGross.setVisibility(View.VISIBLE);
//            holder.ProductDiscount.setVisibility(View.VISIBLE);
//        }

//            Animation animation = AnimationUtils.loadAnimation(cContext,
//                    (position > clastPosition-1) ? R.anim.up_from_bottom
//                            : R.anim.down_from_top);
//            holder.cpl_rl.startAnimation(animation);
//            clastPosition = position;
        Animation animation = AnimationUtils.loadAnimation(cContext, R.anim.up_from_bottom);
           holder.cpl_rl.startAnimation(animation);
        clastPosition = position;



    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
        TextView productName,productPrice,productGross,ProductDiscount,item_gross_weight,item_net_weight,increment_tv,cpa_big_price;
        LinearLayout item_price_ll,llDeliveryTime,inCart_ll;
        Button buy_button,outOfStock_button,decrement_btn,increment_btn,checkout_button;
        ProgressBar cat_pro_adp_pb;
        RelativeLayout cpl_rl;
        CardView cpl_cv;
        TextView nop_tv_cpl,pro_dic_name_tv,pro_brief,gross_price_cpl,between_line_tv,tvDeliveryTime;
 //------------------------------------Define OnItemClickListner inside View Holder (step 5)-------------------------------------------------------------------------

        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);

//            cpl_cv = (CardView) itemView.findViewById(R.id.cpl_cv);
            productImage = (ImageView)itemView.findViewById(R.id.item_image);
            productName = (TextView)itemView.findViewById(R.id.item_name);
            productPrice = (TextView)itemView.findViewById(R.id.item_price);
            productGross = (TextView)itemView.findViewById(R.id.item_price_gross);
            item_gross_weight = (TextView)itemView.findViewById(R.id.item_gross_weight);
            item_net_weight = (TextView)itemView.findViewById(R.id.item_net_weight);
            ProductDiscount = (TextView)itemView.findViewById(R.id.item_discount);
            increment_tv = (TextView)itemView.findViewById(R.id.increment_tv);
            gross_price_cpl = (TextView)itemView.findViewById(R.id.gross_price_cpl);
            between_line_tv = (TextView)itemView.findViewById(R.id.between_line_tv);

            cpa_big_price = (TextView)itemView.findViewById(R.id.cpa_big_price);
            nop_tv_cpl = (TextView)itemView.findViewById(R.id.nop_tv_cpl);

            tvDeliveryTime = (TextView)itemView.findViewById(R.id.tvDeliveryTime);
            pro_dic_name_tv = (TextView)itemView.findViewById(R.id.pro_dic_name_tv);
            pro_brief = (TextView)itemView.findViewById(R.id.pro_brief);
            item_price_ll = (LinearLayout)itemView.findViewById(R.id.item_price_ll);
            llDeliveryTime = (LinearLayout)itemView.findViewById(R.id.llDeliveryTime);
//            buy_button_ll = (LinearLayout)itemView.findViewById(R.id.buy_button_ll);
            buy_button = (Button) itemView.findViewById(R.id.buy_button);
            decrement_btn = (Button) itemView.findViewById(R.id.decrement_btn);
            increment_btn = (Button) itemView.findViewById(R.id.increment_btn);
            outOfStock_button = (Button) itemView.findViewById(R.id.outOfStock_button);
            checkout_button = (Button) itemView.findViewById(R.id.checkout_button);
            inCart_ll = (LinearLayout) itemView.findViewById(R.id.inCart_ll);
            cat_pro_adp_pb = (ProgressBar) itemView.findViewById(R.id.cat_pro_adp_pb);
            cpl_rl = (RelativeLayout) itemView.findViewById(R.id.cpl_rl);


//------------------------------------item click start (step 6)-------------------------------------------------------------------------
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCatProductClick(position,productImage);
                        }
                    }
                }
            });

            buy_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inCart_ll.setVisibility(View.VISIBLE);
                   buy_button.setVisibility(View.GONE);
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onAddCartClick(position,increment_tv,inCart_ll);
                        }
                    }
                }
            });


// //------------------------------------item click end-------------------------------------------------------------------------
            increment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCartIncrement(position,increment_tv,increment_tv, cpa_big_price);
                        }
                    }
                }
            });

            decrement_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCartdecrement(position,increment_tv,increment_tv,inCart_ll,buy_button, cpa_big_price);
                        }
                    }
                }
            });

            checkout_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCheckOutClick(position);
                        }
                    }
                }
            });
        }
    }
}
