package com.abfresh.in;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class FaqActivity extends AppCompatActivity {
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

        faq_home_btn = (ImageView)findViewById(R.id.faq_home_btn);
        
        
        faq_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
