package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Adapter.ExploreCatAdapter;
import com.abfresh.in.Adapter.SearchItemAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class SearchItem extends AppCompatActivity {
    SessionManagement sessionManagement;
    private RecyclerView search_item_rv;
    private ExploreCatAdapter siAdapter;
    private java.util.ArrayList<ArrayList> sihorizontalList;
    Toolbar search_item_toolbar;
    EditText editText_search_item;
    RecyclerView recyclerView;
    java.util.ArrayList<ArrayList> item;
    private java.util.ArrayList<ArrayList> myitem;
    SearchItemAdapter searchAdapter;
    ImageView search_item_line;
    TextView noitemfound_tv;
    RelativeLayout ec_rl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        search_item_toolbar = (Toolbar)findViewById(R.id.search_item_tolbar);
        setSupportActionBar(search_item_toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        sessionManagement = new SessionManagement(getApplicationContext());
        search_item_rv = (RecyclerView)findViewById(R.id.recyclerView_search_item);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_chhoseitem);
        editText_search_item = (EditText)findViewById(R.id.editText_search_item);
        search_item_line = (ImageView) findViewById(R.id.search_item_line);
        noitemfound_tv = (TextView) findViewById(R.id.noitemfound_tv);
        ec_rl = (RelativeLayout) findViewById(R.id.ec_rl);
        Offers.offerClick=false;
        getSearchExploreCategory();

        item = new java.util.ArrayList<ArrayList>();
        editText_search_item.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if(editText_search_item.getText().toString().trim().length()==0){
//                    search_item_rv.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                    search_item_line.setVisibility(View.VISIBLE);
                    ec_rl.setVisibility(View.VISIBLE);

                }else{
                    recyclerView.setVisibility(View.VISIBLE);
//                    search_item_rv.setVisibility(View.GONE);
//                    search_item_line.setVisibility(View.GONE);
                    ec_rl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(editText_search_item.getText().toString().trim().length()==0){

                    ec_rl.setVisibility(View.VISIBLE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);

                    ec_rl.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                if(editText_search_item.getText().toString().trim().length()==0){
                    search_item_rv.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    search_item_line.setVisibility(View.VISIBLE);

                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    search_item_rv.setVisibility(View.GONE);
                    search_item_line.setVisibility(View.GONE);

                    getSearchItem();

                }


            }
        });


    }



    private void getSearchItem() {
        try {
            sessionManagement = new SessionManagement(getApplicationContext());
            JSONObject getcatObject = new JSONObject();
            String tempid= Settings.Secure.getString(getApplication().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            getcatObject.put("temp_user_id",tempid);
            getcatObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            getcatObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            getcatObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            getcatObject.put("search",editText_search_item.getText().toString());
            Log.w("SRTAG", "response getcatObject "+getcatObject);
            JsonObjectRequest catJsonObject = new JsonObjectRequest(Request.Method.POST, Utility.Search, getcatObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if ((response.getInt("success"))==1){
                            Log.w("SRTAG", "response response "+response);

                            JSONArray catJarray = response.getJSONArray("productList");
                            item.clear();
                            if(catJarray.length()!=0){
                                noitemfound_tv.setVisibility(View.GONE);
                            }else{
                                noitemfound_tv.setVisibility(View.VISIBLE);

                            }

//                            ec_rl.setVisibility(View.GONE);

                            for (int i=0;i<catJarray.length();i++){

                                JSONObject catJObject = catJarray.getJSONObject(i);
                                String catProductId = catJObject.getString("product_id");
                                String catgross_amt = catJObject.getString("category_id");
                                String catName = catJObject.getString("product_name");
                                String catproduct_amt = catJObject.getString("product_amt");
                                String catProductGwt = catJObject.getString("product_gross_weight");
                                String catProductwt = catJObject.getString("product_weight");
                                String catdiscount = catJObject.getString("product_disc_name");
                                String catProductStock = catJObject.getString("product_brief");
                                String catImage = catJObject.getString("product_image");
                                String is_in_cart = ""+catJObject.getString("is_in_cart");
                                String cart_qty = catJObject.getString("cart_qty");

                                Log.w("SRTAG", "response catProductId "+catProductId);
                                Log.w("SRTAG", "response catgross_amt "+catgross_amt);
                                Log.w("SRTAG", "response catName "+catName);
                                Log.w("SRTAG", "response catproduct_amt "+catproduct_amt);
                                Log.w("SRTAG", "response catProductGwt "+catProductGwt);
                                Log.w("SRTAG", "response catProductwt "+catProductwt);
                                Log.w("SRTAG", "response catdiscount "+catdiscount);
                                Log.w("SRTAG", "response catProductStock "+catProductStock);
                                Log.w("SRTAG", "response catImage "+catImage);
                                Log.w("SRTAG", "response is_in_cart "+is_in_cart);
                                Log.w("SRTAG", "response cart_qty "+cart_qty);

//                                ProductId = catJObject.getString("product_id");

                                item.add(new com.abfresh.in.Model.ArrayList(catName,catproduct_amt,catImage,catgross_amt,catdiscount,catProductId,catProductGwt,catProductwt,catProductStock,is_in_cart,cart_qty) );
                            }
                            searchAdapter = new SearchItemAdapter(getApplicationContext(), item);
                            recyclerView.setNestedScrollingEnabled(false);
                            final GridLayoutManager manager
                                    = new GridLayoutManager(SearchItem.this,1);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(searchAdapter);
//                            cat_pro_pb.setVisibility(View.GONE);
                            ViewCompat.setNestedScrollingEnabled(recyclerView, false);

                            searchAdapter.setOnItemClickListner(new SearchItemAdapter.OnItemClickListner() {
                                @Override
                                public void onCatProductClick(int position, ImageView productImage) {
                                    String proId = item.get(position).getProductId();
                                    String catId = item.get(position).getProGross();

                                    Intent intent = new Intent(SearchItem.this, NewProductDiscription.class);
                                    intent.putExtra("categoryId",catId);
                                    intent.putExtra("productId",proId);
                                    Log.w("SNTAG","Cat==>"+catId+","+"proId==>"+proId);
                                    startActivity(intent);
                                }

                                @Override
                                public void onAddCartClick(int position) {

                                }

                                @Override
                                public void onCartIncrement(int position, TextView increment_tv) {

                                }

                                @Override
                                public void onCartdecrement(int position, TextView increment_tv) {

                                }
                            });
//------------------------------------------Product Discription-----------------------------------------------
//                            searchAdapter.setOnItemClickListner(new CategoryProductAdapter.OnItemClickListner() {
//                                @Override
//                                public void onCatProductClick(int position, ImageView productImage) {
//                                    String proId = item.get(position).getProductId();
//                                    Intent intent = new Intent(SearchItem.this, Product_Discription.class);
//                                    intent.putExtra("productId",proId);
//
//                                    startActivity(intent);
////                                    categoryProductAdapter.notifyDataSetChanged();
//                                }
//                                //--------------------------------------Add to Cart----------------------------------------------------
//                                @Override
//                                public void onAddCartClick(int position) {
//                                    int number =1;
//                                    addToCart(position, number);
//                                }
//
//                                @Override
//                                public void onCartIncrement(int position, TextView increment_tv) {
//
//                                    int number = Integer.parseInt(increment_tv.getText().toString()) ;
//                                    number = number +1;
//                                    String NewNumber = String.valueOf(number);
////                                    Toast.makeText(getActivity(),NewNumber, Toast.LENGTH_SHORT).show();
//                                    increment_tv.setText(String.valueOf(number));
//                                    addToCart(position,number);
//                                }
//
//                                @Override
//                                public void onCartdecrement(int position, TextView increment_tv) {
//
//                                    int number = Integer.parseInt(increment_tv.getText().toString()) ;
//                                    number = number -1;
//
//                                    if(number==0){
//                                        String proId = item.get(position).getProductId();
//                                        removeFromCartCPF(proId);
//                                    }else{
//                                        String NewNumber = String.valueOf(number);
////                                        Toast.makeText(getActivity(),NewNumber, Toast.LENGTH_SHORT).show();
//                                        increment_tv.setText(String.valueOf(number));
//                                        addToCart(position,number);
//                                    }
//
//
//
//                                }
////--------------------------------------increment / decrement Cart----------------------------------------------------
//
////                                @Override
////                                public void onCartNumber(int position) {
////                                    addToCart(position);
////                                }
//                            });

                        }else{
                            noitemfound_tv.setVisibility(View.VISIBLE);
//                            ec_rl.setVisibility(View.VISIBLE);

//                            cat_pro_pb.setVisibility(View.GONE);
//                            Toast.makeText(SearchItem.this, "Please try after some time", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
//                        cat_pro_pb.setVisibility(View.GONE);
                        Toast.makeText(SearchItem.this, "Please try after some time", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    cat_pro_pb.setVisibility(View.GONE);

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            };catJsonObject.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(catJsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
//            cat_pro_pb.setVisibility(View.GONE);

        }


    }



    private void getSearchExploreCategory() {
        try {
        sessionManagement = new SessionManagement(SearchItem.this);
        JSONObject siTabJsonObjectNew = new JSONObject();
        String tempid = Settings.Secure.getString(SearchItem.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
            siTabJsonObjectNew.put("temp_user_id", tempid);
            siTabJsonObjectNew.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            siTabJsonObjectNew.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            siTabJsonObjectNew.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
        Log.w("SITAG", "response siTabJsonObjectNew " + siTabJsonObjectNew);


        JsonObjectRequest siTabRequestNew = new JsonObjectRequest(Request.Method.POST, Utility.Home, siTabJsonObjectNew, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.w("SITAG", "response New " + response);
                    sihorizontalList = new java.util.ArrayList<ArrayList>();

                    if ((response.getString("success")).equals("1")) {

                        JSONArray tabArray = response.getJSONArray("categoryList");

                        for (int i = 0; i < tabArray.length(); i++) {
                            JSONObject tabobject = tabArray.getJSONObject(i);
                            String imageCatId = tabobject.getString("category_id");
                            String imageName = tabobject.getString("category_name");
//                            String imageName = "";
                            String imageIcon = tabobject.getString("category_image");
//                            String imageIcon = tabobject.getString("");
                            Log.w("NBTAG", "Image  " + imageName);

//                            echorizontalList.add(new com.tbd.abFresh.model.ArrayList(imageCatId,imageName,imageIcon));
                            Log.w("NBTAG", "Image  " + imageName);

                            sihorizontalList.add(new com.abfresh.in.Model.ArrayList(imageCatId,imageName,imageIcon));


                        }
                        siAdapter = new ExploreCatAdapter(SearchItem.this, sihorizontalList);
                        final GridLayoutManager manager
                                 = new GridLayoutManager(SearchItem.this,2);

                        search_item_rv.setLayoutManager(manager);
                        search_item_rv.setAdapter(siAdapter);
                        siAdapter.setOnItemClickListner(new ExploreCatAdapter.OnItemClickListner() {
                            @Override
                            public void onBannerClick(int position, ImageView productImage) {
//                                TabActivity.tabCurrentItem = position;
                                AppController.tabCurrentItem = Integer.parseInt(sihorizontalList.get(position).getName());;
                                Intent intent = new Intent(SearchItem.this, TabActivity.class);
                                intent.putExtra("ID", sihorizontalList.get(position).getName());
                                intent.putExtra("image",sihorizontalList.get(position).getCatImage());
                                Log.w("BTAG", "ImageURL "+sihorizontalList.get(position).getCatImage());
                                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        SearchItem.this, productImage, ViewCompat.getTransitionName(productImage));
                                startActivity(intent, options.toBundle());
                            }
                        });


                    } else {


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.w("NBTAG", "JSONException e " + e);


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.w("NBTAG", "VolleyError error " + error);
//                container_pb.setVisibility(View.GONE);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put(Utility.ServerUsername, Utility.ServerPassword);
                return headers;
            }
        };
            siTabRequestNew.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addRequestInQueue(siTabRequestNew);
    } catch (JSONException e) {
        e.printStackTrace();
        Log.w("NBTAG", "JSONException e 2  " + e);
//        container_pb.setVisibility(View.GONE);

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
}
