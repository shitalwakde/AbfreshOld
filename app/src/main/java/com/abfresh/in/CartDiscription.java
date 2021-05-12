package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Adapter.CartAdapter;

import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.CartList;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.muddzdev.styleabletoast.StyleableToast;
import com.sanojpunchihewa.glowbutton.GlowButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class CartDiscription extends AppCompatActivity {
    RecyclerView cart_rv;
    CartAdapter cartAdapter;
    ArrayList<CartList> cartLists;
    Toolbar cartToolbar;
    GlowButton cart_proceed_btn;
    RelativeLayout cart_proceed_ll;
    LinearLayout apply_add_ll,chng_add_ll,apply_login_ll;
    TextView cart_total_amt,my_add_tv,add_adress_id,join_tv,free_delivery_msg;
    ProgressBar cartlay_pb;
    SessionManagement sessionManagement;
    String userid;
    public  static String totalAmount="";
    public  static String deliveryCharges="";
    public  static String totalWithDelivery="";
    RelativeLayout join_add_rl;
    CardView join_add_cv;
    ImageView cart_home_btn;
    public static FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(CartDiscription.this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        cartToolbar = (Toolbar)findViewById(R.id.cart_toolbar);
        setSupportActionBar(cartToolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        cartlay_pb = (ProgressBar)findViewById(R.id.cartlay_pb);
        cartlay_pb.setVisibility(View.GONE);
        cart_rv = (RecyclerView)this.findViewById(R.id.cart_rv);
        cart_proceed_ll = (RelativeLayout)findViewById(R.id.cart_proceed_ll);
        cart_total_amt = (TextView)findViewById(R.id.cart_total_amt);
        my_add_tv = (TextView)findViewById(R.id.my_add_tv);
        free_delivery_msg = (TextView)findViewById(R.id.free_delivery_msg);
        add_adress_id = (TextView)findViewById(R.id.add_adress_id);
        apply_add_ll = (LinearLayout) findViewById(R.id.apply_add_ll);
        chng_add_ll = (LinearLayout) findViewById(R.id.chng_add_ll);
        apply_login_ll = (LinearLayout) findViewById(R.id.apply_login_ll);
        join_add_rl = (RelativeLayout) findViewById(R.id.join_add_rl);
        join_add_cv = (CardView) findViewById(R.id.join_add_cv);
        cart_home_btn = (ImageView) findViewById(R.id.cart_home_btn);
        sessionManagement = new SessionManagement(getApplicationContext());
        getCartProductList();
//        private void enableSubmitIfReady() {
//            boolean isReady = true;
//            my_add_tv.setEnabled(isReady);
//
//
//        }cart_proceed_ll
//        getButtonStatus();


        cart_proceed_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!(sessionManagement.isLoggedIn())){
                  Intent intent = new Intent(CartDiscription.this,Login.class);
                  startActivity(intent);
              }else{
                  Intent intent = new Intent(CartDiscription.this,ChooseDelivery.class);
                  startActivity(intent);
                  finish();
              }

            }
        });
//        HashMap<String, String> user = sessionManagement.getUserDetails();
//        userid = user.get(sessionManagement.KEY_USERID);
        apply_login_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent = new Intent(CartDiscription.this,Login.class);
                    Utility.FromCart =true;
                    startActivity(intent);
            }
        });
        apply_add_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                    if(sessionManagement.getUserDetails().get(KEY_USERFULLNAME).trim().length()!=0){
                        Intent intent = new Intent(CartDiscription.this,AddShippingAddress.class);
                        startActivity(intent);
//                    }
//                    else{
//                        Intent intent = new Intent(CartDiscription.this,UpdateProfile.class);
//                        startActivity(intent);
//
//
//                }

//                finish();
            }
        });

        chng_add_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartDiscription.this,AddShippingAddress.class);
                startActivity(intent);
            }
        });

        cart_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }




    private void getButtonStatus() {
        if(apply_add_ll.getVisibility()==View.VISIBLE){
            cart_proceed_ll.setVisibility(View.GONE);
            apply_add_ll.setVisibility(View.VISIBLE);
             chng_add_ll.setVisibility(View.GONE);
            apply_login_ll.setVisibility(View.GONE);

        }else{
            cart_proceed_ll.setVisibility(View.VISIBLE);
            apply_add_ll.setVisibility(View.GONE);
             chng_add_ll.setVisibility(View.VISIBLE);
            apply_login_ll.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getCartProductList();
     //   if(Utility.MyAddressNew!=null){
        if(Utility.MyAddressNew.length()!=0){
            Log.w("BTAG","if condition");
            Log.w("BTAG","Utility.MyAddressNew===>"+Utility.MyAddressNew);
            my_add_tv.setText(Utility.MyAddressNew);
            if(!(sessionManagement.isLoggedIn())) {
                Log.w("BTAG","session end===>"+Utility.MyAddressNew);
                apply_add_ll.setVisibility(View.GONE);
               apply_login_ll.setVisibility(View.VISIBLE);
           }else{
                Log.w("BTAG","session start===>"+Utility.MyAddressNew);
//               apply_add_ll.setVisibility(View.VISIBLE);
//              apply_login_ll.setVisibility(View.GONE);
//                getCartProductList();
                getButtonStatus();
           }
//            my_add_tv.setText(Utility.MyAddress);
        }else{
            getCartProductList();
            Log.w("BTAG","else condition");

            if(!(sessionManagement.isLoggedIn())) {
                apply_add_ll.setVisibility(View.GONE);
                apply_login_ll.setVisibility(View.VISIBLE);
            }else{
                apply_add_ll.setVisibility(View.VISIBLE);
                apply_login_ll.setVisibility(View.GONE);

                getButtonStatus();
            }
        }


//        recreate();

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


    private void getCartProductList() {
        try {
        JSONObject cartListObject = new JSONObject();
            cartlay_pb.setVisibility(View.VISIBLE);
            sessionManagement = new SessionManagement(getApplicationContext());
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            cartListObject.put( "temp_user_id",tempid);
            cartListObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            cartListObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            cartListObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));

            Log.w("CDTAG", "JSONObject cartListObject" + cartListObject);

            JsonObjectRequest clRequest = new JsonObjectRequest(Request.Method.POST, Utility.CartList, cartListObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("CDTAG", "JSONObject response" + response);

                        if ((response.getString("success")).equals(""+1)){
                            deliveryCharges= response.getString("delivery_charge");
                            cart_total_amt.setText("₹ "+ response.getString("cart_total_amt"));
                            totalAmount= response.getString("cart_total_amt");
                           totalWithDelivery= response.getString("total_amt");
                            cartLists = new ArrayList<CartList>();
                                JSONArray clnewArray = response.getJSONArray("cart_list");
                                for(int i=0;i<clnewArray.length();i++){
                                    JSONObject newObject = clnewArray.getJSONObject(i);
                                    String cart_id = newObject.getString("product_id");
                                    String cart_qty = newObject.getString("qty");
                                    String product_name = newObject.getString("product_name");
                                    String product_gross_amount = newObject.getString(""+"cart_gross_amt");
                                    String product_discount = newObject.getString("product_discount");
                                    String product_gross_wt = newObject.getString("product_gross_weight");
                                    String product_piec = newObject.getString("no_of_pieces");
//                                    String product_amt = newObject.getString("product_amt");
                                    String product_amt = newObject.getString("cart_amt");
                                    String product_wt = newObject.getString("product_weight");
                                    cartLists.add(new CartList(cart_id,cart_qty,product_name,product_gross_wt,product_piec,product_amt,product_wt,product_gross_amount,product_discount));
                                }


                                   String delivery_location_id = response.getString("delivery_location_id");
                                   String delivery_username = response.getString("delivery_location_user_name");
                                   String delivery_location_house_no = response.getString("delivery_location_house_no");
                                   String delivery_location_area = response.getString("delivery_location_area");
                                   String delivery_location_address = response.getString("delivery_location_address");
                                   String delivery_location_state_name = response.getString("delivery_location_state_name");
                                   String delivery_location_city_name = response.getString("delivery_location_city_name");
                                   String delivery_location_pincode = response.getString("delivery_location_pincode");
                                   String delivery_location_user_mobile = response.getString("delivery_location_user_mobile");
                                   if(delivery_username.length()!=0){
                                       Utility.MyAddress = delivery_username +","+ delivery_location_house_no
                                               +","+ delivery_location_area+","+delivery_location_address+","+ delivery_location_state_name+","+ delivery_location_city_name
                                               +","+  delivery_location_pincode+", M.No :"+delivery_location_user_mobile + "";
                                       my_add_tv.setText(Utility.MyAddress);
                                       Utility.Delivery_location_id = delivery_location_id;
                                       Log.w("CDTAG", "JSONObject  Utility.Delivery_location_id one" +  Utility.MyAddress );

                                   }

                                   try {
                                       JSONArray claddArray = response.getJSONArray("delivery_list");
                                       for(int i=0;i<clnewArray.length();i++){
                                           if(claddArray.length()!=0) {
                                               JSONObject newObject = claddArray.getJSONObject(i);
                                               if (Utility.Delivery_location_id.equals(newObject.getString("delivery_location_id"))) {

                                                   String delivery_username_new = newObject.getString("delivery_location_user_name");
                                                   String delivery_location_house_no_new  = newObject.getString("delivery_location_house_no");
                                                   String delivery_location_area_new  = newObject.getString("delivery_location_area");
                                                   String delivery_location_address_new  = newObject.getString("delivery_location_address");
                                                   String delivery_location_state_name_new  = newObject.getString("delivery_location_state_name");
                                                   String delivery_location_city_name_new  = newObject.getString("delivery_location_city_name");
                                                   String delivery_location_pincode_new  = newObject.getString("delivery_location_pincode");
                                                   String delivery_location_user_mobile_new  = newObject.getString("delivery_location_user_mobile");
                                                   if(delivery_username_new.length()!=0) {
//                                                       Utility.MyAddress = delivery_username_new + "," + delivery_location_house_no_new
                                                       Utility.MyAddressNew = delivery_username_new + "," + delivery_location_house_no_new
                                                               + "," + delivery_location_area_new + "," +delivery_location_address_new+ "," + delivery_location_state_name_new + "," + delivery_location_city_name_new
                                                               + "," + delivery_location_pincode_new + "," + delivery_location_user_mobile_new + "";
//                                            my_add_tv.setText(Utility.MyAddress);
                                                       my_add_tv.setText(Utility.MyAddressNew);
                                                       Log.w("CDTAG", "JSONObject  Utility.Delivery_location_id two " +  Utility.MyAddress);

                                                   }
                                                   chng_add_ll.setVisibility(View.VISIBLE);
                                                   apply_add_ll.setVisibility(View.GONE);
                                               }
//                                        else {
//                                            my_add_tv.setText("Shipping Address");
//                                            chng_add_ll.setVisibility(View.GONE);
//
//                                        }
                                           }

                                       }} catch (JSONException e) {
                                       e.printStackTrace();

                               }
                       if(response.getString("default_delivery_location_available").equals("Yes")){
                                    my_add_tv.setText(Utility.MyAddress );
                                    chng_add_ll.setVisibility(View.VISIBLE);
                                    apply_add_ll.setVisibility(View.GONE);
                                    cart_proceed_ll.setVisibility(View.VISIBLE);
//                                    join_add_cv.setVisibility(View.GONE);
//                                    join_tv.setVisibility(View.GONE);

                                }else{
//                           if(sessionManagement.getUserDetails().get(KEY_FULLADDRESS).length()!=0){
//                               my_add_tv.setText(sessionManagement.getUserDetails().get(KEY_FULLADDRESS));
//                               cart_proceed_ll.setVisibility(View.GONE);
//                               chng_add_ll.setVisibility(View.GONE);
////                               apply_login_ll.setVisibility(View.VISIBLE);
//                           }else{
                               my_add_tv.setText("Shipping Address");
                               cart_proceed_ll.setVisibility(View.GONE);
                               chng_add_ll.setVisibility(View.GONE);
//                               apply_login_ll.setVisibility(View.VISIBLE);
//                           }


//                                    apply_login_ll.setVisibility(View.VISIBLE);
//                                    join_add_cv.setVisibility(View.VISIBLE);
//                                    join_tv.setVisibility(View.VISIBLE);
                                }
                            cartAdapter = new CartAdapter(CartDiscription.this, cartLists);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartDiscription.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            cart_rv.setLayoutManager(linearLayoutManager);
                            cart_rv.setAdapter(cartAdapter);
                            cart_rv.setNestedScrollingEnabled(false);
                            cartlay_pb.setVisibility(View.GONE);
                            cartAdapter.setOnItemClickListner(new CartAdapter.OnItemClickListner() {
                                @Override
                                public void onCartIncrementClick(int position, TextView increment_tv_cart) {
                                    int number = Integer.parseInt(increment_tv_cart.getText().toString()) ;
                                    number = number +1;
                                    String NewNumber = String.valueOf(number);
//                                    Toast.makeText(CartDiscription.this,NewNumber, Toast.LENGTH_SHORT).show();
                                    increment_tv_cart.setText(String.valueOf(number));
                                    addToCartCD(position,number);
                                }

                                @Override
                                public void onCartDecrementClick(int position, TextView increment_tv_cart) {

                                    int number = Integer.parseInt(increment_tv_cart.getText().toString()) ;
                                    number = number -1;

                                    if(number==0){
                                        LayoutInflater factory = LayoutInflater.from(CartDiscription.this);
                                        final View deleteDialogView = factory.inflate(R.layout.custom_delete_dialog, null);
                                        final AlertDialog deleteDialog = new AlertDialog.Builder(CartDiscription.this).create();
                                        deleteDialog.setView(deleteDialogView);
                                        deleteDialogView.findViewById(R.id.cdd_yes_tv).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                String proId = cartLists.get(position).getCart_id_new();
                                                removeFromCartCPF(proId);
                                                deleteDialog.dismiss();
                                            }
                                        });
                                        deleteDialogView.findViewById(R.id.cdd_no_tv).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                deleteDialog.dismiss();
                                            }
                                        });
                                        deleteDialog.show();
//                                        final AlertDialog.Builder builder = new AlertDialog.Builder(CartDiscription.this);
//                                        builder.setTitle("Alert!");
//
//                                        //Setting message manually and performing action on button click
//                                        builder.setMessage("Do you really want to Remove This Product?");
//                                        //This will not allow to close dialogbox until user selects an option
//                                        builder.setCancelable(false);
//                                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
////                Toast.makeText(this, "positive button", Toast.LENGTH_SHORT).show();
//                                                String proId = cartLists.get(position).getCart_id_new();
//                                                removeFromCartCPF(proId);
//                                            }
//                                        });
//                                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                //  Action for 'NO' Button
//
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                                        //Creating dialog box
//                                        AlertDialog alert = builder.create();
//                                        //Setting the title manually
//                                        //alert.setTitle("AlertDialogExample");
//                                        alert.show();



                                    }else{
                                        String NewNumber = String.valueOf(number);
//                                        Toast.makeText(CartDiscription.this,NewNumber, Toast.LENGTH_SHORT).show();
                                        increment_tv_cart.setText(String.valueOf(number));
                                        addToCartCD(position,number);
                                    }
                                }

//                                @Override
//                                public void onCartRemoveClick(int position) {
//
//                                }

                            });

                            free_delivery_msg.setText(response.getString("display_msg"));

                        }else{
                            cartlay_pb.setVisibility(View.GONE);
//                            StyleableToast.makeText(CartDiscription.this,"Please try after some time", R.style.mySizeToast).show();
                            }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        cartlay_pb.setVisibility(View.GONE);
//                        StyleableToast.makeText(CartDiscription.this,"Please try after some time", R.style.mySizeToast).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cartlay_pb.setVisibility(View.GONE);
                    Log.w("error===>", error);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            };clRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(clRequest);


        } catch (JSONException e) {
            e.printStackTrace();
            cartlay_pb.setVisibility(View.GONE);

        }



//        for (int i=0;i< MyData.cartBrandName.length;i++){
//            cartLists.add(new CartList(
//
//                    MyData.cartBrandName[i],
//                    MyData.cartProductWeight[i],
//                    MyData.cartNewPricee[i]
//
//            ));
//        }

    }

    private void removeFromCartCPF(String proId) {

        try {
            JSONObject rcobject = new JSONObject();

            String tempid= Settings.Secure.getString(CartDiscription.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            rcobject.put("temp_user_id",tempid);
            rcobject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            rcobject.put(  "product_id",proId);
            rcobject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            rcobject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("PDTAG", "JSONObject rcobject" + rcobject);
            JsonObjectRequest rcRequest = new JsonObjectRequest(Request.Method.POST, Utility.RemoveFromCart, rcobject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if ((response.getString("success")).equals("1")){
                            Toast.makeText(CartDiscription.this,response.getString("message"), Toast.LENGTH_SHORT).show();
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            int cartcount = Integer.parseInt(response.getString("cart_count"));
//                            invalidateOptionsMenu();
//                            cartAdapter.notifyDataSetChanged();
                            getCartProductList();
                            if(cartcount==0){
                                Intent intent = new Intent(CartDiscription.this, Container_Main_Class.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
//                            ((TabActivity)getContext()).invalidateOptionsMenu();
//                            categoryProductAdapter.notifyDataSetChanged();
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
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            }; rcRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(rcRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addToCartCD(int position, int number) {

//        sessionManagement = new SessionManagement(getContext());
        String cartNumber = cartLists.get(position).getCart_qty();
        try{
            JSONObject cpCartObject = new JSONObject();
            String tempid= Settings.Secure.getString(CartDiscription.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String proId = cartLists.get(position).getCart_id_new();
            cpCartObject.put("temp_user_id",tempid);
            cpCartObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            cpCartObject.put("product_id",proId);
            cpCartObject.put("qty",number);
            cpCartObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            cpCartObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));

            Log.w("CPTAG","response cpCartObject"+cpCartObject);
            JsonObjectRequest cpRequest = new JsonObjectRequest(Request.Method.POST, Utility.AddToCart, cpCartObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("CPTAG","response response"+response);

                        if ((response.getString("success")).equals("1")){
                            getCartProductList();

                        }else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        StyleableToast.makeText(CartDiscription.this, "Please try after some time", R.style.mySizeToast).show();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            };cpRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                    ,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(cpRequest);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}
