package com.abfresh.in;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abfresh.in.Abfresh.activities.DashboardActivity;
import com.abfresh.in.Abfresh.model.UserModel;
import com.abfresh.in.Abfresh.utils.RestClient;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.MySMSBroadcastReceiver;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Custom.CustomEditText;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

//import com.example.darubottle.R;


public class VerifyOTP extends AppCompatActivity {

    private static final String TAG = "VerifyOTP";
    private static final String OTP_REGEX = "[0-9]";
    Button reg_btn;
    CustomEditText signup_otp_et;
    TextView resend_otp_tv,otp_btn;
    ProgressBar votppb;
    ImageView close_reg, votp_imv;
    SessionManagement sessionManagement;
    TextInputLayout signup_pass;
    Boolean clickFlag = true;
    LinearLayout otp_ll;
    MySMSBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        reg_btn = (Button) findViewById(R.id.reg_btn);
        signup_otp_et=(CustomEditText)findViewById(R.id.signup_otp_et);
        resend_otp_tv = (TextView) findViewById(R.id.resend_otp_tv);
        otp_ll = (LinearLayout) findViewById(R.id.otp_ll);
        otp_btn = (TextView)findViewById(R.id.otp_btn);
        votppb = (ProgressBar)findViewById(R.id.votppb);
        close_reg = (ImageView)findViewById(R.id.close_reg);
        votp_imv = (ImageView)findViewById(R.id.votp_imv);
        votppb.setVisibility(View.GONE);
        sessionManagement = new SessionManagement(VerifyOTP.this);

        signup_otp_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                votp_imv.setVisibility(View.GONE);
                return false;
            }
        });

        signup_otp_et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    votp_imv.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        signup_otp_et.setKeyboardListener(new CustomEditText.KeyboardHideListener() {
            @Override
            public void onKeyboardHide() {
                votp_imv.setVisibility(View.VISIBLE);
            }
        });

        close_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = ProgressDialog.show(VerifyOTP.this, null, null, true);
                progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.show();

                Handler mHandler = new Handler();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
//                        finish();
                        onBackPressed();
                    }
                }, 1000);

            }
        });

        resend_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                votp_imv.setVisibility(View.VISIBLE);
                try {
                JSONObject resendObject =new JSONObject();
                    votppb.setVisibility(View.VISIBLE);
                    String tempid= Settings.Secure.getString(getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    resendObject.put( "temp_user_id",tempid);
                    resendObject.put( "username",getIntent().getStringExtra("mobileNumber"));
                    resendObject.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
                    resendObject.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
                    resendObject.put( "via","Android");

                    Log.w("VTAG","Verification resendObject"+resendObject);

                    JsonObjectRequest verifyRequest = new JsonObjectRequest(Request.Method.POST, Utility.ResendOTP, resendObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.w("VTAG","resend Verification Response"+response);

                                if ((response.getString("success")).equals("1")){

//                                    StyleableToast.makeText(VerifyOTP.this,response.getString("message"), R.style.mySizeToast).show();
                                    Toast.makeText(VerifyOTP.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                                    votppb.setVisibility(View.GONE);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                votppb.setVisibility(View.GONE);
//                                StyleableToast.makeText(VerifyOTP.this,"Please Try after some time", R.style.mySizeToast).show();
                                Toast.makeText(VerifyOTP.this, "Please Try after some time", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            votppb.setVisibility(View.GONE);

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put(Utility.ServerUsername,Utility.ServerPassword);
                            return headers;
                        }
                    }; verifyRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addRequestInQueue(verifyRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                    votppb.setVisibility(View.GONE);

                }
            }
        });

//        Toast.makeText(this,""+ Utility.FromMainClass, Toast.LENGTH_SHORT).show();


        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setFocusable(true);
                //If no view currently has focus, create a new one, just so we can grab a window token from it
                if (v == null) {
                    v = new View(VerifyOTP.this);
                }
                InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                votp_imv.setVisibility(View.VISIBLE);
                try {
                JSONObject votpObject = new JSONObject();
                    votppb.setVisibility(View.VISIBLE);

                    votpObject.put("username",getIntent().getStringExtra("mobileNumber"));
                    votpObject.put("via","Android");
                    if(clickFlag){
                        if(signup_otp_et.getText().toString().trim().equals("")){
                            signup_otp_et.requestFocus();
                            signup_otp_et.setError("Please Enter OTP");
//                            StyleableToast.makeText(VerifyOTP.this,"Please Enter OTP", R.style.mySizeToast).show();
//                            Toast.makeText(VerifyOTP.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

                            votppb.setVisibility(View.GONE);

                        }else{
                            String tempid= Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                            votpObject.put( "temp_user_id",tempid);
                            votpObject.put(    "username",getIntent().getStringExtra("mobileNumber"));
                            votpObject.put(    "otp",signup_otp_et.getText().toString().trim());
                            votpObject.put(    "verify_flag","Otp");
                            votpObject.put(    "Player_id",AppController.player_id);
                            votpObject.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
                            votpObject.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
                            votpObject.put( "via","Android");

                        }
                    }



                    JsonObjectRequest votpRequest = new JsonObjectRequest(Request.Method.POST, Utility.VerifyOTP, votpObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.w("VTAG","Verification Response"+response);
                            try {
                                if ((response.getString("success")).equals("1")){
                                    Toast.makeText(VerifyOTP.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    sessionManagement.createLoginSession(response.getString("user_id"),response.getString("mobile"));
                                    sessionManagement.updateProfile(response.getString("name"),response.getString("email"),response.getString("mobile"), response.getString("profile_img"));
                                    if(Utility.FromCart){
//
                                        finish();
                                    }else if (Utility.FromPD){

                                        finish();
                                    }else if (Utility.FromTabClass){
//
                                        finish();
                                    }else{
                                        finish();
//                                        Intent intent = new Intent(VerifyOTP.this,MyAccount.class);
                                        Intent intent = new Intent(VerifyOTP.this, DashboardActivity.class);
                                        startActivity(intent);
                                        votppb.setVisibility(View.GONE);
                                        getProfile();
                                    }

                                }else{
                                    votppb.setVisibility(View.GONE);
//                                    StyleableToast.makeText(VerifyOTP.this,"Please Try after some time", R.style.mySizeToast).show();
                                    Toast.makeText(VerifyOTP.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                votppb.setVisibility(View.GONE);
//                                StyleableToast.makeText(VerifyOTP.this,"Please Try after some time", R.style.mySizeToast).show();
                                Toast.makeText(VerifyOTP.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            votppb.setVisibility(View.GONE);

                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put(Utility.ServerUsername,Utility.ServerPassword);
                            return headers;
                        }
                    }; votpRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addRequestInQueue(votpRequest);


                } catch (JSONException e) {
                    e.printStackTrace();
                    votppb.setVisibility(View.GONE);

                }
            }
        });


        SmsRetrieverClient client = SmsRetriever.getClient(VerifyOTP.this /* context */);
        Task<Void> task = client.startSmsRetriever();
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Successfully started retriever, expect broadcast intent
                // ...
                Log.w("BTAG","Successfully Message ");
                broadcastReceiver = new MySMSBroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                            Bundle extras = intent.getExtras();
                            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
                            Log.w("BTAG","Message status===> "+status);
                            switch(status.getStatusCode()) {
                                case CommonStatusCodes.SUCCESS:
                                    // Get SMS message contents
                                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                                    Log.w("BTAG","Message===> "+message);

                                    String code = parseCode(message);
                                    signup_otp_et.setText(code);
                                    break;
                                case CommonStatusCodes.TIMEOUT:
                                    // Waiting for SMS timed out (5 minutes)
                                    // Handle the error ...
                                    break;
                            }
                        }
                    }
                };
                IntentFilter filterSend = new IntentFilter();
                filterSend.addAction("com.google.android.gms.auth.api.phone.SMS_RETRIEVED");
                registerReceiver(broadcastReceiver, filterSend);
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Failed to start retriever, inspect Exception for more details
                // ...
            }
        });
    }



    private void getProfile(){

        JsonObject jsonObject = new JsonObject();
        if(sessionManagement.getUserDetails().get(KEY_USERID) != null){
            jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            jsonObject.addProperty("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            jsonObject.addProperty("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
            jsonObject.addProperty("via", "Android");
        }

        new RestClient().getApiService().getProfile(jsonObject, new Callback<UserModel>() {
            @Override
            public void success(UserModel userModel, retrofit.client.Response response) {
                if(userModel.getSuccess().equals("1")){
                    sessionManagement.getProfile(userModel.getProfile_img(),userModel.getWallet(), userModel.getReferal_code(), userModel.getShare_msg(), userModel.getDisplay_msg());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });



    }

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        votp_imv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        invalidateOptionsMenu();

    }
}
