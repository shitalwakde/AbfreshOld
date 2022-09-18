package com.abfresh.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.darubottle.R;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.LocationRequest;
import com.abfresh.in.Adapter.RecentSearchAdapter;
import com.abfresh.in.Adapter.Search_Location_Adapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.CustomRequest;
import com.abfresh.in.Controller.LocationManagerInterface;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.SmartLocationManager;
import com.abfresh.in.Controller.Utility;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_FULLADDRESS;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class Delivery_Address extends AppCompatActivity implements LocationManagerInterface{
    RecyclerView recyclerView,current_saved_add_rv;
    ArrayList<com.abfresh.in.Model.ArrayList> location;
//    private ArrayList<com.tbd.abFresh.model.ArrayList> mylocation;
    java.util.ArrayList<com.abfresh.in.Model.ArrayList> mylocation;
    java.util.ArrayList<com.abfresh.in.Model.ArrayList> mylocation_recent;
    SessionManagement sessionManagement;
    Search_Location_Adapter adapter;
    public static ImageView loca_search_icon,loca_selected_icon;
   Toolbar addrs_toolbar;
   public static EditText editTextSearch;
   LinearLayout cdl_ll;
   ImageView home_btn_da;
   TextView current_saved_add;
   RelativeLayout use_my_cuurent_location_rl;
    LinearLayout saved_add_dal_ll,recent_search_ll;
    RecentSearchAdapter recentSearchAdapter;
    double latitude, longitude;
    String cityName, postalCode,stateName,stateNewName;
    String fullAddress="";
    boolean btnClick = false;
    String btnClickLoc = "";
    String apiloc = "";
    String apilocNew = "";
    String apilocNewError = "";
    List<String> city;
    List<String> cityid;
    ArrayList<String> cityNewArray;
    ArrayList<String> stateNewArray;
    ArrayList<String> cityNewArrayID;
    ProgressDialog progressDialog;
    SmartLocationManager mLocationManager;
    public static boolean locClick=false;
    LinearLayout change_add_ll_cdl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_delivery_location);
        sessionManagement = new SessionManagement(Delivery_Address.this);
        mLocationManager = new SmartLocationManager(getApplicationContext(), Delivery_Address.this, Delivery_Address.this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE);
        mLocationManager.startLocationFetching();
        mylocation = new java.util.ArrayList<>();
        mylocation_recent = new java.util.ArrayList<>();
//        Toast.makeText(this, ""+getIntent().getStringExtra("userId"), Toast.LENGTH_SHORT).show();
        addrs_toolbar = (Toolbar)findViewById(R.id.choose_loca_tolbar);
        setSupportActionBar(addrs_toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        loca_search_icon = (ImageView)findViewById(R.id.loca_search_icon);
        loca_selected_icon = (ImageView)findViewById(R.id.loca_selected_icon);
        current_saved_add = (TextView) findViewById(R.id.current_saved_add);
        use_my_cuurent_location_rl = (RelativeLayout) findViewById(R.id.use_my_cuurent_location_rl);
        saved_add_dal_ll = (LinearLayout) findViewById(R.id.saved_add_dal_ll);
        recent_search_ll = (LinearLayout) findViewById(R.id.recent_search_ll);
        change_add_ll_cdl = (LinearLayout) findViewById(R.id.change_add_ll_cdl);
        city = new ArrayList<String>();
        cityid = new ArrayList<String>();
        progressDialog= ProgressDialog.show(Delivery_Address.this, null, null, true);
        progressDialog.dismiss();

//        Delivery_Address.loca_search_icon.setVisibility(View.VISIBLE);
//        Delivery_Address.loca_selected_icon.setVisibility(View.GONE);
        cdl_ll = (LinearLayout)findViewById(R.id.cdl_ll);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_chhose_location);
        current_saved_add_rv = (RecyclerView) findViewById(R.id.current_saved_add_rv);


        use_my_cuurent_location_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("UMLTAG", "btnClicked--");
                btnClick = true;
                btnClickLoc = "1";
                locClick=false;
//                 progressDialog = ProgressDialog.show(Delivery_Address.this, null, null, true);
                progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.show();
                Log.w("UMLTAG", "btnClicked--");
            }
        });
        editTextSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                recyclerView.setVisibility(View.VISIBLE);
                recentSearchAdapter.getFilter().filter(editTextSearch.getText().toString().trim().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
//                recyclerView.setVisibility(View.VISIBLE);
//                getMyCity();
//                filter(editable.toString(),editTextSearch);



            }
        });

        change_add_ll_cdl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Delivery_Address.this,Location_Text_Intent.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMyLocation();
        getMyCity();
        getCity();
    }



    private void getCity() {
        CustomRequest cityRequest = new CustomRequest(Request.Method.POST, Utility.GetCity, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    lfp_pb.setVisibility(View.VISIBLE);
                    Log.e("CTAG", "response--" + response);
                    if (response.getString("success").equals("1")) {
                        JSONArray cityArray = response.getJSONArray("city_list");
                        cityNewArray = new ArrayList<>();
                        cityNewArrayID = new ArrayList<>();
                        stateNewArray = new ArrayList<>();

                        for (int i = 0; i < cityArray.length(); i++) {
                            JSONObject cityObject = cityArray.getJSONObject(i);
                            city.add(cityObject.getString("city_name"));
                            cityNewArray.add(cityObject.getString("city_name"));
                            cityNewArrayID.add(cityObject.getString("city_id"));
                            stateNewArray.add(cityObject.getString("state_name"));

                        }


                        // Creating adapter for spinner

                    } else {
//                        lfp_pb.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    lfp_pb.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                lfp_pb.setVisibility(View.GONE);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(Utility.ServerUsername, Utility.ServerPassword);
                return headers;
            }
        };
        cityRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addRequestInQueue(cityRequest);
    }

    private void getMyLocation() {
        try {

            JSONObject dla_object = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            dla_object.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            dla_object.put("temp_user_id",tempid);
            Log.w("DLATAG", "Object " + dla_object);
            JsonObjectRequest dlaRequest = new JsonObjectRequest(Request.Method.POST, Utility.GetLocation, dla_object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("DLATAG", "response " + response);
                        mylocation_recent.clear();
                        if (response.getInt("success") == 1) {

                            Log.w("DLATAG", "response inside==> " + response);

                            JSONArray dlArray = response.getJSONArray("saved_list");
                            if(dlArray.length()!=0){
                                saved_add_dal_ll.setVisibility(View.VISIBLE);
                                Log.w("DLATAG", "response dlArray==> " + dlArray.length());
                                for (int i=0;i<dlArray.length();i++) {
                                    JSONObject dlaobject = dlArray.getJSONObject(i);

                                    String name_city = dlaobject.getString("city_name");
                                    Log.w("DLATAG", "response name_city==> " + name_city);
                                    String state = dlaobject.getString("state_name");
                                    Log.w("DLATAG", "response name_state==> " + state);
                                    String name_pincode = dlaobject.getString("pincode");
                                    Log.w("DLATAG", "response name_pincode==> " + name_pincode);
                                    String name_address = dlaobject.getString("address");
                                    Log.w("DLATAG", "response name_address==> " + name_address);
                                    mylocation_recent.add(new com.abfresh.in.Model.ArrayList(dlaobject.getString("city_id"),name_city,state,name_pincode,name_address));

//                                if(categoryId.equals(catId))
                                    current_saved_add.setText("");
//                                    Log.w("DLATAG", "response parameter==> " + (name_address + ","+ name_city + ","+ state + ","+ name_pincode));
                                    current_saved_add.setText(name_address+ " "+name_city+","+state+","+name_pincode);
                                }
                                recentSearchAdapter = new RecentSearchAdapter(Delivery_Address.this, mylocation_recent);
                                final LinearLayoutManager manager
                                        = new LinearLayoutManager(Delivery_Address.this);
                                current_saved_add_rv.setLayoutManager(manager);
                                current_saved_add_rv.setAdapter(recentSearchAdapter);

                                recentSearchAdapter.setOnItemClickListner(new RecentSearchAdapter.OnItemClickListner() {
                                    @Override
                                    public void onAddClick(int position) {
                                        sessionManagement.updateCity(mylocation_recent.get(position).getRecipeName(),mylocation_recent.get(position).getRecipeTypeName(),mylocation_recent.get(position).getRecipeImage(),mylocation_recent.get(position).getRecipeDuration());
                                        saveNewLocation(mylocation_recent.get(position).getRecipeName(),mylocation_recent.get(position).getRecipeDuration(),mylocation_recent.get(position).getRecipeServes(),mylocation_recent.get(position).getRecipeTypeName(),mylocation_recent.get(position).getRecipeImage());


                                    }
                                });



                            }else{
                                saved_add_dal_ll.setVisibility(View.GONE);
                            }


//                                if(AppController.completeAddress.trim().length()!=0){
//                                    current_saved_add.setText(AppController.completeAddress);
//                                }else{
//                                    current_saved_add.setText(sessionManagement.getUserDetails().get(KEY_City)+",Maharashtra India");
//                                }

//                            progressDialog.dismiss();


                        } else {

                            Toast.makeText(Delivery_Address.this, response.getString("message"), Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername, Utility.ServerPassword);
                    return headers;
                }
            };
            dlaRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(dlaRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getMyCity() {
        try {
            JSONObject dla_object2 = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            dla_object2.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            dla_object2.put("temp_user_id",tempid);
            Log.w("DLATAG", "Object dla_object2 " + dla_object2);
            JsonObjectRequest cityRequest = new JsonObjectRequest(Request.Method.POST, Utility.GetLocation, dla_object2, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        mylocation.clear();
//                        location = new ArrayList<>();
//                        location.clear();
                        if (response.getInt("success") == 1) {
                            Log.w("DLATAG", "response inside recent search==> " + response);
//                            JSONArray dlArray = response.getJSONArray("recent_list");
                            JSONArray cityArray = response.getJSONArray("recent_list");
                            if(cityArray.length()!=0){
                                recent_search_ll.setVisibility(View.VISIBLE);
                                for (int j = 0;j< cityArray.length();j++) {
                                    JSONObject cityObject = cityArray.getJSONObject(j);
                                    //  location.add(cityObject.getString("city_name"));
                                    Log.w("DLATAG", "response inside recent search for loop==> " + response);
                                    String city_id = cityObject.getString("city_id");
                                    Log.w("DLATAG", "response city_id==> " + city_id);
                                    String city_name = cityObject.getString("city_name");
                                    Log.w("DLATAG", "response city_name==> " + city_name);
                                    String state_name = cityObject.getString("state_name");
                                    Log.w("DLATAG", "response state_name==> " + state_name);
                                    String pincode_city = cityObject.getString("pincode");
                                    Log.w("DLATAG", "response pincode_city==> " + pincode_city);
                                    String address_city = cityObject.getString("address");
                                    Log.w("DLATAG", "response address_city==> " + address_city);

//                                mylocation.add(new com.tbd.abFresh.model.ArrayList(city_name, city_id));
//                                location.add(new com.tbd.abFresh.model.ArrayList(city_name, city_id));
                                    mylocation.add(new com.abfresh.in.Model.ArrayList(city_id,city_name,state_name,pincode_city,address_city));
                                }
                                recentSearchAdapter = new RecentSearchAdapter(Delivery_Address.this, mylocation);
                                final LinearLayoutManager manager
                                        = new LinearLayoutManager(Delivery_Address.this);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(recentSearchAdapter);

                                recentSearchAdapter.setOnItemClickListner(new RecentSearchAdapter.OnItemClickListner() {
                                    @Override
                                    public void onAddClick(int position) {
             sessionManagement.updateCity(mylocation.get(position).getRecipeName(),mylocation.get(position).getRecipeTypeName(),mylocation.get(position).getRecipeImage(),mylocation.get(position).getRecipeDuration());
              saveNewLocation(mylocation.get(position).getRecipeName(),mylocation.get(position).getRecipeDuration(),mylocation.get(position).getRecipeServes(),mylocation.get(position).getRecipeTypeName(),mylocation.get(position).getRecipeImage());


                                    }
                                });

                            }else{
                                recent_search_ll.setVisibility(View.GONE);

                            }
//                            mylocation = new ArrayList<com.tbd.abFresh.model.ArrayList>();



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
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername, Utility.ServerPassword);
                    return headers;
                }
            };
            cityRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(cityRequest);
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveNewLocation(String city_id, String pincode, String address, String city_name, String state) {
        try {
            JSONObject gvp_object = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            gvp_object.put("pincode",pincode);
            gvp_object.put("city_id",city_id);
            gvp_object.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            gvp_object.put("temp_user_id",tempid);
            gvp_object.put("address",address);
            Log.w("GVPTAG", "Object " + gvp_object);

            JsonObjectRequest gvpRequest = new JsonObjectRequest(Request.Method.POST, Utility.SaveLocation, gvp_object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("success") == 1) {

//                            Toast.makeText(Delivery_Address.this,response.getString("message"), Toast.LENGTH_SHORT).show();
                            AppController.completeAddress = address+" "+city_name+","+state+","+"India";
                            sessionManagement.addFullAddress(AppController.completeAddress, "");
                            finish();
//                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(Delivery_Address.this, response.getString("message"), Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername, Utility.ServerPassword);
                    return headers;
                }
            };
            gvpRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(gvpRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    @Override
    public void locationFetched(Location mLocation, Location oldLocation, String time, String locationProvider) {

        try {

            Geocoder mgeocoder;
            List<Address> maddresses;
            mgeocoder = new Geocoder(this, Locale.getDefault());

            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
            Log.w("LOCTAB", "longitude====>" + longitude + "\nlatitude===>" + latitude);
            Log.w("LOCTAB", "time====>" + time + "\nlocationProvider===>" + locationProvider);


            maddresses = mgeocoder.getFromLocation(mLocation.getLatitude(), mLocation.getLongitude(), 1);
            if (maddresses != null && maddresses.size() > 0) {
                cityName = maddresses.get(0).getLocality();
                postalCode = maddresses.get(0).getPostalCode();
                String knownName = maddresses.get(0).getFeatureName();
                Log.w("LOCTAB", "inside city--" + cityName);
                Log.w("LOCTAB", "inside postalCode--" + postalCode);
                Log.w("LOCTAB", "inside knownName--" + knownName);
                Log.w("LOCTAB", "inside getAddressLine--" + maddresses.get(0).getAddressLine(0));
                Log.w("LOCTAB", "inside getAdminArea--" + maddresses.get(0).getAdminArea());

                AppController.completeAddress = maddresses.get(0).getAdminArea();
//                sessionManagement.addFullAddress(AppController.completeAddress);
                stateNewName = maddresses.get(0).getAdminArea();
                fullAddress = maddresses.get(0).getAddressLine(0);

//                Log.w("LOCTAB", "inside getAdminArea--" + maddresses.get(0).getAdminArea());
                //Shan20Nov2020
                if (btnClickLoc.equals("1")) {
                    btnClickLoc = "";
                    Log.w("BTAG", "inside loc fetch");
                    if (postalCode.equals("") | postalCode.equals("0") | postalCode.length() == 0 | postalCode.equals("null") | postalCode.equals(null)) {
                        StyleableToast.makeText(Delivery_Address.this, "Due to weather we are Unable to detect pincode.Please Insert Manually", R.style.mySizeToast).show();
                        progressDialog.dismiss();
                        btnClick = false;
//                        uml_rl.setEnabled(true);

                    } else if (cityName.equals("") | cityName.equals("0") | cityName.length() == 0 | cityName.equals("null") | cityName.equals(null)) {
                        StyleableToast.makeText(Delivery_Address.this, "Due to weather we are Unable to detect City.Please Enter Manually", R.style.mySizeToast).show();
                        progressDialog.dismiss();
                        btnClick = false;
//                        uml_rl.setEnabled(true);
                    } else {
//                                getMyCity();
                        int size = city.size();
//                                StyleableToast.makeText(Location_Text_Intent.this, "Size : "+ size + cityName, R.style.mySizeToast).show();
                        if (size != 0) {
                            for (int i = 0; i < size; i++) {
//                                     cityNewArray.add(city.get(i));
//                                     cityNewArrayID.add(cityid.get(i));

                                if (cityNewArray.get(i).length() != 0) {
                                    if (cityNewArray.get(i).equals(cityName)) {
                                        if (cityNewArrayID.get(i).length() != 0) {
                                            sessionManagement.updateCity(cityNewArrayID.get(i), cityNewArray.get(i),stateNewName, postalCode);

                                            AppController.completeAddress = maddresses.get(0).getAddressLine(0);
                                            sessionManagement.addFullAddress(AppController.completeAddress, maddresses.get(0).getAdminArea());
                                            saveCurrentLoca(cityNewArrayID.get(i),cityNewArray.get(i),stateNewName,postalCode);
//                                            Intent intent = new Intent(Delivery_Address.this, Container_Main_Class.class);
//                                            startActivity(intent);
                                            finish();
                                            MainActivity.firstTime = false;
//                                                    progressDialog.dismiss();
                                            btnClick = false;
//                                            uml_rl.setEnabled(true);
                                            //  }

                                            // }, 3000L);
//                                                 Toast.makeText(Location_Text_Intent.this, cityNewArrayID.get(i)+" \n "+cityNewArray.get(i) + " \n "+postalCode, Toast.LENGTH_SHORT).show();
//                                                 Toast.makeText(Location_Text_Intent.this, cityNewArray.get(i) + " \n "+postalCode, Toast.LENGTH_SHORT).show();


                                        }
                                    } else {
//                                        progressDialog.dismiss();
                                        getGoogleCityByPincode(maddresses.get(0).getAddressLine(0), maddresses.get(0).getAdminArea());

                                    }
                                }
                            }

                        } else {
                            progressDialog.dismiss();
                            btnClick = false;
//                            uml_rl.setEnabled(true);
                        }

//                                Intent intent = new Intent(Location_Text_Intent.this,Container_Main_Class.class);
//                                startActivity(intent);
                    }
                }

                //
            } else {
                if(!locClick){
                    StyleableToast.makeText(Delivery_Address.this, "Due to weather we are Unable to detect pincode.Please Insert Manually", R.style.mySizeToast).show();
                    locClick=true;
                }
                progressDialog.dismiss();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveCurrentLoca(String cl_cityId, String cl_cityName, String cl_state, String cl_postalCode) {
        try {
            JSONObject gvp_object = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            gvp_object.put("pincode",cl_postalCode);
            gvp_object.put("city_id",cl_cityId);
            gvp_object.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            gvp_object.put("temp_user_id",tempid);
            gvp_object.put("address",sessionManagement.getUserDetails().get(KEY_FULLADDRESS));
            Log.w("GVPTAG", "Object " + gvp_object);

            JsonObjectRequest gvpRequest = new JsonObjectRequest(Request.Method.POST, Utility.SaveLocation, gvp_object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("success") == 1) {

                            Toast.makeText(Delivery_Address.this,response.getString("message"), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(Delivery_Address.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername, Utility.ServerPassword);
                    return headers;
                }
            };
            gvpRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(gvpRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getGoogleCityByPincode(String addressLine, String adminArea) {
        apiloc="";
        apilocNew="1";
        apilocNewError="1";
        CustomRequest cityRequest = new CustomRequest(Request.Method.GET, Utility.GoogleApi + postalCode + "&sensor=true", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("results");
                    if (array.length() > 0) {
                        JSONObject jsonObject = array.getJSONObject(0);
                        JSONArray addressArray = jsonObject.getJSONArray("address_components");
                        for (int i = 0; i < addressArray.length(); i++) {
                            for (int k = 0; k < cityNewArray.size(); k++) {
                                if (addressArray.getJSONObject(i).getString("long_name").equals(cityNewArray.get(k))) {
                                    apiloc="1";
                                    if (apiloc.equals("1") &&apilocNew.equals("1")){
                                        Log.w("BTAG", "inside loc google fetch");
                                        apiloc="2";
                                        apilocNew="";
                                        if (cityNewArrayID.get(k).length() != 0) {
                                            sessionManagement.updateCity(cityNewArrayID.get(k), cityNewArray.get(k),stateNewName, postalCode);
                                            AppController.completeAddress = addressLine;
                                            sessionManagement.addFullAddress(AppController.completeAddress, adminArea);
                                            saveCurrentLoca(cityNewArrayID.get(k),cityNewArray.get(k),stateNewName,postalCode);
//                                            Intent intent = new Intent(Delivery_Address.this, Container_Main_Class.class);
//                                            startActivity(intent);
                                            finish();
                                            MainActivity.firstTime = false;
//                                            progressDialog.dismiss();
                                            btnClick = false;
//                                            uml_rl.setEnabled(true);



                                        }

                                    }


                                } else {
                                    if (apilocNewError.equals("1") &&(i == addressArray.length() - 1) && (k == cityNewArray.size() - 1) && apiloc.equals("")) {
                                        progressDialog.dismiss();
                                        apilocNewError="";
                                        btnClick = false;
//                                        uml_rl.setEnabled(true);
                                        Toast.makeText(Delivery_Address.this, "Delivery not available at your location", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });
        cityRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addRequestInQueue(cityRequest);


    }
}
