package com.abfresh.in.Abfresh.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abfresh.in.Adapter.GooglePlacesAutocompleteAdapter;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddAddressActivity extends AppCompatActivity {
    public static final String EXTRA_LOCATION_ID = "EXTRA_LOCATION_ID";
    private static final String TAG = "AddAddressActivity";
    public static final String ADDRESS_LAT_LNG = "AddressLatLng";
    AutoCompleteTextView etAddress;
    public static Double lat=0.0;
    public static Double lon=0.0;
    TextView tv_toolbar_title;
    ImageView iv_cross, iv_back_arrow;
    LinearLayout ll_currentlocation;
    SessionManagement sessionManagement;
    String cityName="",stateName="",postalCode="", areaName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Add Address");
        init();
    }

    private void init(){
        sessionManagement=new SessionManagement(AddAddressActivity.this);
        etAddress=(AutoCompleteTextView)findViewById(R.id.etAddress);
        ll_currentlocation=(LinearLayout)findViewById(R.id.ll_currentlocation);
        iv_cross=(ImageView)findViewById(R.id.iv_cross);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);

        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etAddress.setText("");
            }
        });

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etAddress.setAdapter(new GooglePlacesAutocompleteAdapter(AddAddressActivity.this, R.layout.layout_google_places));
        try {
            Geocoder geo = new Geocoder(AddAddressActivity.this, Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lon, 1);
            Log.w("TAG","address : "+addresses+" lat : "+lat+"" +
                    " lng : "+lon);
            if (addresses.isEmpty()) {
                //et_area.setText("Waiting for Location");
            }
            else {
                if (addresses.size() > 0) {
                    getLocationFromAddress(addresses.get(0).getAddressLine(0));
                    cityName=addresses.get(0).getLocality();
                    areaName=addresses.get(0).getAddressLine(0);
                    stateName= addresses.get(0).getAdminArea();
                    postalCode=addresses.get(0).getPostalCode();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        etAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                LatLng addressLatLng = getLocationFromAddress(etAddress.getText().toString());
                Intent intent= new Intent();
                intent.putExtra(ADDRESS_LAT_LNG, addressLatLng);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });


        ll_currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddressActivity.this, DeliveryDetailActivity.class);
                intent.putExtra("type", "currentLocation");
                startActivity(intent);
                finish();
            }
        });

    }


    public LatLng getLocationFromAddress(String strAddress) {
        Log.w(TAG, "onItemClick: strAddress :"+strAddress);
        Geocoder coder = new Geocoder(AddAddressActivity.this);
        List<Address> address;
        LatLng p1 = null;
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            if(address.size()>0) {
                Address location = address.get(0);
                //Lets take first possibility from the all possibilities.
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                p1= latLng;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return p1;
    }



}