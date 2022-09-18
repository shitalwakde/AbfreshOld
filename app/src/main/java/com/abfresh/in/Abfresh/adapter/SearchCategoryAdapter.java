package com.abfresh.in.Abfresh.adapter;

import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.callback.CategoryCallbackLisener;
import com.abfresh.in.Abfresh.model.CartModel;
import com.abfresh.in.Abfresh.model.ProductModel;
import com.abfresh.in.Abfresh.utils.RestClient;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.abfresh.in.Abfresh.activities.DashboardActivity.actionbar_cart_textview;
import static com.abfresh.in.Abfresh.fragments.ProductFragment.CAT_ADPT;
import static com.abfresh.in.Abfresh.fragments.ProductFragment.CAT_PROD_ADPT;
import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class SearchCategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    int type;
    CategoryCallbackLisener categoryCallbackLisener;
    ArrayList<ProductModel> mdata;
    public static final int ADD=1;
    public static final int REMOVE=2;
    SessionManagement sessionManagement;

    public SearchCategoryAdapter(int type, CategoryCallbackLisener categoryCallbackLisener, ArrayList<ProductModel> mdata) {
        this.type=type;
        this.categoryCallbackLisener=categoryCallbackLisener;
        this.mdata=mdata;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(type == CAT_ADPT){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.search_cat_item,parent,false);
            return new CategoryViewHolder(view);
        }else if (type == CAT_PROD_ADPT){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_adapter,parent,false);

            sessionManagement=new SessionManagement(parent.getContext());

            return new ProductViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductModel productModel = mdata.get(position);
        if(holder instanceof CategoryViewHolder){
            CategoryViewHolder categoryViewHolder = holder instanceof CategoryViewHolder ? ((CategoryViewHolder) holder) : null;
            Picasso.with(holder.itemView.getContext()).load(productModel.getCategory_icon()).into(categoryViewHolder.iv_cat_icon);
            categoryViewHolder.tv_cat_name.setText(productModel.getCategory_name());
            /*if(productModel.getCategory_id().equalsIgnoreCase(selectedId)){
                categoryViewHolder.tv_cat_name.setTextColor(Color.parseColor("#fdcd08"));
            }else{
                categoryViewHolder.tv_cat_name.setTextColor(Color.BLACK);
            }*/
        }else if(holder instanceof ProductViewHolder){
            ProductViewHolder productViewHolder = holder instanceof ProductViewHolder ? ((ProductViewHolder) holder) : null;
            Picasso.with(holder.itemView.getContext()).load(productModel.getProduct_image()).into(productViewHolder.iv_prod_image);
            productViewHolder.tv_prod_name.setText(productModel.getProduct_name());
            productViewHolder.tv_prod_weight.setText("Net wt: "+productModel.getProduct_weight());
            productViewHolder.price.setText("MRP: "+productModel.getProduct_amt());
            productViewHolder.strike_price.setText("MRP: "+productModel.getProduct_gross_amt());
            productViewHolder.tv_apply_offer.setText("Apply Offer MRP : "+productModel.getApply_offer_mrp());
            productViewHolder.tv_orders.setText("ORDERS : "+productModel.getNo_of_orders());
            if(productModel.getProduct_description().equalsIgnoreCase("")){
                productViewHolder.tv_prod_Desc.setVisibility(View.GONE);
            }
            productViewHolder.tv_prod_Desc.setText(productModel.getProduct_description());
            if(productModel.getDelivery_time().equalsIgnoreCase("")){
                productViewHolder.tv_delivery.setVisibility(View.GONE);
            }
            productViewHolder.tv_delivery_time.setText(productModel.getDelivery_time());

            if(productModel.getIs_in_cart().equals("No")){
                productViewHolder.rl_add.setVisibility(View.GONE);
                productViewHolder.ll_add.setVisibility(View.VISIBLE);
            }else {
                productViewHolder.tv_add.setText(productModel.getCart_qty());
                productViewHolder.rl_add.setVisibility(View.VISIBLE);
                productViewHolder.ll_add.setVisibility(View.GONE);
            }

            if(productModel.getProduct_stock_qty().equalsIgnoreCase("0")){
                productViewHolder.tv_addTocart.setVisibility(View.GONE);
                productViewHolder.tv_outOfStock.setVisibility(View.VISIBLE);
            }else {
                productViewHolder.tv_addTocart.setVisibility(View.VISIBLE);
                productViewHolder.tv_outOfStock.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_cat_icon;
        TextView tv_cat_name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_cat_icon=(ImageView)itemView.findViewById(R.id.iv_cat_icon);
            tv_cat_name=(TextView)itemView.findViewById(R.id.tv_cat_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryCallbackLisener.onClickCategoryCallbackLisener(mdata.get(getAdapterPosition()).getCategory_id());
                }
            });
        }
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_prod_image, iv_plus, iv_minus,iv_heart, iv_heart_outline, iv_share;
        TextView tv_prod_name, tv_prod_weight, price, strike_price, tv_prod_Desc, tv_delivery_time, tv_delivery, tv_addTocart,tv_outOfStock,
                tv_add, tv_apply_offer, tv_orders;
        RelativeLayout rl_add;
        LinearLayout ll_add;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_prod_image=(ImageView)itemView.findViewById(R.id.iv_prod_image);
            tv_prod_name=(TextView)itemView.findViewById(R.id.tv_prod_name);
            tv_prod_weight=(TextView)itemView.findViewById(R.id.tv_prod_weight);
            price=(TextView)itemView.findViewById(R.id.price);
            strike_price=(TextView)itemView.findViewById(R.id.strike_price);
            tv_prod_Desc=(TextView)itemView.findViewById(R.id.tv_prod_Desc);
            tv_delivery_time=(TextView)itemView.findViewById(R.id.tv_delivery_time);
            tv_delivery=(TextView)itemView.findViewById(R.id.tv_delivery);
            tv_addTocart=(TextView)itemView.findViewById(R.id.tv_addTocart);
            tv_outOfStock=(TextView)itemView.findViewById(R.id.tv_outOfStock);
            tv_add=(TextView)itemView.findViewById(R.id.tv_add);
            tv_orders=(TextView)itemView.findViewById(R.id.tv_orders);
            tv_apply_offer=(TextView)itemView.findViewById(R.id.tv_apply_offer);
            iv_plus=(ImageView)itemView.findViewById(R.id.iv_plus);
            iv_minus=(ImageView)itemView.findViewById(R.id.iv_minus);
            iv_heart_outline=(ImageView)itemView.findViewById(R.id.iv_heart_outline);
            iv_heart=(ImageView)itemView.findViewById(R.id.iv_heart);
            iv_share=(ImageView)itemView.findViewById(R.id.iv_share);
            rl_add=(RelativeLayout)itemView.findViewById(R.id.rl_add);
            ll_add=(LinearLayout) itemView.findViewById(R.id.ll_add);


            iv_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_heart_outline.setVisibility(View.VISIBLE);
                    iv_heart.setVisibility(View.GONE);
                }
            });

            iv_heart_outline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iv_heart.setVisibility(View.VISIBLE);
                    iv_heart_outline.setVisibility(View.GONE);
                }
            });

            iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);

                    shareIntent.setType("plain/text");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Hi there, ");
                    itemView.getContext().startActivity(Intent.createChooser(shareIntent, "Share using"));
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryCallbackLisener.onClickProductCallbackLisner(mdata.get(getAdapterPosition()).getCategory_id(), mdata.get(getAdapterPosition()).getProduct_id());
                }
            });


            iv_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeQty(getAdapterPosition(),ADD);
                }
            });



            tv_addTocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeQty(getAdapterPosition(),ADD);
                }
            });

            iv_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeQty(getAdapterPosition(),REMOVE);
                }
            });


        }

        private void changeQty(int adapterPosition,int type) {
//            mdata.get(getAdapterPosition()).setLoading(true);
            //notifyItemChanged(getAdapterPosition());
            int qty=mdata.get(adapterPosition).getCartQuantityInteger();
            if(type==ADD) {
                qty = qty + 1;
            }else if(type ==REMOVE) {
                qty = qty - 1;
            }else {
                qty = 0;
            }
            int maxQuantity = Integer.parseInt(mdata.get(getAdapterPosition()).getProduct_stock_qty());
            if(qty <= maxQuantity){
                addTocart(qty,getAdapterPosition(),mdata, type);
            }else{
                Toast.makeText(itemView.getContext(), "You can not add any more quantities for this product!", Toast.LENGTH_SHORT).show();
//                mdata.get(getAdapterPosition()).setLoading(false);
                notifyItemChanged(getAdapterPosition());
            }
        }


        public void addTocart(int qty, final int position, List<ProductModel> mdata, int type) {
            JsonObject jsonObject = new JsonObject();
            String tempid= Settings.Secure.getString(itemView.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            jsonObject.addProperty("temp_user_id", tempid);
            jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            jsonObject.addProperty("product_id",mdata.get(position).getProduct_id());
            jsonObject.addProperty("qty",qty);
            jsonObject.addProperty("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            jsonObject.addProperty("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            jsonObject.addProperty("via", "Android");
            if(type == ADD )
                new RestClient().getApiService().addToCart(jsonObject, getModelCallback(qty, position, mdata));
            else if(type == REMOVE) {
                if (qty > 0)
                    new RestClient().getApiService().addToCart(jsonObject, getModelCallback(qty, position, mdata));
                else
                    new RestClient().getApiService().removeFromCart(jsonObject, getModelCallback(qty, position, mdata));
            }
        }


        @NotNull
        private Callback<CartModel> getModelCallback(int qty, int position, List<ProductModel> mdata) {
            return new Callback<CartModel>() {
                @Override
                public void success(CartModel cartModel, Response response) {
                    if(cartModel.getSuccess().equals("1")){
                        mdata.get(position).setCart_qty(qty == 0? "" : String.valueOf(qty));
                        mdata.get(position).setIs_in_cart(qty==0?"No" : "Yes");
//                        tv_add.setText(String.valueOf(qty));
//                        fragmentCallbackLisener.checkoutCallbackLisener(cartModel.getCart_total_amt());
                        if(cartModel.getCart_count() !=null){
                            if(!cartModel.getCart_count().equals("0")){
                                actionbar_cart_textview.setVisibility(View.VISIBLE);
                                actionbar_cart_textview.setText(cartModel.getCart_count());
                            }else {
                                actionbar_cart_textview.setVisibility(View.GONE);
                            }
                        }
                        notifyItemChanged(position);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            };
        }


    }
}
