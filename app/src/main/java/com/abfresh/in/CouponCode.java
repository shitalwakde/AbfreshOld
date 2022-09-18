package com.abfresh.in;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.abfresh.in.Adapter.CouponAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class CouponCode extends AppCompatActivity {
    SessionManagement sessionManagement;
    ProgressBar ccd_pb;
    RecyclerView ccd_rv;
    CouponAdapter couponAdapter;
    java.util.ArrayList<ArrayList> CcList;
    ImageView iv_back_arrow;
    LinearLayout no_data_ll_ccd;
    TextView submit_mcoupon_tv, tv_toolbar_title;
    EditText manual_code_et;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coupon_custom_dialog);

        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        tv_toolbar_title=(TextView)findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Coupons");

        sessionManagement = new SessionManagement(getApplicationContext());
        ccd_rv = (RecyclerView)findViewById(R.id.ccd_rv);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        ccd_pb = (ProgressBar) findViewById(R.id.ccd_pb);
        no_data_ll_ccd = (LinearLayout) findViewById(R.id.no_data_ll_ccd);
        manual_code_et = (EditText) findViewById(R.id.manual_code_et);
        submit_mcoupon_tv = (TextView) findViewById(R.id.submit_mcoupon_tv);

        submit_mcoupon_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manual_code_et.getText().toString().trim().length()==0){
                    Toast.makeText(CouponCode.this, "Please Enter Coupon Code", Toast.LENGTH_SHORT).show();
                }else{
                    Utility.couponCode = manual_code_et.getText().toString().trim();
                    finish();
                }
            }
        });

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getCoupons();
    }

    private void getCoupons() {
        JSONObject transHisObject = new JSONObject();
        try {

            transHisObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            transHisObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            transHisObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));

            Log.w("LTAG","transHisObject Details====>" +transHisObject);
            JsonObjectRequest copRequest = new JsonObjectRequest(Request.Method.POST, Utility.MyCoupons, transHisObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("LTAG","Login response====>" +response);

                        if((response.getInt("success"))==1){
                            no_data_ll_ccd.setVisibility(View.GONE);
                            CcList = new java.util.ArrayList<>();
                            JSONArray jsonArrayTh = response.getJSONArray("coupon_list");
                            for(int i=0;i<jsonArrayTh.length();i++){
                                JSONObject jsonObjectTH = jsonArrayTh.getJSONObject(i);

                                String copId = jsonObjectTH.getString("id");
                                String copTitle = jsonObjectTH.getString("title");
                                String copMinOrder = jsonObjectTH.getString("minimum_order");
                                String promoCode = jsonObjectTH.getString("promo_code");
                                String copDis = jsonObjectTH.getString("discription");
                                String copImage =jsonObjectTH.getString("promo_img");
                                String copValidity = jsonObjectTH.getString("validity");
                                Log.w("IMTAG","Image response====>" +copImage);
                                CcList.add(new ArrayList(copId,copTitle,copMinOrder,promoCode,copDis,copImage,copValidity));
                            }
                            couponAdapter = new CouponAdapter(getApplicationContext(), CcList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CouponCode.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            ccd_rv.setLayoutManager(linearLayoutManager);
                            ccd_rv.setAdapter(couponAdapter);
                            ccd_pb.setVisibility(View.GONE);

                            couponAdapter.setOnItemClickListner(new CouponAdapter.OnItemClickListner() {
                                @Override
                                public void onCouponClick(int position) {
                                    Utility.couponCode = CcList.get(position).getPromoCode();
                                    finish();
                                }
                            });
                        }else{
                            no_data_ll_ccd.setVisibility(View.VISIBLE);
                            ccd_pb.setVisibility(View.GONE);
                            Toast.makeText(CouponCode.this, response.getString("message"), Toast.LENGTH_SHORT).show();
//                            StyleableToast.makeText(CouponCode.this,"Please try after some time", R.style.mySizeToast).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("LTAG","Login JSONException====>" +e);
//                        loginPb.setVisibility(View.GONE);
                        ccd_pb.setVisibility(View.GONE);
                        Toast.makeText(CouponCode.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.w("LTAG","Login VolleyError====>" +error);
//                    loginPb.setVisibility(View.GONE);
                    ccd_pb.setVisibility(View.GONE);
                    Toast.makeText(CouponCode.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            }; copRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(copRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
