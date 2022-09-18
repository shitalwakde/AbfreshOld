package com.abfresh.in.Abfresh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.adapter.DeliveryListAdapter;
import com.abfresh.in.Abfresh.callback.SaveAddrIdCallbackLisener;
import com.abfresh.in.Abfresh.model.CartModel;
import com.abfresh.in.Abfresh.model.DeliveryList;
import com.abfresh.in.Abfresh.utils.RestClient;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class DeliveryListActivity extends AppCompatActivity implements SaveAddrIdCallbackLisener {
    private static final String TAG = "DeliveryListActivity";
    private static final int CODE_DELIVERY_DETAIL_RESULT = 101;
    RecyclerView rv_deliveryList;
    SessionManagement sessionManagement;
    TextView tv_toolbar_title;
    Button btn_new_address;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_list);

        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        tv_toolbar_title=(TextView)findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Delivery List");

        init();
    }

    private void init(){
        sessionManagement=new SessionManagement(DeliveryListActivity.this);
        rv_deliveryList=(RecyclerView)findViewById(R.id.rv_deliveryList);
        btn_new_address=(Button)findViewById(R.id.btn_new_address);
        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_new_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeliveryListActivity.this, DeliveryDetailActivity.class);
                intent.putExtra("type","DeliveryList");
                startActivity(intent);

            }
        });
    }


    private void getDeliveryList(){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
        jsonObject.addProperty("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
        jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
        jsonObject.addProperty("via", "Android");

        new RestClient().getApiService().getDeliveryLocation(jsonObject, new Callback<CartModel>() {
            @Override
            public void success(CartModel cartModel, Response response) {
                if(cartModel.getSuccess().equals("1")){
                    manageDetail(cartModel.getDelivery_location_list());
                }else {
                    Toast.makeText(DeliveryListActivity.this, cartModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }


    private void manageDetail(ArrayList<DeliveryList> delivery_location_list){
        DeliveryListAdapter adapter=new DeliveryListAdapter(delivery_location_list, this);
        rv_deliveryList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDeliveryList();
    }

    @Override
    public void editAddrIdCallback(DeliveryList deliveryList) {
        Intent intent = new Intent(DeliveryListActivity.this, DeliveryDetailActivity.class);
        intent.putExtra("deliveryList", (Serializable) deliveryList);
        startActivityForResult(intent,CODE_DELIVERY_DETAIL_RESULT);
    }

    @Override
    public void saveAddrIdCallback(DeliveryList deliveryList) {
        Intent intent = new Intent(DeliveryListActivity.this, ShippingDetailActivity.class);
        intent.putExtra(AddAddressActivity.EXTRA_LOCATION_ID,deliveryList.getDelivery_location_id());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CODE_DELIVERY_DETAIL_RESULT){
            if(resultCode == RESULT_OK){
                getDeliveryList();
            }
        }
    }
}