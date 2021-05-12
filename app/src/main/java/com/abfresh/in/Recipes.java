package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.abfresh.in.Adapter.RecipeAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recipes extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    RecyclerView recipies_rv;
    ArrayList<com.abfresh.in.Model.ArrayList> catProductList;
    RecipeAdapter recipeCollectioAdapter;
    ProgressBar recipe_pb;
    ImageView rec_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_layout);

        Toolbar toolbar=(Toolbar)findViewById(R.id.recipe_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        recipies_rv = (RecyclerView)findViewById(R.id.recipies_rv);
        recipe_pb = (ProgressBar) findViewById(R.id.recipe_pb);
        rec_home_btn = (ImageView) findViewById(R.id.rec_home_btn);
        getCatProduct();
//        viewPager=(ViewPager)findViewById(R.id.recipe_viewPager);
//
//        if(viewPager!=null)
//        {
//            setUpViewPager(viewPager);
//        }
//        viewPager.setCurrentItem(0);
//
//        tabLayout = (TabLayout) findViewById(R.id.recipe_tabs);
//        tabLayout.setupWithViewPager(viewPager);

        rec_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getCatProduct() {

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

                                String recipeName = tabobject.getString("recipe_name");
                                String recipeTypeName = tabobject.getString("recipe_type_name");
                                String recipeImage = tabobject.getString("image");
                                String recipeDuration = tabobject.getString("duration");
                                String recipeServes = tabobject.getString("serves");


                                catProductList.add(new com.abfresh.in.Model.ArrayList(recipeName, recipeTypeName, recipeImage, recipeDuration, recipeServes));


                            }
                            recipeCollectioAdapter = new RecipeAdapter(getApplicationContext(), catProductList);
                            final GridLayoutManager manager
                                    = new GridLayoutManager(Recipes.this, 1);

                            recipies_rv.setLayoutManager(manager);
                            recipies_rv.setAdapter(recipeCollectioAdapter);
                            recipeCollectioAdapter.setOnItemClickListner(new RecipeAdapter.OnItemClickListner() {
                                @Override
                                public void onRecipeClick(int position, TextView rec_name) {
//                String recipeName = String.valueOf(catProductList.get(position).getRecName());
//                Toast.makeText(Recipes.this, catProductList.get(position).getRecName(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Recipes.this, RecipeProductDiscription.class);
                                    intent.putExtra("RecipeName", catProductList.get(position).getRecipeName());
                                    startActivity(intent);
                                }
                            });
//


                            recipe_pb.setVisibility(View.GONE);

                        } else {
                            recipe_pb.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("RTAG", "JSONException e " + e);
                        recipe_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.w("RTAG", "VolleyError error " + error);
                    recipe_pb.setVisibility(View.GONE);
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
            recipe_pb.setVisibility(View.GONE);

        }
    }
    //    private void setUpViewPager(ViewPager viewPager) {
//        Adapter adapter=new Adapter(getSupportFragmentManager());
//        adapter.addFragment(new Collection(),"Collections");
//        adapter.addFragment(new Favourites(),"Favourites");
//
//        viewPager.setAdapter(adapter);
//
//    }
//
//    static class Adapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragments = new ArrayList<>();
//        private final List<String> mFragmentTitles = new ArrayList<>();
//        public Adapter(FragmentManager fm) {
//            super(fm);
//        }
//        public void addFragment(Fragment fragment, String title) {
//            mFragments.add(fragment);
//            mFragmentTitles.add(title);
//        }
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitles.get(position);
//        }
//    }
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == android.R.id.home){
//            finish();
////            Intent intent = new Intent(Proceed_To_Pay.this,Container_Main_Class.class);
////            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            startActivity(intent);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
