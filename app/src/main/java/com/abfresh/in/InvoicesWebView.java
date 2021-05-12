package com.abfresh.in;

import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class InvoicesWebView extends AppCompatActivity {
    private WebView invoice_webview;
    Toolbar invoice_toolbar;
    ImageView invoice_home_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_web_view);
        String url = getIntent().getStringExtra("invoice_url_new");
       Log.w("INTAG",url);
        invoice_toolbar = (Toolbar)findViewById(R.id.invoice_toolbar);
        setSupportActionBar(invoice_toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        invoice_home_btn = (ImageView)findViewById(R.id.invoice_home_btn);
//        invoice_webview = (WebView)findViewById(R.id.invoice_webview);
//        invoice_webview.getSettings().setDomStorageEnabled(true); // Add this
//        invoice_webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        invoice_webview.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
//            }
//        });
//        invoice_webview.loadUrl(url);
//        WebSettings webSettings = invoice_webview.getSettings();
//        webSettings.setJavaScriptEnabled(true);



//        invoice_webview.setWebChromeClient(new WebChromeClient() {
//            public void onProgressChanged(WebView view, int progress) {
//
//                getApplicationContext().setTitle("Loading...");
//                activity.setProgress(progress * 100);
//
//                if (progress == 100)
//                    activity.setTitle(getResources().getString(R.string.app_name));
//            }
//        });

//        invoice_webview.setWebViewClient(new WebViewClient() {
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
//        invoice_webview.getSettings().setJavaScriptEnabled(true);
//        invoice_webview.getSettings().setLoadWithOverviewMode(true);
//        invoice_webview.getSettings().setUseWideViewPort(true);
//        invoice_webview.getSettings().setDomStorageEnabled(true);
//        invoice_webview.loadUrl(url);
        invoice_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

//    @Override
//    public void onBackPressed() {
//        if (invoice_webview.canGoBack()) {
//            invoice_webview.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
