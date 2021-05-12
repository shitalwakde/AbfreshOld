package com.abfresh.in;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.abfresh.in.Controller.MySMSBroadcastReceiver;
import com.abfresh.in.Controller.SmsListner;
import com.abfresh.in.Controller.SmsReceiver;
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
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.google.android.material.textfield.TextInputEditText;



import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.example.darubottle.R;


public class VerifyOTP extends AppCompatActivity {
    private static final String OTP_REGEX = "[0-9]";
    Button resend_otp_tv;
    String mobileNumber;
    TextInputEditText mobile_no,signup_pass_et;
    EditText signup_otp_et;
    TextView reg_btn,password_btn,otp_btn;
    ProgressBar votppb;
    ImageView close_reg;
    SessionManagement sessionManagement;
    String fromPage;
    TextInputLayout signup_pass;
    Boolean clickFlag = true;
    LinearLayout otp_ll;
    MySMSBroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);
        mobile_no = (TextInputEditText)findViewById(R.id.signup_mn_et);
        signup_otp_et = (EditText)findViewById(R.id.signup_otp_et);
        signup_pass_et = (TextInputEditText)findViewById(R.id.signup_pass_et);

        reg_btn = (TextView) findViewById(R.id.reg_btn);
        resend_otp_tv = (Button) findViewById(R.id.resend_otp_tv);
        password_btn = (TextView)findViewById(R.id.password_btn);
        otp_ll = (LinearLayout) findViewById(R.id.otp_ll);
        otp_btn = (TextView)findViewById(R.id.otp_btn);
        signup_pass = (TextInputLayout)findViewById(R.id.signup_pass);
//        signup_otp = (TextInputLayout)findViewById(R.id.signup_otp);
        votppb = (ProgressBar)findViewById(R.id.votppb);
        mobileNumber = getIntent().getStringExtra("mobileNumber");
        mobile_no.setText(mobileNumber);
        close_reg = (ImageView)findViewById(R.id.close_reg);
        votppb.setVisibility(View.GONE);
//        reg_btn.setEnabled(false);
//        reg_btn.setTextColor(getResources().getColor(R.color.colorToolbar));
        sessionManagement = new SessionManagement(VerifyOTP.this);
//        fromPage = getIntent().getStringExtra("fromPage");
//        sessionManagement.
//        signup_otp_et.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                enableSubmitIfReady();
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });
//       signup_pass_et.addTextChangedListener(new TextWatcher() {
//           @Override
//           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//           }
//
//           @Override
//           public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//           }
//
//           @Override
//           public void afterTextChanged(Editable s) {
//                enableSubmitIfReady1();
//           }
//       });
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
                        //start your activity here

                        progressDialog.dismiss();
                        finish();
                    }

                }, 1000);


            }
        });

        resend_otp_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                JSONObject resendObject =new JSONObject();
                    votppb.setVisibility(View.VISIBLE);

                    if(mobile_no.getText().toString().trim().length()==0){
//                        StyleableToast.makeText(VerifyOTP.this,"Please enter your Mobile Number", R.style.mySizeToast).show();
                        Toast.makeText(VerifyOTP.this,"Please enter Mobile Number", Toast.LENGTH_SHORT).show();

                        votppb.setVisibility(View.GONE);

                    }else if (mobile_no.getText().toString().trim().length()!=10){
//                        StyleableToast.makeText(VerifyOTP.this,"Please enter valid Mobile Number", R.style.mySizeToast).show();
                        Toast.makeText(VerifyOTP.this,"Please enter valid Mobile Number", Toast.LENGTH_SHORT).show();

                        votppb.setVisibility(View.GONE);
                    }else{
                        String tempid= Settings.Secure.getString(getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        resendObject.put( "temp_user_id",tempid);
                        resendObject.put( "username",mobile_no.getText().toString().trim());

                    }

                    Log.w("VTAG","Verification resendObject"+resendObject);

                    JsonObjectRequest verifyRequest = new JsonObjectRequest(Request.Method.POST, Utility.ResendOTP, resendObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.w("VTAG","Verification Response"+response);

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
                try {
                JSONObject votpObject = new JSONObject();
                    votppb.setVisibility(View.VISIBLE);

                    votpObject.put("username",mobile_no.getText().toString().trim());
                    if(clickFlag){
                        if(signup_otp_et.getText().toString().trim().equals("")){
//                            StyleableToast.makeText(VerifyOTP.this,"Please Enter OTP", R.style.mySizeToast).show();
                            Toast.makeText(VerifyOTP.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

                            votppb.setVisibility(View.GONE);

                        }else{
                            String tempid= Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                            votpObject.put( "temp_user_id",tempid);
                            votpObject.put(    "username",mobile_no.getText().toString().trim());
                            votpObject.put(    "otp",signup_otp_et.getText().toString().trim());
                            votpObject.put(    "verify_flag","Otp");
                            votpObject.put(    "Player_id",AppController.player_id);

                        }
                    }else{
                        if(signup_pass_et.getText().toString().trim().equals("")){
//                            StyleableToast.makeText(VerifyOTP.this,"Please Enter Password", R.style.mySizeToast).show();
                            Toast.makeText(VerifyOTP.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                            votppb.setVisibility(View.GONE);

                        }else{
                            String tempid= Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                            votpObject.put( "temp_user_id",tempid);
                            votpObject.put(    "username",mobile_no.getText().toString().trim());
                            votpObject.put(    "otp",signup_pass_et.getText().toString().trim());
                            votpObject.put(    "verify_flag","Password");
                            votpObject.put(    "Player_id",AppController.player_id);
                        }
                    }



                    JsonObjectRequest votpRequest = new JsonObjectRequest(Request.Method.POST, Utility.VerifyOTP, votpObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if ((response.getString("success")).equals("1")){
                                    Toast.makeText(VerifyOTP.this, response.getString("message"), Toast.LENGTH_SHORT).show();

//                                    StyleableToast.makeText(VerifyOTP.this,response.getString("message"), R.style.mySizeToast).show();
//                                    Utility.UserId = response.getString("user_id");
                                    sessionManagement.createLoginSession(response.getString("user_id"),response.getString("mobile"));

//                                    HashMap<String, String> user = sessionManagement.getUserDetails();
//                                    String userid = user.get(sessionManagement.KEY_USERID);
//                                    Utility.UserId = userid;

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
                                        Intent intent = new Intent(VerifyOTP.this,MyAccount.class);
//
                                        startActivity(intent);
                                        votppb.setVisibility(View.GONE);

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

        password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color1 = getResources().getColor(R.color.colorPrimary);
                int color2 = getResources().getColor(R.color.off_btn_color);
                int color3 = getResources().getColor(R.color.tab_color_selected);
                int color4 = getResources().getColor(R.color.black);
//                password_btn.setBackgroundColor(color1);
                password_btn.setTextColor(color3);
//                otp_btn.setBackgroundColor(color2);
                otp_btn.setTextColor(color4);
                signup_pass.setVisibility(View.VISIBLE);
//                signup_otp.setVisibility(View.GONE);
                otp_ll.setVisibility(View.GONE);
                clickFlag = false;
                signup_otp_et.setText("");
//                reg_btn.setEnabled(false);
//                reg_btn.setTextColor(getResources().getColor(R.color.colorToolbar));
            }
        });

        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color1 = getResources().getColor(R.color.colorPrimary);
                int color2 = getResources().getColor(R.color.off_btn_color);
                int color3 = getResources().getColor(R.color.tab_color_selected);
                int color4 = getResources().getColor(R.color.black);
//                otp_btn.setBackgroundColor(color1);
                otp_btn.setTextColor(color3);
//                password_btn.setBackgroundColor(color2);
                password_btn.setTextColor(color4);
                signup_pass.setVisibility(View.GONE);
//                signup_otp.setVisibility(View.VISIBLE);
                otp_ll.setVisibility(View.VISIBLE);
                clickFlag =true;
                signup_pass_et.setText("");
//                reg_btn.setEnabled(false);
//                reg_btn.setTextColor(getResources().getColor(R.color.colorToolbar));
          }
        });

        if(!(sessionManagement.isLoggedIn())){
            password_btn.setVisibility(View.GONE);
            password_btn.setEnabled(true);
        }else{
            password_btn.setVisibility(View.VISIBLE);
        }
//        smsVerifyCatcher = new SmsVerifyCatcher(VerifyOTP.this, new OnSmsCatchListener<String>() {
//            @Override
//            public void onSmsCatch(String message) {
//                String code = parseCode(message);//Parse verification code
//                signup_otp_et.setText(code);//set code in edit text
//                //then you can send verification code to server
//            }
//        });
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

//                                    Pattern pattern = Pattern.compile(VerifyOTP.OTP_REGEX);
//                                    Matcher matcher = pattern.matcher(message);
//
//                                    while (matcher.find())
//                                    {
//                                        signup_otp_et.setText(matcher.group());
//                                    }

//                                    signup_otp_et.setText(message.substring(message.indexOf(":")+0,message.indexOf(":")+0));
//                                    Pattern    pattern = Pattern.compile("\\p{Digit}");
//                                    Matcher matcher = pattern.matcher(message);
//                                    if(matcher.matches()) {
//                                        // yay! alphanumeric!
//                                        signup_otp_et.setText(message);
//                                    }
//                                    otp_textbox_two.setText(message.substring(message.indexOf(":")+3,message.indexOf(":")+4));
//                                    otp_textbox_three.setText(message.substring(message.indexOf(":")+4,message.indexOf(":")+5));
//                                    otp_textbox_four.setText(message.substring(message.indexOf(":")+5,message.indexOf(":")+6));
                                    // Extract one-time code from the message and complete verification
                                    // by sending the code back to your server.
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

    private String parseCode(String message) {
        Pattern p = Pattern.compile("\\b\\d{4}\\b");
        Matcher m = p.matcher(message);
        String code = "";
        while (m.find()) {
            code = m.group(0);
        }
        return code;
    }

    //    @Override
//    protected void onStart() {
//        super.onStart();
//        smsVerifyCatcher.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        smsVerifyCatcher.onStop();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        smsVerifyCatcher.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//    private String parseCode(String message) {
//        Pattern p = Pattern.compile("\\b\\d{6}\\b");
//        Matcher m = p.matcher(message);
//        String code = "";
//        while (m.find()) {
//            code = m.group(0);
//        }
//        return code;
//    }
    private void enableSubmitIfReady1() {
        boolean isReady1 = signup_pass_et.getText().toString().length() >=4;
        reg_btn.setEnabled(isReady1);
        reg_btn.setTextColor(Color.WHITE);
    }


    private void enableSubmitIfReady() {
        boolean isReady = signup_otp_et.getText().toString().length() ==4;

            reg_btn.setEnabled(isReady);
            reg_btn.setTextColor(Color.WHITE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        invalidateOptionsMenu();

    }
}
