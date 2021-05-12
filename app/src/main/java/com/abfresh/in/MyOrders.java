package com.abfresh.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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
import com.abfresh.in.Adapter.MyOrderAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.CartList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class MyOrders extends AppCompatActivity {
    RecyclerView mo_rv;
    Toolbar moToolbar;
    SessionManagement sessionManagement;
    ArrayList<CartList> mOrderLists;
    MyOrderAdapter myOrderAdapter;
    ProgressBar myorder_pb;
    TextView blank_text_mo;
    ImageView mord_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order);
        sessionManagement = new SessionManagement(getApplicationContext());

        moToolbar =  (Toolbar)findViewById(R.id.myorder_toolbar);
        setSupportActionBar(moToolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        mord_home_btn = (ImageView)findViewById(R.id.mord_home_btn);
        mo_rv = (RecyclerView)this.findViewById(R.id.my_order_rv);
        myorder_pb = (ProgressBar)findViewById(R.id.myorder_pb);
        blank_text_mo = (TextView)findViewById(R.id.blank_text_mo);


        mord_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.fromThanks){
                    finish();
                    Intent intent = new Intent(MyOrders.this,Container_Main_Class.class);
                    Utility.fromThanks=false;
                    startActivity(intent);

                }else{
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!(sessionManagement.isLoggedIn())){
            blank_text_mo.setVisibility(View.VISIBLE);
            myorder_pb.setVisibility(View.GONE);
        }else{
            blank_text_mo.setVisibility(View.GONE);
            getMyOrderList();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Utility.fromThanks){
            Intent intent = new Intent(MyOrders.this,Container_Main_Class.class);
            Utility.fromThanks=false;
            startActivity(intent);

        }

    }

    private void getMyOrderList() {


        try {
//            myorder_pb.setVisibility(View.VISIBLE);
            JSONObject moObject = new JSONObject();
            moObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            moObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            moObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            Log.w("MOTAG===>moObject",moObject+"");
            JsonObjectRequest moRequest = new JsonObjectRequest(Request.Method.POST, Utility.MyOrder, moObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {


                        if(response.getInt("success")==1){
                            mOrderLists = new ArrayList<>();
                            JSONArray moArray = response.getJSONArray("order_list");

                            for(int i=0;i<moArray.length();i++){

                                JSONObject moNewObject = moArray.getJSONObject(i);
                                String order_id = moNewObject.getString("order_id");
                                Log.w("MOTAG===>order_id",order_id+"");
                                String  order_number = moNewObject.getString("order_number");
                                Log.w("MOTAG===>order_number",order_number+"");
                                String  order_status = moNewObject.getString("order_status");
                                Log.w("MOTAG===>order_status",order_status+"");

                                String  order_payment_type = moNewObject.getString("order_payment_type_short");
                                String  order_amount = moNewObject.getString("order_amount");
                                String  order_date = moNewObject.getString("order_date");
//                                String  order_add = moNewObject.getString("store_name");
                                String delivery_location_city_name = moNewObject.getString("delivery_location_city_name");
                                String delivery_location_state_name = moNewObject.getString("delivery_location_state_name");
                                String delivery_username = moNewObject.getString("delivery_location_person_name");
                                String delivery_location_user_mobile = moNewObject.getString("delivery_location_person_mobile");
                                String delivery_location_pincode = moNewObject.getString("delivery_location_person_pincode");
                                String delivery_location_house_no = moNewObject.getString("delivery_location_person_house_no");
                                String delivery_location_area = moNewObject.getString("delivery_location_person_area");
                                String  order_add = delivery_username +","+ delivery_location_house_no
                                        +","+ delivery_location_area+","+ delivery_location_state_name+","+ delivery_location_city_name
                                        +","+  delivery_location_pincode+", M.No :"+delivery_location_user_mobile + "";


                                mOrderLists.add(new CartList(order_id,order_number,order_status,order_payment_type,order_amount,order_date,order_add));
                                Log.w("MOTAG===>order_status",mOrderLists+"");
                            }
                            Collections.reverse(mOrderLists);
                            myOrderAdapter = new MyOrderAdapter(getApplicationContext(), mOrderLists);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyOrders.this);

                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            linearLayoutManager.setStackFromEnd(true);
                            linearLayoutManager.setReverseLayout(true);
                            mo_rv.setLayoutManager(linearLayoutManager);

                            mo_rv.setAdapter(myOrderAdapter);
                            mo_rv.setNestedScrollingEnabled(false);
                            myOrderAdapter.setOnItemClickListner(new MyOrderAdapter.OnItemClickListner() {
                                @Override
                                public void onMyOrderListtClick(int position) {
                                    String orderId = mOrderLists.get(position).getCart_id();
                                    final ProgressDialog progressDialog = ProgressDialog.show(MyOrders.this, null, null, true);
                                    progressDialog.setContentView(R.layout.custom_progress_dialog);
                                    progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    progressDialog.show();


                                    Handler mHandler = new Handler();
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            //start your activity here



                                            Intent intent = new Intent(MyOrders.this, MyOrderDetails.class);
                                            intent.putExtra("orderId",orderId);
                                            startActivity(intent);
                                            myOrderAdapter.notifyDataSetChanged();
                                            progressDialog.dismiss();

                                        }

                                    }, 2000);
                                }

                                @Override
                                public void onCancelOrderRequest(int position) {

//                                    LayoutInflater factory = LayoutInflater.from(MyOrders.this);
//                                    final View deleteDialogView = factory.inflate(R.layout.confirm_dialog, null);
//                                    final AlertDialog deleteDialog = new AlertDialog.Builder(MyOrders.this).create();
//                                    deleteDialog.setView(deleteDialogView);
//                                    deleteDialogView.findViewById(R.id.yes_tv).setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            //your business logic
//
//
//                                        }
//                                    });
//                            deleteDialogView.findViewById(R.id.no_tv).setOnClickListener(new View.OnClickListener() {
//                              @Override
//                              public void onClick(View v) {
//                        deleteDialog.dismiss();
//                          }
//                         });
//
//                                    deleteDialog.show();

//=================================================================================================================
                                    LayoutInflater factory = LayoutInflater.from(MyOrders.this);
                                    final View deleteDialogView = factory.inflate(R.layout.cancel_order_custom_dialog, null);
                                    final AlertDialog deleteDialog = new AlertDialog.Builder(MyOrders.this).create();
                                    deleteDialog.setView(deleteDialogView);
                                    deleteDialogView.findViewById(R.id.cocd_yes_tv).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            JSONObject coObject = new JSONObject();
                                            String orderId = mOrderLists.get(position).getCart_id();
                                            try {

                                                coObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
                                                coObject.put("reason","No Data");
                                                coObject.put("order_id",orderId);
                                                Log.w("COTAG===>coObject",coObject+"");
                                                JsonObjectRequest cojsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Utility.CancelledOrder, coObject, new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            if(response.getInt("success")==1){
                                                                deleteDialog.dismiss();
                                                                Toast.makeText(MyOrders.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                                                getMyOrderList();


                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {

                                                    }
                                                }){
                                                    @Override
                                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                                        HashMap<String,String> header = new HashMap<>();
                                                        header.put(Utility.ServerUsername,Utility.ServerPassword);
                                                        return header;
                                                    }
                                                };cojsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                                AppController.getInstance().addRequestInQueue(cojsonObjectRequest);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                    deleteDialogView.findViewById(R.id.cocd_no_tv).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteDialog.dismiss();
                                        }
                                    });
                                    deleteDialog.show();

                                }
                            });
                            myorder_pb.setVisibility(View.GONE);
                            blank_text_mo.setVisibility(View.GONE);

                        }else{
                            myorder_pb.setVisibility(View.GONE);
//                            Toast.makeText(MyOrders.this, "Please try after some time", Toast.LENGTH_SHORT).show();
                            blank_text_mo.setVisibility(View.VISIBLE);
//                            myorder_pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MyOrders.this, "No Data Available", Toast.LENGTH_SHORT).show();
                        myorder_pb.setVisibility(View.GONE);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myorder_pb.setVisibility(View.GONE);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            };moRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(moRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            myorder_pb.setVisibility(View.GONE);
        }


    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == android.R.id.home){
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//}
}
