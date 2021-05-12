package com.abfresh.in;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.abfresh.in.Adapter.FrontPageMenuBlackAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.CurvedBottomNavigationView;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Fragments.Container_Fragment;
import com.abfresh.in.Model.ArrayList;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.muddzdev.styleabletoast.StyleableToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class Container_Main_Class extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {
    SessionManagement sessionManagement;
    Toolbar toolbar;
    private DrawerLayout drawer;
    private SimpleSearchView searchView;
    TextView textCartItemCount,headerLogo,city_name_loca;
    public static TextView logo_txt;
    LinearLayout menu_wallet_ll,drawer_menu_ll,myaccount_link_ll,noti_ll,contactus_ll,refer_ll,recipe_ll,my_order_ll,logout_ll,blog_ll,tandc_ll,faq_ll,privacy_ll;
    RelativeLayout paypal_rl,rlCart,mlinId,main_layout_search_rl,menu_joinnow_rl;
    public static int mCartItemCount = 0;
    public static boolean fromMainPage = false;
    FrameLayout fragment_container;
//    public static BottomNavigationView bottomNavigationView;
    NavigationView navigationView;
    LinearLayout search_cmc_ll;
    int slectedBottomMenu;
   public static AppBarLayout appBarLayout;
    String userid;
    HashMap<String, String> user;
//    private RecyclerView front_page_menu_rv;
    private FrontPageMenuBlackAdapter front_page_menu_Adapter;
    private java.util.ArrayList<ArrayList> front_page_menu_horizontalList;
    ImageView myaccount_iv,search_icon_toolbar,logout_ll_line,my_whatsapp_iv;
    TextView cart_badge,join_now_tv;
    VectorMasterView heartVector,heartVector1,heartVector2,heartVector3,heartVector4;
    CurvedBottomNavigationView mView;
//    private EndDrawerToggle drawerToggle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(MainActivity.popup){
                    showPopup();
                }
            }
        }, 100);
//        getAboveMenu();
        sessionManagement = new SessionManagement(getApplicationContext());
        search_cmc_ll = (LinearLayout)findViewById(R.id.search_cmc_ll);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        toggle.setDrawerIndicatorEnabled(false);
//        toolbar.setNavigationIcon(R.drawable.nav_draw_icon);
        search_icon_toolbar = (ImageView)findViewById(R.id.search_icon_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if(Math.abs(verticalOffset)==appBarLayout.getTotalScrollRange()){
//                    // Collapsed
//                    search_icon_toolbar.setVisibility(View.VISIBLE);
////                    Animation animation = AnimationUtils.loadAnimation(Container_Main_Class.this, R.anim.fade_in);
////                    search_icon_toolbar.startAnimation(animation);
//                }else if(verticalOffset==0){
//                    // Expanded
//                    search_cmc_ll.setVisibility(View.VISIBLE);
////                    Animation animation = AnimationUtils.loadAnimation(Container_Main_Class.this, R.anim.fade_out);
////                    search_icon_toolbar.startAnimation(animation);
//                    search_icon_toolbar.setVisibility(View.GONE);
//                }else{
//                    search_cmc_ll.setVisibility(View.GONE);
//                }
//            }
//        });
//        search_icon_toolbar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                appBarLayout.setExpanded(true, true);
//
//            }
//        });
        fragment_container = (FrameLayout)findViewById(R.id.fragment_container);
        mView = findViewById(R.id.customBottomBar);
        mView.setOnNavigationItemSelectedListener(bnListner);

        mView.inflateMenu(R.menu.bottom_menu);
            heartVector = (VectorMasterView) findViewById(R.id.fab);
            heartVector1 = (VectorMasterView) findViewById(R.id.fab1);
            heartVector2 = (VectorMasterView) findViewById(R.id.fab2);
             heartVector3 = (VectorMasterView) findViewById(R.id.fab4);
             heartVector4 = (VectorMasterView) findViewById(R.id.fab5);
        mlinId = (RelativeLayout)findViewById(R.id.lin_id);
//        front_page_menu_rv = (RecyclerView)findViewById(R.id.front_page_menu_rv);
//        bottomNavigation = (MeowBottomNavigation) findViewById(R.id.bottomview);
//        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.order_icon2));
//        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_store));
//        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_profile));
//        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.ic_order_history));
//        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_offers));
//        bottomNavigation.add(new MeowBottomNavigation.Model(5, R.drawable.ic_home));
//        bottomNavigationView.setOnNavigationItemSelectedListener(navListner

//        logo_txt = (TextView)findViewById(R.id.logo_txt);
//        logo_txt.setVisibility(View.VISIBLE);

        menu_wallet_ll = (LinearLayout)findViewById(R.id.menu_wallet_ll);
        drawer_menu_ll = (LinearLayout)findViewById(R.id.drawer_menu_ll);
        logout_ll = (LinearLayout)findViewById(R.id.logout_ll);
        my_order_ll = (LinearLayout)findViewById(R.id.my_order_ll);
        myaccount_link_ll = (LinearLayout)findViewById(R.id.myaccount_link_ll);
        noti_ll = (LinearLayout)findViewById(R.id.noti_ll);
        contactus_ll = (LinearLayout)findViewById(R.id.contactus_ll);
        paypal_rl = (RelativeLayout) findViewById(R.id.paypal_rl);
        rlCart = (RelativeLayout) findViewById(R.id.rlCart);
        main_layout_search_rl = (RelativeLayout) findViewById(R.id.main_layout_search_rl);
        refer_ll = (LinearLayout) findViewById(R.id.refer_ll);
        recipe_ll = (LinearLayout) findViewById(R.id.recipe_ll);
        blog_ll = (LinearLayout) findViewById(R.id.blog_ll);
        tandc_ll = (LinearLayout) findViewById(R.id.tandc_ll);
        faq_ll = (LinearLayout) findViewById(R.id.faq_ll);
        privacy_ll = (LinearLayout) findViewById(R.id.privacy_ll);
        menu_joinnow_rl = (RelativeLayout) findViewById(R.id.menu_joinnow_rl);

//        city_name_loca = (TextView) findViewById(R.id.city_name_loca_ml);
        cart_badge = (TextView) findViewById(R.id.cart_badge);
        join_now_tv = (TextView) findViewById(R.id.join_now_tv);
        myaccount_iv = (ImageView) findViewById(R.id.myaccount_iv);
        logout_ll_line = (ImageView) findViewById(R.id.logout_ll_line);
        my_whatsapp_iv = (ImageView) findViewById(R.id.my_whatsapp_iv);
//        city_name_loca.setText(sessionManagement.getUserDetails().get(KEY_City));
        drawer = findViewById(R.id.drawer_layout);

//        drawer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        navigationView = findViewById(R.id.nav_view);
//         getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Container_Main_Class.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
//        drawer.openDrawer(Gravity.RIGHT);
        toggle.syncState();
//        toggle.setDrawerIndicatorEnabled(false);
//        toolbar.setNavigationIcon(R.drawable.nav_draw_icon);
//        Drawable drawable= getResources().getDrawable(R.drawable.nav_draw_icon);
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 30, 30, true));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_bar_logo);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_button);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Container_Fragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_message);
        }
//        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
//        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
//        spaceNavigationView.addSpaceItem(new SpaceItem("Order", R.drawable.order_icon));
//        spaceNavigationView.addSpaceItem(new SpaceItem("Store", R.drawable.store_icon));
//        spaceNavigationView.addSpaceItem(new SpaceItem("Order History", R.drawable.order_history));
//        spaceNavigationView.addSpaceItem(new SpaceItem("Offers", R.drawable.offer));

        if(!(sessionManagement.isLoggedIn())){
            join_now_tv.setVisibility(View.VISIBLE);
            logout_ll.setVisibility(View.GONE);
            logout_ll_line.setVisibility(View.GONE);
        }else{
            join_now_tv.setVisibility(View.GONE);
            logout_ll.setVisibility(View.VISIBLE);
            logout_ll_line.setVisibility(View.VISIBLE);
        }
        heartVector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    intent.putExtra("fromPage","one");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Container_Main_Class.this,MyAccount.class);
                    intent.putExtra("fromPage","one");
                    startActivity(intent);
                }
            }
        });

        myaccount_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    intent.putExtra("fromPage","one");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Container_Main_Class.this,MyAccount.class);
                    intent.putExtra("fromPage","one");
                    startActivity(intent);
                }
            }
        });

        rlCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getCartCount();
                Log.w("CCTAG",""+Utility.CartCount);
                if(Utility.CartCount==0){
                    Toast.makeText(Container_Main_Class.this, "Zero items in cart", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(Container_Main_Class.this,CartDiscription.class);
//                    intent.putExtra("fromPage","one");
                    Utility.FromTabClass=false;
                    Utility.FromMainClass=true;
                    Utility.FromPD=false;
                    startActivity(intent);
                }
            }
        });

        logout_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.MyAddressNew="";
//                final AlertDialog.Builder builder = new AlertDialog.Builder(Container_Main_Class.this);
//                builder.setTitle("Alert!");
//
//                //Setting message manually and performing action on button click
//                builder.setMessage("Do you really want to Logout?");
//                //This will not allow to close dialogbox until user selects an option
//                builder.setCancelable(false);
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {

//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        //  Action for 'NO' Button
//
//                        dialog.cancel();
//                    }
//                });
//
//                //Creating dialog box
//                AlertDialog alert = builder.create();
//                //Setting the title manually
//                //alert.setTitle("AlertDialogExample");
//                alert.show();
                LayoutInflater factory = LayoutInflater.from(Container_Main_Class.this);
                final View deleteDialogView = factory.inflate(R.layout.logout_custom_dialog, null);
                final AlertDialog deleteDialog = new AlertDialog.Builder(Container_Main_Class.this).create();
                deleteDialog.setView(deleteDialogView);
                deleteDialogView.findViewById(R.id.lcd_yes_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                Toast.makeText(this, "positive button", Toast.LENGTH_SHORT).show();
                        sessionManagement.logoutUser();
                        Intent intent = new Intent(Container_Main_Class.this,Location_Text_Intent.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        MainActivity.locationget = true;
                        Utility.CartCount=0;
                        deleteDialog.dismiss();
                    }
                });
                deleteDialogView.findViewById(R.id.lcd_no_tv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteDialog.dismiss();
                    }
                });
                deleteDialog.show();
            }
        });

        drawer_menu_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        myaccount_link_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Container_Main_Class.this,MyAccount.class);
                    startActivity(intent);
                }
            }
        });

        menu_wallet_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Container_Main_Class.this,Wallet.class);
                    startActivity(intent);
                }

            }
        });

        noti_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Container_Main_Class.this, Notification.class);
                    startActivity(intent);
                }
            }
        });

        contactus_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Container_Main_Class.this,ContactUs.class);
                startActivity(intent);
            }
        });

        paypal_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Container_Main_Class.this,PayPal.class);
                startActivity(intent);
            }
        });

        refer_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Container_Main_Class.this,ReferAndEarn.class);
                    startActivity(intent);
                }
            }
        });

        recipe_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(Container_Main_Class.this,Recipes.class);
                startActivity(intent);
            }
        });

        my_order_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
                if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(Container_Main_Class.this, MyOrders.class);
                    startActivity(intent);
                }
            }
        });

        mView.getMenu().findItem(R.id.bitem2).setChecked(true);

        main_layout_search_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Container_Main_Class.this,SearchItem.class);
                startActivity(intent);
            }
        });

        search_icon_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Container_Main_Class.this,SearchItem.class);
                startActivity(intent);
            }
        });

        blog_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Container_Main_Class.this,BlogActivity.class);
                startActivity(intent);
            }
        });



        tandc_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Container_Main_Class.this,TermsAndCondition.class);
                startActivity(intent);
            }
        });
        faq_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Container_Main_Class.this,FaqActivity.class);
                startActivity(intent);
            }
        });
        privacy_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Container_Main_Class.this,PrivacyPolicy.class);
                startActivity(intent);
            }
        });
        join_now_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Container_Main_Class.this,Login.class);
                startActivity(intent);
            }
        });

        my_whatsapp_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PackageManager packageManager = Container_Main_Class.this.getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone="+ "918551085505" +"&text=" + URLEncoder.encode("Hi AbFresh!", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                       startActivity(i);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private void showPopup() {
//        MainActivity.popup = false;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//        LayoutInflater inflater = (LayoutInflater) getSystemService(Container_Main_Class.this.LAYOUT_INFLATER_SERVICE);
//
//        PopupWindow pw = new PopupWindow(inflater.inflate(R.layout.popup_layout, null, false), getWindow().getDecorView().getWidth(), getWindow().getDecorView().getHeight(), true);
//        pw.showAtLocation(findViewById(R.id.main_content), Gravity.CENTER, 0, 0);
//        View pView = pw.getContentView();
//       ImageView close = (ImageView) pView.findViewById(R.id.close_btn_pop);
//
//       close.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               pw.dismiss();
//           }
//       });
//            }
//        },100);
    }

    CurvedBottomNavigationView.OnNavigationItemSelectedListener bnListner = new CurvedBottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bitem1:
                    if(!(sessionManagement.isLoggedIn())){
                    Intent intent = new Intent(Container_Main_Class.this,Login.class);
                    intent.putExtra("fromPage","one");
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Container_Main_Class.this,MyOrders.class);
                    intent.putExtra("fromPage","one");
                    startActivity(intent);
                }
                   break;
                case R.id.bitem2:
                    if(!(sessionManagement.isLoggedIn())){
                        Intent intent = new Intent(Container_Main_Class.this,Login.class);
                        intent.putExtra("fromPage","one");
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(Container_Main_Class.this,MyAccount.class);
                        intent.putExtra("fromPage","one");
                        startActivity(intent);
                    }
                    break;
                case R.id.bitem3:
                    if(!(sessionManagement.isLoggedIn())){
                        Intent intent = new Intent(Container_Main_Class.this,Login.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(Container_Main_Class.this, MyOrders.class);
                        mView.getMenu().findItem(R.id.bitem2).setChecked(true);

                        startActivity(intent);
                    }
                    break;

                case R.id.bitem4:
                    if(!(sessionManagement.isLoggedIn())){
                        Intent intent = new Intent(Container_Main_Class.this,Login.class);
                        startActivity(intent);
                    }else {
                        if( Utility.CartCount!=0){
                            Intent intent = new Intent(Container_Main_Class.this, CartDiscription.class);
                            mView.getMenu().findItem(R.id.bitem2).setChecked(true);
                            startActivity(intent);
                        }else{
                            Toast.makeText(Container_Main_Class.this, "Zero items in chart", Toast.LENGTH_SHORT).show();
                        }
                    }
//                    File outputFile = new File(Environment.getExternalStoragePublicDirectory
//                            (Environment.DIRECTORY_DOWNLOADS), "9.pdf");
//                    Uri uri = Uri.fromFile(outputFile);
//                    Intent intent = new Intent(Intent.ACTION_SEND);
////                    intent.setType("pdf");
////                    intent.putExtra(Intent.EXTRA_STREAM, uri);
//                    intent.setType("text/plain");
////                    intent.setPackage("com.whatsapp");
//                    intent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
//                    intent.setPackage("com.whatsapp");
//                    startActivity(intent);
                    break;

                case R.id.bitem5:

                        Intent intent = new Intent(Container_Main_Class.this, Offers.class);
                        mView.getMenu().findItem(R.id.bitem2).setChecked(true);
                        startActivity(intent);

                    break;
            }

            return true;
        }

    };


//--------------------------------------------------BackPress-------------------------------------
@Override
public void onBackPressed() {
//    if (searchView.isSearchOpen()) {
//        return;
//    }
    super.onBackPressed();
    int navitemId = navigationView.getId();


  this.finish();
  finishAffinity();

    if(drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
    }
//    else {
//        getFragmentManager().popBackStack();
//
//
//    }

}
    public static void setHomeItem(Activity activity) {


//        bottomNavigationView.setSelectedItemId(R.id.bitem1);
    }
//--------------------------------backPress End------------------------------
//--------------------------------onResume End------------------------------


    @Override
    protected void onResume() {
        super.onResume();
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
//        invalidateOptionsMenu();
        getCartCount();
        if(!(sessionManagement.isLoggedIn())){
            join_now_tv.setVisibility(View.VISIBLE);
            logout_ll.setVisibility(View.GONE);
            logout_ll_line.setVisibility(View.GONE);

        }else{
            join_now_tv.setVisibility(View.GONE);
            logout_ll.setVisibility(View.VISIBLE);
            logout_ll_line.setVisibility(View.VISIBLE);
        }
//        this.recreate();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void getCartCount() {
        try {
            sessionManagement = new SessionManagement(getApplicationContext());

//            Toast.makeText(Container_Main_Class.this, userid, Toast.LENGTH_SHORT).show();

            JSONObject cmc_object = new JSONObject();
            String tempid= Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            cmc_object.put("temp_user_id",tempid);
            cmc_object.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            cmc_object.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            cmc_object.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("CMCTAG", "Object "+cmc_object);

//            container_pb.setVisibility(View.VISIBLE);


            JsonObjectRequest cmcBanner = new JsonObjectRequest(Request.Method.POST, Utility.Home, cmc_object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("CMCTAG", "response "+response);
                    JSONObject jsonObject =response;
                    try {
                        if ((jsonObject.getString("success")).equals("1")){
                            if (response.getString("cart_count").equals("") || response.getString("cart_count").equals("0")){
                                cart_badge.setVisibility(View.GONE);
                                Utility.CartCount = 0;
                            }else {
                                cart_badge.setText(response.getString("cart_count"));
                                Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
                                cart_badge.setVisibility(View.VISIBLE);
                            }

                        }else {
//                            container_pb.setVisibility(View.GONE);
                            StyleableToast.makeText(Container_Main_Class.this,"Please try after some time", R.style.mySizeToast).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("BTAG", "JSONException "+e);
//                        container_pb.setVisibility(View.GONE);
                        StyleableToast.makeText(Container_Main_Class.this,"Please try after some time", R.style.mySizeToast).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.w("BTAG", "VolleyError "+error);
//                    container_pb.setVisibility(View.GONE);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            };cmcBanner.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(cmcBanner);
        } catch (JSONException e) {
            e.printStackTrace();
//            container_pb.setVisibility(View.GONE);

        }
    }


}
