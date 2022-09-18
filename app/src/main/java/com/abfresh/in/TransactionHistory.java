package com.abfresh.in;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.muddzdev.styleabletoast.StyleableToast;
import com.abfresh.in.Adapter.TransactionHistoryAdapter;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Model.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class TransactionHistory extends AppCompatActivity {
    RecyclerView th_rv;
    TransactionHistoryAdapter th_Adapter;
    SessionManagement sessionManagement;
    java.util.ArrayList<ArrayList> ThList;
    TextView tv_toolbar_title;
    ImageView iv_back_arrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_history);
        sessionManagement = new SessionManagement(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar_new);
        setSupportActionBar(toolbar);
        tv_toolbar_title=(TextView)findViewById(R.id.tv_toolbar_title);
        tv_toolbar_title.setText("Transaction History");

        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);
        th_rv = (RecyclerView)findViewById(R.id.th_rv);

        iv_back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getTransactionHistory();

    }

    private void getTransactionHistory() {
        JSONObject transHisObject = new JSONObject();
        try {
            transHisObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            Log.w("LTAG","transHisObject Details====>" +transHisObject);
            JsonObjectRequest transHisRequest = new JsonObjectRequest(Request.Method.POST, Utility.WalletTransaction, transHisObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("LTAG","Login response====>" +response);

                        if((response.getString("success")).equals("1")){
                            ThList = new java.util.ArrayList<>();
                            JSONArray jsonArrayTh = response.getJSONArray("wallet_list");
                            for(int i=0;i<jsonArrayTh.length();i++){
                                JSONObject jsonObjectTH = jsonArrayTh.getJSONObject(i);

                                String payAmount = jsonObjectTH.getString("amount");
                                String payType = jsonObjectTH.getString("type");
                                String payName = jsonObjectTH.getString("description");
                                String payTime = jsonObjectTH.getString("transaction_time");
                                ThList.add(new ArrayList(payAmount,payType,payName,payTime));
                            }
                            th_Adapter = new TransactionHistoryAdapter(getApplicationContext(), ThList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TransactionHistory.this);
                            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            th_rv.setLayoutManager(linearLayoutManager);
                            th_rv.setAdapter(th_Adapter);

                        }else{
//                            loginPb.setVisibility(View.GONE);
                            StyleableToast.makeText(TransactionHistory.this,"Please try after some time", R.style.mySizeToast).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("LTAG","Login JSONException====>" +e);
//                        loginPb.setVisibility(View.GONE);
                        StyleableToast.makeText(TransactionHistory.this,"Please try after some time", R.style.mySizeToast).show();

                    }

                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.w("LTAG","Login VolleyError====>" +error);
//                    loginPb.setVisibility(View.GONE);
                    StyleableToast.makeText(TransactionHistory.this,"Please try after some time", R.style.mySizeToast).show();

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put(Utility.ServerUsername,Utility.ServerPassword);
                    return headers;
                }
            }; transHisRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(transHisRequest);
        } catch (JSONException e) {
            e.printStackTrace();
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
