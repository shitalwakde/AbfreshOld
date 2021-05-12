package com.abfresh.in.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.CartDiscription;
import com.abfresh.in.Container_Main_Class;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.CartList;
import com.abfresh.in.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>  {
    private ArrayList<CartList>CartProductList;
    Activity activity;
    public int num = 0;
    Context Context;
    SessionManagement sessionManagement;
    String userid;
    HashMap<String, String> user;
    private OnItemClickListner mListner;

    public interface OnItemClickListner{
        void onCartIncrementClick(int position, TextView increment_tv_cart);
        void onCartDecrementClick(int position, TextView increment_tv_cart);
//        void onCartRemoveClick(int position);

    }
    public CartAdapter(Context context, ArrayList<CartList> cartProductList) {
        Context = context;
        CartProductList = cartProductList;

    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_cart_item_layout,parent,false);
        return new CartAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.increment_tv_cart.setText(CartProductList.get(position).getCart_qty_new());
        holder.cartProductName.setText(CartProductList.get(position).getProduct_name_new());
        holder.cart_product_gross_price.setText("₹"+CartProductList.get(position).getProduct_gross_amount_new());
        holder.cart_product_gross_price.setPaintFlags(holder.cart_product_gross_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        if(CartProductList.get(position).getProduct_discount_new().equals("0")){
            holder.slash_nci.setVisibility(View.GONE);
            holder.cart_product_gross_price.setVisibility(View.GONE);
        }else{
            holder.slash_nci.setVisibility(View.VISIBLE);
            holder.cart_product_gross_price.setVisibility(View.VISIBLE);
        }
//        holder.cartProductweight.setText(CartProductList.get(position).getProductWeight());
        holder.cart_product_number.setText(String.valueOf(position + 1));
        holder.cl_price_gross.setPaintFlags(holder.cl_price_gross.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.cart_dis_gross_wt.setText(CartProductList.get(position).getProduct_gross_wt_new());
        holder.cart_dis_piecies.setText(CartProductList.get(position).getProduct_piec_new());
        holder.cart_product_price.setText("₹"+CartProductList.get(position).getProduct_amt_new());
        holder.cartProductweight.setText(CartProductList.get(position).getProduct_wt_new());
        holder.removefromcart_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater factory = LayoutInflater.from(v.getContext());
                final View deleteDialogView = factory.inflate(R.layout.custom_delete_dialog, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(v.getContext()).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.cdd_yes_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                        holder.remove_pb.setVisibility(View.VISIBLE);
                        removeFromCart(v,position,holder.remove_pb);

                    }
                });
                deleteDialogView.findViewById(R.id.cdd_no_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
//                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("Alert!");
//
//                //Setting message manually and performing action on button click
//                builder.setMessage("Do you really want to Remove This Product?");
//                //This will not allow to close dialogbox until user selects an option
//                builder.setCancelable(false);
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
////                Toast.makeText(this, "positive button", Toast.LENGTH_SHORT).show();
//                        holder.remove_pb.setVisibility(View.VISIBLE);
//                        removeFromCart(v,position,holder.remove_pb);
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //  Action for 'NO' Button
//
//                        dialog.cancel();
//                    }
//                });
//
//                //Creating dialog box
//                AlertDialog alert = builder.create();
//                //Setting the title manually
//                //alert.setTitle("AlertDialogExample");
//                alert.show();


                 }
        });
//        if(CartProductList.get(position).getProduct_gross_amt().equals("0")){
//            holder.cl_price_gross.setVisibility(View.GONE);
//            holder.cl_discount.setVisibility(View.GONE);
//        }else{
//            holder.cl_price_gross.setVisibility(View.VISIBLE);
//            holder.cl_discount.setVisibility(View.VISIBLE);
//        }
//        holder.cart_color_tv.setSolidColor("#3F51B5");

    }




    private void removeFromCart(View v, int position, ProgressBar remove_pb) {
        try {
            JSONObject rcobject = new JSONObject();
            sessionManagement = new SessionManagement(Context);
            String tempid= Settings.Secure.getString(v.getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            rcobject.put("temp_user_id",tempid);
            rcobject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            rcobject.put(  "product_id",CartProductList.get(position).getCart_id_new());
            rcobject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            rcobject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));

            Log.w("RCTAG", "JSONObject rcobject" + rcobject);
            JsonObjectRequest rcRequest = new JsonObjectRequest(Request.Method.POST, Utility.RemoveFromCart, rcobject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if ((response.getString("success")).equals("1")){
                            Log.w("RCTAG", "JSONObject response" + response);
                            Toast.makeText(Context,response.getString("message"), Toast.LENGTH_SHORT).show();
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            int cartcount = Integer.parseInt(response.getString("cart_count"));

                            notifyDataSetChanged();
//                            removeFromCart(v,position);
//                            Context.startActivity(CartDiscription.class);
                            Intent intentremove = new Intent(v.getContext(), CartDiscription.class);
                            v.getContext().startActivity(intentremove);
                            ((CartDiscription) v.getContext()).finish();

                            if(cartcount==0){
                                Intent intent = new Intent(v.getContext(), Container_Main_Class.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                v.getContext().startActivity(intent);
                            }
                            remove_pb.setVisibility(View.GONE);

                        }else{
                            remove_pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        remove_pb.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    remove_pb.setVisibility(View.GONE);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            }; rcRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(rcRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return CartProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout instock_ll,increment_button_ll;

        TextView cartProductName,cartProductweight,cart_product_price,cart_product_number,cl_price_gross,cl_discount,increment_tv_cart;
        ImageView removefromcart_iv;
        RelativeLayout cl_cv;
        ImageView decrement_btn_cart,increment_btn_cart;
        TextView cart_dis_piecies,cart_dis_gross_wt,cart_product_gross_price,slash_nci;
        ProgressBar remove_pb;
        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);

            cartProductName = (TextView)itemView.findViewById(R.id.cart_product_name);
            cartProductweight = (TextView)itemView.findViewById(R.id.cart_product_weight);
            cart_product_price = (TextView)itemView.findViewById(R.id.cart_product_price);
            cart_product_number = (TextView)itemView.findViewById(R.id.cart_product_number);
            cl_price_gross = (TextView)itemView.findViewById(R.id.cl_price_gross);
            cl_discount = (TextView)itemView.findViewById(R.id.cl_discount);
            increment_tv_cart = (TextView)itemView.findViewById(R.id.increment_tv_cart);
            cart_dis_piecies = (TextView)itemView.findViewById(R.id.cart_dis_piecies);
            cart_dis_gross_wt = (TextView)itemView.findViewById(R.id.cart_dis_gross_wt);
            removefromcart_iv = (ImageView)itemView.findViewById(R.id.removefromcart_iv);
            remove_pb = (ProgressBar) itemView.findViewById(R.id.remove_pb);
            cl_cv = (RelativeLayout)itemView.findViewById(R.id.cl_cv);
            increment_button_ll = (LinearLayout) itemView.findViewById(R.id.increment_button_ll);
            decrement_btn_cart = (ImageView) itemView.findViewById(R.id.decrement_btn_cart);
            increment_btn_cart = (ImageView) itemView.findViewById(R.id.increment_btn_cart);
            cart_product_gross_price = (TextView) itemView.findViewById(R.id.cart_product_gross_price);
            slash_nci = (TextView) itemView.findViewById(R.id.slash_nci);
//            cart_color_tv = (CircularTextView)itemView.findViewById(R.id.cart_colore_tv);

            increment_btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCartIncrementClick(position,increment_tv_cart);
                        }
                    }
                }
            });


            decrement_btn_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onCartDecrementClick(position,increment_tv_cart);
                        }
                    }
                }
            });
//            removefromcart_iv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listner != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listner.onCartRemoveClick(position);
//                        }
//                    }
//                }
//            });
        }


    }
}
