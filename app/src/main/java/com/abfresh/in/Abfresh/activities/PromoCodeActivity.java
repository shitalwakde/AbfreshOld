package com.abfresh.in.Abfresh.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.adapter.PromoCodeAdapter;
import com.abfresh.in.Abfresh.model.UserModel;
import com.abfresh.in.Abfresh.utils.RestClient;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class PromoCodeActivity extends AppCompatActivity {

    RecyclerView rv_notification;
    ImageView iv_back_arrow;
    SessionManagement sessionManagement;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_code);

        init();
    }


    private void init(){
        sessionManagement=new SessionManagement(PromoCodeActivity.this);
        rv_notification=(RecyclerView)findViewById(R.id.rv_notification);
        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);


        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    private void viewCouponCode(){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        String tempid= Settings.Secure.getString(PromoCodeActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        jsonObject.addProperty("temp_user_id", tempid);
        jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
        jsonObject.addProperty("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
        jsonObject.addProperty("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
        jsonObject.addProperty("via", "Android");


        new RestClient().getApiService().viewCouponCode(jsonObject, new Callback<UserModel>() {
            @Override
            public void success(UserModel userModel, Response response) {
                progressBar.setVisibility(View.GONE);
                if(userModel.getSuccess().equals("1")){
                    manageDetail(userModel);
                }else {
                    Toast.makeText(PromoCodeActivity.this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });
    }


    private void manageDetail(UserModel userModel){
        PromoCodeAdapter adapter = new PromoCodeAdapter(userModel.getCoupon_list());
        rv_notification.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        viewCouponCode();
    }
}