package com.abfresh.in.Abfresh.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.abfresh.in.R;


public class RewardActivity extends AppCompatActivity {

    ImageView iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        init();
    }

    private void init(){
        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);


        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}