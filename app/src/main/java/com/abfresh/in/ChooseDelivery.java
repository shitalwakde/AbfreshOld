package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Adapter.ChooseDeliveryAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Fragments.DeliverySlotBottomSheetDialog;
import com.abfresh.in.Model.CartList;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class ChooseDelivery extends AppCompatActivity implements DeliverySlotBottomSheetDialog.BottomSheetListener{
    RecyclerView cd_rv;
    ChooseDeliveryAdapter cdtAdapter;
    ArrayList<CartList> cdLists;
    Toolbar dsToolbar;
    LinearLayout ds_Place_order_ll;
    RelativeLayout selct_slot_ll;
    TextView slot_tv,cd_total_price;
    SessionManagement sessionManagement;
    ProgressBar cd_pd;
    public static int product_quantity;
    ImageView dslot_home_btn;
    public static String online_payment,wallet_payment,cod_payment;
    public static FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_slot_layout);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(ChooseDelivery.this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        Utility.deliverySlot=null;
        dsToolbar = (Toolbar)findViewById(R.id.ds_toolbar);
        setSupportActionBar(dsToolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
        cd_rv = (RecyclerView)findViewById(R.id.delivery_sot_rv);

        cd_pd = (ProgressBar)findViewById(R.id.cd_pd);
        cd_pd.setVisibility(View.GONE);
        cd_total_price = (TextView)findViewById(R.id.cd_total_price);
        ds_Place_order_ll = (LinearLayout)findViewById(R.id.ds_Place_order_ll);
        slot_tv = (TextView)findViewById(R.id.slot_tv);
        dslot_home_btn = (ImageView) findViewById(R.id.dslot_home_btn);

        selct_slot_ll = (RelativeLayout)findViewById(R.id.selct_slot_ll);

        selct_slot_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliverySlotBottomSheetDialog bottomSheet = new DeliverySlotBottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(), "slotBottomSheet");

            }
        });


        ds_Place_order_ll.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.w("CDTAG  choodel===>",Utility.deliverySlot+"");


                if(Utility.deliverySlot==null){
//                    StyleableToast.makeText(ChooseDelivery.this,"Please Select Slots", R.style.mySizeToast).show();
                    Toast.makeText(ChooseDelivery.this, "Please Select Slots", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(ChooseDelivery.this,Proceed_To_Pay.class);
                    startActivity(intent);
//                    finish();
                }

            }
        });

        dslot_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.deliverySlot=null;
                finish();
            }
        });
        getSlotProductList();
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.deliverySlot=null;
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id =item.getItemId();
//        if(id== android.R.id.home){
//            finish();
//            Utility.deliverySlot=null;
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    private void getSlotProductList() {


        try {
            JSONObject cartListObject = new JSONObject();
            cd_pd.setVisibility(View.VISIBLE);
            sessionManagement = new SessionManagement(getApplicationContext());
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            cartListObject.put( "temp_user_id",tempid);
            cartListObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            cartListObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            cartListObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("CDTAG", "JSONObject cartListObject" + cartListObject);

            JsonObjectRequest clRequest = new JsonObjectRequest(Request.Method.POST, Utility.CartSummery, cartListObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("CDTAG", "JSONObject response" + response);

                        if ((response.getString("success")).equals("1")){
                            cdLists = new ArrayList<CartList>();
                            JSONArray clnewArray = response.getJSONArray("cart_list");
                            cd_total_price.setText("₹ " + response.getString("final_amt"));
                            CartDiscription.totalAmount=response.getString("final_amt");
                            product_quantity = response.getInt("cart_count");
                            for(int i=0;i<clnewArray.length();i++){
                                JSONObject newObject = clnewArray.getJSONObject(i);
                                String cart_id = newObject.getString("cart_id");
                                String product_qnty = newObject.getString("qty");
                                String product_name = newObject.getString("product_name");
                                String product_gross_amt = newObject.getString(""+"cart_gross_amt");
                                String product_discount = newObject.getString("product_discount");
//                                String product_amt = newObject.getString("product_amt");
                                String product_amt = newObject.getString("cart_amt");
                                String product_img = newObject.getString("product_image");
                                String product_wt = newObject.getString("product_weight");
                                cdLists.add(new CartList(cart_id,product_name,product_gross_amt,product_discount,product_amt,product_qnty,product_img,product_wt));
                            }
                            cdtAdapter = new ChooseDeliveryAdapter(getApplicationContext(), cdLists);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ChooseDelivery.this);
                            cd_rv.setLayoutManager(linearLayoutManager);
                            cd_rv.setAdapter(cdtAdapter);
                            cd_rv.setNestedScrollingEnabled(false);

                            online_payment = response.getString("is_online_payment");
                            wallet_payment = response.getString("is_wallet_payment");
                            cod_payment = response.getString("is_cod_payment");
                            cd_pd.setVisibility(View.GONE);

                        }else{
                            cd_pd.setVisibility(View.GONE);
//                            StyleableToast.makeText(ChooseDelivery.this,"Please try after some time", R.style.mySizeToast).show();
                            Toast.makeText(ChooseDelivery.this, "Please try after some time", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        cd_pd.setVisibility(View.GONE);
//                        StyleableToast.makeText(ChooseDelivery.this,"Please try after some time", R.style.mySizeToast).show();
                        Toast.makeText(ChooseDelivery.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cd_pd.setVisibility(View.GONE);

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            };clRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(clRequest);


        } catch (JSONException e) {
            e.printStackTrace();
            cd_pd.setVisibility(View.GONE);

        }

    }

    @Override
    public void onButtonClicked(String text) {
        slot_tv.setText(text);
//       dismissDialog();
    }

//    private void dismissDialog() {
//        dismissDialog();
//    }
}
