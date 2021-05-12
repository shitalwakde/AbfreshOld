package com.abfresh.in.Adapter;

import android.content.Context;
import android.util.Log;
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
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.IncrementCart;
import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.ViewHolder> {

    private java.util.ArrayList<ArrayList> productList;
    private java.util.ArrayList<ArrayList> Exampleisfull;
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
        void onAddCartClick(int position);
        void onCartIncrement(int position, TextView increment_tv);
        void onCartdecrement(int position, TextView increment_tv);
    }
    //------------------------------------Creating method that we call from main page (step 3)-------------------------------------------------------------------------

    public void setOnItemClickListner(SearchItemAdapter.OnItemClickListner listner){
        mListner = listner;
    }


    public SearchItemAdapter(Context context, java.util.ArrayList<ArrayList> productList) {
        cContext = context;
        this.productList = productList;
        this.Exampleisfull = productList;
        this.increment = increment;

    }
//    public void filterList(java.util.ArrayList<ArrayList> filterdNames, EditText editTextSearch) {
////        this.productList = filterdNames;
////        notifyDataSetChanged();
//
//        for (ArrayList item : Exampleisfull) {
//            if (item.getProName().toLowerCase().contains(editTextSearch.getText().toString().trim())) {
//                this.productList = filterdNames;
//                notifyDataSetChanged();
//            }
//        }
//    }

//            public Filter getFilter()    {
//             return exampleFilter;
//                }
//
//    private Filter exampleFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            java.util.ArrayList<ArrayList> filteredList = new java.util.ArrayList<>();
//            if (constraint == null || constraint.length() == 0) {
//                filteredList.addAll(Exampleisfull);
//            } else {
//                String filterPattern = constraint.toString().toLowerCase().trim();
//                for (ArrayList item : Exampleisfull) {
//                    if (item.getProName().toLowerCase().contains(filterPattern)) {
//                        filteredList.add(item);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredList;
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            productList.clear();
//            productList.addAll((List) results.values);
//            notifyDataSetChanged();
//        }
//    };


        @NonNull
    @Override
    public SearchItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item_adapter_layout,parent,false);
//------------------------------------Define mListner in return (step 4)-------------------------------------------------------------------------
        return new SearchItemAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemAdapter.ViewHolder holder, int position) {

        holder.productName.setText(productList.get(position).getProName());
        holder.productPrice.setText("₹"+ productList.get(position).getProPrice());
        Picasso.with(cContext).load(productList.get(position).getProImage()).fit().into(holder.productImage);

//        holder.productGross.setPaintFlags(holder.productGross.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        holder.productGross.setText("₹."+productList.get(position).getProGross());
//        holder.ProductDiscount.setText("("+productList.get(position).getProDiscount()+"%)");
        holder.item_gross_weight.setText(productList.get(position).getProductGrosswt());
        holder.item_net_weight.setText(productList.get(position).getProductwt());
        holder.pro_dic_name_sia_tv.setText(productList.get(position).getProDiscount());
        holder.pro_brief_sia_tv.setText(productList.get(position).getProductStock());

        Log.w("SRATAG", "response catName "+productList.get(position).getProName());
        Log.w("SRATAG", "response catproduct_amt "+productList.get(position).getProPrice());
        Log.w("SRATAG", "response catProductGwt "+productList.get(position).getProductGrosswt());
        Log.w("SRATAG", "response catProductwt "+productList.get(position).getProductwt());
        Log.w("SRATAG", "response catdiscount "+productList.get(position).getProDiscount());
        Log.w("SRATAG", "response catProductStock "+productList.get(position).getProductStock());
        Log.w("SRATAG", "response catImage "+productList.get(position).getProImage());
//        Log.w("SRTAG", "response is_in_cart "+is_in_cart);
//        Log.w("SRTAG", "response cart_qty "+cart_qty);
//        Log.d("CAtAdp","Name"+productList.get(position).getProName());
//        Log.d("CAtAdp","price"+productList.get(position).getProPrice());
//        Log.d("CAtAdp","image"+productList.get(position).getProImage());
//        Log.d("CAtAdp","gross amount"+productList.get(position).getProGross());
//        Log.d("CAtAdp","discount"+productList.get(position).getProDiscount());
//        Log.d("CAtAdp","gross wt"+productList.get(position).getProductGrosswt());
//        Log.d("CAtAdp","net wt"+productList.get(position).getProductwt());




//        holder.cat_pro_adp_pb.setVisibility(View.GONE);
//        if(productList.get(position).getProductStock().equals("0")){
//            holder.buy_button.setVisibility(View.GONE);
//            holder.outOfStock_button.setVisibility(View.VISIBLE);
//        }else{
//            holder.outOfStock_button.setVisibility(View.GONE);
//            if(productList.get(position).getIs_in_cart().equals("No")){
//
//                holder.inCart_ll.setVisibility(View.GONE);
//                holder.buy_button.setVisibility(View.VISIBLE);
//            }else{
//                holder.inCart_ll.setVisibility(View.VISIBLE);
//                holder.buy_button.setVisibility(View.GONE);
//
//                if(productList.get(position).getCart_qty().equals("")){
//                    holder.increment_tv.setText("1");
//                }else{
//                    String Number = productList.get(position).getCart_qty();
//                    holder.increment_tv.setText(Number);
//                }
//            }
//        }


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
        TextView productName,productPrice,pro_dic_name_sia_tv,pro_brief_sia_tv,item_gross_weight,item_net_weight,increment_tv;
        LinearLayout item_price_ll,buy_button_ll,inCart_ll;
        Button buy_button,outOfStock_button,decrement_btn,increment_btn;
        ProgressBar cat_pro_adp_pb;
        RelativeLayout cpl_rl;
        //------------------------------------Define OnItemClickListner inside View Holder (step 5)-------------------------------------------------------------------------

        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);

            productImage = (ImageView)itemView.findViewById(R.id.search_item_image);
            productName = (TextView)itemView.findViewById(R.id.search_item_name);
            productPrice = (TextView)itemView.findViewById(R.id.search_item_price);
//            productGross = (TextView)itemView.findViewById(R.id.search_item_price_gross);
            item_gross_weight = (TextView)itemView.findViewById(R.id.search_item_gross_weight);
            item_net_weight = (TextView)itemView.findViewById(R.id.search_item_net_weight);
            pro_dic_name_sia_tv = (TextView)itemView.findViewById(R.id.pro_dic_name_sia_tv);
            pro_brief_sia_tv = (TextView)itemView.findViewById(R.id.pro_brief_sia_tv);
//            item_price_ll = (LinearLayout)itemView.findViewById(R.id.search_item_price_ll);
//            buy_button_ll = (LinearLayout)itemView.findViewById(R.id.search_item_buy_button_ll);
//            buy_button = (Button) itemView.findViewById(R.id.search_item_buy_button);
//            decrement_btn = (Button) itemView.findViewById(R.id.search_item_decrement_btn);
//            increment_btn = (Button) itemView.findViewById(R.id.search_item_increment_btn);
//            outOfStock_button = (Button) itemView.findViewById(R.id.search_item_outOfStock_button);
//            inCart_ll = (LinearLayout) itemView.findViewById(R.id.search_item_inCart_ll);
//            cat_pro_adp_pb = (ProgressBar) itemView.findViewById(R.id.search_item_adp_pb);
            cpl_rl = (RelativeLayout) itemView.findViewById(R.id.search_item_rl);


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

//            buy_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    inCart_ll.setVisibility(View.VISIBLE);
//                    buy_button.setVisibility(View.GONE);
//                    if(listner != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listner.onAddCartClick(position);
//                        }
//                    }
//                }
//            });


// //------------------------------------item click end-------------------------------------------------------------------------
//            increment_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listner != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listner.onCartIncrement(position,increment_tv);
//                        }
//                    }
//                }
//            });
//
//            decrement_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listner != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listner.onCartdecrement(position,increment_tv);
//                        }
//                    }
//                }
//            });
//        }
    }
    }
}
