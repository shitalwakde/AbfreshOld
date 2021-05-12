package com.abfresh.in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
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

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.appbar.AppBarLayout;

import com.abfresh.in.Adapter.HorizontallScrollAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Fragments.NewProductDiscriptionFragment;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City;
import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_FULLADDRESS;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class NewProductDiscription extends AppCompatActivity  implements IncrementCart{
        SessionManagement sessionManagement;
    private ArrayList<com.abfresh.in.Model.ArrayList> horizontalList;
    private HorizontallScrollAdapter horizontalAdapter;
    RecyclerView rv_hori_tab;
    public static String productID,categoryId;
    public static TextView textCartItemCount_npd;
    AppBarLayout appBarLayout_toolbar;
    ImageView backbtn_npd;
    RelativeLayout loca_rl_new_npd;
    TextView city_name_tab_loca_npd;
    LinearLayout no_data_ll_npd;
    FrameLayout fragment_container_npd;
    public static FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_product_discription);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(NewProductDiscription.this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        Toolbar toolbar=(Toolbar)findViewById(R.id.tab_toolbar_npd);
        setSupportActionBar(toolbar);
        appBarLayout_toolbar = (AppBarLayout) findViewById(R.id.app_bar_layout_toolbar_npd);
         loca_rl_new_npd=(RelativeLayout) findViewById(R.id.loca_rl_new_npd);


        sessionManagement = new SessionManagement(getApplicationContext());
        city_name_tab_loca_npd = (TextView)findViewById(R.id.city_name_tab_loca_npd);


        rv_hori_tab = (RecyclerView)findViewById(R.id.rv_hori_tab);
        no_data_ll_npd = (LinearLayout) findViewById(R.id.no_data_ll_npd);
        fragment_container_npd = (FrameLayout) findViewById(R.id.fragment_container_npd);
        productID = getIntent().getStringExtra("productId");
        categoryId = getIntent().getStringExtra("categoryId");



        backbtn_npd = (ImageView)findViewById(R.id.backbtn_npd);

        backbtn_npd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                Intent intent = new Intent(NewProductDiscription.this,TabActivity.class);
//                startActivity(intent);
            }
        });

        loca_rl_new_npd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewProductDiscription.this,Delivery_Address.class);
               Delivery_Address.locClick=false;
                startActivity(intent);
            }
        });
        getMyTab();
    }

    private void getMyTab() {

        try {
            final ProgressDialog progressDialog = ProgressDialog.show(NewProductDiscription.this, null, null, true);
            progressDialog.setContentView(R.layout.custom_progress_dialog);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.show();
            JSONObject tabJsonObject = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            tabJsonObject.put("temp_user_id",tempid);
            tabJsonObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            tabJsonObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            tabJsonObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("NPDTAG", "response tabJsonObject "+tabJsonObject);

            JsonObjectRequest tabRequest = new JsonObjectRequest(Request.Method.POST, Utility.Home, tabJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if((response.getString("success")).equals("1")) {
                            no_data_ll_npd.setVisibility(View.GONE);
                            rv_hori_tab.setVisibility(View.VISIBLE);
                            fragment_container_npd.setVisibility(View.VISIBLE);
                            horizontalList = new ArrayList<com.abfresh.in.Model.ArrayList>();
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            setupBadge();
                            Log.w("NPDTAG", "response inside ===>inside "+response);
                            Log.w("NPDTAG", "response Utility.CartCount ===>inside "+Utility.CartCount);

                            JSONArray tabArray = response.getJSONArray("categoryList");
                            for (int i=0;i<tabArray.length();i++) {
                                JSONObject tabobject = tabArray.getJSONObject(i);
                                String bannerName = tabobject.getString("category_name");
                                String catId = tabobject.getString("category_id");

//                                if(categoryId.equals(catId))

                                horizontalList.add(new com.abfresh.in.Model.ArrayList(bannerName,catId));
                            }

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //start your activity here

                                    progressDialog.dismiss();

                                }

                            }, 2000);
                        }else{
                            Toast.makeText(NewProductDiscription.this, "Please Try after some time", Toast.LENGTH_SHORT).show();
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //start your activity here

                                    progressDialog.dismiss();

                                }

                            }, 2000);
                            no_data_ll_npd.setVisibility(View.VISIBLE);
                            rv_hori_tab.setVisibility(View.GONE);
                            fragment_container_npd.setVisibility(View.GONE);
                        }
                        horizontalAdapter = new HorizontallScrollAdapter(getApplicationContext(),horizontalList);
                        final LinearLayoutManager horizontalProduct
                                = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                        rv_hori_tab.setLayoutManager(horizontalProduct);
                        rv_hori_tab.setNestedScrollingEnabled(false);

                        rv_hori_tab.setAdapter(horizontalAdapter);

                        rv_hori_tab.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                                super.onScrolled(recyclerView, dx, dy);
                                int firstItemVisible = horizontalProduct.findFirstVisibleItemPosition();
                                if (firstItemVisible != 0 && firstItemVisible % horizontalList.size() == 0) {
                                    recyclerView.getLayoutManager().scrollToPosition(0);
                                }
                            }
                        });
                        horizontalAdapter.setOnItemClickListner(new HorizontallScrollAdapter.OnItemClickListner() {
                            @Override
                            public void onBannerClick(int position) {
                                finish();
//                                String proId = horizontalList.get(position).getCatImage();
////                                    Intent intent = new Intent(getContext(), Product_Discription.class);
//                                Intent intent = new Intent(NewProductDiscription.this, TabActivity.class);
//                                intent.putExtra("categoryId",proId);
//                                intent.putExtra("productId",proId);
//                                startActivity(intent);


                                AppController.tabCurrentItem =  Integer.parseInt(horizontalList.get(position).getCatImage());;
//                                proImage = String.valueOf(productImage);
//                                    Toast.makeText(getContext(), ""+echorizontalList.get(position).getColorArray(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewProductDiscription.this, TabActivity.class);
                                // intent.putExtra("ID", String.valueOf(position));
                                intent.putExtra("ID", horizontalList.get(position).getCatImage());
                                intent.putExtra("image","echorizontalList.get(position).getCatImage()");
                                Log.w("BTAG", "ImageURL "+"echorizontalList.get(position).getCatImage()");
//                                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                                        getActivity(), productImage, ViewCompat.getTransitionName(productImage));
                                startActivity(intent);
                            }
                        });
//                        cat_pro_pb.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    no_data_ll_npd.setVisibility(View.VISIBLE);
                    rv_hori_tab.setVisibility(View.GONE);
                    fragment_container_npd.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            };tabRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(tabRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void addCount(boolean ADD_FLAG) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View  actionView = menuItem.getActionView();
        textCartItemCount_npd = (TextView) actionView.findViewById(R.id.cart_badge);

//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        getMyTab();
        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("BTAG", "response Utility.CartCount ===>new "+Utility.CartCount);
                Log.w("BTAG", "response Utility.menuItem ===>new "+menuItem);
                onOptionsItemSelected(menuItem);

            }
        });

        return true;


    }

    public void setupBadge() {

        if (textCartItemCount_npd != null) {
//            Log.w("BTAG", "response Utility.CartCount ===>outside "+Utility.CartCount);

            if (Utility.CartCount==0){
                if (textCartItemCount_npd.getVisibility() != View.GONE) {
                    textCartItemCount_npd.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount_npd.setText(String.valueOf(Utility.CartCount));
//                Utility.CartCount = String.valueOf(tabCartCount);
                if (textCartItemCount_npd.getVisibility() != View.VISIBLE) {
                    textCartItemCount_npd.setVisibility(View.VISIBLE);
                }
            }
        }
        invalidateOptionsMenu();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_search:
                Intent intent = new Intent(NewProductDiscription.this,SearchItem.class);
                startActivity(intent);
                return true;

            case R.id.action_cart:
                Log.w("BTAG", "response Utility.CartCount ===>new "+Utility.CartCount);
                if (Utility.CartCount==0){
                    Toast.makeText(this, "Zero items in cart", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intentn = new Intent(NewProductDiscription.this,CartDiscription.class);
//                    Utility.FromTabClass=true;
//                    Utility.FromMainClass=false;
//                    Utility.FromPD=false;
                    startActivity(intentn);
                }
                return true;
//            case  R.id.action_account:
//                if(!(sessionManagement.isLoggedIn())){
////
////                    Utility.FromPD = false;
////                    Utility.FromMainClass = false;
//                    Intent intent = new Intent(NewProductDiscription.this,Login.class);
//                    intent.putExtra("fromPage","two");
//                    startActivity(intent);
//                }else{
//                    Intent intent = new Intent(NewProductDiscription.this,MyAccount.class);
//                    intent.putExtra("fromPage","two");
//                    startActivity(intent);
//                }

//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_npd,
                new NewProductDiscriptionFragment()).commit();

        if(sessionManagement.getUserDetails().get(KEY_FULLADDRESS).length()!=0){
            city_name_tab_loca_npd.setText(sessionManagement.getUserDetails().get(KEY_FULLADDRESS));
        }else{
            city_name_tab_loca_npd.setText(sessionManagement.getUserDetails().get(KEY_City)+",Maharashtra India");
        }
    }
}
