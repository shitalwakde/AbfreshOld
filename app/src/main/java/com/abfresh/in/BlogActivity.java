package com.abfresh.in;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abfresh.in.Controller.Utility;

public class BlogActivity extends AppCompatActivity {
    private WebView blog_webview;
    Toolbar blogToolbar;
    ProgressBar blog_pb;
    ImageView blog_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blog_activity);
        blogToolbar = (Toolbar)findViewById(R.id.blog_toolbar);
        blog_pb = (ProgressBar) findViewById(R.id.blog_pb);
        setSupportActionBar(blogToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        blog_home_btn = (ImageView)findViewById(R.id.blog_home_btn);
        blog_webview = (WebView)findViewById(R.id.blog_webview);
        blog_webview.setWebViewClient(new WebViewClient());
        blog_webview.loadUrl(Utility.BlogUrl);
        WebSettings webSettings = blog_webview.getSettings();
        webSettings.setJavaScriptEnabled(true);


        blog_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (blog_webview.canGoBack()) {
            blog_webview.goBack();
        } else {
            super.onBackPressed();
        }
        blog_pb.setVisibility(View.GONE);
    }

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
