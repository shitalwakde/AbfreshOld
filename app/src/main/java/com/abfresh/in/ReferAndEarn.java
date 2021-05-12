package com.abfresh.in;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_MOBILE;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;

public class ReferAndEarn extends AppCompatActivity {
    LinearLayout whatsapp_ll,share_ll;
    TextView referal_code,refer_msg;
    ImageView copy_refer_code,rae_home_btn;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    SessionManagement sessionManagement;
    ProgressBar rae_pb;
    String share_msg_string;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer_earn);
        sessionManagement = new SessionManagement(getApplicationContext());
        Toolbar toolbar=(Toolbar)findViewById(R.id.refer_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        rae_pb = (ProgressBar) findViewById(R.id.rae_pb);
        whatsapp_ll = (LinearLayout)findViewById(R.id.whatsapp_ll);
        share_ll = (LinearLayout)findViewById(R.id.share_ll);
        referal_code = (TextView) findViewById(R.id.referal_code);
        copy_refer_code = (ImageView) findViewById(R.id.copy_refer_code);
        rae_home_btn = (ImageView) findViewById(R.id.rae_home_btn);
        refer_msg = (TextView) findViewById(R.id.refer_msg);

        referal_code.setText(sessionManagement.getUserDetails().get(KEY_MOBILE));
        whatsapp_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = ReferAndEarn.this.getPackageManager();

                try
                {
                    // Raise exception if whatsapp doesn't exist
                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                            + "/drawable/" + "new_logo_yellow");
                    Intent waIntent = new Intent(Intent.ACTION_SEND);
                    waIntent.setType("text/plain");
                    waIntent.setPackage("com.whatsapp");
                    waIntent.putExtra(Intent.EXTRA_TEXT, share_msg_string);
                     startActivity(waIntent);
                }
                catch (PackageManager.NameNotFoundException e)
                {
                    Toast.makeText(ReferAndEarn.this, "Please install whatsapp app", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        share_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody =
                        share_msg_string;
                String shareSub = "Download with this code and save money";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        copy_refer_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                String referalCode =
                        share_msg_string;
                myClip = ClipData.newPlainText("text", referalCode);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Code Copied",
                        Toast.LENGTH_SHORT).show();
            }
        });
        rae_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRandEMessage();
    }

    private void getRandEMessage() {
        JSONObject walletObject = new JSONObject();
        try {
            walletObject.put("user_id",sessionManagement.getUserDetails().get(KEY_USERID));
            Log.w("WTAG","response====>" +walletObject);
            JsonObjectRequest walletRequest = new JsonObjectRequest(Request.Method.POST, Utility.getProfile, walletObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if(response.getInt("success")==1){
                            Log.w("WTAG","response====>" + response);
                            refer_msg.setText(response.getString("display_msg"));
                            share_msg_string = response.getString("share_msg");
                            rae_pb.setVisibility(View.GONE);
                        }else{
//                            wallet_balance_tv.setText("0");
                            rae_pb.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        rae_pb.setVisibility(View.GONE);
//                        myaccount_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    myaccount_pb.setVisibility(View.GONE);
                    rae_pb.setVisibility(View.GONE);
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String,String> header= new HashMap<>();
                    header.put(Utility.ServerUsername,Utility.ServerPassword);
                    return header;
                }
            }; walletRequest.setRetryPolicy(new DefaultRetryPolicy( 10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addRequestInQueue(walletRequest);
        } catch (JSONException e) {
            e.printStackTrace();
            rae_pb.setVisibility(View.GONE);
//            myaccount_pb.setVisibility(View.GONE);

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
