package com.abfresh.in.Abfresh.activities;

import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.adapter.WishlistAdapter;
import com.abfresh.in.Abfresh.model.CartModel;
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

public class WishlistActivity extends AppCompatActivity {

    TextView tv_toolbar_title;
    SessionManagement sessionManagement;
    ProgressBar progressBar;
    RecyclerView rv_wishlist;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        tv_toolbar_title=(TextView)findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("My Wishlist");
        sessionManagement=new SessionManagement(WishlistActivity.this);

        init();

    }


    private void init(){
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        rv_wishlist=(RecyclerView)findViewById(R.id.rv_wishlist);
        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        getWishList();
    }

    private void getWishList(){
        progressBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        String tempid= Settings.Secure.getString(WishlistActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        jsonObject.addProperty("temp_user_id", tempid);
        jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
        jsonObject.addProperty("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
        jsonObject.addProperty("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
        jsonObject.addProperty("via", "Android");

        new RestClient().getApiService().wishList(jsonObject, new Callback<CartModel>() {
            @Override
            public void success(CartModel cartModel, Response response) {
                progressBar.setVisibility(View.GONE);
                if(cartModel.getSuccess().equals("1")){
                    getWishlistAdpt(cartModel);
                }else {
                    Toast.makeText(WishlistActivity.this, cartModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });
    }


    private void getWishlistAdpt(CartModel cartModel){
        WishlistAdapter adapter = new WishlistAdapter(cartModel.getWishlist_list());
        rv_wishlist.setAdapter(adapter);
    }

}