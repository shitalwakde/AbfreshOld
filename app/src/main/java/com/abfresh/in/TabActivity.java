package com.abfresh.in;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.appbar.AppBarLayout;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;

import com.abfresh.in.Fragments.Category_Product_Fragment;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.abfresh.in.Controller.SessionManagement.KEY_City;
import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_FULLADDRESS;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class TabActivity extends AppCompatActivity implements IncrementCart{
    SessionManagement sessionManagement;
    LinearLayout no_data_ll;
    private SimpleSearchView searchView;
    public static  TextView textCartItemCount;
    TextView tabOne,tabTwo,tabThree,tabfour,tabfive,tabsix,tabseven;
     public static int tabCartCount;
    String userid;
    TextView tab,city_name_tab_loca,city_name_tab_loca1;
   public static int cart;
    public static TabLayout tabLayout;
    private ViewPager viewPager;
   public static TabActivity tabActivity;
    Adapter adapter;
    ImageView backbtn,tab_toolbar_line;
    public static  ImageView tab_up_image;
    AppBarLayout appBarLayout_toolbar;
    RelativeLayout loca_rl,toolbar_rl_new;
    int ImageId=0;
    LinearLayout checkout_tl_ll;
    public static TextView cp_big_price;
    Button checkout_button_tml;
    ProgressDialog progressDialog;
    public static FirebaseAnalytics mFirebaseAnalytics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_main_activity);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(TabActivity.this);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        tabActivity=this;
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        Toolbar toolbar=(Toolbar)findViewById(R.id.tab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        appBarLayout_toolbar = (AppBarLayout) findViewById(R.id.app_bar_layout_toolbar);
        loca_rl = (RelativeLayout)findViewById(R.id.loca_rl);
        toolbar_rl_new = (RelativeLayout)findViewById(R.id.toolbar_rl_new);
        tab_toolbar_line = (ImageView) findViewById(R.id.tab_toolbar_line);
        checkout_tl_ll = (LinearLayout) findViewById(R.id.checkout_tl_ll);
        cp_big_price = (TextView) findViewById(R.id.cp_big_price);
        checkout_button_tml = (Button) findViewById(R.id.checkout_button_tml);
        no_data_ll = (LinearLayout) findViewById(R.id.no_data_ll);
         progressDialog = ProgressDialog.show(TabActivity.this, null, null, true);
        progressDialog.setContentView(R.layout.custom_progress_dialog);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        city_name_tab_loca = (TextView)findViewById(R.id.city_name_tab_loca);
//        city_name_tab_loca1 = (TextView)findViewById(R.id.city_name_tab_loca1);
//        int fullAdd = sessionManagement.getUserDetails().get("full_address").length();
        sessionManagement = new SessionManagement(getApplicationContext());

//        appBarLayout_toolbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
//                    // Collapsed
//
//                        tab_toolbar_line.setVisibility(View.VISIBLE);
//                        loca_rl.setVisibility(View.GONE);
////                        Animation animation = AnimationUtils.loadAnimation(TabActivity.this, R.anim.fade_in);
////                        loca_rl.startAnimation(animation);
//
//                } else if (verticalOffset == 0) {
//                    // Expanded
////                    toolbar_rl_new.setVisibility(View.VISIBLE);
//                        tab_toolbar_line.setVisibility(View.GONE);
//                        loca_rl.setVisibility(View.VISIBLE);
////                        Animation animation = AnimationUtils.loadAnimation(TabActivity.this, R.anim.fade_out);
////                        loca_rl.startAnimation(animation);
//                 } else {
//                    // Somewhere in between
////                    toolbar_rl_new.setVisibility(View.GONE);
//                }
//            }
//        });

        loca_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(TabActivity.this,Location_Text_Intent.class);
//                startActivity(intent);
                Intent intent = new Intent(TabActivity.this,Delivery_Address.class);
                intent.putExtra("userId",sessionManagement.getUserDetails().get(KEY_USERID));
                Delivery_Address.locClick=false;
                startActivity(intent);
            }
        });

        checkout_button_tml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabActivity.this,CartDiscription.class);
                startActivity(intent);
            }
        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

//        tabCurrentItem = getIntent().getShortExtra()


        tab_up_image = (ImageView) findViewById(R.id.tab_up_image);
        backbtn = (ImageView) findViewById(R.id.backbtn);



//        city_name_tab_loca1.setText(sessionManagement.getUserDetails().get(KEY_City));
//        Animation animation = AnimationUtils.loadAnimation(tabActivity, R.anim.fade_in);
//        tab_up_image.startAnimation(animation);

            String ImageId = getIntent().getStringExtra("ID");

//            if(Image.length()==0){
//                getMyTab();
//            }else{

//            }
//            if(Offers.offerClick){
//                    Offers.offerClick=false;
//            }else{
//                String Image = getIntent().getStringExtra("image");
//                Picasso.with(TabActivity.this).load(Image).fit().centerCrop().into(tab_up_image);
//                Offers.offerClick=false;
//            }

//        Uri ImageURl = getIntent().getParcelableExtra("byteArray");
//        Toast.makeText(tabActivity, ImageURl, Toast.LENGTH_SHORT).show();
//        Bitmap bmp = BitmapFactory.decodeFile(getIntent().getStringExtra("byteArray"));
//        tab_up_image.setImageBitmap(bmp );

//        if(getIntent().hasExtra("byteArray")) {
//            Bitmap bmp = BitmapFactory.decodeByteArray(
//                    getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
//
//            tab_up_image.setMinimumHeight(0);
//
//            tab_up_image.getLayoutParams().width= ViewGroup.LayoutParams.MATCH_PARENT;
//            tab_up_image.getLayoutParams().height= 1000;
//            tab_up_image.setImageBitmap(bmp );
//         }

        viewPager=(ViewPager)findViewById(R.id.tab_viewPager);

//        if(viewPager!=null)
//        {
//            setUpViewPager(viewPager);
//        }
        Log.w("FPA","TAb current item===>"+AppController.tabCurrentItem);

//        viewPager.setCurrentItem(tabCurrentItem);
        tabLayout.setupWithViewPager(viewPager);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMyTab();
            }
        }, 1000);



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.FromCart = false;
                finish();
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
        Log.w("BTAG", "from resume ");
//        getMyTab();
        if((sessionManagement.getUserDetails().get(KEY_FULLADDRESS)).length()!=0){
            city_name_tab_loca.setText("");
            city_name_tab_loca.setText(sessionManagement.getUserDetails().get(KEY_FULLADDRESS));

        }else{
            city_name_tab_loca.setText("");
            city_name_tab_loca.setText(sessionManagement.getUserDetails().get(KEY_City)+",Maharashtra India");
        }
        invalidateOptionsMenu();
    }

    private void getMyTab() {
        try {


            progressDialog.show();
            JSONObject tabJsonObject = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            tabJsonObject.put("temp_user_id",tempid);
            tabJsonObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            tabJsonObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            tabJsonObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("BTAG", "response tabJsonObject "+tabJsonObject);


            JsonObjectRequest tabRequest = new JsonObjectRequest(Request.Method.POST, Utility.Home, tabJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("BTAG", "response tabJsonObject "+response);
                        adapter=new Adapter(getSupportFragmentManager());

                        if((response.getString("success")).equals("1")){
                            loca_rl.setVisibility(View.VISIBLE);
                            checkout_button_tml.setVisibility(View.VISIBLE);
//                            checkout_tl_ll.setVisibility(View.VISIBLE);
                            no_data_ll.setVisibility(View.GONE);
                            viewPager.setVisibility(View.VISIBLE);
                            tabLayout.setVisibility(View.VISIBLE);
//                            checkout_tl_ll.setVisibility(View.VISIBLE);
                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                            setupBadge();
                            Log.w("BTAG", "response Utility.CartCount ===>inside "+Utility.CartCount);

                            JSONArray tabArray = response.getJSONArray("categoryList");

                            for (int i=0;i<tabArray.length();i++){
                                JSONObject tabobject = tabArray.getJSONObject(i);
                                Category_Product_Fragment fragment=new Category_Product_Fragment();
//----------------------------------------for image--------------------------------------------------
//                                Picasso.with(TabActivity.this).load(tabobject.getString("category_image")).fit().into(tab_up_image);
                                String bannerName = tabobject.getString("category_image");
                                String imageName = tabobject.getString("category_icon");
                             //   String ImageId = getIntent().getStringExtra("ID");
                                if(getIntent().getStringExtra("ID").equals(tabobject.getString("category_id"))){

                                    ImageId=i;
                                    Log.w("DTAG",getIntent().getStringExtra("ID"));

                                    if (ImageId==0){
                                        Picasso.with(TabActivity.this).load(tabobject.getString("category_image")).fit().centerCrop().into(tab_up_image);

                                    }
//                                    if (ImageId==1){
//                                        Picasso.with(TabActivity.this).load(tabobject.getString("category_image")).fit().centerCrop().into(tab_up_image);
//
//                                    }
                                }
                                int resID = getResources().getIdentifier(imageName, "drawable", "package.name");
                              //  tabLayout.addTab(tabLayout.newTab());
                                Log.w("IMGTAG", "Image  "+imageName);

 //----------------------------------------for image--------------------------------------------------
                                Bundle bundle=new Bundle();
                                bundle.putString("id",tabobject.getString("category_id"));
                                fragment.setArguments(bundle);
                                adapter.addFragment(fragment,tabobject.getString("category_name"),imageName,bannerName);

                                viewPager.setAdapter(adapter);
                            //    viewPager.setCurrentItem(AppController.tabCurrentItem);
                                Log.w("STAG", "Banner "+bannerName);

                            }

                            for (int i = 0; i <tabArray.length(); i++) {
                                TabLayout.Tab tab = tabLayout.getTabAt(i);
                                String newtab = String.valueOf(tab);
//                                Toast.makeText(TabActivity.this, ""+tab, Toast.LENGTH_SHORT).show();
                                  if(!(newtab.length()==0)){
                                      if(tab!=null){
                                          tab.setCustomView(adapter.getTabView(i,viewPager));

                                      }

                                    }


                            }
                            Log.w("IMGTAG", "ImageId===>  "+ImageId);
                          //  String ImageId = getIntent().getStringExtra("ID");

                            if(ImageId>0 ){
                                tabLayout.getTabAt((ImageId)).select();
                            }
//                            tabLayout.getTabAt((ImageId)).select();
                            //27Aug2020
                            TextView tab_name = (TextView) tabLayout.getTabAt((ImageId)).getCustomView().findViewById(R.id.textView);
                            tab_name.setTextColor(getResources().getColor(R.color.newYellow));
                            ImageView image = (ImageView)tabLayout.getTabAt((ImageId)).getCustomView().findViewById(R.id.imgView);
                            image.setColorFilter(getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);

                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //start your activity here

                                    progressDialog.dismiss();

                                }

                            }, 2000);

                        }else{
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //start your activity here
                                    Toast.makeText(TabActivity.this, "Please Try after some time", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();

                                }

                            }, 2000);

                            no_data_ll.setVisibility(View.VISIBLE);
                            tabLayout.setVisibility(View.GONE);
                            loca_rl.setVisibility(View.GONE);
                            checkout_button_tml.setVisibility(View.GONE);
//                            checkout_tl_ll.setVisibility(View.GONE);
                            viewPager.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    no_data_ll.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.GONE);
                    checkout_tl_ll.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
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
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        View  actionView = menuItem.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
//        getMyTab();
        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;


    }
    public void setupBadge() {

        if (textCartItemCount != null) {
            Log.w("BTAG", "response Utility.CartCount ===>outside "+Utility.CartCount);

            if (Utility.CartCount==0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                      checkout_tl_ll.setVisibility(View.GONE);
//                    if(Category_Product_Fragment.totalC.trim().length()!=0){
//                        Category_Product_Fragment.totalC = String.valueOf(0);
//                    }

                }
            } else {
                textCartItemCount.setText(String.valueOf(Utility.CartCount));
                checkout_tl_ll.setVisibility(View.VISIBLE);
//                Utility.CartCount = String.valueOf(tabCartCount);
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_cart:

                 if (Utility.CartCount==0){
                     Toast.makeText(this, "Zero items in cart", Toast.LENGTH_SHORT).show();

                 } else{
                     Intent intent = new Intent(TabActivity.this,CartDiscription.class);

                     Utility.FromTabClass=true;
                     Utility.FromMainClass=false;
                     Utility.FromPD=false;
                     startActivity(intent);
                 }


                return true;
            case R.id.action_search:
                Intent intent = new Intent(TabActivity.this,SearchItem.class);
                startActivity(intent);
                return true;
//            case  R.id.action_account:
//                if(!(sessionManagement.isLoggedIn())){
////
////                    Utility.FromPD = false;
////                    Utility.FromMainClass = false;
//                    Intent intent = new Intent(TabActivity.this,Login.class);
//                    intent.putExtra("fromPage","two");
//                    startActivity(intent);
//                }else{
//                    Intent intent = new Intent(TabActivity.this,MyAccount.class);
//                    intent.putExtra("fromPage","two");
//                    startActivity(intent);
//                }

//                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
//public static void finishme(){
//        tabLayout.finish();
//}

    @Override
    public void addCount(boolean ADD_FLAG) {
        if (ADD_FLAG) {
//If true increment..
            tabCartCount++;
            textCartItemCount.setText(String.valueOf(tabCartCount));
        } else{
//If false decrement..
            tabCartCount--;
            textCartItemCount.setText(String.valueOf(tabCartCount));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Utility.FromCart = false;
//        Container_Main_Class.
        if (!TabActivity.this.isFinishing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
         private final List<String> mFragmentTitles = new ArrayList<>();
         private final List<String> mFragmentImages = new ArrayList<>();
         private final List<String> mFragmentBannerImages = new ArrayList<>();
        private int tlastPosition = -1;
        Context context;
        public Adapter(FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment, String title, String image, String bannerImages) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
            mFragmentImages.add(image);
            mFragmentBannerImages.add(bannerImages);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
//            return mFragments.
//            return mFragments.newInstance(position);// IMPORTANT
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
//            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentTitles.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }

        public View getTabView(int position, ViewPager viewPager) {
            // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
            View v = LayoutInflater.from(tabActivity).inflate(R.layout.custom_tab, null);

            TextView tv = (TextView) v.findViewById(R.id.textView);
            tv.setText(mFragmentTitles.get( position));
            ImageView img = (ImageView) v.findViewById(R.id.imgView);
                img.setVisibility(View.GONE);
            Picasso.with(tabActivity).load(mFragmentImages.get(position)).into(img);
//            Toast.makeText(v.getContext(),""+mFragmentImages.get(0), Toast.LENGTH_SHORT).show();



            getTabListner(viewPager,v);
            Animation animation = AnimationUtils.loadAnimation(tabActivity, R.anim.fade_in);
            img.startAnimation(animation);

            return v;
        }

        private void getTabListner(ViewPager viewPager, View v) {
//            viewPager.setCurrentItem(TabActivity.tabCurrentItem);
            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int c = tab.getPosition();
                    viewPager.setCurrentItem(c);
//                    Toast.makeText(v.getContext(),""+TabActivity.tabCurrentItem, Toast.LENGTH_SHORT).show();
                    if(Objects.requireNonNull(tabLayout.getTabAt(c)).isSelected()){
                        TextView tab_name = (TextView) tabLayout.getTabAt(c).getCustomView().findViewById(R.id.textView);
                        tab_name.setTextColor(v.getResources().getColor(R.color.newYellow));
                        ImageView image = (ImageView)tabLayout.getTabAt(c).getCustomView().findViewById(R.id.imgView);
                        image.setColorFilter(v.getContext().getColor(R.color.tab_color), android.graphics.PorterDuff.Mode.MULTIPLY);

                     Picasso.with(v.getContext()).load(mFragmentBannerImages.get(c)).fit().centerCrop().into(tab_up_image);

                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    int c = tab.getPosition();
                    TextView tab_name = (TextView) tabLayout.getTabAt(c).getCustomView().findViewById(R.id.textView);
                    tab_name.setTextColor(v.getResources().getColor(R.color.white));
                    ImageView image = (ImageView)tabLayout.getTabAt(c).getCustomView().findViewById(R.id.imgView);
                    image.setColorFilter(v.getContext().getColor(R.color.colorToolbar), android.graphics.PorterDuff.Mode.MULTIPLY);
//                    viewPager.setCurrentItem(c);


                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    int c = tab.getPosition();
                    viewPager.setCurrentItem(c);

//                    tabcurrent.setCurrentItem(tabLayout.getSelectedTabPosition());
                }
            });
        }


    }

    //Tablayout
    private View createTabItemView(String imgUri) {
        ImageView imageView = new ImageView(this);
        TabLayout.LayoutParams params = new TabLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        Picasso.with(TabActivity.this).load(imgUri).into(imageView);
        return imageView;
    }
}
