package com.abfresh.in.Abfresh.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.activities.DashboardActivity;
import com.abfresh.in.Abfresh.callback.FragmentCallbackLisener;
import com.abfresh.in.Abfresh.model.CartModel;
import com.abfresh.in.Abfresh.model.WishListModel;
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
import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;


public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.MyViewHolder> {
    int type;
    ArrayList<WishListModel> mdata;
    public static final int ADD=1;
    public static final int REMOVE=2;
    SessionManagement sessionManagement;
    FragmentCallbackLisener fragmentCallbackLisener;

    public WishlistAdapter(ArrayList<WishListModel> mdata) {
        this.mdata=mdata;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_adpter, parent, false);
        sessionManagement=new SessionManagement(parent.getContext());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WishListModel wishListModel = mdata.get(position);
        Picasso.with(holder.itemView.getContext()).load(wishListModel.getProduct_image()).into(holder.iv_prod_image);
        holder.tv_prodName.setText(wishListModel.getProduct_name());
        holder.tv_prodWeight.setText("Net wt: "+wishListModel.getProduct_weight());
        holder.price.setText("MRP: "+wishListModel.getProduct_amt());
        holder.strike_price.setText("MRP: "+wishListModel.getProduct_gross_amt());

        if(wishListModel.getIs_in_whishlist() != null && wishListModel.getIs_in_whishlist().equals("No")){
            holder.iv_heart_outline.setVisibility(View.VISIBLE);
            holder.iv_heart.setVisibility(View.GONE);
        }else {
            holder.iv_heart.setVisibility(View.VISIBLE);
            holder.iv_heart_outline.setVisibility(View.GONE);
        }

        if(wishListModel.getIs_in_cart() != null && wishListModel.getIs_in_cart().equals("No")){
            holder.rl_add.setVisibility(View.GONE);
            holder.ll_add.setVisibility(View.VISIBLE);
        }else {
            holder.tv_add.setText(wishListModel.getCart_qty());
            holder.rl_add.setVisibility(View.VISIBLE);
            holder.ll_add.setVisibility(View.GONE);
        }

        if(wishListModel.getProduct_stock_qty().equalsIgnoreCase("0")){
            holder.tv_addTocart.setVisibility(View.GONE);
            holder.tv_outOfStock.setVisibility(View.VISIBLE);
        }else {
            holder.tv_addTocart.setVisibility(View.VISIBLE);
            holder.tv_outOfStock.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_close, iv_prod_image, iv_minus, iv_plus, iv_heart, iv_heart_outline;
        TextView  price, strike_price, tv_add, tv_prodName, tv_prodWeight, tv_addTocart, tv_outOfStock;
        RelativeLayout rl_heart, rl_add;
        LinearLayout ll_add;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_close=(ImageView)itemView.findViewById(R.id.iv_close);
            iv_prod_image=(ImageView)itemView.findViewById(R.id.iv_prod_image);
            iv_minus=(ImageView)itemView.findViewById(R.id.iv_minus);
            iv_plus=(ImageView)itemView.findViewById(R.id.iv_plus);
            iv_heart=(ImageView)itemView.findViewById(R.id.iv_heart);
            iv_heart_outline=(ImageView)itemView.findViewById(R.id.iv_heart_outline);
            price=(TextView)itemView.findViewById(R.id.price);
            strike_price=(TextView)itemView.findViewById(R.id.strike_price);
            tv_add=(TextView)itemView.findViewById(R.id.tv_add);
            tv_prodName=(TextView)itemView.findViewById(R.id.tv_prodName);
            tv_prodWeight=(TextView)itemView.findViewById(R.id.tv_prodWeight);
            tv_outOfStock=(TextView)itemView.findViewById(R.id.tv_outOfStock);
            tv_addTocart=(TextView)itemView.findViewById(R.id.tv_addTocart);
            rl_heart=(RelativeLayout)itemView.findViewById(R.id.rl_heart);
            rl_add=(RelativeLayout)itemView.findViewById(R.id.rl_add);
            ll_add=(LinearLayout)itemView.findViewById(R.id.ll_add);


            ll_add.setVisibility(View.VISIBLE);
            rl_heart.setVisibility(View.VISIBLE);
            iv_close.setVisibility(View.GONE);


            iv_heart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(itemView.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dlg_exit);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView tv_yes=(TextView)dialog.findViewById(R.id.tv_yes);
                    TextView tv_no=(TextView)dialog.findViewById(R.id.tv_no);
                    TextView tv_sure=(TextView)dialog.findViewById(R.id.tv_sure);

                    tv_sure.setText("Are you sure you want to remove this item from your wishlist?");

                    tv_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeFromWishList(getAdapterPosition(), mdata);
                            dialog.dismiss();
                        }
                    });

                    tv_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            iv_heart_outline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToWishList(getAdapterPosition(), mdata);
                }
            });


            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(itemView.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dlg_exit);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView tv_yes=(TextView)dialog.findViewById(R.id.tv_yes);
                    TextView tv_no=(TextView)dialog.findViewById(R.id.tv_no);
                    TextView tv_sure=(TextView)dialog.findViewById(R.id.tv_sure);

                    tv_sure.setText("Are you sure you want to remove this item from your Cart?");

                    tv_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addTocart(0,getAdapterPosition(),mdata, REMOVE);
                            dialog.dismiss();
                        }
                    });

                    tv_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

            tv_addTocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeQty(getAdapterPosition(),ADD);
                }
            });

            iv_plus.setOnClickListener(new View.OnClickListener() {
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
            int qty=mdata.get(adapterPosition).getQtyInteger();
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


        public void addTocart(int qty, final int position, List<WishListModel> mdata, int type) {
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
        private Callback<CartModel> getModelCallback(int qty, int position, List<WishListModel> mdata) {
            return new Callback<CartModel>() {
                @Override
                public void success(CartModel cartModel, Response response) {
                    if(cartModel.getSuccess().equals("1")){
                        mdata.get(position).setCart_qty(qty == 0? "" : String.valueOf(qty));
                        if(mdata.get(position).getQtyInteger() == 0){
                            mdata.remove(position);
                        }
                        if(mdata.size()==0){
                            itemView.getContext().startActivity(new Intent(itemView.getContext(), DashboardActivity.class));
                        }
//                        fragmentCallbackLisener.checkoutCallbackLisener(cartModel.getCart_total_amt());
                        if(cartModel.getCart_count() !=null){
                            actionbar_cart_textview.setText(cartModel.getCart_count());
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


        private void addToWishList(int position, ArrayList<WishListModel> mdata){
            JsonObject jsonObject = new JsonObject();
            String tempid= Settings.Secure.getString(itemView.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            jsonObject.addProperty("temp_user_id", tempid);
            jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            jsonObject.addProperty("product_id", mdata.get(position).getProduct_id());
            jsonObject.addProperty("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            jsonObject.addProperty("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            jsonObject.addProperty("via", "Android");

            new RestClient().getApiService().addToWishList(jsonObject, new Callback<CartModel>() {
                @Override
                public void success(CartModel cartModel, Response response) {
                    if(cartModel.getSuccess().equals("1")){
                        mdata.get(position).setIs_in_whishlist("Yes");
                        notifyItemChanged(position);
                        Toast.makeText(itemView.getContext(), "Added in wishlist successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(itemView.getContext(), cartModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }


        private void removeFromWishList(int position, ArrayList<WishListModel> mdata){
            JsonObject jsonObject = new JsonObject();
            String tempid= Settings.Secure.getString(itemView.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            jsonObject.addProperty("temp_user_id", tempid);
            jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            jsonObject.addProperty("wishlist_id", mdata.get(position).getWhishlist_id());
            jsonObject.addProperty("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            jsonObject.addProperty("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            jsonObject.addProperty("via", "Android");


            new RestClient().getApiService().removeProductFromWishlist(jsonObject, new Callback<CartModel>() {
                @Override
                public void success(CartModel cartModel, Response response) {
                    if(cartModel.getSuccess().equals("1")){
                        mdata.get(position).setIs_in_whishlist("No");
                        mdata.remove(position);
                        notifyItemChanged(position);
                        notifyDataSetChanged();
                        Toast.makeText(itemView.getContext(), "Removed from wishlist successfully", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(itemView.getContext(), cartModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }

    }




}
