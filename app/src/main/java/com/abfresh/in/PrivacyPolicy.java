package com.abfresh.in;

import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class PrivacyPolicy extends AppCompatActivity {
    private WebView pp_webview;
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
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        ppo_home_btn = (ImageView)findViewById(R.id.ppo_home_btn);
//        pp_webview = (WebView)findViewById(R.id.pp_webview);
//        pp_webview.setWebViewClient(new WebViewClient());
//        pp_webview.loadUrl(Utility.PrivacyUrl);
//        WebSettings webSettings = pp_webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        pp_webview.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                Log.d("Failure Url :" , failingUrl);
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                Log.d("Ssl Error:",handler.toString() + "error:" +  error);
//                handler.proceed();
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        });
//        pp_webview.getSettings().setJavaScriptEnabled(true);
//        pp_webview.getSettings().setLoadWithOverviewMode(true);
//        pp_webview.getSettings().setUseWideViewPort(true);
//        pp_webview.getSettings().setDomStorageEnabled(true);
//        pp_webview.loadUrl(Utility.PrivacyUrl);
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
//    @Override
//    public void onBackPressed() {
//        if (pp_webview.canGoBack()) {
//            pp_webview.goBack();
//        } else {
//            super.onBackPressed();
//        }
//        pp_pb.setVisibility(View.GONE);
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id =item.getItemId();
//        if(id== android.R.id.home){
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
