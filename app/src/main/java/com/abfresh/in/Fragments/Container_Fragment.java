package com.abfresh.in.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.daimajia.slider.library.SliderLayout;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.abfresh.in.Adapter.AdvSlidingAdapter;
import com.abfresh.in.Adapter.BestSellerAdapter;
import com.abfresh.in.Adapter.ExploreCatAdapter;
import com.abfresh.in.Adapter.ReviewAdapter;
import com.abfresh.in.Adapter.SliderAdapterExample;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.NewProductDiscription;
import com.abfresh.in.Offers;
import com.abfresh.in.R;
import com.abfresh.in.TabActivity;
import com.abfresh.in.Model.SliderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

//import jrizani.jrspinner.JRSpinner;

public class Container_Fragment extends Fragment {
    SessionManagement sessionManagement;
    NestedScrollView nesteed_scroll;
    //    private SliderLayout mDemoSlider;
    private RecyclerView horizontal_recycler_view;
    private ArrayList<com.abfresh.in.Model.ArrayList> horizontalList;

    public static Boolean cityBoolean = false;
    public static int cityPosition;
    ProgressBar container_pb;
    //    private RecyclerView pp_rv;
//    private PopularProductAdapter ppAdapter;
//    private ArrayList<com.example.myapplication.model.ArrayList> pphorizontalList;
    RelativeLayout container_rl;
    private RecyclerView ec_rv,wcs_rv,bsom_rv;
    //    private RecyclerViewBouncy ec_rv;
    private ExploreCatAdapter ecAdapter;
    private ReviewAdapter reviewAdapter;
    private BestSellerAdapter bestSellerAdapter;
    private ArrayList<com.abfresh.in.Model.ArrayList> echorizontalList;
    private ArrayList<com.abfresh.in.Model.ArrayList> reviewList;
    private ArrayList<com.abfresh.in.Model.ArrayList> bestSellerList;
    private ArrayList<com.abfresh.in.Model.ArrayList> bannerList = new ArrayList<com.abfresh.in.Model.ArrayList>();
    private ArrayList<com.abfresh.in.Model.ArrayList> advList;

    //    private RecyclerView ba_rv;
//    private BlogAdapter baAdapter;
    private ArrayList<com.abfresh.in.Model.ArrayList> bahorizontalList;
    String userid;
    String[] data;
    public static String proImage;
    private SliderLayout mDemoSlider;
//    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<SliderModel> ImagesArray;
    SliderView sliderView,sliderView2;
    SliderAdapterExample adapter;
    AdvSlidingAdapter adapter2;
    TextView deal_pro_name_tv,dod_pro_dis_tv,price_dod_tv,price_pri_dod_tv,slash_dob;
    ImageView dod_iv;
    Button dod_buy_btn;
    LinearLayout no_data_ll_nc;
//    ArrayList<Integer> ImagesArray = new ArrayList();imageSlider
//    WormDotsIndicator indicator;
    public static  FirebaseAnalytics mFirebaseAnalytics;
    ImageView facebook_icon_nc,insta_icon_nc,tweet_icon_nc;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_container, container, false);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
//        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
//        nesteed_scroll = (NestedScrollView)view.findViewById(R.id.nesteed_scroll);
//        pp_rv = (RecyclerView)view.findViewById(R.id.pp_rv);
        ec_rv = (RecyclerView) view.findViewById(R.id.ec_rv);
        wcs_rv = (RecyclerView) view.findViewById(R.id.wcs_rv);
        bsom_rv = (RecyclerView) view.findViewById(R.id.bsom_rv);
        deal_pro_name_tv = (TextView) view.findViewById(R.id.deal_pro_name_tv);
        dod_pro_dis_tv = (TextView) view.findViewById(R.id.dod_pro_dis_tv);
        price_dod_tv = (TextView) view.findViewById(R.id.price_dod_tv);
        slash_dob = (TextView) view.findViewById(R.id.slash_dob);
        price_pri_dod_tv = (TextView) view.findViewById(R.id.price_pri_dod_tv);
        price_pri_dod_tv.setPaintFlags(price_pri_dod_tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        dod_iv = (ImageView) view.findViewById(R.id.dod_iv);
        facebook_icon_nc = (ImageView) view.findViewById(R.id.facebook_icon_nc);
        insta_icon_nc = (ImageView) view.findViewById(R.id.insta_icon_nc);
        tweet_icon_nc = (ImageView) view.findViewById(R.id.tweet_icon_nc);
        dod_buy_btn = (Button) view.findViewById(R.id.dod_buy_btn);
        no_data_ll_nc = (LinearLayout) view.findViewById(R.id.no_data_ll_nc);
//        bs_iv = (ImageView) view.findViewById(R.id.bs_iv);
        Offers.offerClick=false;
//        ba_rv = (RecyclerView)view.findViewById(R.id.blog_rv);
        container_pb = (ProgressBar) view.findViewById(R.id.container_pb);

        container_rl = (RelativeLayout) view.findViewById(R.id.container_rl);

//        mPager = (ViewPager) view.findViewById(R.id.pager);
//        indicator = (WormDotsIndicator)
//                view.findViewById(R.id.worm_dots_indicator);
        Fade fade = new Fade();
        View decor = getActivity().getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getActivity().getWindow().setEnterTransition(fade);
        getActivity().getWindow().setExitTransition(fade);
//----------------------------------------------Horizontal scroll Banner---------------------------------

        sliderView =view.findViewById(R.id.imageSlider);
        sliderView2 =view.findViewById(R.id.imageSlider2);
//--------------------------------------Blog End------------------------------------------------------------
        getCatBannerNew();

        facebook_icon_nc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://facebook.com/abfresh.in/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        insta_icon_nc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/abfresh.india/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        tweet_icon_nc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://twitter.com/Abfresh_in";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        return view;
    }





    @Override
    public void onResume() {
        if (AppController.clickBanner.equals("1") && bannerList.size()>0){
            adapter.notifyDataSetChanged();
            AppController.clickBanner="";
        }
        super.onResume();
    }
    
    private void getCatBannerNew() {
        try {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), null, null, true);
            progressDialog.setContentView(R.layout.custom_progress_dialog);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.show();
            sessionManagement = new SessionManagement(getContext());
            JSONObject tabJsonObjectNew = new JSONObject();
            String tempid = Settings.Secure.getString(getContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            tabJsonObjectNew.put("temp_user_id", tempid);
            tabJsonObjectNew.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            tabJsonObjectNew.put("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            tabJsonObjectNew.put("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
            Log.w("CFTAG", "response tabJsonObjectNew " + tabJsonObjectNew);

            JsonObjectRequest tabRequestNew = new JsonObjectRequest(Request.Method.POST, Utility.Home, tabJsonObjectNew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("CFTAG", "response New " + response);
                        echorizontalList = new ArrayList<com.abfresh.in.Model.ArrayList>();
                        reviewList = new ArrayList<com.abfresh.in.Model.ArrayList>();
                        bannerList = new ArrayList<com.abfresh.in.Model.ArrayList>();
                        bestSellerList = new ArrayList<com.abfresh.in.Model.ArrayList>();
                        advList = new ArrayList<com.abfresh.in.Model.ArrayList>();
                        ImagesArray = new ArrayList<>();
                        HashMap<String, String> file_maps = new HashMap<String, String>();
                        if ((response.getString("success")).equals("1")) {
                            no_data_ll_nc.setVisibility(View.GONE);
                            container_rl.setVisibility(View.VISIBLE);
                            JSONArray tabArray = response.getJSONArray("categoryList");

                            for (int i = 0; i < tabArray.length(); i++) {
                                JSONObject tabobject = tabArray.getJSONObject(i);

                                String imageCatId = tabobject.getString("category_id");
                                String imageName = tabobject.getString("category_name");
                                String imageIcon = tabobject.getString("category_image");
                                Log.w("CFTAG", "Image  " + imageName);

                                echorizontalList.add(new com.abfresh.in.Model.ArrayList(imageCatId,imageName,imageIcon));
                            }
                            ecAdapter = new ExploreCatAdapter(getContext(), echorizontalList);
                           final GridLayoutManager manager
                                 = new GridLayoutManager(getContext(),3);

                             ec_rv.setLayoutManager(manager);
                            ec_rv.setAdapter(ecAdapter);

                            ecAdapter.setOnItemClickListner(new ExploreCatAdapter.OnItemClickListner() {
                                @Override
                                public void onBannerClick(int position, ImageView productImage) {
//                                    TabActivity.tabCurrentItem = position;
                                    AppController.tabCurrentItem =  Integer.parseInt(echorizontalList.get(position).getName());
                                    proImage = String.valueOf(productImage);
//                                    Toast.makeText(getContext(), ""+echorizontalList.get(position).getColorArray(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getContext(), TabActivity.class);
                                    // intent.putExtra("ID", String.valueOf(position));
                                    intent.putExtra("ID", echorizontalList.get(position).getName());
                                    intent.putExtra("image",echorizontalList.get(position).getWeight());
                                    Log.w("CFTAG", "ImageURL "+echorizontalList.get(position).getWeight());
                                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                            getActivity(), productImage, ViewCompat.getTransitionName(productImage));
                                    startActivity(intent, options.toBundle());
                                }
                            });

            /////////////////////////////////////////////BannerList Banner///////////////////////////////////////////////////////

                            JSONArray bannerArray = response.getJSONArray("bannerList");
                            for (int j = 0; j < bannerArray.length(); j++) {
                                JSONObject bannerobject = bannerArray.getJSONObject(j);
                                SliderModel model=new SliderModel();
                                String banner_id = bannerobject.getString("banner_id");
                                String banner_name = bannerobject.getString("banner_name");
                                String banner_image = bannerobject.getString("banner_image");
                                bannerList.add(new com.abfresh.in.Model.ArrayList(banner_id,banner_image));

//                               IMAGES = banner_image;
                            }
                            adapter = new SliderAdapterExample(getActivity(),bannerList);
                            sliderView.setSliderAdapter(adapter);
                            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            sliderView.setIndicatorSelectedColor(getResources().getColor(R.color.newYellowBg));
                            sliderView.setIndicatorUnselectedColor(Color.WHITE);

                            sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
                            sliderView.startAutoCycle();



 /////////////////////////////////////////////Deal of Day///////////////////////////////////////////////////////
                            JSONObject topDealObject = response.getJSONObject("topDealProduct");
                            deal_pro_name_tv.setText(topDealObject.getString("product_name"));

                            dod_pro_dis_tv.setText(topDealObject.getString("product_description"));
                            if(topDealObject.getString("product_gross_amt").length()!=0){
                                slash_dob.setVisibility(View.VISIBLE);
                                price_pri_dod_tv.setText("₹ "+topDealObject.getString("product_gross_amt"));
                            }else{
                                slash_dob.setVisibility(View.GONE);
                            }

                            price_dod_tv.setText("₹ "+topDealObject.getString("product_amt"));
                            Picasso.with(getActivity()).load(topDealObject.getString("product_image")).into(dod_iv);
                            dod_buy_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        if(!(topDealObject.getString("product_name").length()==0)){
                                            Intent intent = new Intent(getContext(), NewProductDiscription.class);
                                            // intent.putExtra("ID", String.valueOf(position));
                                            intent.putExtra("productId",topDealObject.getString("product_id"));
                                            intent.putExtra("categoryId",topDealObject.getString("category_id"));
//                                    Log.w("BTAG", "ImageURL "+echorizontalList.get(position).getWeight());

                                            startActivity(intent);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
 /////////////////////////////////////////////Best Seller///////////////////////////////////////////////////////
//                            JSONObject bestSellerListObject = response.getJSONObject("bestSellerList");
                            JSONArray bestSellerListArray = response.getJSONArray("bestSellerList");

                            for (int i = 0; i < bestSellerListArray.length(); i++) {
                                JSONObject bestSellerListObject = bestSellerListArray.getJSONObject(i);

                                String bsProId = bestSellerListObject.getString("product_id");
                                String bsCatId = bestSellerListObject.getString("category_id");
                                String bsproduct_name = bestSellerListObject.getString("product_name");
                                String bsproduct_amt = bestSellerListObject.getString("product_amt");
                                String bsProImage = bestSellerListObject.getString("product_image");
//                                Log.w("NBTAG", "Image  " + imageName);

                                bestSellerList.add(new com.abfresh.in.Model.ArrayList(bsProId,bsCatId,bsproduct_name,bsproduct_amt,bsProImage));




                            }
                            bestSellerAdapter = new BestSellerAdapter(getContext(), bestSellerList);
                            final LinearLayoutManager bsManager
                                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


                            bsom_rv.setLayoutManager(bsManager);
                            bsom_rv.setAdapter(bestSellerAdapter);
//                            bsom_rv.scrollToPosition(bestSellerAdapter.getItemCount()-(bestSellerAdapter.getItemCount() - 1));
//                            bsManager.scrollTo((int)bsom_rv.getScrollX() -250, (int)bsom_rv.getScrollY());
                            bsom_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                                    super.onScrolled(recyclerView, dx, dy);
                                    int firstItemVisible = bsManager.findFirstVisibleItemPosition();
                                    if (firstItemVisible != 0 && firstItemVisible % bestSellerList.size() == 0) {
                                        recyclerView.getLayoutManager().scrollToPosition(0);
                                    }
                                }
                            });

                            bestSellerAdapter.setOnItemClickListner(new BestSellerAdapter.OnItemClickListner() {
                                @Override
                                public void onBsClick(int position, ImageView productImage) {
                                    Intent intent = new Intent(getContext(), NewProductDiscription.class);
                                    // intent.putExtra("ID", String.valueOf(position));
                                    intent.putExtra("productId", bestSellerList.get(position).getRecipeName());
                                    intent.putExtra("categoryId",bestSellerList.get(position).getRecipeTypeName());
//                                    Log.w("BTAG", "ImageURL "+echorizontalList.get(position).getWeight());

                                    startActivity(intent);
                                }
                            });

//                            Picasso.with(getActivity()).load(bestSellerListObject.getString("product_image")).into(bs_iv);
/////////////////////////////////////////////Advertisment Banner///////////////////////////////////////////////////////

                            JSONArray advArray = response.getJSONArray("advertisementList");
                            for (int j = 0; j < advArray.length(); j++) {
                                JSONObject advobject = advArray.getJSONObject(j);
                                SliderModel model=new SliderModel();
                                String adv_id = advobject.getString("advertisement_id");
                                String adv_name = advobject.getString("advertisement_name");
                                String adv_image = advobject.getString("advertisement_image");
                                advList.add(new com.abfresh.in.Model.ArrayList(adv_id,adv_image));

//                               IMAGES = banner_image;
                            }
                            adapter2 = new AdvSlidingAdapter(getActivity(),advList);
                            sliderView2.setSliderAdapter(adapter2);
                            sliderView2.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
                            sliderView2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                            sliderView2.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                            sliderView2.setIndicatorSelectedColor(Color.WHITE);
                            sliderView2.setIndicatorUnselectedColor(Color.GRAY);
                            sliderView2.setScrollTimeInSec(4); //set scroll delay in seconds :
                            sliderView2.startAutoCycle();
/////////////////////////////////////////////Review Banner///////////////////////////////////////////////////////

                            JSONArray reviewArray = response.getJSONArray("reviewList");

                            for (int i = 0; i < reviewArray.length(); i++) {
                                JSONObject reviewobject = reviewArray.getJSONObject(i);

                                String reviewId = reviewobject.getString("review_id");
                                String reviewName = reviewobject.getString("user_name");
                                String reviewDis = reviewobject.getString("description");
//                                Log.w("NBTAG", "Image  " + imageName);

                                reviewList.add(new com.abfresh.in.Model.ArrayList(reviewId,reviewName,reviewDis));

                            }
                            reviewAdapter = new ReviewAdapter(getContext(), reviewList);
                            final LinearLayoutManager reviewManager
                                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);


                            wcs_rv.setLayoutManager(reviewManager);
                            wcs_rv.setAdapter(reviewAdapter);

                            wcs_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                                    super.onScrolled(recyclerView, dx, dy);
                                    int firstItemVisible = reviewManager.findFirstVisibleItemPosition();
                                    if (firstItemVisible != 0 && firstItemVisible % reviewList.size() == 0) {
                                        recyclerView.getLayoutManager().scrollToPosition(0);
                                    }
                                }
                            });
                            container_pb.setVisibility(View.GONE);
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //start your activity here

                                    progressDialog.dismiss();

                                }

                            }, 2000);
                        } else {
                            container_pb.setVisibility(View.GONE);
                            no_data_ll_nc.setVisibility(View.VISIBLE);
                            container_rl.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Please Try After some Time", Toast.LENGTH_SHORT).show();
                            Handler mHandler = new Handler();
                            mHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //start your activity here

                                    progressDialog.dismiss();

                                }

                            }, 2000);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("NBTAG", "JSONException e " + e);
                        container_pb.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.w("NBTAG", "VolleyError error " + error);
                    container_pb.setVisibility(View.GONE);
                    progressDialog.dismiss();
                    no_data_ll_nc.setVisibility(View.VISIBLE);
                    container_rl.setVisibility(View.GONE);
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
        } catch (JSONException e) {
            e.printStackTrace();
            Log.w("NBTAG", "JSONException e 2  " + e);
            container_pb.setVisibility(View.GONE);
        }

    }


//    @Override
//    public void onResume() {
//        super.onResume();
//
//        getCatBannerNew();
//
//    }
}
