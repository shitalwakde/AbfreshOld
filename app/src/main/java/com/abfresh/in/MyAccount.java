package com.abfresh.in;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

//import com.example.darubottle.R;

public class MyAccount extends AppCompatActivity {
    SessionManagement session;
    Button logout_btn;
    LinearLayout join_now_adv,count_ll;
    TextView account_mobile,account_name,account_email,ma_wallet_balance,order_count_tv, tv_toolbar_title;
    CircleImageView  profile_Image, camera_img;
    ProgressBar myaccount_pb;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount_activity);

        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        tv_toolbar_title=(TextView)findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("My Account");

        session = new SessionManagement(getApplicationContext());
        logout_btn = (Button)findViewById(R.id.logout_btn);
        join_now_adv = (LinearLayout)findViewById(R.id.join_now_adv);
        count_ll = (LinearLayout)findViewById(R.id.count_ll);
        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);
        account_mobile = (TextView) findViewById(R.id.account_mobile);
        account_name = (TextView) findViewById(R.id.account_name);
        order_count_tv = (TextView) findViewById(R.id.order_count_tv);
        account_email = (TextView) findViewById(R.id.account_email);
        ma_wallet_balance = (TextView) findViewById(R.id.ma_wallet_balance);
        profile_Image = (CircleImageView) findViewById(R.id.account_img);
        camera_img = (CircleImageView) findViewById(R.id.camera_img_update);
        myaccount_pb = (ProgressBar)findViewById(R.id.myaccount_pb);

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        camera_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this,UpdateProfile.class);
                startActivity(intent);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(MyAccount.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dlg_exit);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView tv_yes=(TextView)dialog.findViewById(R.id.tv_yes);
                TextView tv_no=(TextView)dialog.findViewById(R.id.tv_no);
                TextView tv_sure=(TextView)dialog.findViewById(R.id.tv_sure);

                tv_sure.setText("Are you sure you want to logout?");

                tv_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        session.logoutUser();
                        MainActivity.locationget = true;
                        Utility.CartCount=0;
                        dialog.dismiss();
                    }
                });

                tv_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });


        if(!(session.isLoggedIn())){
            join_now_adv.setVisibility(View.VISIBLE);
        }else{
            join_now_adv.setVisibility(View.GONE);
        }

        count_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this,MyOrders.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyAccount();
    }

    private void getMyAccount() {
        JSONObject gmaObject = new JSONObject();
        try {
            gmaObject.put("user_id",session.getUserDetails().get(KEY_USERID));

            JsonObjectRequest gmaRequest = new JsonObjectRequest(Request.Method.POST, Utility.getProfile, gmaObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getInt("success")==1){
//                            account_name.setText(response.getString("name"));
//                            account_email.setText(response.getString("email"));
//                            account_mobile.setText(response.getString("mobile"));
//                            Picasso.with(MyAccount.this).load(response.getString("profile_img")).fit().into(profile_Image);
                            if(response.getString("name").equals("")){
                                Intent intent = new Intent(MyAccount.this,UpdateProfile.class);
                                Toast.makeText(MyAccount.this, "Please Update Profile", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                                myaccount_pb.setVisibility(View.GONE);
                            }else{
                                account_name.setText(response.getString("name"));
                                account_email.setText(response.getString("email"));
                                account_mobile.setText(response.getString("mobile"));
                                ma_wallet_balance.setText(" ₹" +response.getString("wallet"));
                                if(response.getString("profile_img").length()!=0){
                                    Picasso.with(MyAccount.this).load(response.getString("profile_img")).fit().into(profile_Image);

                                }
                                order_count_tv.setText(response.getString("order_count"));
                                myaccount_pb.setVisibility(View.GONE);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        myaccount_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myaccount_pb.setVisibility(View.GONE);

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header= new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            }; gmaRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(gmaRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            myaccount_pb.setVisibility(View.GONE);

        }
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//            case android.R.id.home:
////             if(fromPage.equals("one")){
//                 Intent intent = new Intent(MyAccount.this,Container_Main_Class.class);
//                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                 startActivity(intent);
//                 finish();
////               }else if(fromPage.equals("two")){
//////                 Intent intent = new Intent(MyAccount.this,TabActivity.class);
//////                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//////                 startActivity(intent);
////                 finish();
////             }else if(fromPage.equals("three")){
//////                 Intent intent = new Intent(MyAccount.this,Product_Discription.class);
//////                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//////                 startActivity(intent);
////                 finish();
////             }
//
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
