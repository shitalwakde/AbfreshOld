package com.abfresh.in.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Adapter.CategoryProductAdapter;
import com.abfresh.in.CartDiscription;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.NewProductDiscription;
import com.abfresh.in.R;
import com.abfresh.in.TabActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class Category_Product_Fragment extends Fragment {
    SessionManagement sessionManagement;
    RecyclerView catProRecycleView;
    ArrayList<com.abfresh.in.Model.CatModel> catProductList;
    CategoryProductAdapter categoryProductAdapter;
    String catid;
    ProgressBar cat_pro_pb;
    int catValue = -1;
    public static String ProductId;
    String userid;
    public static String totalC;
    Boolean remove = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_product, container, false);

        catProRecycleView = view.findViewById(R.id.category_product_rv);
        catid = getArguments().getString("id");
        cat_pro_pb = (ProgressBar) view.findViewById(R.id.cat_pro_pb);
        cat_pro_pb.setVisibility(View.VISIBLE);


        // getCatProduct();
        Utility.FromCart = false;
        int cart;
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getCatProduct();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void getCatProduct() {

        try {
//            ProgressDialog progressDialog = new ProgressDialog(getContext());
//            progressDialog.setMessage("Please wait..");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
            sessionManagement = new SessionManagement(getContext());
            JSONObject getcatObject = new JSONObject();
            String tempid = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            getcatObject.put("temp_user_id", tempid);
            getcatObject.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            getcatObject.put("category_id", getArguments().getString("id"));
            getcatObject.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            getcatObject.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("CPBTAG", "response getcatObject " + getcatObject);
            JsonObjectRequest catJsonObject = new JsonObjectRequest(Request.Method.POST, Utility.CategoryWiseProduct, getcatObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if ((response.getString("success")).equals("1")) {
                            Log.w("CPBTAG", "response response " + response);

                            JSONArray catJarray = response.getJSONArray("productList");
                            catProductList = new ArrayList<com.abfresh.in.Model.CatModel>();
                            totalC = String.valueOf(response.getInt("cart_total_amt"));
                            if (totalC.trim().length() != 0) {
                                TabActivity.cp_big_price.setText("₹" + totalC);
                            }

                            for (int i = 0; i < catJarray.length(); i++) {
                                JSONObject catJObject = catJarray.getJSONObject(i);
                                String catName = catJObject.getString("product_name");
                                String catGrossAmountNew = catJObject.getString("product_gross_amt");
                                String catDiscountNew = catJObject.getString("product_discount");

                                String catproduct_amt = catJObject.getString("product_amt");
                                String catImage = catJObject.getString("product_image");
                                String catdiscName = catJObject.getString("product_disc_name");
                                String catbrief = catJObject.getString("product_brief");
                                String catProductId = catJObject.getString("product_id");
                                String catProductGwt = catJObject.getString("product_gross_weight");
                                String catProductwt = catJObject.getString("product_weight");
                                String catProductStock = "" + catJObject.getInt("product_stock_qty");
                                String catProduct_nop = catJObject.getString("no_of_pieces");
                                String is_in_cart = "" + catJObject.getString("is_in_cart");
                                String cart_qty = catJObject.getString("cart_qty");
                                String delivery_time = catJObject.getString("delivery_time");

                                Log.w("array list===>", "" + catName + catproduct_amt + catImage + catdiscName + catbrief + catProductId);
                                Log.w("array list===>", "" + catProductGwt + catProductwt + catProductStock + catProduct_nop + is_in_cart + cart_qty);
//                                ProductId = catJObject.getString("product_id");

                                catProductList.add(new com.abfresh.in.Model.CatModel(catName, catGrossAmountNew, catDiscountNew, catproduct_amt, catImage, catdiscName, catbrief, catProductId, catProductGwt, catProductwt, catProductStock, catProduct_nop, is_in_cart, cart_qty,delivery_time));
                            }
                            categoryProductAdapter = new CategoryProductAdapter(getContext(), catProductList);
                            catProRecycleView.setNestedScrollingEnabled(false);
                            final GridLayoutManager manager
                                    = new GridLayoutManager(getContext(), 1);
                            catProRecycleView.setLayoutManager(manager);
                            catProRecycleView.setAdapter(categoryProductAdapter);
                            cat_pro_pb.setVisibility(View.GONE);
                            ViewCompat.setNestedScrollingEnabled(catProRecycleView, false);
//------------------------------------------Product Discription-----------------------------------------------
                            categoryProductAdapter.setOnItemClickListner(new CategoryProductAdapter.OnItemClickListner() {
                                @Override
                                public void onCatProductClick(int position, ImageView productImage) {
                                    String proId = catProductList.get(position).getCatProductId();
//                                    Intent intent = new Intent(getContext(), Product_Discription.class);
                                    Intent intent = new Intent(getContext(), NewProductDiscription.class);
                                    intent.putExtra("categoryId", getArguments().getString("id"));
                                    intent.putExtra("productId", proId);
                                    TabActivity.tabActivity.finish();
                                    startActivity(intent);
//                                    categoryProductAdapter.notifyDataSetChanged();
                                }

                                //--------------------------------------Add to Cart----------------------------------------------------
                                @Override
                                public void onAddCartClick(int position, TextView textView, LinearLayout linearLayout) {
                                    int number = 1;
                                    addToCartNew(position, number, linearLayout);
                                }

                                @Override
                                public void onCheckOutClick(int position) {
                                    if (Utility.CartCount == 0) {
                                        Toast.makeText(getActivity(), "Zero items in chart", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(getActivity(), CartDiscription.class);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCartIncrement(int position, TextView increment_tv, TextView textView, TextView cpa_big_price) {

                                    int number = Integer.parseInt(increment_tv.getText().toString());
                                    number = number + 1;
                                    String NewNumber = String.valueOf(number);
//                                    Toast.makeText(getActivity(),NewNumber, Toast.LENGTH_SHORT).show();
                                    increment_tv.setText(String.valueOf(number));
                                    remove = false;
                                    addToCart(position, number, textView, cpa_big_price);
                                }

                                @Override
                                public void onCartdecrement(int position, TextView increment_tv, TextView textView, LinearLayout linearLayout, Button buy_button, TextView cpa_big_price) {

                                    int number = Integer.parseInt(increment_tv.getText().toString());
                                    number = number - 1;

                                    if (number == 0) {
                                        String proId = catProductList.get(position).getCatProductId();
                                        removeFromCartCPF(proId, linearLayout, buy_button, position);
                                    } else {
                                        String NewNumber = String.valueOf(number);
//                                        Toast.makeText(getActivity(),NewNumber, Toast.LENGTH_SHORT).show();
                                        increment_tv.setText(String.valueOf(number));
                                        remove = true;
                                        addToCart(position, number, textView, cpa_big_price);
//                                        String proId = catProductList.get(position).getProductId();
//                                        removeFromCartCPF(proId,linearLayout,buy_button,position);
                                    }


                                }
//--------------------------------------increment / decrement Cart----------------------------------------------------

//                                @Override
//                                public void onCartNumber(int position) {
//                                    addToCart(position);
//                                }
                            });
//                            progressDialog.dismiss();
                        } else {
                            cat_pro_pb.setVisibility(View.GONE);
//                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Please try after some time", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        cat_pro_pb.setVisibility(View.GONE);
//                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Please try after some time", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    cat_pro_pb.setVisibility(View.GONE);
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
            catJsonObject.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(catJsonObject);


        } catch (JSONException e) {
            e.printStackTrace();
            cat_pro_pb.setVisibility(View.GONE);

        }


    }

    private void removeFromCartCPF(String productID, LinearLayout linearLayout, Button buy_button, int position) {

        try {
            JSONObject rcobject = new JSONObject();

            String tempid = Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            rcobject.put("temp_user_id", tempid);
            rcobject.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            rcobject.put("product_id", productID);
            rcobject.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            rcobject.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));

            Log.w("PDTAG", "JSONObject rcobject" + rcobject);
            JsonObjectRequest rcRequest = new JsonObjectRequest(Request.Method.POST, Utility.RemoveFromCart, rcobject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if ((response.getString("success")).equals("1")) {
                            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            int cartcount = Integer.parseInt(response.getString("cart_count"));
//                            invalidateOptionsMenu();

                            linearLayout.setVisibility(View.GONE);
                            buy_button.setVisibility(View.VISIBLE);
                            TabActivity.textCartItemCount.setText(response.getString("cart_count"));
                            ((TabActivity) getContext()).invalidateOptionsMenu();
                            TabActivity.cp_big_price.setText("₹" + String.valueOf(response.getInt("cart_total_amt")));

//                            categoryProductAdapter.notifyDataSetChanged();
                            //  getCatProduct();
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
                    return header;
                }
            };
            rcRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(rcRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void addToCart(int position, int number, TextView textView, TextView cpa_big_price) {
        sessionManagement = new SessionManagement(getContext());
        String cartNumber = catProductList.get(position).getCart_qty();
        try {
            JSONObject cpCartObject = new JSONObject();
            String tempid = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String proId = catProductList.get(position).getCatProductId();
            cpCartObject.put("temp_user_id", tempid);
            cpCartObject.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            cpCartObject.put("product_id", proId);
            cpCartObject.put("qty", number);
            cpCartObject.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            cpCartObject.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));

            Log.w("CPTAG", "response cpCartObject" + cpCartObject);
            JsonObjectRequest cpRequest = new JsonObjectRequest(Request.Method.POST, Utility.AddToCart, cpCartObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("CPTAG", "response response" + response);

                        if ((response.getString("success")).equals("1")) {
                            textView.setText("" + number);
                            cpa_big_price.setText("₹" + (number * (Integer.parseInt(catProductList.get(position).getCatproduct_amt()))));
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            TabActivity.textCartItemCount.setText(response.getString("cart_count"));
                            ((TabActivity) getContext()).invalidateOptionsMenu();
                            TabActivity.cp_big_price.setText("₹" + String.valueOf(response.getInt("cart_total_amt")));

//                            if(remove){
//                                int remTotal = Integer.parseInt(totalC) - (Integer.parseInt(catProductList.get(position).getProPrice()));
//                                totalC = String.valueOf(remTotal);
//                                TabActivity.cp_big_price.setText("₹"+totalC);
//                                Log.w("CPTAG","response remove"+remove);
//                                Log.w("CPTAG","response remTotal"+remTotal);
//                                Log.w("CPTAG","response remTot"+totalC);
//                            }else{
//                                int addTotal = Integer.parseInt(totalC) + (Integer.parseInt(catProductList.get(position).getProPrice()));
//                                totalC = String.valueOf(addTotal);
//                                TabActivity.cp_big_price.setText("₹"+totalC);
//                                Log.w("CPTAG","response remove"+remove);
//                                Log.w("CPTAG","response addTotal"+addTotal);
//                                Log.w("CPTAG","response addTot"+totalC);
//                            }

                            // getCatProduct();
//                            categoryProductAdapter.notifyDataSetChanged();
//                            StyleableToast.makeText(getActivity(), response.getString("message"), R.style.mySizeToast).show();

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Please Try after some time", Toast.LENGTH_SHORT).show();

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
                    return header;
                }
            };
            cpRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                    , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(cpRequest);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void addToCartNew(int position, int number, LinearLayout linearLayout) {
        sessionManagement = new SessionManagement(getContext());
        String cartNumber = catProductList.get(position).getCart_qty();
        try {
            JSONObject cpCartObject = new JSONObject();
            String tempid = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            String proId = catProductList.get(position).getCatProductId();
            cpCartObject.put("temp_user_id", tempid);
            cpCartObject.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            cpCartObject.put("product_id", proId);
            cpCartObject.put("qty", number);
            cpCartObject.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            cpCartObject.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));

            Log.w("CPTAG", "response cpCartObject" + cpCartObject);
            JsonObjectRequest cpRequest = new JsonObjectRequest(Request.Method.POST, Utility.AddToCart, cpCartObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("CPTAG", "response response" + response);

                        if ((response.getString("success")).equals("1")) {
                            linearLayout.setVisibility(View.VISIBLE);
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            TabActivity.textCartItemCount.setText(response.getString("cart_count"));
                            ((TabActivity) getContext()).invalidateOptionsMenu();
                            TabActivity.cp_big_price.setText("₹" + String.valueOf(response.getInt("cart_total_amt")));
//                            int addTotal = Integer.parseInt(totalC) + (Integer.parseInt(catProductList.get(position).getProPrice()));
//                            totalC = String.valueOf(addTotal);
//                            TabActivity.cp_big_price.setText("₹"+totalC);
//                            Log.w("CPTAG","response addTotalNew"+addTotal);
//                            Log.w("CPTAG","response addTotNew"+totalC);
                            // getCatProduct();
//                            categoryProductAdapter.notifyDataSetChanged();
//                            StyleableToast.makeText(getActivity(), response.getString("message"), R.style.mySizeToast).show();

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Please Try after some time", Toast.LENGTH_SHORT).show();

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
                    return header;
                }
            };
            cpRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES
                    , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(cpRequest);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

}
