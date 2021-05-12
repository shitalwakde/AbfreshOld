package com.abfresh.in;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class UpdateShippingAddress extends AppCompatActivity {
    TextInputEditText uda_ufn,uda_mn,uda_fn,uda_add,uda_state,uda_city,uda_pc,uda_address;
    Button update_addrs_btn;
    SessionManagement sessionManagement;
    ImageView uda_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_shipping_address);
        sessionManagement = new SessionManagement(getApplicationContext());
        uda_ufn = (TextInputEditText)findViewById(R.id.uda_ufn);
        uda_mn = (TextInputEditText)findViewById(R.id.uda_mn);
        uda_fn = (TextInputEditText)findViewById(R.id.uda_fn);
        uda_add = (TextInputEditText)findViewById(R.id.uda_add);
        uda_address = (TextInputEditText)findViewById(R.id.uda_address);
        uda_state = (TextInputEditText)findViewById(R.id.uda_state);
        uda_city = (TextInputEditText)findViewById(R.id.uda_city);
        uda_pc = (TextInputEditText)findViewById(R.id.uda_pc);
        update_addrs_btn = (Button) findViewById(R.id.update_addrs_btn);
        uda_home_btn = (ImageView) findViewById(R.id.uda_home_btn);

        String Id = getIntent().getStringExtra("delivery_location_id");
        String userName = getIntent().getStringExtra("delivery_username");
        uda_ufn.setText(userName);
        String houseNo = getIntent().getStringExtra("delivery_location_house_no");
        uda_fn.setText(houseNo);
        String area = getIntent().getStringExtra("delivery_location_area");
        uda_add.setText(area);
        String address = getIntent().getStringExtra("delivery_location_add");
        uda_address.setText(address);
        String state = getIntent().getStringExtra("delivery_location_state_name");
        uda_state.setText(state);
        String city = getIntent().getStringExtra("delivery_location_city_name");
        uda_city.setText(city);
        String pincode = getIntent().getStringExtra("delivery_location_pincode");
        uda_pc.setText(pincode);
        String mobile = getIntent().getStringExtra("delivery_location_user_mobile");
        uda_mn.setText(mobile);

        update_addrs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(uda_ufn.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a User Name", Toast.LENGTH_SHORT).show();
                }else if(uda_mn.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a Mobile Number", Toast.LENGTH_SHORT).show();
                }else if(uda_mn.getText().toString().trim().length()!=10){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a Valid 10 digit Mobile Number", Toast.LENGTH_SHORT).show();
                }else if(uda_fn.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a House Number", Toast.LENGTH_SHORT).show();
                }else if(uda_add.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a Area|Colony|Street|Village", Toast.LENGTH_SHORT).show();
                }else if(uda_address.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a Address", Toast.LENGTH_SHORT).show();
                }else if(uda_state.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a State", Toast.LENGTH_SHORT).show();
                }else if(uda_city.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a City", Toast.LENGTH_SHORT).show();
                }else if(uda_pc.getText().toString().trim().length()==0){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a Pincode", Toast.LENGTH_SHORT).show();
                }else if(uda_pc.getText().toString().trim().length()!=6){
                    Toast.makeText(UpdateShippingAddress.this, "Please Enter a Valid 6 digit Pincode", Toast.LENGTH_SHORT).show();
                }else{
                    getUpdateMyAddress();
                }

            }
        });

        uda_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getUpdateMyAddress() {

        try {
            JSONObject anaObject = new JSONObject();
            anaObject.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
            anaObject.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            anaObject.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            anaObject.put("delivery_location_city_name", uda_city.getText().toString().trim());
            anaObject.put("delivery_location_state_name", uda_state.getText().toString().trim());
            anaObject.put("delivery_location_user_name", uda_ufn.getText().toString().trim());
            anaObject.put("delivery_location_user_mobile", uda_mn.getText().toString().trim());
            anaObject.put("delivery_location_house_no", uda_fn.getText().toString().trim());
            anaObject.put("delivery_location_area", uda_add.getText().toString().trim());
            anaObject.put("delivery_location_address", uda_address.getText().toString().trim());
            anaObject.put("delivery_location_pincode", uda_pc.getText().toString().trim());
            anaObject.put("delivery_location_id", getIntent().getStringExtra("delivery_location_id"));

            Log.w("DTAG", "Object " + anaObject);
            Log.w("DTAG", "Utility.SaveDeliveryLocation " + Utility.SaveDeliveryLocation);

            JsonObjectRequest anaRequest = new JsonObjectRequest(Request.Method.POST, Utility.SaveDeliveryLocation, anaObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("DTAG", "Object response ===>" + response);
                    try {


                        if (response.getInt("success") == 1) {

                            Toast.makeText(UpdateShippingAddress.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();

//                            Intent intent = new Intent(AddShippingAddress.this,CartDiscription.class);
//                             startActivity(intent);

                        } else {
                            Toast.makeText(UpdateShippingAddress.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            Utility.MyAddress = null;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> header = new HashMap<>();
                    header.put(Utility.ServerUsername, Utility.ServerPassword);
                    //  header.put("Authorization", "Basic bWVhdDoxMTAw");
                    return header;
                }
            };
            //  anaRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(anaRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
