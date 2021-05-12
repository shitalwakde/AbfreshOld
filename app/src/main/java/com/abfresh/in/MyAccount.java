package com.abfresh.in;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
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
    String fromPage;
    LinearLayout update_profile_ll,join_now_adv,count_ll;
    TextView account_mobile,account_name,account_email,ma_wallet_balance,order_count_tv;
    CircleImageView  profile_Image;
    ProgressBar myaccount_pb;
    ImageView ma_home_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount_activity);

        Toolbar toolbar=(Toolbar)findViewById(R.id.myaccount_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        Toast.makeText(this,""+ Utility.FromMainClass, Toast.LENGTH_SHORT).show();

        session = new SessionManagement(getApplicationContext());
        logout_btn = (Button)findViewById(R.id.logout_btn);
        update_profile_ll = (LinearLayout)findViewById(R.id.update_profile);
        join_now_adv = (LinearLayout)findViewById(R.id.join_now_adv);
        count_ll = (LinearLayout)findViewById(R.id.count_ll);

        account_mobile = (TextView) findViewById(R.id.account_mobile);
        account_name = (TextView) findViewById(R.id.account_name);
        order_count_tv = (TextView) findViewById(R.id.order_count_tv);
        account_email = (TextView) findViewById(R.id.account_email);
        ma_wallet_balance = (TextView) findViewById(R.id.ma_wallet_balance);
        profile_Image = (CircleImageView) findViewById(R.id.account_img);
        myaccount_pb = (ProgressBar)findViewById(R.id.myaccount_pb);
        ma_home_btn = (ImageView) findViewById(R.id.ma_home_btn);
//        account_mobile.setText(session.getUserDetails().get(KEY_MOBILE));
//        fromPage = getIntent().getStringExtra("fromPage");
        update_profile_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this,UpdateProfile.class);
                startActivity(intent);
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final AlertDialog.Builder builder = new AlertDialog.Builder(MyAccount.this);
//
//
//                builder.setTitle("Alert!");
//
//                //Setting message manually and performing action on button click
//                builder.setMessage("Do you really want to Logout?");
//                //This will not allow to close dialogbox until user selects an option
//                builder.setCancelable(false);
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
////                Toast.makeText(this, "positive button", Toast.LENGTH_SHORT).show();
//                        session.logoutUser();
//                        Intent intent = new Intent(MyAccount.this,Location_Text_Intent.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                // Add new Flag to start new Activity
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        startActivity(intent);
//                        MainActivity.locationget = true;
//                        Utility.CartCount=0;
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //  Action for 'NO' Button
//                        dialog.cancel();
//                    }
//                });
//
//                //Creating dialog box
//                AlertDialog alert = builder.create();
//                alert.show();

                LayoutInflater factory = LayoutInflater.from(MyAccount.this);
                final View deleteDialogView = factory.inflate(R.layout.logout_custom_dialog, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(MyAccount.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.lcd_yes_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Toast.makeText(this, "positive button", Toast.LENGTH_SHORT).show();
                        session.logoutUser();
                        Intent intent = new Intent(MyAccount.this,Location_Text_Intent.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);                // Add new Flag to start new Activity
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        MainActivity.locationget = true;
                        Utility.CartCount=0;
                        deleteDialog.dismiss();
                    }
                });
                deleteDialogView.findViewById(R.id.lcd_no_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
            }
        });


        if(!(session.isLoggedIn())){
            join_now_adv.setVisibility(View.VISIBLE);
        }else{
            join_now_adv.setVisibility(View.GONE);
        }
//        if(account_name.getText().toString().trim().length()==0){
//            Intent intent = new Intent(MyAccount.this,UpdateProfile.class);
//            startActivity(intent);
//        }else{
//
//        }

        ma_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this,Container_Main_Class.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        count_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccount.this,MyOrders.class);
                startActivity(intent);
            }
        });
//        session.checkLogin();
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
