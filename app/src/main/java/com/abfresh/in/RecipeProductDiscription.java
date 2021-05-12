package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;
import com.abfresh.in.Adapter.IngradientAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecipeProductDiscription extends AppCompatActivity {
    RecyclerView ingradient_rv;
    TextView recipe_type,method_tv,rec_msg,method_name_tv,ingradient_tv,toolbar_tv,rec_name,recipe_type_rd_tv,duration_rd,serves_rd_tv;
    LinearLayout method_ll,ingradient_ll;
    View ing_view,mtd_view;
    ImageView ingradient_iv,method_iv,rec_pro_image,recipe_type_img,repd_home_btn;
    ProgressBar rpd_pb;

    ArrayList<com.abfresh.in.Model.ArrayList> catProductList;
    IngradientAdapter ingradientAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_product_discription);
        ingradient_ll = (LinearLayout)findViewById(R.id.ingradient_ll);
        method_ll = (LinearLayout)findViewById(R.id.method_ll);
        recipe_type = (TextView)findViewById(R.id.recipe_type);
        method_tv = (TextView)findViewById(R.id.method_tv);
        ingradient_tv = (TextView)findViewById(R.id.ingradient_tv);
        rec_msg = (TextView)findViewById(R.id.rec_msg);
        ingradient_rv = (RecyclerView) findViewById(R.id.ingradient_rv);
        ing_view = (View) findViewById(R.id.ing_view);
        mtd_view = (View) findViewById(R.id.mtd_view);
        method_name_tv = (TextView) findViewById(R.id.method_name_tv);
        toolbar_tv = (TextView) findViewById(R.id.toolbar_tv);
        rec_name = (TextView) findViewById(R.id.rec_name);
        serves_rd_tv = (TextView) findViewById(R.id.serves_rd_tv);
        duration_rd = (TextView) findViewById(R.id.duration_rd);
        recipe_type_rd_tv = (TextView) findViewById(R.id.recipe_type_rd_tv);
        ingradient_iv = (ImageView) findViewById(R.id.ingradient_iv);
        method_iv = (ImageView) findViewById(R.id.method_iv);
        rec_pro_image = (ImageView) findViewById(R.id.rec_pro_image);
        recipe_type_img = (ImageView) findViewById(R.id.recipe_type_img);
        repd_home_btn = (ImageView) findViewById(R.id.repd_home_btn);
        rpd_pb = (ProgressBar) findViewById(R.id.rpd_pb);


        Intent i= getIntent();
        String recipeName = i.getStringExtra("RecipeName");

//        Toast.makeText(this, recipeName, Toast.LENGTH_SHORT).show();
        Toolbar toolbar=(Toolbar)findViewById(R.id.recipe_pro_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        ingradient_rv.setNestedScrollingEnabled(false);
        toolbar_tv.setText(getIntent().getStringExtra("RecipeName"));

        ViewCompat.setNestedScrollingEnabled(ingradient_rv, false);

        getIngradient();


        method_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                method_tv.setVisibility(View.VISIBLE);
                ingradient_rv.setVisibility(View.GONE);
                recipe_type.setText("Method");
                rec_msg.setVisibility(View.GONE);
//                method_tv.setText(R.string.lorem2);
                ing_view.setVisibility(View.GONE);
                mtd_view.setVisibility(View.VISIBLE);

                method_iv.setColorFilter(ContextCompat.getColor(RecipeProductDiscription.this, R.color.newYellowBg), android.graphics.PorterDuff.Mode.SRC_IN);
                method_name_tv.setTextColor(getResources().getColor(R.color.newYellowBg));
                method_ll.setBackgroundColor(getResources().getColor(R.color.black));
                ingradient_ll.setBackgroundColor(getResources().getColor(R.color.white));
                ingradient_iv.setColorFilter(ContextCompat.getColor(RecipeProductDiscription.this,R.color.colorgray), android.graphics.PorterDuff.Mode.SRC_IN);
                ingradient_tv.setTextColor(getResources().getColor(R.color.colorgray));
            }
        });

        ingradient_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getIngradient();
            }
        });

        repd_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIngradient() {
        recipe_type.setText("");
        rec_msg.setVisibility(View.GONE);
        method_tv.setVisibility(View.GONE);
        recipe_type.setText("Ingradients");
        ingradient_rv.setVisibility(View.VISIBLE);
        ing_view.setVisibility(View.VISIBLE);
        mtd_view.setVisibility(View.GONE);
//        catProductList.clear();
        ingradient_iv.setColorFilter(ContextCompat.getColor(RecipeProductDiscription.this, R.color.newYellowBg), android.graphics.PorterDuff.Mode.SRC_IN);
        ingradient_tv.setTextColor(getResources().getColor(R.color.newYellowBg));
        ingradient_ll.setBackgroundColor(getResources().getColor(R.color.black));
        method_ll.setBackgroundColor(getResources().getColor(R.color.white));
        method_iv.setColorFilter(ContextCompat.getColor(RecipeProductDiscription.this,R.color.colorgray), android.graphics.PorterDuff.Mode.SRC_IN);
        method_name_tv.setTextColor(getResources().getColor(R.color.colorgray));

//        catProductList = new ArrayList<com.tbd.abFresh.model.ArrayList>();
//        for(int i = 0; i< MyData.ingrName.length; i++){
//            catProductList.add(new com.tbd.abFresh.model.ArrayList(
//                    MyData.ingrName[i],
//                    MyData.ingrWeight[i],
//                    MyData.ingraQuantity[i]
//
//            ));
//        }
//        ingradientAdapter = new IngradientAdapter(getApplicationContext(), catProductList);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(RecipeProductDiscription.this,1);
//        ingradient_rv.setLayoutManager(gridLayoutManager);
//        ingradient_rv.setAdapter(ingradientAdapter);


        try {


            JsonObjectRequest tabRequestNew = new JsonObjectRequest(Request.Method.POST, Utility.Recipes, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("RTAG", "response New " + response);
                        catProductList = new ArrayList<com.abfresh.in.Model.ArrayList>();

                        if ((response.getInt("success")) == 1) {

                            JSONArray tabArray = response.getJSONArray("recipes_list");

                            for (int i = 0; i < tabArray.length(); i++) {
                                JSONObject tabobject = tabArray.getJSONObject(i);
                                rec_name.setText(tabobject.getString("recipe_name"));
                                recipe_type_rd_tv.setText(tabobject.getString("recipe_type_name"));
                                Picasso.with(RecipeProductDiscription.this).load(tabobject.getString("recipe_type_image")).fit().centerCrop().into(recipe_type_img);
                                method_tv.setText(tabobject.getString("description"));
                                Picasso.with(RecipeProductDiscription.this).load(tabobject.getString("image")).fit().centerCrop().into(rec_pro_image);
                                duration_rd.setText(tabobject.getString("duration"));
                                serves_rd_tv.setText("Serves: "+tabobject.getString("serves"));
//
//
//                                String recipeName = tabobject.getString("recipe_name");
//                                String recipeTypeName = tabobject.getString("recipe_type_name");
//                                String recipeImage = tabobject.getString("image");
//                                String recipeDuration = tabobject.getString("duration");
//                                String recipeServes = tabobject.getString("serves");
//                                rec_pro_image.
//                                catProductList.add(new com.tbd.abFresh.model.ArrayList(recipeName, recipeTypeName, recipeImage, recipeDuration, recipeServes));
                                JSONArray ingradientArray = tabobject.getJSONArray("ingradients_list");
                                Log.w("RDTAG", "onResponse: "+ingradientArray);
                                for (int j = 0; j < ingradientArray.length(); j++) {
//                                    catProductList.clear();
                                    JSONObject ingradientobject = ingradientArray.getJSONObject(j);
                                    String gradientName = ingradientobject.getString("ingradient_name");
                                     String gradientQty = ingradientobject.getString("qty");
                                    Log.w("RDTAG", "onResponse: "+gradientName);
                                    catProductList.add(new com.abfresh.in.Model.ArrayList(gradientName,gradientQty));
                                }

                            }
                            ingradientAdapter = new IngradientAdapter(getApplicationContext(), catProductList);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(RecipeProductDiscription.this,1);
                            ingradient_rv.setLayoutManager(gridLayoutManager);
                            ingradient_rv.setAdapter(ingradientAdapter);
//                            ingradientAdapter = new IngradientAdapter(getApplicationContext(), catProductList);
//                            final GridLayoutManager manager
//                                    = new GridLayoutManager(RecipeProductDiscription.this, 1);
//
//                            ingradient_rv.setLayoutManager(manager);
//                            ingradient_rv.setAdapter(ingradientAdapter);



                            rpd_pb.setVisibility(View.GONE);

                        } else {
                            rpd_pb.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("RTAG", "JSONException e " + e);
                        rpd_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.w("RTAG", "VolleyError error " + error);
                    rpd_pb.setVisibility(View.GONE);
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername, Utility.ServerPassword);
                    return headers;
                }
            };
            tabRequestNew.setRetryPolicy(new DefaultRetryPolicy(50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(tabRequestNew);


        } catch (Exception e) {
            e.printStackTrace();
            rpd_pb.setVisibility(View.GONE);
        }

    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
