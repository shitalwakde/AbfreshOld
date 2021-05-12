package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.abfresh.in.Controller.Utility;

public class ThanksPage extends AppCompatActivity {

    private ProgressBar thanks_pb;
    Button go_to_home_id,go_to_track_order;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thnaks_page);

        LottieAnimationView thank_icon = (LottieAnimationView)findViewById(R.id.thank_icon);
        thank_icon.playAnimation();
        go_to_home_id = (Button)findViewById(R.id.go_to_home_id);
        go_to_track_order = (Button)findViewById(R.id.go_to_track_order);

        go_to_home_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanksPage.this,Container_Main_Class.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // Add new Flag to start new Activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                Utility.fromThanks = true;
                startActivity(intent);
                finish();
            }
        });

        go_to_track_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThanksPage.this,MyOrders.class);
                Utility.fromThanks = true;
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ThanksPage.this,Container_Main_Class.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void startApp() {

        Intent intent = new Intent(ThanksPage.this,Container_Main_Class.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
//        mProgress.setVisibility(G);
    }


//    private void doWork() {
//        for (int progress=0; progress<150; progress+=3) {
//            try {
//                Thread.sleep(150);
//                thanks_pb.setProgress(progress);
//            } catch (Exception e) {
//                e.printStackTrace();
////                Timber.e(e.getMessage());
//            }
//        }
//
//    }
}
