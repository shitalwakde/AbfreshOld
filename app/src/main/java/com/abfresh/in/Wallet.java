package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class Wallet extends AppCompatActivity {

    Button upload_money_btn;
    TextView transaction_tv,wallet_balance_tv;
    SessionManagement sessionManagement;
    ProgressBar wallet_pb;
    ImageView wall_home_btn;
    public static FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_layout);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Wallet.this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        Toolbar toolbar=(Toolbar)findViewById(R.id.wallet_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
        wallet_pb = (ProgressBar)findViewById(R.id.wallet_pb);
        upload_money_btn = (Button)findViewById(R.id.upload_money_btn);
        transaction_tv = (TextView) findViewById(R.id.transaction_tv);
        wallet_balance_tv = (TextView) findViewById(R.id.wallet_balance_tv);
        wall_home_btn = (ImageView) findViewById(R.id.wall_home_btn);

        upload_money_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,Wallet_Recharge.class);
                startActivity(intent);
            }
        });
        transaction_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Wallet.this,TransactionHistory.class);
                startActivity(intent);
            }
        });

        wall_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWalletBalance();
    }

    private void getWalletBalance() {
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
                            wallet_pb.setVisibility(View.GONE);
                            wallet_balance_tv.setText("₹" + response.getString("wallet"));
                        }else{
//                            wallet_balance_tv.setText("0");
                            wallet_pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        wallet_pb.setVisibility(View.GONE);
//                        myaccount_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    myaccount_pb.setVisibility(View.GONE);
                    wallet_pb.setVisibility(View.GONE);
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
            wallet_pb.setVisibility(View.GONE);
//            myaccount_pb.setVisibility(View.GONE);

        }

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
}
