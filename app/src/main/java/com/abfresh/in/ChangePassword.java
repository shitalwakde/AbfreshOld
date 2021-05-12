package com.abfresh.in;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class ChangePassword extends AppCompatActivity {
    ImageView cpass_home_btn,close_change_password;
    TextInputEditText new_password,confirm_password;
    Button update_pass_btn;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        Toolbar toolbar=(Toolbar)findViewById(R.id.chg_pro_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        cpass_home_btn = (ImageView)findViewById(R.id.cpass_home_btn);
        close_change_password = (ImageView)findViewById(R.id.close_change_password);
        new_password = (TextInputEditText) findViewById(R.id.new_password);
        confirm_password = (TextInputEditText) findViewById(R.id.confirm_password);
        update_pass_btn = (Button) findViewById(R.id.update_pass_btn);

        cpass_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        close_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update_pass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(new_password.getText().toString().length()==0 ){
                    Toast.makeText(ChangePassword.this, "Please Enter New Passwrod", Toast.LENGTH_SHORT).show();

                }else if(confirm_password.getText().toString().length()==0){
                    Toast.makeText(ChangePassword.this, "Please Enter Confirm Passwrod", Toast.LENGTH_SHORT).show();

                }else{
                    if(new_password.getText().toString().trim().equals(confirm_password.getText().toString().trim())){
                        updatePassword();
                    }else{
                        Toast.makeText(ChangePassword.this, "Both Passwords are not same", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void updatePassword() {
        sessionManagement = new SessionManagement(getApplicationContext());
        JSONObject upJsonObjectNew = new JSONObject();

        try {
            upJsonObjectNew.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            upJsonObjectNew.put("new_password", new_password.getText().toString().trim());

            JsonObjectRequest upRequest = new JsonObjectRequest(Request.Method.POST, Utility.Offers, upJsonObjectNew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getInt("success")==1){
                            Toast.makeText(ChangePassword.this, "Password change successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(ChangePassword.this, "Please try after sometime", Toast.LENGTH_SHORT).show();

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
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername, Utility.ServerPassword);
                    return headers;
                }
            };
            upRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(upRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
