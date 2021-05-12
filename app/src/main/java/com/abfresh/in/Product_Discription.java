package com.abfresh.in;

import androidx.appcompat.app.AppCompatActivity;

public class Product_Discription extends AppCompatActivity {
    //    GlowButton add_to_cart_btn,go_to_cart_btn;
//    SessionManagement sessionManagement;
//    TextView textCartItemCount,pd_price_gross,pd_discount,pd_price,pd_product_name;
//    ProgressBar pd_pb;
//    int mCartItemCount = 2;
//    Toolbar toolbar;
//    TextView wyg_text, sourcing_text,increment_tv_pd,cpd_gross_weight,cpd_net_weight,pd_nop_text,pd_serves_text,wyd_tv,sourcing_tv,pd_product_disc_tv,seemore_tv,seeless_tv;
//    ImageView wyg_line, sourcing_line, lic_adv1, lic_adv2,pdProImage,pd_home_btn,pd_nop_image,pd_serves_image,pd_gw_image,pd_nw_image;
//    Button btn_add_to_cart,btn_outofstock,decrement_btn_pd,increment_btn_pd;
//    String productID;
//    int pdCount=0;
//    private RecyclerView recipe_rv;
//    private RecipeAdapterInsideProDisc recipeAdapterInsideProDisc;
//    private ArrayList<com.tbd.abFresh.model.ArrayList> recipehorizontalList;
//    LinearLayout already_in_cart_ll;
//    RecyclerView yalProRecycleView;
//    ArrayList<com.tbd.abFresh.model.ArrayList> yalProductList;
//    YouAlsoLikeAdapter yalProductAdapter;
//    String userid;
//    Boolean isCheck= true;
//    ExpandableTextView expandableTextView;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.collapsing_product_discription);
////        int productImage = getIntent().getIntExtra("proimage", R.drawable.pop_fish);
//          productID = getIntent().getStringExtra("productId");
////        Toast.makeText(this, productID, Toast.LENGTH_SHORT).show();
////        int imageArra[] = { R.drawable.wiskey1, R.drawable.wiskey1,R.drawable.wiskey1};
////        int imageArra[] = {productImage, productImage, productImage};
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Fade fade = new Fade();
//        View decor = getWindow().getDecorView();
//        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
//        fade.excludeTarget(android.R.id.statusBarBackground, true);
//        fade.excludeTarget(android.R.id.navigationBarBackground, true);
//        getWindow().setEnterTransition(fade);
//        getWindow().setExitTransition(fade);
//        pd_pb = (ProgressBar)findViewById(R.id.pd_pb);
//        pd_pb.setVisibility(View.GONE);
////        getProductDiscription();
//        expandableTextView = new ExpandableTextView(Product_Discription.this);
//        recipe_rv = (RecyclerView) this.findViewById(R.id.recipe_rv);
//        wyg_text = (TextView) this.findViewById(R.id.wyg_txt);
//        sourcing_text = (TextView) this.findViewById(R.id.sourceing_txt);
//        increment_tv_pd = (TextView) this.findViewById(R.id.increment_tv_pd);
//        pdProImage = (ImageView)this.findViewById(R.id.pdProImage);
//        wyg_line = (ImageView) this.findViewById(R.id.wyg_line);
//        sourcing_line = (ImageView) this.findViewById(R.id.sourceing_line);
//        lic_adv1 = (ImageView) this.findViewById(R.id.lic_adv1);
//        lic_adv2 = (ImageView) this.findViewById(R.id.lic_adv2);
//        pd_home_btn = (ImageView) this.findViewById(R.id.pd_home_btn);
//
//        pd_nw_image = (ImageView) this.findViewById(R.id.pd_nw_image);
//        pd_gw_image = (ImageView) this.findViewById(R.id.pd_gw_image);
//        pd_serves_image = (ImageView) this.findViewById(R.id.pd_serves_image);
//        pd_nop_image = (ImageView) this.findViewById(R.id.pd_nop_image);
//
//        btn_add_to_cart = (Button) this.findViewById(R.id.btn_add_to_cart);
//        btn_outofstock = (Button) this.findViewById(R.id.btn_outofstock);
//        decrement_btn_pd = (Button) this.findViewById(R.id.decrement_btn_pd);
//        increment_btn_pd = (Button) this.findViewById(R.id.increment_btn_pd);
//        already_in_cart_ll = (LinearLayout) this.findViewById(R.id.already_in_cart_ll);
//
//
//        yalProRecycleView = (RecyclerView) this.findViewById(R.id.yal_rv);
//        pd_price_gross = (TextView)this.findViewById(R.id.pd_price_gross);
//        pd_price_gross.setPaintFlags(pd_price_gross.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//        pd_discount = (TextView)this.findViewById(R.id.pd_discount);
//        pd_price = (TextView)this.findViewById(R.id.pd_price);
//        pd_product_name = (TextView)this.findViewById(R.id.pd_product_name);
//        cpd_gross_weight = (TextView)this.findViewById(R.id.cpd_gross_weight);
//        cpd_net_weight = (TextView)this.findViewById(R.id.cpd_net_weight);
//
//        pd_nop_text = (TextView)this.findViewById(R.id.pd_nop_text);
//        pd_serves_text = (TextView)this.findViewById(R.id.pd_serves_text);
//        pd_product_disc_tv = (TextView) this.findViewById(R.id.pd_product_disc_tv);
//        wyd_tv = (TextView)this.findViewById(R.id.wyd_tv);
//        sourcing_tv = (TextView)this.findViewById(R.id.sourcing_tv);
//        seemore_tv = (TextView)this.findViewById(R.id.seemore_tv);
//        seeless_tv = (TextView)this.findViewById(R.id.seeless_tv);
//
//        toolbar = (Toolbar) findViewById(R.id.collpase_toolbar);
//        setSupportActionBar(toolbar);
//        // enabling action bar app icon and behaving it as toggle button
//        getSupportActionBar().setTitle("");
////        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        getSupportActionBar().setHomeButtonEnabled(true);
//        lic_adv1.setVisibility(View.VISIBLE);
//        wyd_tv.setVisibility(View.VISIBLE);
////        HashMap<String, String> user = sessionManagement.getUserDetails();
////        userid = user.get(KEY_USERID);
//        sourcing_text.setBackgroundColor(getResources().getColor(R.color.tab_color));
//        wyg_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sourcing_line.setVisibility(View.GONE);
//                lic_adv2.setVisibility(View.GONE);
//                wyd_tv.setVisibility(View.VISIBLE);
//                wyg_text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
//                sourcing_text.setBackgroundColor(getResources().getColor(R.color.tab_color));
//                sourcing_tv.setVisibility(View.GONE);
//                wyg_line.setVisibility(View.VISIBLE);
//                lic_adv1.setVisibility(View.VISIBLE);
//            }
//        });
//
//        sourcing_text.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                wyg_line.setVisibility(View.GONE);
//                lic_adv1.setVisibility(View.GONE);
//                wyd_tv.setVisibility(View.GONE);
//                wyg_text.setBackgroundColor(getResources().getColor(R.color.tab_color));
//                sourcing_text.setBackgroundColor(getResources().getColor(R.color.colorWhite));
//                sourcing_tv.setVisibility(View.VISIBLE);
//                sourcing_line.setVisibility(View.VISIBLE);
//                lic_adv2.setVisibility(View.VISIBLE);
//            }
//        });
//        pd_home_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utility.FromPD=false;
//                finish();
//            }
//        });
//
//
////        pd_product_disc_tv.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                if (isCheck) {
////                    pd_product_disc_tv.setMaxLines(20);
////                    isCheck = false;
////                } else {
////                    pd_product_disc_tv.setMaxLines(3);
////                    isCheck = true;
////                }
////            }
////        });
//
//            seemore_tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (isCheck) {
//                    pd_product_disc_tv.setMaxLines(20);
//                    isCheck = false;
//                        seeless_tv.setVisibility(View.VISIBLE);
//                        seemore_tv.setVisibility(View.GONE);
//                }
//
//                }
//            });
//            seeless_tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(!isCheck){
//                        pd_product_disc_tv.setMaxLines(3);
//                        isCheck = true;
//                        seeless_tv.setVisibility(View.GONE);
//                        seemore_tv.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
////        getBtnIdentity();
////----------------------------------------------------Recipe Start------------------------------------------------
//
//
//        recipehorizontalList = new ArrayList<com.tbd.abFresh.model.ArrayList>();
//        for (int i = 0; i < MyData.baProductPic.length; i++) {
//            recipehorizontalList.add(new com.tbd.abFresh.model.ArrayList(
//                    MyData.baProductPic[i],
//                    MyData.baProductName[i],
//                    MyData.recipeProductdisc[i]
//            ));
//        }
//        recipeAdapterInsideProDisc = new RecipeAdapterInsideProDisc(recipehorizontalList);
//        final LinearLayoutManager recipe
//                = new LinearLayoutManager(Product_Discription.this, LinearLayoutManager.HORIZONTAL, false);
//        recipe_rv.setNestedScrollingEnabled(false);
//        recipe_rv.setLayoutManager(recipe);
//        recipe_rv.setAdapter(recipeAdapterInsideProDisc);
//
//        recipe_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                super.onScrolled(recyclerView, dx, dy);
//                int firstItemVisible = recipe.findFirstVisibleItemPosition();
//                if (firstItemVisible != 0 && firstItemVisible % recipehorizontalList.size() == 0) {
//                    recyclerView.getLayoutManager().scrollToPosition(0);
//                }
//            }
//        });
////----------------------------------------------------Recipe End------------------------------------------------
////----------------------------------------------------You may also like start------------------------------------------------
//
//        yalProductList = new ArrayList<com.tbd.abFresh.model.ArrayList>();
//        for (int i = 0; i < MyData.fragProductName.length; i++) {
//            yalProductList.add(new com.tbd.abFresh.model.ArrayList(
//                    MyData.fragProductName[i],
//                    MyData.fragProductPrice[i],
//                    MyData.fragProductPic[i]
//
//            ));
//        }
//        yalProductAdapter = new YouAlsoLikeAdapter(yalProductList);
//        final LinearLayoutManager youAlsoLike
//                = new LinearLayoutManager(Product_Discription.this, LinearLayoutManager.HORIZONTAL, false);
//        yalProRecycleView.setLayoutManager(youAlsoLike);
//        yalProRecycleView.setAdapter(yalProductAdapter);
//        yalProRecycleView.setNestedScrollingEnabled(false);
//
//
//        yalProRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                super.onScrolled(recyclerView, dx, dy);
//                int firstItemVisible = youAlsoLike.findFirstVisibleItemPosition();
//                if (firstItemVisible != 0 && firstItemVisible % yalProductList.size() == 0) {
//                    recyclerView.getLayoutManager().scrollToPosition(0);
//                }
//            }
//        });
//
//
//        yalProductAdapter.setOnItemClickListner(new YouAlsoLikeAdapter.OnItemClickListner() {
//            @Override
//            public void onCatProductClick(int position) {
//
////                Intent intent = new Intent(Product_Discription.this, Product_Discription.class);
////                intent.putExtra("proimage", MyData.fragProductPic[position]);
////                String price = MyData.fragProductPrice[position];
////                intent.putExtra("proprice", price);
////                startActivity(intent);
////                yalProductAdapter.notifyDataSetChanged();
//            }
//        });
//
////----------------------------------------------------You may also like End------------------------------------------------
//
//        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int number =1;
//                        addToCartPD(number);
//
//            }
//        });
//        decrement_btn_pd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                    int number = Integer.parseInt(increment_tv_pd.getText().toString()) ;
//                    number = number -1;
//
//                    if(number==0){
//                        btn_add_to_cart.setVisibility(View.VISIBLE);
//                        already_in_cart_ll.setVisibility(View.GONE);
//                        removeFromCartPD(number);
//                    }else{
//                        String NewNumber = String.valueOf(number);
////                        Toast.makeText(Product_Discription.this,NewNumber, Toast.LENGTH_SHORT).show();
//                        increment_tv_pd.setText(String.valueOf(number));
//                        addToCartPD(number);
//                    }
//
//
//            }
//        });
//
//        increment_btn_pd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int number = Integer.parseInt(increment_tv_pd.getText().toString()) ;
//                number = number +1;
//                String NewNumber = String.valueOf(number);
//                Toast.makeText(Product_Discription.this,NewNumber, Toast.LENGTH_SHORT).show();
//                increment_tv_pd.setText(String.valueOf(number));
//                addToCartPD(number);
//            }
//        });
//    }
//
//    private void removeFromCartPD(int number) {
//        try {
//            JSONObject rcobject = new JSONObject();
//
//            String tempid= Settings.Secure.getString(Product_Discription.this.getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
//            rcobject.put("temp_user_id",tempid);
//            rcobject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
//            rcobject.put(  "product_id",productID);
//            Log.w("PDTAG", "JSONObject rcobject" + rcobject);
//            JsonObjectRequest rcRequest = new JsonObjectRequest(Request.Method.POST, Utility.RemoveFromCart, rcobject, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        if ((response.getString("success")).equals("1")){
//                            Toast.makeText(Product_Discription.this,response.getString("message"), Toast.LENGTH_SHORT).show();
//                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
//                            int cartcount = Integer.parseInt(response.getString("cart_count"));
//                            invalidateOptionsMenu();
//                            getProductDiscription();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }){
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String,String> header = new HashMap<>();
//                    header.put(Utility.ServerUsername,Utility.ServerPassword);
//                    return header;
//                }
//            }; rcRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            AppController.getInstance().addRequestInQueue(rcRequest);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    private void addToCartPD(int number) {
//        try {
//            pd_pb.setVisibility(View.VISIBLE);
//            sessionManagement = new SessionManagement(getApplicationContext());
//
//            JSONObject pdJsonObjectnew = new JSONObject();
//            String tempid= Settings.Secure.getString(getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
//            pdJsonObjectnew.put("temp_user_id",tempid);
//            pdJsonObjectnew.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
//            pdJsonObjectnew.put("product_id",productID);
//            pdJsonObjectnew.put("qty",number);
//            pdJsonObjectnew.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
//            pdJsonObjectnew.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
//
//            Log.w("PDTAG","JSONObject pdCartObject"+pdJsonObjectnew);
//            JsonObjectRequest pdRequestnew = new JsonObjectRequest(Request.Method.POST, Utility.AddToCart, pdJsonObjectnew, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.w("PDTAG","JSONObject response"+response);
//
//                    try {
//                        if ((response.getString("success")).equals("1")){
////                                    Utility.CartCount = ++Utility.CartCount ;
//
//
//                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
//                            textCartItemCount.setText(response.getString("cart_count"));
//                            invalidateOptionsMenu();
////                            StyleableToast.makeText(Product_Discription.this, response.getString("message"), R.style.mySizeToast).show();
//                            getProductDiscription();
//                            pd_pb.setVisibility(View.GONE);
//
//                        }else{
//                            pd_pb.setVisibility(View.GONE);
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        pd_pb.setVisibility(View.GONE);
//                        StyleableToast.makeText(Product_Discription.this,"Please try after some time", R.style.mySizeToast).show();
//
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    pd_pb.setVisibility(View.GONE);
//                    StyleableToast.makeText(Product_Discription.this,"Please try after some time", R.style.mySizeToast).show();
//
//                }
//            }){
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String,String> header = new HashMap<>();
//                    header.put(Utility.ServerUsername,Utility.ServerPassword);
//                    return header;
//                }
//            }; pdRequestnew.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            AppController.getInstance().addRequestInQueue(pdRequestnew);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            pd_pb.setVisibility(View.GONE);
//            StyleableToast.makeText(Product_Discription.this,"Please try after some time", R.style.mySizeToast).show();
//
//        }
//    }
//
////    private void getBtnIdentity() {
////        if (Utility.BtnPD){
////            btn_add_to_cart.setBackgroundColor(Color.LTGRAY);
////            btn_add_to_cart.setTextColor(Color.GRAY);
////            btn_add_to_cart.setEnabled(false);
////        }
////    }
//
//    private void getProductDiscription() {
//        try {
//            pd_pb.setVisibility(View.VISIBLE);
//        JSONObject pdObject = new JSONObject();
//            sessionManagement = new SessionManagement(getApplicationContext());
//            String tempid= Settings.Secure.getString(getContentResolver(),
//                    Settings.Secure.ANDROID_ID);
//            pdObject.put("temp_user_id",tempid);
//            pdObject.put( "user_id", sessionManagement.getUserDetails().get(KEY_USERID));
//            pdObject.put(  "product_id",productID);
//            pdObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
//            pdObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
//            Log.w("PDTAG", "response pdObject "+ pdObject);
//            JsonObjectRequest pdRequest = new JsonObjectRequest(Request.Method.POST,Utility.ProductDetails,pdObject,new Response.Listener<JSONObject>(){
//                @Override
//                public void onResponse(JSONObject response) {
//                    try {
//                        Log.w("PDTAG", "response response "+response);
////
//                        if ((response.getString("success")).equals("1")){
//                            String productPrice,Productgross,ProductDiscount,productName,Productgrosswt,Productnetwt;
//                            productName =  response.getString("product_name");
//                            productPrice = "₹"+ response.getString("product_amt");
//                            Productgross = "₹"+ response.getString("product_gross_amt");
//                            ProductDiscount = response.getString("product_discount")+"% Off";
//                            Productgrosswt = response.getString("product_gross_weight");
//                            Productnetwt = response.getString("product_weight");
//
//
//
//                            if(response.getInt("product_stock_qty")==0){
//                                btn_add_to_cart.setVisibility(View.GONE);
//                                btn_outofstock.setVisibility(View.VISIBLE);
//                            }else {
//                                if (response.getString("is_in_cart").equals("No")){
//                                    btn_add_to_cart.setVisibility(View.VISIBLE);
//                                    already_in_cart_ll.setVisibility(View.GONE);
//                                }else {
//                                    btn_add_to_cart.setVisibility(View.GONE);
//                                    already_in_cart_ll.setVisibility(View.VISIBLE);
//                                }
//
//                                btn_outofstock.setVisibility(View.GONE);
//                            }
////                            Productgrosswt = response.getString("product_discount");
////                            Productnetwt = response.getString("product_discount");
//                            Utility.CartCount = Integer.parseInt(response.getString("cart_count"));
//                            setupBadge();
//                            Log.w("PDTAG", "Utility.CartCount ===>inside "+ Utility.CartCount);
//
//                            pd_price.setText(productPrice);
//                            pd_product_name.setText(productName);
//                            pd_price_gross.setText(Productgross);
//                            pd_discount.setText(ProductDiscount);
//                            cpd_gross_weight.setText(Productgrosswt);
//                            cpd_net_weight.setText(Productnetwt);
//
//
//                            if(response.getString("product_gross_amt").equals("0")){
//                                pd_price_gross.setVisibility(View.GONE);
//                                pd_discount.setVisibility(View.GONE);
//                            }else{
//                                pd_price_gross.setVisibility(View.VISIBLE);
//                                pd_discount.setVisibility(View.VISIBLE);
//                            }
//                            pd_product_disc_tv.setText(response.getString("product_description"));
//
//                            if(pd_product_disc_tv.getMaxLines()<=2){
//                                seemore_tv.setVisibility(View.GONE);
//                            }else{
//                                seemore_tv.setVisibility(View.VISIBLE);
//
//                            }
//                            Picasso.with(Product_Discription.this).load(response.getString("product_image")).fit().into(pdProImage);
//                            Animation animation2 = AnimationUtils.loadAnimation(Product_Discription.this, R.anim.fade_image);
//                            pdProImage.startAnimation(animation2);
//                          if(response.getString("cart_qty").equals("")){
//                              increment_tv_pd.setText("1");
//
//                          }else{
//                              increment_tv_pd.setText(response.getString("cart_qty"));
//
//                          }
//                            pd_pb.setVisibility(View.GONE);
//
//                        }else{
//                            pd_pb.setVisibility(View.GONE);
//                            StyleableToast.makeText(Product_Discription.this,"Please try after some time", R.style.mySizeToast).show();
//
//                        }
//                        if(response.getString("no_of_pieces").length()==0){
//                            pd_nop_text.setText("0");
//                        }else{
//                            pd_nop_text.setText(response.getString("no_of_pieces"));
//                        }
//
//                        if(response.getString("serves").length()==0){
//                            pd_serves_text.setText("0");
//                        }else{
//                            pd_serves_text.setText(response.getString("serves"));
//                        }
//
//                        wyd_tv.setText(response.getString("what_you_get"));
//                        if(response.getString("what_you_get_image").length()!=0){
//                            Picasso.with(Product_Discription.this).load(response.getString("what_you_get_image")).fit().centerCrop().into(lic_adv1);
//
//                        }
//                        sourcing_tv.setText(response.getString("sourcing"));
//                        if(response.getString("sourcing_image").length()!=0) {
//                            Picasso.with(Product_Discription.this).load(response.getString("sourcing_image")).fit().centerCrop().into(lic_adv2);
//                        }
//                        Log.w("PDTAG", "what_you_get_image===>inside "+ response.getString("what_you_get_image"));
//                        Log.w("PDTAG", "what_you_get_image===>inside "+ response.getString("sourcing_image"));
//
////                        pd_nop_text,pd_serves_text
//                        Picasso.with(Product_Discription.this).load(response.getString("no_of_pieces_icon")).fit().into(pd_nop_image);
//                        Picasso.with(Product_Discription.this).load(response.getString("serves_icon")).fit().into(pd_serves_image);
//                        Picasso.with(Product_Discription.this).load(response.getString("gross_weight_icon")).fit().into(pd_gw_image);
//                        Picasso.with(Product_Discription.this).load(response.getString("net_weight_icon")).fit().into(pd_nw_image);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        pd_pb.setVisibility(View.GONE);
//                        StyleableToast.makeText(Product_Discription.this,"Please try after some time", R.style.mySizeToast).show();
//
//                    }
//
//                }
//            },new Response.ErrorListener(){
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    pd_pb.setVisibility(View.GONE);
//
//                }
//            }){
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
//                    return headers;
//                }
//            };pdRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
//                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//            AppController.getInstance().addRequestInQueue(pdRequest);
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//            pd_pb.setVisibility(View.GONE);
//
//        }
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//
//        getMenuInflater().inflate(R.menu.product_disc_menu, menu);
//
//        final MenuItem menuItem = menu.findItem(R.id.pro_disc_cart);
//        View actionView = menuItem.getActionView();
//        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge_pd);
////        getProductDiscription();
//
//        setupBadge();
//
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
//            }
//        });
////        invalidateOptionsMenu();
//        return true;
//
//
//    }
//
//    private void setupBadge() {
//
//        if (textCartItemCount != null) {
//            Log.w("PDTAG", "Utility.CartCount ===>outside "+ Utility.CartCount);
//
//            if (Utility.CartCount==0) {
//                if (textCartItemCount.getVisibility() != View.GONE) {
//                    textCartItemCount.setVisibility(View.GONE);
//                }
//            } else {
//                textCartItemCount.setText(String.valueOf(Utility.CartCount));
////                Utility.CartCount = String.valueOf(tabCartCount);
//                if (textCartItemCount.getVisibility() != View.VISIBLE) {
//                    textCartItemCount.setVisibility(View.VISIBLE);
//                }
//            }
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//
//            case R.id.pro_disc_cart:
//
//                if (Utility.CartCount==0){
//                    Toast.makeText(this, "Zero items in chart", Toast.LENGTH_SHORT).show();
//                } else{
//                    Intent intent = new Intent(Product_Discription.this,CartDiscription.class);
//                    Utility.FromTabClass=false;
//                    Utility.FromMainClass=false;
//                    Utility.FromPD=true;
//                    startActivity(intent);
//                }
//
//                return true;
//
////            case android.R.id.home:
////                Utility.FromPD=false;
////                finish();
////                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }
//
//    @Override
//    public void onBackPressed() {
////        ((TabActivity)getApplicationContext()).invalidateOptionsMenu();
////        invalidateOptionsMenu();
////        ((Category_Product_Fragment)getBaseContext()).recreate();
//        Utility.FromPD=false;
//        super.onBackPressed();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        invalidateOptionsMenu();
//        getProductDiscription();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        invalidateOptionsMenu();
//    }
}
