package com.abfresh.in.Fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Settings;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import com.abfresh.in.CartDiscription;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.NewProductDiscription;
import com.abfresh.in.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class NewProductDiscriptionFragment extends Fragment {
    TextView item_name_pd,item_net_weight_pd,pd_price_gross,pd_product_disc_tv,seemore_tv,seeless_tv,pd_price,increment_tv_pd,pd_nop_text,pd_serves_text;
    ImageView pdProImage,pd_nop_image,pd_serves_image,pd_gw_image,pd_nw_image;
    Boolean isCheck= true;
    SessionManagement sessionManagement;
    Button btn_add_to_cart,btn_outofstock,updatecart_button,decrement_btn_pd,increment_btn_pd;
    LinearLayout already_in_cart_ll,llDeliveryTime;
    ProgressBar cat_pro_adp_pb;
    TextView nop_big_tv,cpd_gross_weight,cpd_net_weight,tvDeliveryTime;
//    TextView pd_big_price,between_line_tv_fnpd,between_line_tv_fnpd_big,gross_price_fnpd_big;
    TextView gross_price_fnpd;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_pro_dis,container,false);
        pdProImage = (ImageView)view.findViewById(R.id.pdProImage);
        pd_nop_image = (ImageView)view.findViewById(R.id.pd_nop_image);
        pd_serves_image = (ImageView)view.findViewById(R.id.pd_serves_image);
        pd_gw_image = (ImageView)view.findViewById(R.id.pd_gw_image);
        pd_nw_image = (ImageView)view.findViewById(R.id.pd_nw_image);
        cat_pro_adp_pb = (ProgressBar)view.findViewById(R.id.cat_pro_adp_pb);
        sessionManagement = new SessionManagement(getActivity());

        tvDeliveryTime = (TextView)view.findViewById(R.id.tvDeliveryTime);
        item_name_pd = (TextView)view.findViewById(R.id.item_name_pd);
        cpd_gross_weight = (TextView)view.findViewById(R.id.cpd_gross_weight);
        cpd_net_weight = (TextView)view.findViewById(R.id.cpd_net_weight);
        item_net_weight_pd = (TextView)view.findViewById(R.id.item_net_weight_pd);
        pd_price_gross = (TextView)view.findViewById(R.id.pd_price_gross);
        pd_product_disc_tv = (TextView)view.findViewById(R.id.pd_product_disc_tv);
        seemore_tv = (TextView)view.findViewById(R.id.seemore_tv);
        seeless_tv = (TextView)view.findViewById(R.id.seeless_tv);
//        pd_big_price = (TextView)view.findViewById(R.id.pd_big_price);
        pd_price = (TextView)view.findViewById(R.id.pd_price);
        increment_tv_pd = (TextView)view.findViewById(R.id.increment_tv_pd);
        nop_big_tv = (TextView)view.findViewById(R.id.nop_big_tv);
//        between_line_tv_fnpd = (TextView)view.findViewById(R.id.between_line_tv_fnpd);
//        between_line_tv_fnpd_big = (TextView)view.findViewById(R.id.between_line_tv_fnpd_big);

//        gross_price_fnpd_big = (TextView)view.findViewById(R.id.gross_price_fnpd_big);
//        gross_price_fnpd_big.setPaintFlags(gross_price_fnpd_big.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        gross_price_fnpd = (TextView)view.findViewById(R.id.gross_price_fnpd);
        gross_price_fnpd.setPaintFlags(gross_price_fnpd.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        pd_serves_text = (TextView)view.findViewById(R.id.pd_serves_text);
        pd_nop_text = (TextView)view.findViewById(R.id.pd_nop_text);
        already_in_cart_ll = (LinearLayout) view.findViewById(R.id.already_in_cart_ll);
        llDeliveryTime = (LinearLayout) view.findViewById(R.id.llDeliveryTime);

        btn_add_to_cart = (Button) view.findViewById(R.id.btn_add_to_cart);
        btn_outofstock = (Button) view.findViewById(R.id.btn_outofstock);
        updatecart_button = (Button) view.findViewById(R.id.updatecart_button);
        decrement_btn_pd = (Button) view.findViewById(R.id.decrement_btn_pd);
        increment_btn_pd = (Button) view.findViewById(R.id.increment_btn_pd);
//        productID = getActivity().getStr.getStringExtra("productId");
        getProductDiscription();
        seemore_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCheck) {
                    pd_product_disc_tv.setMaxLines(20);
                    isCheck = false;
                    seeless_tv.setVisibility(View.VISIBLE);
                    seemore_tv.setVisibility(View.GONE);
                }

            }
        });
        seeless_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCheck){
                    pd_product_disc_tv.setMaxLines(2);
                    isCheck = true;
                    seeless_tv.setVisibility(View.GONE);
                    seemore_tv.setVisibility(View.VISIBLE);
                }
            }
        });

                decrement_btn_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    int number = Integer.parseInt(increment_tv_pd.getText().toString()) ;
                    number = number -1;

                    if(number==0){
                        btn_add_to_cart.setVisibility(View.VISIBLE);
                        already_in_cart_ll.setVisibility(View.GONE);
                        removeFromCartPD(number);
                    }else{
                        String NewNumber = String.valueOf(number);
//                        Toast.makeText(Product_Discription.this,NewNumber, Toast.LENGTH_SHORT).show();
                        increment_tv_pd.setText(String.valueOf(number));
                        addToCartPD(number);
                    }


            }
        });
          increment_btn_pd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(increment_tv_pd.getText().toString()) ;
                number = number +1;
                String NewNumber = String.valueOf(number);
//                Toast.makeText(Product_Discription.this,NewNumber, Toast.LENGTH_SHORT).show();
                increment_tv_pd.setText(String.valueOf(number));
                addToCartPD(number);
            }
        });

        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number =1;
                        addToCartPD(number);

            }
        });

        updatecart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CartDiscription.class);
                startActivity(intent);
            }
        });

        return view;
    }
        private void removeFromCartPD(int number) {
        try {
            JSONObject rcobject = new JSONObject();

            String tempid= Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            rcobject.put("temp_user_id",tempid);
            rcobject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            rcobject.put(  "product_id",NewProductDiscription.productID);
            rcobject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            rcobject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("PDTAG", "JSONObject rcobject" + rcobject);
            JsonObjectRequest rcRequest = new JsonObjectRequest(Request.Method.POST, Utility.RemoveFromCart, rcobject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if ((response.getString("success")).equals("1")){
                            Log.w("PDTAG", "JSONObject response" + response);
                            Toast.makeText(getActivity(),response.getString("message"), Toast.LENGTH_SHORT).show();
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            int cartcount = Integer.parseInt(response.getString("cart_count"));
//                            invalidateOptionsMenu();

                            NewProductDiscription.textCartItemCount_npd.setText(response.getString("cart_count"));
                            ((NewProductDiscription)getContext()).invalidateOptionsMenu();
                            getProductDiscription();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

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


    private void addToCartPD(int number) {
        try {
//            pd_pb.setVisibility(View.VISIBLE);
            sessionManagement = new SessionManagement(getActivity());

            JSONObject pdJsonObjectnew = new JSONObject();
            String tempid= Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            pdJsonObjectnew.put("temp_user_id",tempid);
            pdJsonObjectnew.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            pdJsonObjectnew.put("product_id",NewProductDiscription.productID);
            pdJsonObjectnew.put("qty",number);
            pdJsonObjectnew.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            pdJsonObjectnew.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));

            Log.w("PDTAG","JSONObject pdCartObject"+pdJsonObjectnew);
            JsonObjectRequest pdRequestnew = new JsonObjectRequest(Request.Method.POST, Utility.AddToCart, pdJsonObjectnew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("PDTAG","JSONObject response"+response);

                    try {
                        if ((response.getString("success")).equals("1")){
//                                    Utility.CartCount = ++Utility.CartCount ;z


                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            NewProductDiscription.textCartItemCount_npd.setText(response.getString("cart_count"));

                            ((NewProductDiscription)getContext()).invalidateOptionsMenu();
//                            invalidateOptionsMenu();
//                            StyleableToast.makeText(Product_Discription.this, response.getString("message"), R.style.mySizeToast).show();
                            getProductDiscription();
//                            pd_pb.setVisibility(View.GONE);

                        }else{
//                            pd_pb.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        pd_pb.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Please Try after some time", Toast.LENGTH_SHORT).show();

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    pd_pb.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Please Try after some time", Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            }; pdRequestnew.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(pdRequestnew);
        } catch (JSONException e) {
            e.printStackTrace();
//            pd_pb.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Please Try after some time", Toast.LENGTH_SHORT).show();

        }
    }
    private void getProductDiscription() {
        try {

            cat_pro_adp_pb.setVisibility(View.VISIBLE);
            JSONObject pdObject = new JSONObject();
            sessionManagement = new SessionManagement(getContext());
            String tempid= Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            pdObject.put("temp_user_id",tempid);
            pdObject.put( "user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            pdObject.put(  "product_id", NewProductDiscription.productID);
            pdObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            pdObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("PDTAG", "response pdObject "+ pdObject);
            JsonObjectRequest pdRequest = new JsonObjectRequest(Request.Method.POST, Utility.ProductDetails,pdObject,new Response.Listener<JSONObject>(){
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("PDTAG", "response response "+response);
//
                        if ((response.getString("success")).equals("1")){
                            String productPrice,Productgross,ProductDiscount,productName,Productgrosswt,Productnetwt,productGrossAmountNew,productDiscountNew;
                            productName =  response.getString("product_name");
                            productGrossAmountNew =  response.getString("product_gross_amt");
                            productDiscountNew =  response.getString("product_discount");
                            productPrice = "₹"+ response.getString("product_amt");
                            Productgross = "₹"+ response.getString("product_gross_amt");
                            ProductDiscount = response.getString("product_discount")+"% Off";
                            Productgrosswt = response.getString("product_gross_weight");
                            Productnetwt = response.getString("product_weight");
                            tvDeliveryTime.setText(response.getString("delivery_time"));
                            if (response.getString("delivery_time").equals("")){
                                llDeliveryTime.setVisibility(View.GONE);
                            }else {
                                llDeliveryTime.setVisibility(View.VISIBLE);

                            }

                            gross_price_fnpd.setText("₹"+productGrossAmountNew);
//                            gross_price_fnpd_big.setText("₹"+productGrossAmountNew);
                            if(productDiscountNew.trim().equals("0")){
//                                gross_price_fnpd_big.setVisibility(View.GONE);
//                                between_line_tv_fnpd_big.setVisibility(View.GONE);
                                gross_price_fnpd.setVisibility(View.GONE);
//                                between_line_tv_fnpd.setVisibility(View.GONE);

                            }else{
//                                gross_price_fnpd_big.setVisibility(View.VISIBLE);
//                                between_line_tv_fnpd_big.setVisibility(View.VISIBLE);
                                gross_price_fnpd.setVisibility(View.VISIBLE);
//                                between_line_tv_fnpd.setVisibility(View.VISIBLE);
                            }

                            if(response.getString("product_stock_qty").equals("0")){
                                btn_add_to_cart.setVisibility(View.GONE);
                                btn_outofstock.setVisibility(View.VISIBLE);
                                already_in_cart_ll.setVisibility(View.GONE);
                                updatecart_button.setVisibility(View.GONE);
                            }else {
                                if (response.getString("is_in_cart").equals("No")){
                                    btn_add_to_cart.setVisibility(View.VISIBLE);
                                    already_in_cart_ll.setVisibility(View.GONE);
                                    updatecart_button.setVisibility(View.GONE);
                                }else {
                                    btn_add_to_cart.setVisibility(View.GONE);
                                    already_in_cart_ll.setVisibility(View.VISIBLE);
                                    updatecart_button.setVisibility(View.VISIBLE);
                                }

                                btn_outofstock.setVisibility(View.GONE);
                            }
//                            Productgrosswt = response.getString("product_discount");
//                            Productnetwt = response.getString("product_discount");
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
//                            setupBadge();
                            Log.w("PDTAG", "Utility.CartCount ===>inside "+ Utility.CartCount);

                            pd_price.setText(productPrice);
                            if(response.getString("cart_qty").length()!=0){
                                int productAmount = Integer.parseInt(response.getString("product_amt"));
                                int cartQuantity = Integer.parseInt(response.getString("cart_qty"));

                                String totalPrice = String.valueOf(productAmount * cartQuantity);
//                                pd_big_price.setText("₹"+ totalPrice);
                            }else{
//                                pd_big_price.setText(productPrice);
                            }
//                            cpd_gross_weight,cpd_net_weight
                            item_name_pd.setText(productName);
                            pd_price_gross.setText(Productgross);
//                            pd_discount.setText(ProductDiscount);
                            pd_price_gross.setText(Productgrosswt);
                            cpd_gross_weight.setText(Productgrosswt);
                            item_net_weight_pd.setText(Productnetwt);
                            cpd_net_weight.setText(Productnetwt);


                            if(response.getString("product_gross_amt").equals("0")){
                                pd_price_gross.setVisibility(View.GONE);
//                                pd_discount.setVisibility(View.GONE);
                            }else{
                                pd_price_gross.setVisibility(View.VISIBLE);
//                                pd_discount.setVisibility(View.VISIBLE);
                            }
                            pd_product_disc_tv.setText(response.getString("product_description"));

                            if(pd_product_disc_tv.getMaxLines()<=1){
                                seemore_tv.setVisibility(View.GONE);
                            }else{
                                seemore_tv.setVisibility(View.VISIBLE);

                            }
                            if(response.getString("product_description").length()==0){
                                seemore_tv.setVisibility(View.GONE);
                                pd_product_disc_tv.setVisibility(View.GONE);
                            }else{
                                seemore_tv.setVisibility(View.VISIBLE);
                                pd_product_disc_tv.setVisibility(View.VISIBLE);
                            }
                            Picasso.with(getActivity()).load(response.getString("product_image")).fit().into(pdProImage);
                            Animation animation2 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_image);
                            pdProImage.startAnimation(animation2);
                            if(response.getString("cart_qty").equals("")){
                                increment_tv_pd.setText("1");

                            }else{
                                increment_tv_pd.setText(response.getString("cart_qty"));

                            }
                            cat_pro_adp_pb.setVisibility(View.GONE);

                        }else{
                            cat_pro_adp_pb.setVisibility(View.GONE);
//                            StyleableToast.makeText(getActivity(),"Please try after some time", R.style.mySizeToast).show();
                            Toast.makeText(getActivity(), "Please Try after some time", Toast.LENGTH_SHORT).show();

                        }
                        if(response.getString("no_of_pieces").length()==0){
                            pd_nop_text.setText("0");
                            nop_big_tv.setText("0");
                        }else{
                            pd_nop_text.setText(response.getString("no_of_pieces"));
                            nop_big_tv.setText(response.getString("no_of_pieces"));
                        }

                        if(response.getString("serves").length()==0){
                            pd_serves_text.setText("0");
                        }else{
                            pd_serves_text.setText(response.getString("serves"));
                        }

                        Picasso.with(getActivity()).load(response.getString("no_of_pieces_icon")).fit().into(pd_nop_image);
                        Picasso.with(getActivity()).load(response.getString("serves_icon")).fit().into(pd_serves_image);
                        Picasso.with(getActivity()).load(response.getString("gross_weight_icon")).fit().into(pd_gw_image);
                        Picasso.with(getActivity()).load(response.getString("net_weight_icon")).fit().into(pd_nw_image);
//
                    } catch (JSONException e) {
                        e.printStackTrace();
                        cat_pro_adp_pb.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Please try after some time", Toast.LENGTH_SHORT).show();

                    }

                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {
                    cat_pro_adp_pb.setVisibility(View.GONE);

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            };pdRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(pdRequest);



        } catch (JSONException e) {
            e.printStackTrace();
            cat_pro_adp_pb.setVisibility(View.GONE);

        }
    }
}
