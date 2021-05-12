package com.abfresh.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.SliderLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.muddzdev.styleabletoast.StyleableToast;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.CustomRequest;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;

//import net.one97.paytm.nativesdk.PaytmSDK;
//import net.one97.paytm.nativesdk.dataSource.PaytmPaymentsUtilRepository;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class Proceed_To_Pay extends AppCompatActivity {
    Toolbar ptp_toolbar;
    private SliderLayout ptpDemoSlider;
    RelativeLayout cod_ll;
    LinearLayout wallet_checked_ll;
    SessionManagement sessionManagement;
    ProgressBar paytm_pb;
    CardView delivery_coupans;
    TextView ptp_item_tv,edit_slot_tv,ptp_delivery_slot,coupon_applied_tv,coupon_code_et,wallet_cash_tv;
    ImageView ptp_home_btn,un_checked_iv,checked_iv;
    Boolean checked=false;
    Boolean codeApply=false;
    int counter=0;
    String promoCode="";
    String walletMoney="0";
    String cartAmount="0";
    String couponTotalAmount="0";
    String walletTotalAmount="0";
    Button coupon_code_apply_btn,coupon_code_remove_btn,coupon_code_view_btn;
    TextView cong_msg,ptp_delivery_slot_date;
    TextView sub_total_tv_ptp,offer_disc_tv_ptp,mod_amount_tv_ptp,total_amount_ptp_tv,wallet_total_tv_ptp,delivery_charge_tv_ptp;
    LinearLayout pay_method_ll;
    RelativeLayout llwalletpay,llPaytm;
    public static FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.proceed_to_pay_layout);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Proceed_To_Pay.this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
//        cartAmount =  CartDiscription.totalAmount;
        cartAmount =  CartDiscription.totalWithDelivery;
        llPaytm = (RelativeLayout) findViewById(R.id.llPaytm);
        wallet_checked_ll = (LinearLayout) findViewById(R.id.wallet_checked_ll);
        paytm_pb = (ProgressBar) findViewById(R.id.paytm_pb);
        delivery_coupans = (CardView) findViewById(R.id.delivery_coupans);
        ptp_item_tv = (TextView) findViewById(R.id.ptp_item_tv);
        edit_slot_tv = (TextView) findViewById(R.id.edit_slot_tv);
        sub_total_tv_ptp = (TextView) findViewById(R.id.sub_total_tv_ptp);
        offer_disc_tv_ptp = (TextView) findViewById(R.id.offer_disc_tv_ptp);
        mod_amount_tv_ptp = (TextView) findViewById(R.id.mod_amount_tv_ptp);
        total_amount_ptp_tv = (TextView) findViewById(R.id.total_amount_ptp_tv);
        wallet_total_tv_ptp = (TextView) findViewById(R.id.wallet_total_tv_ptp);
        delivery_charge_tv_ptp = (TextView) findViewById(R.id.delivery_charge_tv_ptp);
        ptp_delivery_slot_date = (TextView) findViewById(R.id.ptp_delivery_slot_date);

        cong_msg = (TextView) findViewById(R.id.cong_msg);
        llwalletpay = (RelativeLayout) findViewById(R.id.llwalletpay);
        pay_method_ll = (LinearLayout) findViewById(R.id.pay_method_ll);
        delivery_charge_tv_ptp.setText(CartDiscription.deliveryCharges);
        if(delivery_charge_tv_ptp.getText().toString().equals("0")){
            cong_msg.setVisibility(View.VISIBLE);
        }else{
            cong_msg.setVisibility(View.GONE);
        }
        sub_total_tv_ptp.setText("₹"+ CartDiscription.totalAmount);
//        sub_total_tv_ptp.setText("₹"+ CartDiscription.totalWithDelivery);
//        total_amount_ptp_tv.setText("₹"+CartDiscription.totalAmount);
        total_amount_ptp_tv.setText("₹"+CartDiscription.totalWithDelivery);
//        view_cpupon_tv = (TextView) findViewById(R.id.view_cpupon_tv);
        ptp_delivery_slot = (TextView) findViewById(R.id.ptp_delivery_slot);
        coupon_applied_tv = (TextView) findViewById(R.id.coupon_applied_tv);
        wallet_cash_tv = (TextView) findViewById(R.id.wallet_cash_tv);
        ptp_home_btn = (ImageView) findViewById(R.id.ptp_home_btn);
        un_checked_iv = (ImageView) findViewById(R.id.un_checked_iv);
        checked_iv = (ImageView) findViewById(R.id.checked_iv);
        ptp_toolbar = (Toolbar)findViewById(R.id.ptp_toolbar);
        coupon_code_et = (TextView) findViewById(R.id.coupon_code_et);
        coupon_code_apply_btn = (Button) findViewById(R.id.coupon_code_apply_btn);
        coupon_code_remove_btn = (Button) findViewById(R.id.coupon_code_remove_btn);
        coupon_code_view_btn = (Button) findViewById(R.id.coupon_code_view_btn);
        setSupportActionBar(ptp_toolbar);
        getSupportActionBar().setTitle("");
        Log.w("PTPTAG","Delivery Date===>"+Utility.deliveryDay);
        ptp_delivery_slot_date.setText(Utility.deliveryDay);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        String item_count = String.valueOf(ChooseDelivery.product_quantity);
        if(item_count.equals("1")){
            ptp_item_tv.setText(item_count + " item will be delivered in 1 shipment");
        }else{
            ptp_item_tv.setText(item_count + " items will be delivered in 1 shipment");
        }
        ptp_delivery_slot.setText(Utility.deliverySlot);
//        int color = getResources().getColor(R.color.colorPrimary);
//        delivery_coupans.setCardBackgroundColor(color);
        wallet_checked_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(checked)){
                    checked = true;
                    counter =1;
                    checked_iv.setVisibility(View.VISIBLE);
                    un_checked_iv.setVisibility(View.GONE);
                    walletMoney = wallet_cash_tv.getText().toString().trim();
//                    wallet_total_tv_ptp.setText(walletMoney);
                    walletTotalAmount = walletMoney;
                    cod_ll.setVisibility(View.GONE);
                    int coupon_amount = Integer.parseInt(couponTotalAmount);
//                    int cart_amount = Integer.parseInt(CartDiscription.totalAmount);
                    int cart_amount = Integer.parseInt(CartDiscription.totalWithDelivery);
                    int total_amount_below = cart_amount - coupon_amount;
                    int walletAmount = Integer.parseInt(walletMoney);
                    if(walletAmount>=total_amount_below){
                        llwalletpay.setVisibility(View.VISIBLE);
//                        total_amount_below= walletAmount;
                        walletMoney = wallet_cash_tv.getText().toString().trim();
//                    wallet_total_tv_ptp.setText(walletMoney);
                        walletTotalAmount = walletMoney;
                        wallet_total_tv_ptp.setText("₹"+total_amount_below);
                        mod_amount_tv_ptp.setText("₹"+0);
                        total_amount_ptp_tv.setText("₹"+0);
                    }else{
                        walletMoney = wallet_cash_tv.getText().toString().trim();

//                    wallet_total_tv_ptp.setText(walletMoney);
                        walletTotalAmount = walletMoney;
                        llwalletpay.setVisibility(View.GONE);
                        total_amount_below= total_amount_below-walletAmount;
                        wallet_total_tv_ptp.setText("₹"+walletAmount);
                        mod_amount_tv_ptp.setText("₹"+total_amount_below);
                        total_amount_ptp_tv.setText("₹"+total_amount_below);
                    }


//                    cartAmount = catTotal;
                }else{
                    llwalletpay.setVisibility(View.GONE);
                    checked = false;
                    counter =0;
                    checked_iv.setVisibility(View.GONE);
                    un_checked_iv.setVisibility(View.VISIBLE);
                    walletMoney="0";
//                    wallet_total_tv_ptp.setText(walletMoney);
                    walletTotalAmount = walletMoney;
                    cod_ll.setVisibility(View.VISIBLE);
                    int coupon_amount = Integer.parseInt(couponTotalAmount);
//                    int cart_amount = Integer.parseInt(CartDiscription.totalAmount);
                    int cart_amount = Integer.parseInt(CartDiscription.totalWithDelivery);
                    int total_amount_below = cart_amount - coupon_amount;

                    int walletAmount = Integer.parseInt(walletMoney);
                    if(walletAmount>=total_amount_below){
//                                total_amount_below= walletAmount-total_amount_below;
                        wallet_total_tv_ptp.setText("₹"+total_amount_below);
                        mod_amount_tv_ptp.setText("₹"+0);
                        total_amount_ptp_tv.setText("₹"+0);
                    }else{
                        total_amount_below= total_amount_below-walletAmount;
                        wallet_total_tv_ptp.setText("₹"+walletAmount);
                        mod_amount_tv_ptp.setText("₹"+total_amount_below);
                        total_amount_ptp_tv.setText("₹"+total_amount_below);
                    }
                }
            }
        });
        cod_ll = (RelativeLayout)findViewById(R.id.cod_ll);
        cod_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(Proceed_To_Pay.this, null, null, true);
                progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.show();
//                cartAmount =  CartDiscription.totalAmount;
                cartAmount =  CartDiscription.totalWithDelivery;
                int wallet_amount = Integer.parseInt(walletTotalAmount);
                int coupon_amount = Integer.parseInt(couponTotalAmount);
                int cart_amount = Integer.parseInt(cartAmount);
                int cart_total = cart_amount - coupon_amount - wallet_amount;

                String totalCart = String.valueOf(cart_total);
                cartAmount = totalCart;


                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //start your activity here
                        placeOrderMethod();
//                        progressDialog.dismiss();

                    }

                }, 2000);


            }
        });
        llPaytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(Proceed_To_Pay.this, null, null, true);
                progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.show();
//                cartAmount =  CartDiscription.totalAmount;
                cartAmount =  CartDiscription.totalWithDelivery;
                int wallet_amount = Integer.parseInt(walletTotalAmount);
                int coupon_amount = Integer.parseInt(couponTotalAmount);
                int cart_amount = Integer.parseInt(cartAmount);
                int cart_total = cart_amount - coupon_amount - wallet_amount;
                String totalCart = String.valueOf(cart_total);
                cartAmount = totalCart;

                paytm_pb.setVisibility(View.VISIBLE);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //start your activity here
                        getCheckSum(progressDialog);
//                        progressDialog.dismiss();

                    }

                }, 2000);
            }
        });
        llwalletpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(Proceed_To_Pay.this, null, null, true);
                progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.show();
//                cartAmount =  CartDiscription.totalAmount;
                cartAmount =  CartDiscription.totalWithDelivery;
                int wallet_amount = Integer.parseInt(walletTotalAmount);
                int coupon_amount = Integer.parseInt(couponTotalAmount);
                int cart_amount = Integer.parseInt(cartAmount);
                int cart_total = cart_amount - coupon_amount - wallet_amount;
                String totalCart = String.valueOf(cart_total);
                cartAmount = totalCart;

                paytm_pb.setVisibility(View.VISIBLE);
                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //start your activity here
                        getCheckSum(progressDialog);
//                        progressDialog.dismiss();

                    }

                }, 2000);
            }
        });
        edit_slot_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });

        ptp_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        coupon_code_apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(coupon_code_et.getText().toString().trim().length()==0){
//                    Toast.makeText(Proceed_To_Pay.this, "Please Enter a Coupan Code", Toast.LENGTH_SHORT).show();
//                }else{
//                    applyCouponCode();
//                }
            }
        });

        coupon_code_view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.couponCode="";
               Intent intent = new Intent(Proceed_To_Pay.this,CouponCode.class);
               startActivity(intent);
//                coupon_code_apply_btn.setVisibility(View.VISIBLE);
//                coupon_code_view_btn.setVisibility(View.GONE);
            }
        });

        coupon_code_remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LayoutInflater factory = LayoutInflater.from(Proceed_To_Pay.this);
                final View deleteDialogView = factory.inflate(R.layout.remove_coupon_code_new, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(Proceed_To_Pay.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.rcc_yes_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                        removeCouponCode();

                    }
                });
                deleteDialogView.findViewById(R.id.rcc_no_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();

            }
        });
            Log.w("PTPTAG", "ChooseDelivery.online_payment.toString().trim() :"+ChooseDelivery.online_payment.toString().trim());
            Log.w("PTPTAG", "ChooseDelivery.wallet_payment.toString().trim() :"+ChooseDelivery.wallet_payment.toString().trim());
            Log.w("PTPTAG", "ChooseDelivery.cod_payment.toString().trim() :"+ChooseDelivery.cod_payment.toString().trim());
                if(ChooseDelivery.online_payment.toString().trim().equals("Yes")){
                    llPaytm.setVisibility(View.VISIBLE);
                }else{
                    llPaytm.setVisibility(View.GONE);
                }
                if(ChooseDelivery.wallet_payment.toString().trim().equals("Yes")){
//                    llwalletpay.setVisibility(View.VISIBLE);
                    pay_method_ll.setVisibility(View.VISIBLE);
                }else{
//                    llwalletpay.setVisibility(View.GONE);
                    pay_method_ll.setVisibility(View.GONE);
                }
                if(ChooseDelivery.cod_payment.toString().trim().equals("Yes")){
                    cod_ll.setVisibility(View.VISIBLE);
                }else{
                    cod_ll.setVisibility(View.GONE);
                }
    }

    private void removeCouponCode() {

        try {
            final ProgressDialog progressDialog = ProgressDialog.show(Proceed_To_Pay.this, null, null, true);
            progressDialog.setContentView(R.layout.custom_progress_dialog);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.show();
            JSONObject transHisObject = new JSONObject();
            transHisObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            transHisObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            transHisObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            transHisObject.put(  "promo_code",coupon_code_et.getText().toString().trim());

            Log.w("LTAG","transHisObject Details====>" +transHisObject);
            JsonObjectRequest copRequest = new JsonObjectRequest(Request.Method.POST, Utility.RemoveCoupons, transHisObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("LTAG","Login response====>" +response);

                        if((response.getInt("success"))==1){//
                            codeApply = false;
                            promoCode = "";
                            couponTotalAmount = "0";
                            offer_disc_tv_ptp.setText("₹"+couponTotalAmount);
                            int coupon_amount = Integer.parseInt(couponTotalAmount);
//                            int cart_amount = Integer.parseInt(CartDiscription.totalAmount);
                            int cart_amount = Integer.parseInt(CartDiscription.totalWithDelivery);
                            int total_amount_below = cart_amount - coupon_amount;
                            int walletAmount = Integer.parseInt(walletMoney);
                            if(walletAmount>=total_amount_below){
//                                total_amount_below= walletAmount-total_amount_below;
                                mod_amount_tv_ptp.setText("₹"+0);
                                total_amount_ptp_tv.setText("₹"+0);
                            }else{
                                total_amount_below= total_amount_below-walletAmount;
                                mod_amount_tv_ptp.setText("₹"+total_amount_below);
                                total_amount_ptp_tv.setText("₹"+total_amount_below);
                            }


                            Toast.makeText(Proceed_To_Pay.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            coupon_code_apply_btn.setVisibility(View.GONE);
                            coupon_code_remove_btn.setVisibility(View.GONE);//                            view_cpupon_tv.setVisibility(View.GONE);
                            coupon_code_et.setTextColor(getResources().getColor(R.color.colorgray));
                            coupon_code_view_btn.setVisibility(View.VISIBLE);
                            coupon_applied_tv.setVisibility(View.GONE);
                            coupon_code_et.setText("Apply Coupon code");
//                            cartAmount =  CartDiscription.totalAmount;
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //start your activity here

                                    progressDialog.dismiss();

                                }

                            }, 2000);

                        }else{
//                            no_data_ll_ccd.setVisibility(View.VISIBLE);
                            Toast.makeText(Proceed_To_Pay.this, response.getString("message"), Toast.LENGTH_SHORT).show();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("LTAG","Login JSONException====>" +e);
//                        loginPb.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        Toast.makeText(Proceed_To_Pay.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.w("LTAG","Login VolleyError====>" +error);
//                    loginPb.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    StyleableToast.makeText(Proceed_To_Pay.this,"Please try after some time", R.style.mySizeToast).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            }; copRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(copRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void applyCouponCode() {
        JSONObject transHisObject = new JSONObject();
        try {
            ProgressDialog progressDialog = new ProgressDialog(getApplicationContext());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.create();
            transHisObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            transHisObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            transHisObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            transHisObject.put( "promo_code",coupon_code_et.getText().toString().trim());
            transHisObject.put("delivery_location_id", Utility.Delivery_location_id);

            Log.w("LTAG","transHisObject Details====>" +transHisObject);
            JsonObjectRequest copRequest = new JsonObjectRequest(Request.Method.POST, Utility.ApplyCoupons, transHisObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("LTAG","Login response====>" +response);

                        if((response.getInt("success"))==1){//


                            codeApply = true;
                            promoCode = coupon_code_et.getText().toString().trim();
                            couponTotalAmount = String.valueOf(response.getInt("discount_amt"));
                            offer_disc_tv_ptp.setText("₹"+couponTotalAmount);

                            int coupon_amount = Integer.parseInt(couponTotalAmount);
//                            int cart_amount = Integer.parseInt(CartDiscription.totalAmount);
                            int cart_amount = Integer.parseInt(CartDiscription.totalWithDelivery);
                            int total_amount_below = cart_amount - coupon_amount;
                            int walletAmount = Integer.parseInt(walletMoney);
                            if(walletAmount>=total_amount_below){
//                                total_amount_below= walletAmount-total_amount_below;
                                mod_amount_tv_ptp.setText("₹"+0);
                                total_amount_ptp_tv.setText("₹"+0);
                            }else{
                                total_amount_below= total_amount_below-walletAmount;
                                mod_amount_tv_ptp.setText("₹"+total_amount_below);
                                total_amount_ptp_tv.setText("₹"+total_amount_below);
                            }

                                    Toast.makeText(Proceed_To_Pay.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            coupon_code_apply_btn.setVisibility(View.GONE);
//                            view_cpupon_tv.setVisibility(View.GONE);
                            coupon_code_remove_btn.setVisibility(View.VISIBLE);
                            coupon_code_et.setTextColor(getResources().getColor(R.color.colorGreen));
                            coupon_applied_tv.setVisibility(View.VISIBLE);

                            LayoutInflater factory = LayoutInflater.from(Proceed_To_Pay.this);
                            final View couponDialogView = factory.inflate(R.layout.dialog_coupon_code, null);
                            final AlertDialog couponDialog = new AlertDialog.Builder(Proceed_To_Pay.this).create();
                            couponDialog.setView(couponDialogView);
                            TextView textView = couponDialogView.findViewById(R.id.alert_tv_ptp);
                            textView.setText("Discount of "+ "₹ "+couponTotalAmount +" successfully applied");
                            couponDialogView.findViewById(R.id.ptp_yes_tv).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    couponDialog.dismiss();
                                }
                            });

                            couponDialog.show();

                            progressDialog.dismiss();

                        }else{
                            promoCode = "";
                            codeApply = false;
                            couponTotalAmount = "0";
//                            no_data_ll_ccd.setVisibility(View.VISIBLE);
                            Toast.makeText(Proceed_To_Pay.this, response.getString("message"), Toast.LENGTH_SHORT).show();
//                            coupon_code_apply_btn.setVisibility(View.VISIBLE);
//                            view_cpupon_tv.setVisibility(View.VISIBLE);
                            coupon_code_remove_btn.setVisibility(View.GONE);
                            coupon_applied_tv.setVisibility(View.GONE);

                            coupon_code_apply_btn.setVisibility(View.GONE);
                            coupon_code_view_btn.setVisibility(View.VISIBLE);
                            String message = response.getString("message");
                            LayoutInflater factory = LayoutInflater.from(Proceed_To_Pay.this);
                            final View couponInvalidDialogView = factory.inflate(R.layout.invalid_coupon_dialog, null);
                            final AlertDialog couponInvDialog = new AlertDialog.Builder(Proceed_To_Pay.this).create();
                            TextView message_tv = couponInvalidDialogView.findViewById(R.id.ialert_tv_ptp);
                            message_tv.setText(message);
                            couponInvDialog.setView(couponInvalidDialogView);
                            couponInvalidDialogView.findViewById(R.id.icd_yes_tv).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    couponInvDialog.dismiss();
                                }
                            });

                            couponInvDialog.show();

                            progressDialog.dismiss();
//                            StyleableToast.makeText(CouponCode.this,"Please try after some time", R.style.mySizeToast).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("LTAG","Login JSONException====>" +e);
//                        loginPb.setVisibility(View.GONE);
                        progressDialog.dismiss();
                        Toast.makeText(Proceed_To_Pay.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.w("LTAG","Login VolleyError====>" +error);
//                    loginPb.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    StyleableToast.makeText(Proceed_To_Pay.this,"Please try after some time", R.style.mySizeToast).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            }; copRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(copRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utility.couponCode.trim().length()!=0){
            coupon_code_apply_btn.setVisibility(View.VISIBLE);
            coupon_code_view_btn.setVisibility(View.GONE);
            coupon_code_et.setText(Utility.couponCode);
            Utility.couponCode="";
            applyCouponCode();

        }

        getWalletAmount();

    }

    private void getWalletAmount() {
        JSONObject walletObject = new JSONObject();
        try {
            walletObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            Log.w("WTAG","response====>" +walletObject);
            JsonObjectRequest walletRequest = new JsonObjectRequest(Request.Method.POST, Utility.getProfile, walletObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getInt("success")==1){
                            Log.w("WTAG","response====>" + response);
                            wallet_cash_tv.setText(response.getString("wallet"));
                        }else{
//                            wallet_balance_tv.setText("0");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        myaccount_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    myaccount_pb.setVisibility(View.GONE);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header= new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            }; walletRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(walletRequest);
        } catch (JSONException e) {
            e.printStackTrace();
//            myaccount_pb.setVisibility(View.GONE);

        }

//        JSONObject transHisObject = new JSONObject();
//        try {
//
//            transHisObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
//            transHisObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
//            transHisObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
//
//            Log.w("LTAG","transHisObject Details====>" +transHisObject);
//            JsonObjectRequest wallRequest = new JsonObjectRequest(Request.Method.POST, Utility.MyCoupons, transHisObject, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        Log.w("LTAG","Login response====>" +response);
//
//                        if((response.getInt("success"))==1){
//                            wallet_cash_tv.setText(response.getInt("wallet") + "");
//
//                        }else{
//                            wallet_cash_tv.setText("0");
////                            Toast.makeText(Proceed_To_Pay.this, "Please try after some time", Toast.LENGTH_SHORT).show();
////                            StyleableToast.makeText(CouponCode.this,"Please try after some time", R.style.mySizeToast).show();
//
//                        }
//                    } catch (JSONException e) {
//
////                        Toast.makeText(CouponCode.this, "Please try after some time", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }
//
//
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.w("LTAG","Login VolleyError====>" +error);
//
//
//                }
//            }){
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
//                    return headers;
//                }
//            }; wallRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            AppController.getInstance().addRequestInQueue(wallRequest);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void placeOrderMethod() {

        try {
            JSONObject poObject = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            poObject.put("temp_user_id",tempid);
            poObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            poObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            poObject.put("delivery_location_id", Utility.Delivery_location_id);
            poObject.put("payment_type","Cash On Delivery");
            poObject.put("delivery_slot",Utility.deliverySlot);
            poObject.put("delivery_slot_date", Utility.deliveryDay);
            poObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            poObject.put("promo_code",promoCode);
            poObject.put("wallet_pay",walletMoney);
//            poObject.put("wallet_pay",walletMoney);
            Log.w("PTPTAG====>",poObject+"");
            JsonObjectRequest poRequest = new JsonObjectRequest(Request.Method.POST, Utility.PlaceOrder, poObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("PTPTAG response====>",response+"");

                    try {

                        if(response.getInt("success")==1){

                            Toast.makeText(Proceed_To_Pay.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Proceed_To_Pay.this,ThanksPage.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Proceed_To_Pay.this, "Please try after some time", Toast.LENGTH_SHORT).show();

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
            };poRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(poRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getCheckSum(ProgressDialog progressDialog) {

        try {

            JSONObject poObject = new JSONObject();

//            poObject.put("TXN_AMOUNT",cartAmount);
//            poObject.put("TXN_AMOUNT",CartDiscription.totalWithDelivery);
            poObject.put("TXN_AMOUNT",total_amount_ptp_tv.getText().toString().trim());
            poObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            poObject.put("delivery_slot",Utility.deliverySlot);
            poObject.put("delivery_slot_date", Utility.deliveryDay);
            poObject.put("delivery_location_id",Utility.Delivery_location_id);
            poObject.put("delivery_charges",delivery_charge_tv_ptp.getText().toString());
            poObject.put("type","Order");
            poObject.put("promo_code",promoCode);
            poObject.put("wallet_pay",walletMoney);
//            poObject.put("wallet_pay",wallet_cash_tv.getText().toString().trim());
//            poObject.put("wallet_pay",walletMoney);
            Log.w("SHAN","SHAN====>"+poObject+"");
            JsonObjectRequest poRequest = new JsonObjectRequest(Request.Method.POST, Utility.getChecksum, poObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("SHAN","PTPTAG response====>"+response+"");
                    try {

                        if(response.getInt("success")==1){
                            HashMap<String, String> paramMap = new HashMap<String, String>();
                            JSONObject pObject = response.getJSONObject("body");
                            paramMap.put("MID", response.getString("MID"));
                            paramMap.put("ORDER_ID", response.getString("order_id"));
                            paramMap.put("TXN_Token", pObject.getString("txnToken"));
                            paramMap.put("TXN_AMOUNT", ""+response.getString("TXN_AMOUNT"));
                            paramMap.put("CALLBACK_URL", response.getString("CALLBACK_URL"));
//                            paramMap.put("CHECKSUMHASH", response.getJSONObject("body").getString("txnToken"));
//                            paramMap.put("CUST_ID", sessionManagement.getUserDetails().get("userid"));
//
//                            paramMap.put("CHANNEL_ID", response.getString("CHANNEL_ID"));
//                            paramMap.put("INDUSTRY_TYPE_ID", response.getString("INDUSTRY_TYPE_ID"));
//                            paramMap.put("WEBSITE", response.getString("WEBSITE"));


                            Log.w("SHAN","paramMap====>"+paramMap+"");

                            paytm_pb.setVisibility(View.GONE);
                            progressDialog.cancel();
//                            paramMap.put("CHECKSUMHASH", response.getString("CHECKSUMHASH"));
                            onStartTransaction(paramMap);
                        }else if(response.getInt("success")==2){
                            Intent intent = new Intent(Proceed_To_Pay.this,ThanksPage.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Proceed_To_Pay.this, "Please try after some time", Toast.LENGTH_SHORT).show();
                            paytm_pb.setVisibility(View.GONE);
                            progressDialog.cancel();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        paytm_pb.setVisibility(View.GONE);
                        progressDialog.cancel();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    paytm_pb.setVisibility(View.GONE);
                    progressDialog.cancel();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            };poRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(poRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            paytm_pb.setVisibility(View.GONE);
            progressDialog.cancel();
        }

    }




    @Override
    public void onBackPressed() {
        finish();
//;        Intent intent = new Intent(Proceed_To_Pay.this,Container_Main_Class.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        super.onBackPressed();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == android.R.id.home){
//            finish();
//
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    public void onStartTransaction( HashMap<String,String> paramMap ) {

        String host = "https://securegw.paytm.in/";
//         String host = "https://securegw-stage.paytm.in/";
       Log.w("SHAN", "Payment paramMap  " + paramMap.toString());
//        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+paramMap.get("ORDER_ID");
        String callBackUrl = paramMap.get("CALLBACK_URL");
//        PaytmOrder Order = new PaytmOrder(paramMap);
        PaytmOrder Order = new PaytmOrder(paramMap.get("ORDER_ID"),paramMap.get("MID"),paramMap.get("TXN_Token"),paramMap.get("TXN_AMOUNT"),callBackUrl);
//        PaytmPGService Service = PaytmPGService.getProductionService();
//        Service.initialize(Order,null);
//        Service.startPaymentTransaction(Proceed_To_Pay.this, true, true, new PaytmPaymentTransactionCallback() {
//            @Override
//            public void onTransactionResponse(Bundle bundle) {
//                Log.w("SHAN", "onTransaction inResponse = " +bundle);
//               if(bundle.getString("STATUS").equals("TXN_SUCCESS")){
//                   Log.w("SHAN", "onTransaction inResponse = success " );
//                   HashMap<String, String> paramMap = new HashMap<String, String>();
//                   paramMap.put("STATUS",bundle.getString("STATUS"));
//                   paramMap.put("CHECKSUMHASH",bundle.getString("CHECKSUMHASH"));
//                   paramMap.put("BANKNAME",bundle.getString("BANKNAME"));
//                   paramMap.put("ORDERID",bundle.getString("ORDERID"));
//                   paramMap.put("TXNAMOUNT",bundle.getString("TXNAMOUNT"));
//                   paramMap.put("TXNDATE",bundle.getString("TXNDATE"));
//                   paramMap.put("MID",bundle.getString("MID"));
//                   paramMap.put("TXNID",bundle.getString("TXNID"));
//                   paramMap.put("RESPCODE",bundle.getString("RESPCODE"));
//                   paramMap.put("PAYMENTMODE",bundle.getString("PAYMENTMODE"));
//                   paramMap.put("BANKTXNID",bundle.getString("BANKTXNID"));
//                   paramMap.put("CURRENCY",bundle.getString("CURRENCY"));
//                   paramMap.put("GATEWAYNAME",bundle.getString("GATEWAYNAME"));
//                   paramMap.put("RESPMSG",bundle.getString("RESPMSG"));
//                   updateStatus(paramMap);
//               }
//            }
//
//            @Override
//            public void networkNotAvailable() {
//
//            }
//
//            @Override
//            public void clientAuthenticationFailed(String inErrorMessage) {
//
//            }
//
//            @Override
//            public void someUIErrorOccurred(String inErrorMessage) {
//
//            }
//
//            @Override
//            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
//
//            }
//
//            @Override
//            public void onBackPressedCancelTransaction() {
//
//            }
//
//            @Override
//            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//
//            }
//        });
        TransactionManager transactionManager = new TransactionManager(Order, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
               Log.w("SHAN", "onTransaction inResponse = " +bundle);
               if(bundle.getString("STATUS").equals("TXN_SUCCESS")){
                   Log.w("SHAN", "onTransaction inResponse = success " );
                   HashMap<String, String> paramMap = new HashMap<String, String>();
                   paramMap.put("STATUS",bundle.getString("STATUS"));
                   paramMap.put("CHECKSUMHASH",bundle.getString("CHECKSUMHASH"));
//                    paramMap.put("BANKNAME",bundle.getString("BANKNAME"));
                   paramMap.put("BANKNAME","");
                   paramMap.put("ORDERID",bundle.getString("ORDERID"));
                   paramMap.put("TXNAMOUNT",bundle.getString("TXNAMOUNT"));
                   paramMap.put("TXNDATE",bundle.getString("TXNDATE"));
                   paramMap.put("MID",bundle.getString("MID"));
                   paramMap.put("TXNID",bundle.getString("TXNID"));
                   paramMap.put("RESPCODE",bundle.getString("RESPCODE"));
                   paramMap.put("PAYMENTMODE",bundle.getString("PAYMENTMODE"));
                   paramMap.put("BANKTXNID",bundle.getString("BANKTXNID"));
                   paramMap.put("CURRENCY",bundle.getString("CURRENCY"));
                   paramMap.put("GATEWAYNAME",bundle.getString("GATEWAYNAME"));
                   paramMap.put("RESPMSG",bundle.getString("RESPMSG"));
                   updateStatus(paramMap);
               }



            }

            @Override
            public void networkNotAvailable() {
               Log.w("SHAN", "Payment Transaction networkNotAvailable");
            }

            @Override
            public void onErrorProceed(String s) {
               Log.w("SHAN", "Payment Transaction onErrorLoadingWebPage inErrorMessage " + s);
            }

            @Override
            public void clientAuthenticationFailed(String s) {
               Log.w("SHAN", " Payment Transaction clientAuthenticationFailed inErrorMessage " + s);
            }

            @Override
            public void someUIErrorOccurred(String s) {
               Log.w("SHAN", " Payment someUIErrorOccurred " + s);
            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {
               Log.w("SHAN", " Payment onErrorLoadingWebPage 1 " + s);
               Log.w("SHAN", " Payment onErrorLoadingWebPage 2 " + s1);
            }

            @Override
            public void onBackPressedCancelTransaction() {
               Log.w("SHAN", " Payment onBackPressedCancelTransaction cancel ");
            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
               Log.w("SHAN", " Payment onTransactionCancel cancel ");
            }
        });
        transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
        transactionManager.startTransaction(this, 2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.w("SHAN" ," result code "+resultCode);
        // -1 means successful  // 0 means failed
        // one error is - nativeSdkForMerchantMessage : networkError
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    Log.w("SHAN", key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
                }
            }
//            Log.w("SHAN", " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
//            Log.w("SHAN", " data response - "+data.getStringExtra("response"));
//            Log.w("SHAN", " payment Successfull");
            try {
                Log.w("SHAN", " data "+  data.getStringExtra("nativeSdkForMerchantMessage"));
                Log.w("SHAN", " data response - "+data.getStringExtra("response"));
                Log.w("SHAN", " payment Successfull");
                JSONObject jsonObject=new JSONObject(data.getStringExtra("response"));

                if(jsonObject.getString("STATUS").equals("TXN_SUCCESS")){
                    Log.w("SHAN", "onTransaction inResponse = success " );
                    HashMap<String, String> paramMap = new HashMap<String, String>();
                    paramMap.put("STATUS",jsonObject.getString("STATUS"));
                    paramMap.put("CHECKSUMHASH",jsonObject.getString("CHECKSUMHASH"));
//                    paramMap.put("BANKNAME",jsonObject.getString("BANKNAME"));
                    paramMap.put("BANKNAME","");
                    paramMap.put("ORDERID",jsonObject.getString("ORDERID"));
                    paramMap.put("TXNAMOUNT",jsonObject.getString("TXNAMOUNT"));
                    paramMap.put("TXNDATE",jsonObject.getString("TXNDATE"));
                    paramMap.put("MID",jsonObject.getString("MID"));
                    paramMap.put("TXNID",jsonObject.getString("TXNID"));
                    paramMap.put("RESPCODE",jsonObject.getString("RESPCODE"));
                    paramMap.put("PAYMENTMODE",jsonObject.getString("PAYMENTMODE"));
                    paramMap.put("BANKTXNID",jsonObject.getString("BANKTXNID"));
                    paramMap.put("CURRENCY",jsonObject.getString("CURRENCY"));
                    paramMap.put("GATEWAYNAME",jsonObject.getString("GATEWAYNAME"));
                    paramMap.put("RESPMSG",jsonObject.getString("RESPMSG"));
                    updateStatus(paramMap);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

/*
 data response - {"BANKNAME":"WALLET","BANKTXNID":"1394221115",
 "CHECKSUMHASH":"7jRCFIk6eRmrep+IhnmQrlrL43KSCSXrmM+VHP5pH0ekXaaxjt3MEgd1N9mLtWyu4VwpWexHOILCTAhybOo5EVDmAEV33rg2VAS/p0PXdk\u003d",
 "CURRENCY":"INR","GATEWAYNAME":"WALLET","MID":"EAcP3138556","ORDERID":"100620202152",
 "PAYMENTMODE":"PPI","RESPCODE":"01","RESPMSG":"Txn Success","STATUS":"TXN_SUCCESS",
 "TXNAMOUNT":"2.00","TXNDATE":"2020-06-10 16:57:45.0","TXNID":"2020061011121280011018328631290118"}
  */
//            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage")
//                    + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }else{
            Log.w("SHAN", " payment failed");
        }

    }


    private void updateStatus(HashMap<String,String> poObject) {

        ProgressDialog  dialog=new ProgressDialog(Proceed_To_Pay.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait....");
         dialog.show();
         Log.w("SHAN", "Params "+poObject);
            CustomRequest poRequest = new CustomRequest(Request.Method.POST, Utility.payemntResponse, poObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("SHAN","SHAN response====>"+response+"");

                    try {

                        if(response.getInt("success")==1){
                            dialog.dismiss();
//                            Toast.makeText(Proceed_To_Pay.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Proceed_To_Pay.this,ThanksPage.class);
                            startActivity(intent);
                            finish();
                        }else{
                            dialog.dismiss();
                            Toast.makeText(Proceed_To_Pay.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        dialog.dismiss();
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // dialog.dismiss();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            };poRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(poRequest);



    }
}
