package com.abfresh.in;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.abfresh.in.Adapter.NotificationAdapter;
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

public class Notification extends AppCompatActivity {

    private RecyclerView notification_msg_rv;
    RelativeLayout noti_rl;
    private NotificationAdapter notificationAdapter;
    private java.util.ArrayList<ArrayList> notificationList;
    SessionManagement sessionManagement;
    ProgressBar noti_pb;
    ImageView noti_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        sessionManagement = new SessionManagement(getApplicationContext());
        Toolbar toolbar=(Toolbar)findViewById(R.id.notification_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        notification_msg_rv = (RecyclerView)findViewById(R.id.notification_msg_rv);
        noti_rl = (RelativeLayout)findViewById(R.id.noti_rl);
        noti_pb = (ProgressBar) findViewById(R.id.noti_pb);
        noti_home_btn = (ImageView) findViewById(R.id.noti_home_btn);
//
//
//        notificationList = new java.util.ArrayList<ArrayList>();
//        for (int i = 0; i < MyData.notificationHeading.length; i++) {
//            notificationList.add(new com.tbd.abFresh.model.ArrayList(
//                    MyData.notificationHeading[i],
//                    MyData.notificationMsg[i]
//            ));
//
//        }
//        notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationList);
//        final GridLayoutManager manager
//                = new GridLayoutManager(Notification.this,1);
//
//
//        notification_msg_rv.setLayoutManager(manager);
//        notification_msg_rv.setAdapter(notificationAdapter);

        getNotification();

        noti_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getNotification() {

        try {
            sessionManagement = new SessionManagement(Notification.this);
            JSONObject tabJsonObjectNew = new JSONObject();
            String tempid = Settings.Secure.getString(Notification.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
            tabJsonObjectNew.put("temp_user_id", tempid);
            tabJsonObjectNew.put("user_id", sessionManagement.getUserDetails().get(KEY_USERID));

            Log.w("NOTAG", "response tabJsonObjectNew " + tabJsonObjectNew);


            JsonObjectRequest tabRequestNew = new JsonObjectRequest(Request.Method.POST, Utility.Notifications, tabJsonObjectNew, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.w("NOTAG", "response New " + response);
                        notificationList = new java.util.ArrayList<ArrayList>();

                        if ((response.getInt("success"))==1) {
                            noti_rl.setVisibility(View.GONE);
                            JSONArray tabArray = response.getJSONArray("notifications_list");

                            for (int i = 0; i < tabArray.length(); i++) {
                                JSONObject tabobject = tabArray.getJSONObject(i);
                                String notificationTitle = tabobject.getString("title");
                                String notificationDisc = tabobject.getString("decription");
                                String notificationImg= tabobject.getString("image");
//                                Log.w("NOTAG", "Image  " + imageName);

                                notificationList.add(new com.abfresh.in.Model.ArrayList(notificationTitle,notificationDisc,notificationImg));


                            }
                            notificationAdapter = new NotificationAdapter(getApplicationContext(), notificationList);
                           final GridLayoutManager manager
                                 = new GridLayoutManager(Notification.this,1);
                            manager.setReverseLayout(true);
                            notification_msg_rv.setLayoutManager(manager);
                            notification_msg_rv.setAdapter(notificationAdapter);


                            noti_pb.setVisibility(View.GONE);

                        } else {
                            noti_pb.setVisibility(View.GONE);
                            noti_rl.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.w("NOTAG", "JSONException e " + e);
                        noti_pb.setVisibility(View.GONE);

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.w("NOTAG", "VolleyError error " + error);
                    noti_pb.setVisibility(View.GONE);
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
            Log.w("NOTAG", "JSONException e 2  " + e);
            noti_pb.setVisibility(View.GONE);

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
