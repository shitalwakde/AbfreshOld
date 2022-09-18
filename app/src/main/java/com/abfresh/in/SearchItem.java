package com.abfresh.in;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.activities.DashboardActivity;
import com.abfresh.in.Abfresh.adapter.SearchCategoryAdapter;
import com.abfresh.in.Abfresh.callback.CategoryCallbackLisener;
import com.abfresh.in.Abfresh.model.HomeModel;
import com.abfresh.in.Abfresh.utils.RestClient;
import com.abfresh.in.Adapter.ExploreCatAdapter;
import com.abfresh.in.Adapter.SearchItemAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.ArrayList;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class SearchItem extends Fragment{


    public static final int CAT_ADPT = 1;
    public static final int CAT_PROD_ADPT = 2;
    CategoryCallbackLisener categoryCallbackLisener;
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
    ImageView search_item_line, iv_back_arrow, iv_back;
    TextView noitemfound_tv;
    RelativeLayout ec_rl;
    FragmentManager fragmentManager;
    RelativeLayout rl_container, search_item_ll;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_layout, container, false);

        search_item_toolbar = (Toolbar)view.findViewById(R.id.search_item_tolbar);
//        setSupportActionBar(search_item_toolbar);
        init(view);
        categoryCallbackLisener.invisibleToolbaar();
        return view;
    }


    private void init(View view){
        sessionManagement = new SessionManagement(getActivity());
        search_item_rv = (RecyclerView)view.findViewById(R.id.recyclerView_search_item);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView_chhoseitem);
        editText_search_item = (EditText)view.findViewById(R.id.editText_search_item);
        search_item_line = (ImageView) view.findViewById(R.id.search_item_line);
        iv_back_arrow = (ImageView) view.findViewById(R.id.iv_back_arrow);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        noitemfound_tv = (TextView) view.findViewById(R.id.noitemfound_tv);
        ec_rl = (RelativeLayout) view.findViewById(R.id.ec_rl);
        rl_container=(RelativeLayout)view.findViewById(R.id.rl_container);
        search_item_ll=(RelativeLayout)view.findViewById(R.id.search_item_ll);



        Offers.offerClick=false;
//        getSearchExploreCategory();
        getHomeData();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        item = new java.util.ArrayList<ArrayList>();
        editText_search_item.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if(editText_search_item.getText().toString().trim().length()==0){
                    ec_rl.setVisibility(View.VISIBLE);

                }else{
                    recyclerView.setVisibility(View.VISIBLE);
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

//                    getSearchItem();
                    getSearch();
                }


            }
        });
    }

    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        search_item_toolbar = (Toolbar)findViewById(R.id.search_item_tolbar);
        setSupportActionBar(search_item_toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        fragmentManager = getSupportFragmentManager();
        sessionManagement = new SessionManagement(getApplicationContext());
        search_item_rv = (RecyclerView)findViewById(R.id.recyclerView_search_item);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_chhoseitem);
        editText_search_item = (EditText)findViewById(R.id.editText_search_item);
        search_item_line = (ImageView) findViewById(R.id.search_item_line);
        iv_back_arrow = (ImageView) findViewById(R.id.iv_back_arrow);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        noitemfound_tv = (TextView) findViewById(R.id.noitemfound_tv);
        ec_rl = (RelativeLayout) findViewById(R.id.ec_rl);
        rl_container=(RelativeLayout)findViewById(R.id.rl_container);
        search_item_ll=(RelativeLayout)findViewById(R.id.search_item_ll);
        rl_container.setVisibility(View.GONE);
        search_item_ll.setVisibility(View.VISIBLE);


        Offers.offerClick=false;
//        getSearchExploreCategory();
        getHomeData();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

//                    getSearchItem();
                    getSearch();
                }


            }
        });


    }*/


    private void getSearch(){
        sessionManagement = new SessionManagement(getActivity());
        JsonObject jsonObject = new JsonObject();
        String tempid= Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        jsonObject.addProperty("temp_user_id",tempid);
        jsonObject.addProperty( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
        jsonObject.addProperty("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
        jsonObject.addProperty("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
        jsonObject.addProperty("search",editText_search_item.getText().toString());
        Log.w("SRTAG", "response getcatObject "+jsonObject);

        new RestClient().getApiService().search(jsonObject, new Callback<HomeModel>() {
            @Override
            public void success(HomeModel homeModel, retrofit.client.Response response) {
                manageSearchAdapter(homeModel);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    private void manageSearchAdapter(HomeModel homeModel){
        SearchCategoryAdapter adapter = new SearchCategoryAdapter(CAT_PROD_ADPT, categoryCallbackLisener, homeModel.getProductList());
        recyclerView.setNestedScrollingEnabled(false);
        final GridLayoutManager manager
                = new GridLayoutManager(getActivity(),1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
//                            cat_pro_pb.setVisibility(View.GONE);
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }



    private void getSearchItem() {
        try {
            sessionManagement = new SessionManagement(getActivity());
            JSONObject getcatObject = new JSONObject();
            String tempid= Settings.Secure.getString(getActivity().getContentResolver(),
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
                            searchAdapter = new SearchItemAdapter(getActivity(), item);
                            recyclerView.setNestedScrollingEnabled(false);
                            final GridLayoutManager manager
                                    = new GridLayoutManager(getActivity(),1);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(searchAdapter);
//                            cat_pro_pb.setVisibility(View.GONE);
                            ViewCompat.setNestedScrollingEnabled(recyclerView, false);

                            searchAdapter.setOnItemClickListner(new SearchItemAdapter.OnItemClickListner() {
                                @Override
                                public void onCatProductClick(int position, ImageView productImage) {
                                    String proId = item.get(position).getProductId();
                                    String catId = item.get(position).getProGross();

                                    Intent intent = new Intent(getActivity(), NewProductDiscription.class);
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
                        Toast.makeText(getActivity(), "Please try after some time", Toast.LENGTH_SHORT).show();
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
    private void getHomeData(){
        JsonObject jsonObject = new JsonObject();
        String tempid = Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        jsonObject.addProperty("temp_user_id", tempid);
        jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
        jsonObject.addProperty("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
        jsonObject.addProperty("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
        jsonObject.addProperty("via", "Android");

        new RestClient().getApiService().home(jsonObject, new Callback<HomeModel>() {
            @Override
            public void success(HomeModel homeModel, retrofit.client.Response response) {
                if(homeModel.getSuccess().equalsIgnoreCase("1")){
                    if(homeModel.getCategoryList() != null){
                        manageDetail(homeModel);
                    }

                }else{
                    Toast.makeText(getActivity(), homeModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }


    private void manageDetail(HomeModel homeModel){
        if(homeModel.getCategoryList().size() != 0){
            SearchCategoryAdapter adpcategory = new SearchCategoryAdapter(CAT_ADPT, categoryCallbackLisener, homeModel.getCategoryList());
            final GridLayoutManager manager
                    = new GridLayoutManager(getActivity(),3);

            search_item_rv.setLayoutManager(manager);
            search_item_rv.setAdapter(adpcategory);
        }
    }


    private void getSearchExploreCategory() {
        try {
        sessionManagement = new SessionManagement(getActivity());
        JSONObject siTabJsonObjectNew = new JSONObject();
        String tempid = Settings.Secure.getString(getActivity().getContentResolver(),
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
                        siAdapter = new ExploreCatAdapter(getActivity(), sihorizontalList);
                        final GridLayoutManager manager
                                 = new GridLayoutManager(getActivity(),3);

                        search_item_rv.setLayoutManager(manager);
                        search_item_rv.setAdapter(siAdapter);
                        siAdapter.setOnItemClickListner(new ExploreCatAdapter.OnItemClickListner() {
                            @Override
                            public void onBannerClick(int position, ImageView productImage) {
//                                TabActivity.tabCurrentItem = position;
                                AppController.tabCurrentItem = Integer.parseInt(sihorizontalList.get(position).getName());;
                                Intent intent = new Intent(getActivity(), TabActivity.class);
                                intent.putExtra("ID", sihorizontalList.get(position).getName());
                                intent.putExtra("image",sihorizontalList.get(position).getCatImage());
                                Log.w("BTAG", "ImageURL "+sihorizontalList.get(position).getCatImage());
                                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                        getActivity(), productImage, ViewCompat.getTransitionName(productImage));
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
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DashboardActivity) {
            categoryCallbackLisener = (CategoryCallbackLisener) context;
        }
    }


}
