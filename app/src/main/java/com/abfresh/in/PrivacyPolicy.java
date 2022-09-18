package com.abfresh.in;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PrivacyPolicy extends AppCompatActivity {
    Toolbar pp_toolbar;
    ProgressBar pp_pb;
    ImageView ppo_home_btn;
    TextView pp_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacy_layout);
        pp_toolbar = (Toolbar)findViewById(R.id.pp_toolbar);
        pp_pb = (ProgressBar) findViewById(R.id.pp_pb);
        pp_tv = (TextView) findViewById(R.id.pp_tv);
        setSupportActionBar(pp_toolbar);

        ppo_home_btn = (ImageView)findViewById(R.id.ppo_home_btn);

        ppo_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPrivacyPolicy();
    }

    private void getPrivacyPolicy() {
//        JSONObject walletObject = new JSONObject();
        //            walletObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
//            Log.w("WTAG","response====>" +walletObject);
        JsonObjectRequest walletRequest = new JsonObjectRequest(Request.Method.POST, Utility.PrivacyUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("success")==1){
                        Log.w("WTAG","response====>" + response);
                        pp_pb.setVisibility(View.GONE);
                        pp_tv.setText(response.getString("desc"));
                    }else{
//                            wallet_balance_tv.setText("0");
                        pp_pb.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    pp_pb.setVisibility(View.GONE);
//                        myaccount_pb.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                    myaccount_pb.setVisibility(View.GONE);
                pp_pb.setVisibility(View.GONE);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header= new HashMap<>();
                header.put(Utility.ServerUsername,Utility.ServerPassword);
                return header;
            }
        };
        walletRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addRequestInQueue(walletRequest);
    }

}
