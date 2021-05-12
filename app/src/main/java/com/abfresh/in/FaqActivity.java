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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abfresh.in.Controller.Utility;

public class FaqActivity extends AppCompatActivity {
    private WebView faq_webview;
    Toolbar faq_toolbar;
    ProgressBar faq_pb;
    ImageView faq_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_layout);

        faq_toolbar = (Toolbar)findViewById(R.id.faq_toolbar);
        faq_pb = (ProgressBar) findViewById(R.id.faq_pb);
        setSupportActionBar(faq_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        faq_home_btn = (ImageView)findViewById(R.id.faq_home_btn);
//        faq_webview = (WebView)findViewById(R.id.faq_webview);
//        faq_webview.setWebViewClient(new WebViewClient());
//        faq_webview.loadUrl(Utility.FaqUrl);
//        WebSettings webSettings = faq_webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);


//        faq_webview.setWebViewClient(new WebViewClient() {
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
//        faq_webview.getSettings().setJavaScriptEnabled(true);
//        faq_webview.getSettings().setLoadWithOverviewMode(true);
//        faq_webview.getSettings().setUseWideViewPort(true);
//        faq_webview.getSettings().setDomStorageEnabled(true);
//        faq_webview.loadUrl(Utility.FaqUrl);
        
        
        
        faq_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        if (faq_webview.canGoBack()) {
//            faq_webview.goBack();
//        } else {
//            super.onBackPressed();
//        }
//        faq_pb.setVisibility(View.GONE);
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
