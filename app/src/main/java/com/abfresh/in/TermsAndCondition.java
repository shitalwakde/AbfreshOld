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

public class TermsAndCondition extends AppCompatActivity {
    Toolbar tac_toolbar;
    ProgressBar tac_pb;
    ImageView tac_home_btn;
    TextView tanc_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tandc_layout);

        tac_toolbar = (Toolbar)findViewById(R.id.tac_toolbar);
        tac_pb = (ProgressBar) findViewById(R.id.tac_pb);
        tanc_tv = (TextView) findViewById(R.id.tanc_tv);
        setSupportActionBar(tac_toolbar);

        tac_home_btn = (ImageView)findViewById(R.id.tac_home_btn);

        tac_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        getTermsAndCond();
    }

    private void getTermsAndCond() {
        JsonObjectRequest walletRequest = new JsonObjectRequest(Request.Method.POST, Utility.TermsConditionUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getInt("success")==1){
                        Log.w("WTAG","response====>" + response);
                        tac_pb.setVisibility(View.GONE);
                        tanc_tv.setText(response.getString("desc"));
                    }else{
                        tac_pb.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    tac_pb.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tac_pb.setVisibility(View.GONE);
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
