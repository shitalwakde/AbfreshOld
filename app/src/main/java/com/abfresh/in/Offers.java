package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Adapter.OfferAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class Offers extends AppCompatActivity {
    OfferAdapter offerAdapter;
    Toolbar offerToolbar;
    ImageView offer_home_btn;
   public static Boolean offerClick;
//    ArrayList<com.tbd.abFresh.model.ArrayList> offerList;
        java.util.ArrayList<ArrayList> offerList;
//    ArrayList<com.tbd.abFresh.model.ArrayList> offerList;
    RecyclerView delivery_sot_rv;
    SessionManagement sessionManagement;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_layout);
        offerToolbar = (Toolbar)findViewById(R.id.offers_toolbar);
        setSupportActionBar(offerToolbar);
        getSupportActionBar().setTitle("");
//        sessionManagement = new SessionManagement(getApplicationContext());
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        offer_home_btn = (ImageView)findViewById(R.id.offer_home_btn);
        delivery_sot_rv = (RecyclerView) findViewById(R.id.delivery_sot_rv);
        offer_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getOfferDetails();
    }

    private void getOfferDetails() {

        try {

            sessionManagement = new SessionManagement(getApplicationContext());
            JSONObject offerJsonObjectNew = new JSONObject();
            String tempid = Settings.Secure.getString(getApplication().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            offerJsonObjectNew.put("temp_user_id", tempid);
            offerJsonObjectNew.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            JsonObjectRequest offerRequestNew = new JsonObjectRequest(Request.Method.POST, Utility.Offers, offerJsonObjectNew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("RTAG", "response New " + response);
                        offerList = new java.util.ArrayList<>();

                        if ((response.getInt("success")) == 1) {

                            JSONArray offerArray = response.getJSONArray("offerList");

                            for (int i = 0; i < offerArray.length(); i++) {
                                JSONObject offerobject = offerArray.getJSONObject(i);

                                String recipeName = offerobject.getString("category_id");
                                String recipeTypeName = offerobject.getString("category_name");
                                String recipeImage = offerobject.getString("category_image");
//
                            Log.w("OFFTAG","Offer Image====>"+offerobject.getString("category_image"));

                                offerList.add(new com.abfresh.in.Model.ArrayList(recipeName, recipeTypeName, recipeImage));


                            }
                            offerAdapter = new OfferAdapter(getApplicationContext(), offerList);
                            final GridLayoutManager manager
                                    = new GridLayoutManager(Offers.this, 1);

                            delivery_sot_rv.setLayoutManager(manager);
                            delivery_sot_rv.setAdapter(offerAdapter);

                            offerAdapter.setOnItemClickListner(new OfferAdapter.OnItemClickListner() {
                                @Override
                                public void onOfferClick(int position, ImageView productImage) {
                                    offerClick = true;
                                    AppController.tabCurrentItem = Integer.parseInt(offerList.get(position).getName());;
                                    Log.w("OFFTAB","offer tab cat===>"+offerList.get(position).getName());
                                    Intent intent = new Intent(Offers.this, TabActivity.class);
                                  //  intent.putExtra("ID", String.valueOf(position));
                                    intent.putExtra("ID",offerList.get(position).getName());
                                    intent.putExtra("image","");
                                    Log.w("OFFTAB", "ImageURL "+offerList.get(position).getWeight());
//                                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                            Offers.this, productImage, ViewCompat.getTransitionName(productImage));
                                    startActivity(intent);

                                }
                            });


                        } else {
//                            recipe_pb.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("RTAG", "JSONException e " + e);
//                        recipe_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.w("RTAG", "VolleyError error " + error);
//                    recipe_pb.setVisibility(View.GONE);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername, Utility.ServerPassword);
                    return headers;
                }
            };
            offerRequestNew.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(offerRequestNew);


        } catch (Exception e) {
            e.printStackTrace();
//            recipe_pb.setVisibility(View.GONE);

        }
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id =item.getItemId();
//        if(id== android.R.id.home){
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
