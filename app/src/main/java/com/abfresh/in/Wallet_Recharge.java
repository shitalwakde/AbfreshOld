package com.abfresh.in;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abfresh.in.Abfresh.activities.WalletActivity;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.CustomRequest;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class Wallet_Recharge extends AppCompatActivity implements PaymentResultWithDataListener {
    private static final String TAG = "Wallet_Recharge";
    RadioGroup wr_radio_grp;
    RadioButton radioButton,wr_radio_btn1,wr_radio_btn2,wr_radio_btn3;
    EditText wr_amount_tv;
    Button btn_razorpay, btn_paytm;
    SessionManagement sessionManagement;
    ImageView wallrech_home_btn;
    public static FirebaseAnalytics mFirebaseAnalytics;
    Order order = null;
    String razorPay_OrderId="";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_recharge);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Wallet_Recharge.this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
        Toolbar toolbar=(Toolbar)findViewById(R.id.wallet_recharge_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        wr_radio_grp = (RadioGroup)findViewById(R.id.wr_radio_grp);
        wr_radio_btn1 = (RadioButton) findViewById(R.id.wr_radio_btn1);
        wr_radio_btn2 = (RadioButton) findViewById(R.id.wr_radio_btn2);
        wr_radio_btn3 = (RadioButton) findViewById(R.id.wr_radio_btn3);
        btn_paytm = (Button) findViewById(R.id.btn_paytm);
        btn_razorpay = (Button) findViewById(R.id.btn_razorpay);
        wallrech_home_btn = (ImageView) findViewById(R.id.wallrech_home_btn);

        wr_amount_tv = (EditText) findViewById(R.id.wr_amount_tv);

        wr_amount_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int radioId = wr_radio_grp.getCheckedRadioButtonId();
//                radioButton = findViewById(radioId);
//                radioButton.setChecked(false);
//                    wr_radio_grp.clearCheck();
                wr_radio_btn1.setChecked(false);
                wr_radio_btn2.setChecked(false);
                wr_radio_btn3.setChecked(false);
            }
        });
        wr_radio_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId!=0){
                    int radioId = wr_radio_grp.getCheckedRadioButtonId();

                    radioButton = findViewById(radioId);
                    Log.w("WRTAG===>",radioButton.getText()+"");
                    Log.w("WRTAG checkedId===>",checkedId+"");
                    wr_amount_tv.setText(radioButton.getText().toString().substring(2));
                }
            }
        });


        btn_razorpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wr_amount_tv.getText().toString().length()!=0){
                    final ProgressDialog progressDialog = ProgressDialog.show(Wallet_Recharge.this, null, null, true);
                    progressDialog.setContentView(R.layout.custom_progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    progressDialog.show();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //start your activity here
                            getWrCheckSum(progressDialog, "razorpay");
//                        progressDialog.dismiss();

                        }

                    }, 2000);

                }else {
                    Toast.makeText(Wallet_Recharge.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(wr_amount_tv.getText().toString().length()!=0){
                    final ProgressDialog progressDialog = ProgressDialog.show(Wallet_Recharge.this, null, null, true);
                    progressDialog.setContentView(R.layout.custom_progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    progressDialog.show();
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //start your activity here
                            getWrCheckSum(progressDialog, "paytm");
//                        progressDialog.dismiss();

                        }

                    }, 2000);

                }else {
                    Toast.makeText(Wallet_Recharge.this, "Please Enter Amount", Toast.LENGTH_SHORT).show();
                }
            }
        });

        wallrech_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Checkout.preload(getApplicationContext());

    }


    public void createOrder(HashMap<String, String> paramMap) {

    try {
            RazorpayClient razorpay = new RazorpayClient("rzp_test_eRad3o4HTL7VTk", "0WKVzNCjdF7dqKETnAhx001U");
            double finalAmount = Integer.parseInt(paramMap.get("TXN_AMOUNT"))*100;

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", finalAmount); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", paramMap.get("ORDER_ID"));


            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        order = razorpay.Orders.create(orderRequest);
                    } catch (RazorpayException e) {
                        e.printStackTrace();
                    }
                    if(order!=null)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startPayment(order.get("id"), order.get("amount"));
                        }
                    });
                }
            };
            new Thread(runnable).start();

        } catch (RazorpayException | JSONException e) {
            e.printStackTrace();
        }
    }


    public void startPayment(String order_id, int amount) {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay");
            options.put("description", "Demoing Charges");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order_id);//
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9766024504");
//            preFill.put("contact", "8788621395");

            options.put("prefill", preFill);

            co.open(activity, options);

            Log.w("SHAN","options paramMap====>"+options+"");
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }



    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        Gson gson = new Gson();
        String json = gson.toJson(paymentData);

        String abc = json;


        /*Toast.makeText(this, "orderId: "+paymentData.getOrderId()+"paymentId: "+paymentData.getPaymentId()+
                "userContact: "+paymentData.getUserContact()+"userEmail: "+paymentData.getUserEmail()+"signature: "+paymentData.getSignature(),
                Toast.LENGTH_SHORT).show();*/

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("STATUS", "TXN_SUCCESS");
        paramMap.put("CHECKSUMHASH","");
        paramMap.put("BANKNAME","");
        paramMap.put("ORDERID",razorPay_OrderId);
        paramMap.put("USERID",sessionManagement.getUserDetails().get(KEY_USERID));
        paramMap.put("TXNAMOUNT", wr_amount_tv.getText().toString().trim());
        paramMap.put("TXNDATE","");
        paramMap.put("MID","");
        paramMap.put("RAZORPAYORDERID",paymentData.getOrderId());
        paramMap.put("TXNID",paymentData.getPaymentId());
        paramMap.put("RESPCODE","");
        paramMap.put("PAYMENTMODE","Online");
        paramMap.put("BANKTXNID","");
        paramMap.put("CURRENCY","INR");
        paramMap.put("GATEWAYNAME","razorpay");
        paramMap.put("RESPMSG","");
        updateRazorPayStatus(paramMap);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Gson gson = new Gson();
        String json = gson.toJson(paymentData);

        String abc = json;
        //Toast.makeText(this, "Failed and cause: "+s, Toast.LENGTH_SHORT).show();

        HashMap<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("STATUS", "TXN_FAILED");
        paramMap.put("CHECKSUMHASH","");
        paramMap.put("BANKNAME","");
        paramMap.put("ORDERID",razorPay_OrderId);
        paramMap.put("USERID",sessionManagement.getUserDetails().get(KEY_USERID));
        paramMap.put("TXNAMOUNT", wr_amount_tv.getText().toString().trim());
        paramMap.put("TXNDATE","");
        paramMap.put("MID","");
        paramMap.put("RAZORPAYORDERID",paymentData.getOrderId());
        paramMap.put("TXNID",paymentData.getPaymentId());
        paramMap.put("RESPCODE","");
        paramMap.put("PAYMENTMODE","Online");
        paramMap.put("BANKTXNID","");
        paramMap.put("CURRENCY","INR");
        paramMap.put("GATEWAYNAME","razorpay");
        paramMap.put("RESPMSG","");
        updateRazorPayStatus(paramMap);
    }


    private void getWrCheckSum(ProgressDialog progressDialog, String paymentType) {
        try {

            JSONObject poObject = new JSONObject();
            poObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            poObject.put("delivery_location_id","");
            poObject.put("delivery_slot", "");
            poObject.put("delivery_slot_date", "");
            poObject.put("TXN_AMOUNT", wr_amount_tv.getText().toString().trim());
//            poObject.put("TXN_AMOUNT", "1");
            poObject.put("type","Wallet");
            poObject.put("promo_code","");
            poObject.put("wallet_pay","0");
            poObject.put("via","Android");
            Log.w("SHAN","SHAN====>"+poObject+"Url: "+Utility.getChecksum);
            JsonObjectRequest poRequest = new JsonObjectRequest(Request.Method.POST, Utility.getChecksum, poObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("SHAN","PTPTAG response====>"+response+"");

                    try {

                        if(response.getInt("success")==1){
                            progressDialog.cancel();
                            HashMap<String, String> paramMap = new HashMap<String, String>();
                            JSONObject pObject = response.getJSONObject("body");
                            paramMap.put("MID", response.getString("MID"));
                            paramMap.put("ORDER_ID", response.getString("order_id"));
                            paramMap.put("TXN_Token", pObject.getString("txnToken"));
                            paramMap.put("TXN_AMOUNT", ""+response.getString("TXN_AMOUNT"));
                            paramMap.put("CALLBACK_URL", response.getString("CALLBACK_URL"));
                            razorPay_OrderId =response.getString("order_id");

                            Log.w("SHAN","paramMap====>"+paramMap+"");

//                            paytm_pb.setVisibility(View.GONE);

//                            paramMap.put("CHECKSUMHASH", response.getString("CHECKSUMHASH"));

                            if(paymentType.equals("paytm")){
                                onStartTransaction(paramMap);
                            }else {
                                createOrder(paramMap);
                            }

                        }else{
                            progressDialog.cancel();
                            Toast.makeText(Wallet_Recharge.this, "Please try after some time", Toast.LENGTH_SHORT).show();
//                            paytm_pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        progressDialog.cancel();
                        e.printStackTrace();
//                        paytm_pb.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.cancel();
//                    paytm_pb.setVisibility(View.GONE);
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
            progressDialog.cancel();
//            paytm_pb.setVisibility(View.GONE);
        }
    }


    private void onStartTransaction(HashMap<String, String> paramMap) {
        String host = "https://securegw.paytm.in/";
//        String host = "https://securegw-stage.paytm.in/";
        Log.w("SHAN", "Payment paramMap  " + paramMap.toString());
//        String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+paramMap.get("ORDER_ID");
        String callBackUrl = paramMap.get("CALLBACK_URL");
//        PaytmOrder Order = new PaytmOrder(paramMap.get("ORDER_ID"),paramMap.get("MID"),paramMap.get("TXN_Token"),paramMap.get("TXN_AMOUNT"),paramMap.get("CALLBACK_URL"));
        PaytmOrder Order = new PaytmOrder(paramMap.get("ORDER_ID"),paramMap.get("MID"),paramMap.get("TXN_Token"),paramMap.get("TXN_AMOUNT"),callBackUrl);

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


    private void updateRazorPayStatus(HashMap<String, String> paramMap) {
        ProgressDialog dialog=new ProgressDialog(Wallet_Recharge.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait....");
        dialog.show();
        Log.w("SHAN", "Params "+paramMap +"url: "+Utility.razorpaypayemntResponse);
        CustomRequest poRequest = new CustomRequest(Request.Method.POST, Utility.razorpaypayemntResponse, paramMap, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.w("SHAN","SHAN response====>"+response+"");

                try {

                    if(response.getInt("success")==1){
                        dialog.dismiss();
                        Toast.makeText(Wallet_Recharge.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Wallet_Recharge.this,WalletActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(Wallet_Recharge.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                    Log.w("SHAN","SHAN JSONException====>"+e+"");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Log.w("SHAN","SHAN VolleyError====>"+error+"");
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


    private void updateStatus(HashMap<String, String> paramMap) {
        ProgressDialog dialog=new ProgressDialog(Wallet_Recharge.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait....");
        dialog.show();
        Log.w("SHAN", "Params "+paramMap +"url: "+Utility.payemntResponse);
        CustomRequest poRequest = new CustomRequest(Request.Method.POST, Utility.payemntResponse, paramMap, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.w("SHAN","SHAN response====>"+response+"");

                try {

                    if(response.getInt("success")==1){
                        dialog.dismiss();
                        Toast.makeText(Wallet_Recharge.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Wallet_Recharge.this,WalletActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        dialog.dismiss();
                        Toast.makeText(Wallet_Recharge.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    dialog.dismiss();
                    e.printStackTrace();
                    Log.w("SHAN","SHAN JSONException====>"+e+"");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                 dialog.dismiss();
                Log.w("SHAN","SHAN VolleyError====>"+error+"");
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



//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

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
//                    paramMap.put("BANKNAME",bundle.getString("BANKNAME"));
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


}
