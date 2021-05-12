package com.abfresh.in;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.darubottle.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.Utility;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RelativeLayout login_rl;
    TextView go_to_registration_btn;
    TextInputEditText login_number,login_password;
    Button login_btn;
    ProgressBar loginPb;
    ImageView close_login;
    String fromPage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(getApplication());
        login_rl = (RelativeLayout)findViewById(R.id.login_rl);
        login_btn = (Button)findViewById(R.id.signin_btn);
        login_number = (TextInputEditText) findViewById(R.id.login_mn_et);
        loginPb = (ProgressBar)findViewById(R.id.loginpb);
        loginPb.setVisibility(View.GONE);
        login_rl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard(v);
            }
        });
        close_login = (ImageView)findViewById(R.id.close_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin(v);
            }
        });
        close_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        login_btn.setEnabled(false);
//        login_btn.setTextColor(getResources().getColor(R.color.colorToolbar));

//      fromPage = getIntent().getStringExtra("fromPage");
//        Toast.makeText(this,""+ fromPage, Toast.LENGTH_SHORT).show();


//        login_number.addTextChangedListener(new TextWatcher() {
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
    }

//    private void enableSubmitIfReady() {
//        boolean isReady = login_number.getText().toString().length() ==10;
//        login_btn.setEnabled(isReady);
//        login_btn.setTextColor(Color.WHITE);
//
//    }

    public void hideKeyboard(View view) {

        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onBackPressed() {
//        Container_Main_Class.bottomNavigationView.setSelectedItemId(R.id.bitem1);
//        finish();
        super.onBackPressed();
    }

    public void checkLogin(View view){
        if ( !validateLoginUsername()) {
            return;
        }
    }

//    private boolean validateLoginPassword() {
//
//        String log_password = login_password.getText().toString().trim();
//        if(log_password.isEmpty()){
////            .makeText(this, "Please Enter Password.", Toast.LENGTH_SHORT).show();
//            StyleableToast.makeText(this, "Please Enter Password.", R.style.mySizeToast).show();
//
//            login_password.setBackground(getResources().getDrawable(R.drawable.error_edit_bg));
//            return false;
//        }else{
//            login_password.setBackground(getResources().getDrawable(R.drawable.edittext_bg));
//            return true;
//        }
//    }

    private boolean validateLoginUsername() {
        String log_username = login_number.getText().toString().trim();
        if(log_username.isEmpty()){
            Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
//            StyleableToast.makeText(this, "Please Enter Mobile Number.", R.style.mySizeToast).show();
//            login_number.setBackground(getResources().getDrawable(R.drawable.error_edit_bg));
            return false;
        }else if(log_username.length()!=10){
//            StyleableToast.makeText(this, "Please Enter Valid Mobile Number.", R.style.mySizeToast).show();
            Toast.makeText(this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;

        }else{
//                Intent intent = new Intent(Login.this,Registration.class);
//                startActivity(intent);
//
                LogMeIn(log_username);
            return true;
            }
        
    }

    private void LogMeIn(String log_username) {
        try {
            loginPb.setVisibility(View.VISIBLE);
        JSONObject logMe = new JSONObject();
        logMe.put("username",log_username.trim());
        Log.w("LTAG","Login Details====>" +logMe);

            JsonObjectRequest loginCustomRequest = new JsonObjectRequest(Request.Method.POST, Utility.Login, logMe, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.w("LTAG","Login response====>" +response);

                    if((response.getString("success")).equals("1")){
//                        StyleableToast.makeText(Login.this, response.getString("message"), R.style.mySizeToast).show();

                        Toast.makeText(Login.this, response.getString("message"), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Login.this,VerifyOTP.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("mobileNumber",log_username.trim());
////
//                        if(fromPage.equals("one")){
//                            bundle.putString("fromPage","one");
//                        }else if(fromPage.equals("two")){
//                            bundle.putString("fromPage","two");
//                        }else{
//                            bundle.putString("fromPage","three");
//                        }

                        intent.putExtras(bundle);
                        startActivity(intent);
                        if(Utility.FromPD | Utility.FromCart | Utility.FromMainClass){
                            finish();
                        }
                        finish();
                        loginPb.setVisibility(View.GONE);

                    }else{
                        loginPb.setVisibility(View.GONE);
//                        StyleableToast.makeText(Login.this,"Please try after some time", R.style.mySizeToast).show();
                        Toast.makeText(Login.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.w("LTAG","Login JSONException====>" +e);
                    loginPb.setVisibility(View.GONE);
//                    StyleableToast.makeText(Login.this,"Please try after some time", R.style.mySizeToast).show();
                    Toast.makeText(Login.this, "Please try after some time", Toast.LENGTH_SHORT).show();

                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w("LTAG","Login VolleyError====>" +error);
                loginPb.setVisibility(View.GONE);
//                StyleableToast.makeText(Login.this,"Please try after some time", R.style.mySizeToast).show();
                Toast.makeText(Login.this, "Please try after some time", Toast.LENGTH_SHORT).show();

            }
        }){
     @Override
     public Map<String, String> getHeaders() throws AuthFailureError {
         HashMap<String, String> headers = new HashMap<String, String>();
         headers.put(Utility.ServerUsername,Utility.ServerPassword);
         return headers;
     }
 }; loginCustomRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addRequestInQueue(loginCustomRequest);
        } catch (JSONException e) {
            e.printStackTrace();
//            loginPb.setVisibility(View.GONE);
//            StyleableToast.makeText(Login.this,"Please try after some time", R.style.mySizeToast).show();

        }
    }
}
