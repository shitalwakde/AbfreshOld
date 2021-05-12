package com.abfresh.in;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.LocationRequest;
import com.abfresh.in.Adapter.CountryAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.CustomRequest;
import com.abfresh.in.Controller.LocationManagerInterface;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.SmartLocationManager;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.CountryItem;
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

public class Location_Text_Intent extends AppCompatActivity implements LocationManagerInterface {
    LinearLayout search_ll;
    RelativeLayout uml_rl;
    Button cl_btn;
    TextView search_et;
    public static int getCityName;
    EditText pincode_et;
    SessionManagement sessionManagement;
    Spinner spinner_countries;
    private ArrayList<com.abfresh.in.Model.CountryItem> mylocation;
    List<String> city;
    List<String> cityid;
    ProgressBar lfp_pb;
    private LocationManager locationManager;
    private Location onlyOneLocation;
    private final int REQUEST_FINE_LOCATION = 1234;
    String cityName, postalCode,stateName,stateNewName;
    Geocoder geocoder;
    List<Address> addresses;
    private Handler mHandler = new Handler();
    double latitude, longitude;
    String clickedCountryID;
    String newCountryName;
    String newCountryId;
    String clickedCountryName;
    ArrayList<String> cityNewArray;
    ArrayList<String> stateNewArray;
    ArrayList<String> cityNewArrayID;
    public static int ltCounter = 0;
    ImageView lfp_close_btn;
    SmartLocationManager mLocationManager;
    ProgressDialog progressDialog;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    boolean btnClick = false;
    String btnClickLoc = "";
    String apiloc = "";
    String apilocNew = "";
    String apilocNewError = "";
    String fullAddress="";
    TextView notice_tv_lfp;
    boolean oneTime=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_front_page);
        search_ll = (LinearLayout) findViewById(R.id.search_ll);
        notice_tv_lfp = (TextView) findViewById(R.id.notice_tv_lfp);
        //        geocoder = new Geocoder(this, Locale.getDefault());
//        LocationTrack locationTrack = new LocationTrack(getApplicationContext());
        if (MainActivity.locationget) {
            mLocationManager = new SmartLocationManager(getApplicationContext(), Location_Text_Intent.this, Location_Text_Intent.this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE);
            mLocationManager.startLocationFetching();
        } else {
            ActivityCompat.requestPermissions(Location_Text_Intent.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
//        else{

//        }
//        else{
//
//            mLocationManager = new SmartLocationManager(getApplicationContext(), Location_Text_Intent.this, Location_Text_Intent.this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE);
//            mLocationManager.initSmartLocationManager();
//            mLocationManager.startLocationFetching();
//        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.location_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        sessionManagement = new SessionManagement(getApplicationContext());


//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        cl_btn = (Button) findViewById(R.id.cl_btn);
        uml_rl = (RelativeLayout) findViewById(R.id.uml_rl);
        search_et = (TextView) findViewById(R.id.search_et);
        pincode_et = (EditText) findViewById(R.id.pincode_et);
        city = new ArrayList<String>();
        cityid = new ArrayList<String>();
        lfp_pb = (ProgressBar) findViewById(R.id.lfp_pb);
        lfp_close_btn = (ImageView) findViewById(R.id.lfp_close_btn);

        if (!(sessionManagement.isLoggedIn())) {
            lfp_close_btn.setVisibility(View.VISIBLE);
        } else {
            lfp_close_btn.setVisibility(View.GONE);
        }
        lfp_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        spinner_countries = (Spinner) findViewById(R.id.spinner_countries);

        getMyCity();

        spinner_countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CountryItem clickedItem = (CountryItem) parent.getItemAtPosition(position);
                String clickedCountryName = clickedItem.getCountryName();
                clickedCountryID = clickedItem.getmCountryId();

                search_et.setText(clickedCountryName);
//                Toast.makeText(Location_Text_Intent.this, clickedCountryName + " selected" + clickedCountryID + " ID", Toast.LENGTH_SHORT).show();
//                                String user = (String) parent.getSelectedItem();
//                                sessionManagement.updateCity();
//                spinner_countries.setActivated(true);
                displayUserData(clickedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        if ((getCityName == 1)) {
            search_et.setText(Utility.CityName);
        }

        uml_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("UMLTAG", "btnClicked--");
                btnClick = true;
                uml_rl.setEnabled(false);
                btnClickLoc = "1";
                oneTime=false;
                progressDialog = ProgressDialog.show(Location_Text_Intent.this, null, null, true);
                progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.show();
                Log.w("UMLTAG", "btnClicked--");
            }
//            }
        });
        cl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(Location_Text_Intent.this, null, null, true);
                progressDialog.setContentView(R.layout.custom_progress_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                progressDialog.show();

                if (pincode_et.getText().toString().length() == 0 & pincode_et.getText().toString().length() != 6) {
//                    Toast.makeText(Location_Text_Intent.this, "Please enter pincode", Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(Location_Text_Intent.this, "Please Enter pincode", R.style.mySizeToast).show();
                    progressDialog.dismiss();
                } else {

                    getVerifyPincode(progressDialog);
                }

            }
        });
    }

    private void getVerifyPincode(ProgressDialog progressDialog) {

        try {
            JSONObject gvp_object = new JSONObject();
            gvp_object.put("city_id", clickedCountryID);
            gvp_object.put("pincode", pincode_et.getText().toString().trim());
            Log.w("GVPTAG", "Object " + gvp_object);

            JsonObjectRequest gvpRequest = new JsonObjectRequest(Request.Method.POST, Utility.VerifyCityPincode, gvp_object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("GVPTAG", "response " + response);
                        if (response.getInt("success") == 1) {
                            MainActivity.firstTime = false;
                            AppController.completeAddress = "";
                            Intent intent = new Intent(Location_Text_Intent.this, Container_Main_Class.class);
                            for(int i=0;i<cityNewArrayID.size();i++){
                                if(clickedCountryID==cityNewArrayID.get(i)){
                                    for(int j=0;j<stateNewArray.size();j++){
                                        String state = stateNewArray.get(j);
                                        stateName = stateNewArray.get(j);
//                                        Toast.makeText(Location_Text_Intent.this, ""+state +"===>"+ cityNewArrayID.get(i), Toast.LENGTH_SHORT).show();
                                        Log.w("DHARMTAG", "state=== " + ""+state +"===>"+ cityNewArrayID.get(i));
                                    }
                                }
                            }
                            sessionManagement.updateCity(clickedCountryID,search_et.getText().toString().trim(),stateName,pincode_et.getText().toString().trim());
                            SaveMyLocation(clickedCountryID,search_et.getText().toString().trim(),stateName,pincode_et.getText().toString().trim());
                                    //                        Toast.makeText(Location_Text_Intent.this, clickedCountryID, Toast.LENGTH_SHORT).show();
//                            SaveMyLocation();
                            startActivity(intent);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(Location_Text_Intent.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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

    private void SaveMyLocation(String cityId, String cname, String stateName, String postcode) {
        try {
            JSONObject gvp_object = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            gvp_object.put("pincode",pincode_et.getText().toString().trim());
            gvp_object.put("city_id", this.clickedCountryID);
            gvp_object.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            gvp_object.put("temp_user_id",tempid);
            gvp_object.put("address","");
            Log.w("GVPTAG", "Object " + gvp_object);

            JsonObjectRequest gvpRequest = new JsonObjectRequest(Request.Method.POST, Utility.SaveLocation, gvp_object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getInt("success") == 1) {

                        Toast.makeText(Location_Text_Intent.this,response.getString("message"), Toast.LENGTH_SHORT).show();
                            AppController.completeAddress = cname+","+stateName+","+"India";
                            sessionManagement.addFullAddress(AppController.completeAddress);
//                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(Location_Text_Intent.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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

//    private Runnable mUpdateTimeTask = new Runnable() {
//        public void run() {
//            // do what you need to do here after the delay
//            lfp_pb.setVisibility(View.GONE);
//

    private void getMyCity() {
        Log.e("CTAG", "response--" + Utility.GetCity);
        CustomRequest cityRequest = new CustomRequest(Request.Method.POST, Utility.GetCity, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    lfp_pb.setVisibility(View.VISIBLE);
                    Log.e("CTAG", "response--" + response);
                    mylocation = new ArrayList<CountryItem>();
                    if (response.getInt("success")==1) {
                        notice_tv_lfp.setText(response.getString("notice"));
                        JSONArray cityArray = response.getJSONArray("city_list");
                        cityNewArray = new ArrayList<>();
                        cityNewArrayID = new ArrayList<>();
                        stateNewArray = new ArrayList<>();
                        for (int i = 0; i < cityArray.length(); i++) {
                            JSONObject cityObject = cityArray.getJSONObject(i);
                            cityNewArrayID.add(cityObject.getString("city_id"));

                            cityNewArray.add(cityObject.getString("city_name"));
                            city.add(cityObject.getString("city_name"));
                            cityid.add(cityObject.getString("city_id"));

                            stateNewArray.add(cityObject.getString("state_name"));
                            String city_id_new = cityObject.getString("city_id");
                            String city_name = cityObject.getString("city_name");

                            mylocation.add(new com.abfresh.in.Model.CountryItem(city_name, city_id_new));
                        }


                        // Creating adapter for spinner

                        CountryAdapter search_location_adapter = new CountryAdapter(getApplicationContext(), mylocation);
                        search_location_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner_countries.setAdapter(search_location_adapter);
                        search_location_adapter.notifyDataSetChanged();

                        lfp_pb.setVisibility(View.GONE);

                    } else {
                        lfp_pb.setVisibility(View.GONE);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    lfp_pb.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                lfp_pb.setVisibility(View.GONE);

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


//

    public void getSelectedUser(View v) {
        CountryItem user = (CountryItem) spinner_countries.getSelectedItem();
        displayUserData(user);
    }

    private void displayUserData(CountryItem user) {
        String name = user.getCountryName();

        String userData = "Country: " + name;
//        Toast.makeText(this, userData, Toast.LENGTH_LONG).show();
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.w("LITAG", "Permission Granted");

                    mLocationManager = new SmartLocationManager(getApplicationContext(), Location_Text_Intent.this, Location_Text_Intent.this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE);
                    mLocationManager.startLocationFetching();

                } else {
                    Toast.makeText(this, "All Permission are needed", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(Location_Text_Intent.this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }

//                                Intent intent = new Intent(Location_Text_Intent.this,Container_Main_Class.class);
//                                startActivity(intent);


                break;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
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
                Log.w("LOCTAB", "inside getAdminArea--" + maddresses.get(0).getLocality());

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
//                        Toast.makeText(Location_Text_Intent.this, "Due to weather we are Unable to detect pincode.Please Insert Manually", Toast.LENGTH_SHORT).show();
                        StyleableToast.makeText(Location_Text_Intent.this, "Due to weather we are Unable to detect pincode.Please Insert Manually", R.style.mySizeToast).show();
                        progressDialog.dismiss();
                        btnClick = false;
                        uml_rl.setEnabled(true);

                    } else if (cityName.equals("") | cityName.equals("0") | cityName.length() == 0 | cityName.equals("null") | cityName.equals(null)) {
//                        Toast.makeText(Location_Text_Intent.this, "Due to weather we are Unable to detect pincode.Please Insert Manually", Toast.LENGTH_SHORT).show();

                        StyleableToast.makeText(Location_Text_Intent.this, "Due to weather we are Unable to detect City.Please Enter Manually", R.style.mySizeToast).show();
                        progressDialog.dismiss();
                        btnClick = false;
                        uml_rl.setEnabled(true);
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
                                            sessionManagement.addFullAddress(AppController.completeAddress);
                                            saveCurrentLoca(cityNewArrayID.get(i),cityNewArray.get(i),stateNewName,postalCode);
                                                    Intent intent = new Intent(Location_Text_Intent.this, Container_Main_Class.class);
                                                    startActivity(intent);
                                                    MainActivity.firstTime = false;
//                                                    progressDialog.dismiss();
                                                    btnClick = false;
                                                    uml_rl.setEnabled(true);
                                              //  }

                                           // }, 3000L);
//                                                 Toast.makeText(Location_Text_Intent.this, cityNewArrayID.get(i)+" \n "+cityNewArray.get(i) + " \n "+postalCode, Toast.LENGTH_SHORT).show();
//                                                 Toast.makeText(Location_Text_Intent.this, cityNewArray.get(i) + " \n "+postalCode, Toast.LENGTH_SHORT).show();


                                        }
                                    } else {

                                        getGoogleCityByPincode(maddresses.get(0).getAddressLine(0));

                                    }
                                }
                            }

                        } else {
                            progressDialog.dismiss();
                            btnClick = false;
                            uml_rl.setEnabled(true);
                        }

//                                Intent intent = new Intent(Location_Text_Intent.this,Container_Main_Class.class);
//                                startActivity(intent);
                    }
                }

                //
            } else {
                if(!oneTime){
                    StyleableToast.makeText(Location_Text_Intent.this, "Due to weather we are Unable to detect pincode.Please Insert Manually", R.style.mySizeToast).show();
                    oneTime = true;
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

                            Toast.makeText(Location_Text_Intent.this,response.getString("message"), Toast.LENGTH_SHORT).show();

                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(Location_Text_Intent.this, response.getString("message"), Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        super.onResume();
        if (!(sessionManagement.isLoggedIn())) {
            lfp_close_btn.setVisibility(View.GONE);
        } else {
            lfp_close_btn.setVisibility(View.VISIBLE);
        }
    }


    //Shan20Nov2020
    private void getGoogleCityByPincode(String addressLine) {
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
                                            sessionManagement.addFullAddress(AppController.completeAddress);
                                            saveCurrentLoca(cityNewArrayID.get(k),cityNewArray.get(k),stateNewName,postalCode);
                                            Intent intent = new Intent(Location_Text_Intent.this, Container_Main_Class.class);
                                            startActivity(intent);
                                            MainActivity.firstTime = false;
//                                            progressDialog.dismiss();
                                            btnClick = false;
                                            uml_rl.setEnabled(true);



                                        }

                                    }


                                } else {
                                    if (apilocNewError.equals("1") &&(i == addressArray.length() - 1) && (k == cityNewArray.size() - 1) && apiloc.equals("")) {
                                        progressDialog.dismiss();
                                        apilocNewError="";
                                        btnClick = false;
                                        uml_rl.setEnabled(true);
                                        Toast.makeText(Location_Text_Intent.this, "Delivery not available at your location", Toast.LENGTH_SHORT).show();
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
