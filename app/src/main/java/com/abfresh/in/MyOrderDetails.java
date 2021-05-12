package com.abfresh.in;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Adapter.MyOrderDetailsAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.CartList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class MyOrderDetails extends AppCompatActivity {

    TextView mod_order_amount,mod_delivery_date,mod_order_status,mod_add,mod_order_date,mod_delivery_slot,mod_purchase_amount
            ,mod_delivery_charges,mod_gst,mod_wallet_used,mod_total_amount,mod_payment_type;
    RecyclerView my_order_details_rv;
    SessionManagement sessionManagement;
    Toolbar modToolbar;
    String myOrderId;
    ArrayList<CartList> mOrderDetailLists;
    MyOrderDetailsAdapter myOrderDetailsAdapter;
    ProgressBar myorderdetail_pb;
    ImageView mod_home_btn;
    ImageView order_placed_iv,order_process_iv,order_out_iv,order_delivered_iv;
    TextView order_placed_line_tv,order_process_line_tv,order_out_line_tv,order_place_tv;
    TextView order_process_tv,order_out_tv,order_deliverd_tv;
    LinearLayout order_animation_ll;
    RelativeLayout cancel_rl;
    LinearLayout cancel_btn_mod;
    TextView track_sum_tv,mod_discount;
    Button view_pdf_btn;
     String invoice_url;
    TextView order_place_message_tv,order_process_message_tv,order_out_message_tv,order_delivered_message_tv,mod_payment_status;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_details);
        sessionManagement = new SessionManagement(getApplicationContext());
        modToolbar =  (Toolbar)findViewById(R.id.myorderdetails_toolbar);
        setSupportActionBar(modToolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        mod_order_amount = (TextView)findViewById(R.id.mod_order_amount);
        mod_delivery_date = (TextView)findViewById(R.id.mod_delivery_date);
        mod_order_status = (TextView)findViewById(R.id.mod_order_status);
        mod_discount = (TextView)findViewById(R.id.mod_discount);
        view_pdf_btn = (Button) findViewById(R.id.view_pdf_btn);

        order_placed_line_tv = (TextView)findViewById(R.id.order_placed_line_tv);
        order_process_line_tv = (TextView)findViewById(R.id.order_process_line_tv);
        order_out_line_tv = (TextView)findViewById(R.id.order_out_line_tv);
        order_place_tv = (TextView)findViewById(R.id.order_place_tv);
        order_process_tv = (TextView)findViewById(R.id.order_process_tv);
        order_out_tv = (TextView)findViewById(R.id.order_out_tv);
        order_deliverd_tv = (TextView)findViewById(R.id.order_deliverd_tv);
        order_placed_iv = (ImageView) findViewById(R.id.order_placed_iv);
        order_process_iv = (ImageView) findViewById(R.id.order_process_iv);
        order_out_iv = (ImageView) findViewById(R.id.order_out_iv);
        order_delivered_iv = (ImageView) findViewById(R.id.order_delivered_iv);
        order_animation_ll = (LinearLayout) findViewById(R.id.order_animation_ll);

        mod_add = (TextView)findViewById(R.id.mod_add);
        mod_order_date = (TextView)findViewById(R.id.mod_order_date);
        mod_delivery_slot = (TextView)findViewById(R.id.mod_delivery_slot);
        mod_purchase_amount = (TextView)findViewById(R.id.mod_purchase_amount);
        mod_delivery_charges = (TextView)findViewById(R.id.mod_delivery_charges);
        mod_gst = (TextView)findViewById(R.id.mod_gst);
        mod_wallet_used = (TextView)findViewById(R.id.mod_wallet_used);
        mod_total_amount = (TextView)findViewById(R.id.mod_total_amount);
        mod_payment_type = (TextView)findViewById(R.id.mod_payment_type);
        mod_payment_status = (TextView)findViewById(R.id.mod_payment_status);
        track_sum_tv = (TextView)findViewById(R.id.track_sum_tv);

        order_place_message_tv = (TextView)findViewById(R.id.order_place_message_tv);
        order_process_message_tv = (TextView)findViewById(R.id.order_process_message_tv);
        order_out_message_tv = (TextView)findViewById(R.id.order_out_message_tv);
        order_delivered_message_tv = (TextView)findViewById(R.id.order_delivered_message_tv);
        cancel_btn_mod = (LinearLayout) findViewById(R.id.cancel_btn_mod);
        cancel_rl = (RelativeLayout) findViewById(R.id.cancel_rl);

        my_order_details_rv = (RecyclerView) findViewById(R.id.my_order_details_rv);
        myorderdetail_pb = (ProgressBar)findViewById(R.id.myorderdetail_pb);
        mod_home_btn = (ImageView) findViewById(R.id.mod_home_btn);
        myOrderId = getIntent().getStringExtra("orderId");
        getOrderDetails();

        mod_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cancel_btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCancelOrder();
            }
        });

        view_pdf_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyOrderDetails.this,MyInvoice.class);
                intent.putExtra("myUrl",invoice_url);
                startActivity(intent);
//                if(invoice_url.trim().length()!=0){
//
//                    Uri uri = Uri.parse(invoice_url); // missing 'http://' will cause crashed
////                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.setDataAndType(uri, "application/pdf");
//                    try{
//                        startActivity(intent);
//                    }catch (ActivityNotFoundException e){
//
//                    }
//
//
////                    Intent intent = new Intent(MyOrderDetails.this,InvoicesWebView.class);
////                    intent.putExtra("invoice_url_new",invoice_url);
////                    startActivity(intent);
////                    Intent intent = new Intent(Intent.ACTION_VIEW);
////
////                    intent.setDataAndType(Uri.parse("https://www.tutorialspoint.com/java/java_tutorial.pdf"), "text/html");
////
////                    startActivity(intent);
//                }else{
//
//                    Toast.makeText(MyOrderDetails.this, "No PDF Found", Toast.LENGTH_SHORT).show();
//                }

            }
        });
    }

    private void getCancelOrder() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Alert!");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you really want to cancel an order?");
        //This will not allow to close dialogbox until user selects an option
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                Toast.makeText(this, "positive button", Toast.LENGTH_SHORT).show();

                try {
                    JSONObject coObject = new JSONObject();
                    String orderId = getIntent().getStringExtra("orderId");
                    coObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
                    coObject.put("reason","No Data");
                    coObject.put("order_id",orderId);
                    Log.w("COTAG===>coObject",coObject+"");
                    JsonObjectRequest cojsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Utility.CancelledOrder, coObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getInt("success")==1){

                                    Toast.makeText(MyOrderDetails.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                    finish();
                                    dialog.cancel();

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
                    };cojsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addRequestInQueue(cojsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //  Action for 'NO' Button
//                Toast.makeText(this, "negative button", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        //alert.setTitle("AlertDialogExample");
        alert.show();
        }

    private void getOrderDetails() {


        try {
            myorderdetail_pb.setVisibility(View.VISIBLE);
            JSONObject odObject = new JSONObject();
            odObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
            odObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
            odObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            odObject.put("order_id",getIntent().getStringExtra("orderId"));
            Log.w("MODTAG===>odObject",odObject+"");
            JsonObjectRequest odRequest = new JsonObjectRequest(Request.Method.POST, Utility.MyOrderDetails, odObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.w("MODTAG===>response",response+"");

                    try {
                        if(response.getInt("success")==1){

                            mod_order_status.setText(response.getString("order_status")) ;


                            String order_status = response.getString("order_status");
                            if(order_status.trim().equals("Pending")){
//                                view_pdf_btn.setVisibility(View.GONE);
                                track_sum_tv.setVisibility(View.VISIBLE);
                                mod_order_status.setTextColor(MyOrderDetails.this.getResources().getColor(R.color.colorGreen));
//                                mod_order_status.setBackground(MyOrderDetails.this.getResources().getDrawable(R.drawable.container_btn_bg_new));
                            }else if(order_status.trim().equals("Processing")){
//                                view_pdf_btn.setVisibility(View.GONE);
//                                holder.mo_cancel_order.setVisibility(View.GONE);
                                track_sum_tv.setVisibility(View.VISIBLE);
                                mod_order_status.setTextColor(MyOrderDetails.this.getResources().getColor(R.color.white));
//                                mod_order_status.setBackground(MyOrderDetails.this.getResources().getDrawable(R.drawable.container_btn_bg_new));
                            }else if(order_status.trim().equals("Delivered")){
//                                view_pdf_btn.setVisibility(View.VISIBLE);
                                cancel_rl.setVisibility(View.GONE);
                                track_sum_tv.setVisibility(View.VISIBLE);
                                mod_order_status.setTextColor(MyOrderDetails.this.getResources().getColor(R.color.newYellow));
//                                mod_order_status.setBackground(MyOrderDetails.this.getResources().getDrawable(R.drawable.container_btn_bg_new));
                            }else{
//                                view_pdf_btn.setVisibility(View.GONE);
                                cancel_rl.setVisibility(View.GONE);
                                track_sum_tv.setVisibility(View.GONE);
                                mod_order_status.setTextColor(MyOrderDetails.this.getResources().getColor(R.color.colorPrimary));
//                                mod_order_status.setBackground(MyOrderDetails.this.getResources().getDrawable(R.drawable.container_btn_bg_new));
                            }
                           mod_wallet_used.setText("₹"+response.getString("wallet_pay"));
                            mod_payment_type.setText(response.getString("order_payment_type_short"));
                            mod_total_amount.setText("₹"+response.getString("order_amount"));
                            mod_order_amount.setText("₹"+response.getString("gross_amount")) ;
//                            mod_delivery_date2.setText(response.getString("delivery_date"));
                            mod_purchase_amount.setText("₹"+response.getString("gross_amount"));
                            mod_discount.setText("₹"+response.getString("discount"));
                            mod_delivery_charges.setText("₹"+response.getString("delivery_charge"));
                            mod_payment_status.setText(response.getString("payment_status")) ;
                            mod_gst.setText("₹"+response.getString("tax"));

                            if(response.getString("delivery_date").equals("")){
                                mod_delivery_date.setText("null");
                            }else{
                                mod_delivery_date.setText(response.getString("delivery_date")) ;

                            }
                            mod_order_date.setText(response.getString("order_date"));
                            mod_delivery_slot.setText(response.getString("delivery_slot"));


                            String delivery_location_city_name = response.getString("delivery_location_city_name");
                            String delivery_location_state_name = response.getString("delivery_location_state_name");
                            String delivery_username = response.getString("delivery_location_person_name");
                            String delivery_location_user_mobile = response.getString("delivery_location_person_mobile");
                            String delivery_location_pincode = response.getString("delivery_location_person_pincode");
                            String delivery_location_house_no = response.getString("delivery_location_person_house_no");
                            String delivery_location_area = response.getString("delivery_location_person_area");
                            String  order_add = delivery_username +","+ delivery_location_house_no
                                    +","+ delivery_location_area+","+ delivery_location_state_name+","+ delivery_location_city_name
                                    +","+  delivery_location_pincode+", M.No :"+delivery_location_user_mobile + "";

                            mod_add.setText(order_add) ;


                            JSONArray modArray = response.getJSONArray("order_detail_list");
                            mOrderDetailLists = new ArrayList<>();
                            for(int i=0;i<modArray.length();i++){
                                JSONObject modNewObject = modArray.getJSONObject(i);

                                String product_name = modNewObject.getString("product_name");
                                String product_image = modNewObject.getString("product_image");
                                String product_weight = modNewObject.getString("product_weight")+modNewObject.getString("product_type");
                                String product_total_amt = modNewObject.getString("amt");
                                String product_gross_amt = modNewObject.getString("product_gross_amt");
                                String product_discount = modNewObject.getString("product_discount");
                                String qty = modNewObject.getString("qty");

                                mOrderDetailLists.add(new CartList(product_name,product_image,product_weight,product_total_amt,product_gross_amt,product_discount,qty));

                            }
                            myOrderDetailsAdapter = new MyOrderDetailsAdapter(getApplicationContext(), mOrderDetailLists);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyOrderDetails.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            my_order_details_rv.setLayoutManager(linearLayoutManager);
                            my_order_details_rv.setAdapter(myOrderDetailsAdapter);
                            my_order_details_rv.setNestedScrollingEnabled(false);
                            myorderdetail_pb.setVisibility(View.GONE);

//                            ImageView order_placed_iv,order_process_iv,order_out_iv,order_delivered_iv;
//                            TextView order_placed_line_tv,order_process_line_tv,order_out_line_tv,order_place_tv;
//                            TextView order_process_tv,order_out_tv,order_deliverd_tv;

                            if(response.getString("place_order").equals("Yes")){
                                order_animation_ll.setVisibility(View.VISIBLE);
//                                view_pdf_btn.setVisibility(View.VISIBLE);
                            }else{
                                order_animation_ll.setVisibility(View.GONE);
//                                view_pdf_btn.setVisibility(View.GONE);
                            }
                             if(response.getString("order_process").equals("Yes")){
//                                order_animation_ll.setVisibility(View.VISIBLE);
                                order_process_tv.setTextColor(getResources().getColor(R.color.newYellow));
                                order_placed_line_tv.setBackgroundColor(getResources().getColor(R.color.newYellow));
                                order_process_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_new));
                            }
                             String order_place_message = response.getString("place_order_msg");
                             String order_process_message = response.getString("order_process_msg");
                             String order_out_message = response.getString("out_of_delivery_msg");
                             String order_deliverd_message = response.getString("deliverd_msg");

                             if(!(order_place_message.trim().length()==0)){
                                 order_place_message_tv.setText( response.getString("place_order_msg"));
                                 order_place_message_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_order_details_orderbg));

                             }
                             if(!(order_process_message.trim().length()==0)){
                                 order_process_message_tv.setText( response.getString("order_process_msg"));
                                 order_process_message_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_order_details_orderbg));
                            }
                             if(!(order_out_message.trim().length()==0)){
                                 order_out_message_tv.setText( response.getString("out_of_delivery_msg"));
                                 order_out_message_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_order_details_orderbg));
                            }
                             if(!(order_deliverd_message.trim().length()==0)){
                                 order_delivered_message_tv.setText( response.getString("deliverd_msg"));
                                 order_delivered_message_tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.my_order_details_orderbg));
                            }
                             if(response.getString("out_of_delivery").equals("Yes")){
//                                order_animation_ll.setVisibility(View.VISIBLE);
                                order_process_tv.setTextColor(getResources().getColor(R.color.newYellow));
                                order_placed_line_tv.setBackgroundColor(getResources().getColor(R.color.newYellow));
                                order_process_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_new));

                                order_out_tv.setTextColor(getResources().getColor(R.color.newYellow));
                                order_process_line_tv.setBackgroundColor(getResources().getColor(R.color.newYellow));
                                order_out_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_new));
                            }
                             if(response.getString("deliverd").equals("Yes")){
//                                order_animation_ll.setVisibility(View.VISIBLE);

                                order_process_tv.setTextColor(getResources().getColor(R.color.newYellow));
                                order_placed_line_tv.setBackgroundColor(getResources().getColor(R.color.newYellow));
                                order_process_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_new));

                                order_out_tv.setTextColor(getResources().getColor(R.color.newYellow));
                                order_process_line_tv.setBackgroundColor(getResources().getColor(R.color.newYellow));
                                order_out_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_new));

                                order_deliverd_tv.setTextColor(getResources().getColor(R.color.newYellow));
                                order_out_line_tv.setBackgroundColor(getResources().getColor(R.color.newYellow));
                                order_delivered_iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_check_new));
                            }
                            invoice_url="";
                            invoice_url = response.getString("invoice_link");
                            if(invoice_url.trim().length()==0){
                                view_pdf_btn.setVisibility(View.GONE);
                            }else{
                                view_pdf_btn.setVisibility(View.VISIBLE);
                            }
                        }else{
                            Toast.makeText(MyOrderDetails.this, "Please try after some time", Toast.LENGTH_SHORT).show();
                            myorderdetail_pb.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        myorderdetail_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    myorderdetail_pb.setVisibility(View.GONE);

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header = new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            };
            odRequest.setRetryPolicy(new DefaultRetryPolicy(1000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(odRequest);

        } catch (JSONException e) {
            e.printStackTrace();
            myorderdetail_pb.setVisibility(View.GONE);

        }
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//        if(id == android.R.id.home){
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
