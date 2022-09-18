package com.abfresh.in.Controller;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.abfresh.in.MainActivity;
import com.abfresh.in.NewProductDiscription;
import com.abfresh.in.TabActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

//import androidx.lifecycle.Lifecycle;
//import androidx.lifecycle.LifecycleEventObserver;
//import androidx.lifecycle.LifecycleObserver;
//import androidx.lifecycle.LifecycleOwner;
//import androidx.lifecycle.OnLifecycleEvent;
//import androidx.lifecycle.ProcessLifecycleOwner;

//import net.one97.paytm.nativesdk.PaytmSDK;

//public class AppController extends Application implements LifecycleObserver {
public class AppController extends Application{

    RequestQueue mRequestQueue;
    public static int tabCurrentItem=0;
    public static AppController appController;
    //    public static String userid;
    public static String hname="";
    public static String clickBanner="";
    public static String player_id;
    public static String completeAddress="";
    public static String MyState="";
    Activity activity;
    private Context mContext;
    Boolean isInForeground,isVisiBle;
    String productId="",categoryId="";
    private Picasso picasso;
    private OkHttpClient okHttpClient;



    @Override
    public void onCreate() {
        super.onCreate();
        //PaytmSDK.init(this);
        /*Picasso picasso = Picasso.with(this);
        picasso.setLoggingEnabled(true);

        picasso.setIndicatorsEnabled(true);*/
        appController = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
//        userid = new String();
        hname = new String();
        clickBanner = new String();
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
        OneSignal.startInit(this)
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .setNotificationReceivedHandler( new MyNotificationReceivedHandler() )
                .unsubscribeWhenNotificationsAreDisabled(false)
//                .filterOtherGCMReceivers(true)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();

        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                player_id=userId;

                Log.w("BTAG", "Player_id "+player_id);


            }
        });

        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
//                Boolean isInBackground = myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        isInForeground = myProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
        isVisiBle = myProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
        Log.w("SHANTAG","App is in background==>"+isVisiBle);

    }
//    public AppController(Context context) {
//        mContext = context;
//    }
    public static synchronized AppController getInstance() {

        return appController;
    }

    public RequestQueue getmRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addRequestInQueue(Request<T> request)    {
        getmRequestQueue().add(request);
    }


    public class MyNotificationReceivedHandler  implements OneSignal.NotificationReceivedHandler {


        @Override
        public void notificationReceived(OSNotification notification) {

            Log.w("NTAG","Noti payload shown====>"+notification.payload.additionalData);


        }
    }

    public class MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        // This fires when a notification is opened by tapping on it.

        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            try {
                Log.w("NTAG","Noti result====>"+result.notification.payload.additionalData);
//                if (additionalData != null) {
//                if((!isInForeground)||(isVisiBle)){
                if((!isInForeground)){
                JSONObject jsonObject = result.notification.payload.additionalData;
                if(jsonObject.getString("flag").equals("product")){
                    try {
                        productId = jsonObject.getString("productId");
                        categoryId = jsonObject.getString("categoryId");
                        Log.w("NTAG","Noti  productId====>"+productId);
                        Log.w("NTAG","Noti categoryId====>"+categoryId);
                        Intent launchIntent = new Intent(AppController.this, NewProductDiscription.class);
                        launchIntent.putExtra("categoryId",categoryId);
                        launchIntent.putExtra("productId",productId);
                        launchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);//                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if(jsonObject.getString("flag").equals("category")){
//                    productId = jsonObject.getString("productId");
                    categoryId = jsonObject.getString("categoryId");
//                    Log.w("NTAG","Noti  productId====>"+productId);
                    Log.w("NTAG","Noti categoryId====>"+categoryId);
                    Intent launchIntent = new Intent(AppController.this, TabActivity.class);
                    launchIntent.putExtra("ID",categoryId);
                    launchIntent.putExtra("image","");
                    launchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);//                    if (launchIntent != null) {
                    startActivity(launchIntent);
                }
                else{
                    Intent launchIntent = new Intent(AppController.this, MainActivity.class);
                   launchIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);//                    if (launchIntent != null) {
                    startActivity(launchIntent);
                }
                        Log.w("NTAG","Noti  productId====>"+productId);
                        Log.w("NTAG","Noti categoryId====>"+categoryId);
//                        if(productId.toString().trim().length()!=0){
                            //null pointer check in case package name was not found
//                        }
//                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.abfresh.in.NewProductDiscription");

//                    }
//                }
                }

//                    Intent intent = new Intent(mContext, MainActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent.putExtra("key", <additionalData to be sent>);
//                    mContext.startActivity(intent);
//                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    public void onAppBackgrounded() {
//        //App in background
////        finishAffinity(activity);
////        android.os.Process.killProcess(android.os.Process.myPid());
//        System.runFinalizersOnExit(true);
////       activity.finishAndRemoveTask();
//        android.os.Process.killProcess(android.os.Process.myPid());
//        Log.w("SHANTAG","App is in background");
//        System.exit(0);
//
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    public void onAppForegrounded() {
//        // App in foreground
//
//        Log.w("SHANTAG","App is in foreground");
//    }



}


