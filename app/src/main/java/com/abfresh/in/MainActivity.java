package com.abfresh.in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.abfresh.in.Controller.AppSignatureHelper;
import com.airbnb.lottie.LottieAnimationView;
import com.abfresh.in.Controller.LocationManagerInterface;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.SmartLocationManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

//import com.example.darubottle.R;

public class MainActivity extends AppCompatActivity implements LocationManagerInterface {
    LottieAnimationView roosteranimation;
    RelativeLayout actvity_main_rl;
    ImageView fash_Img,jewel_img;
    private ProgressBar mProgress;
   SessionManagement session;
   public static Boolean locationget=false;
    SmartLocationManager mLocationManager;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    public static boolean firstTime = false;
    public  static boolean popup=true;
    String currentVersion,latestVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setContentView(R.layout.new_main_layout);
        mProgress = (ProgressBar) findViewById(R.id.splash_screen_progress_bar);
        session = new SessionManagement(getApplicationContext());
        AppSignatureHelper appSignatureHelper = new AppSignatureHelper(MainActivity.this);
        Log.w("BTAG","key "+ appSignatureHelper.getAppSignatures().get(0));
        getCurrentVersion();

        VideoView videoView = findViewById(R.id.video_view_splash);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.splash_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
//        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        videoView.start();
        actvity_main_rl = (RelativeLayout)findViewById(R.id.actvity_main_rl);
        // Check for permission
        ///delete this when  getCurrentVersion(); is uncommented
//        doRegularProcess();



//        mLocationManager = new SmartLocationManager(getApplicationContext(), MainActivity.this, MainActivity.this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE);
//        mLocationManager.startLocationFetching();
              //  session.checkLogin();
//            session.getUserDetails().get(KEY_USERID);

        // Start lengthy operation in a background thread

//        Utility.UserId = "0";
    }

    private void doRegularProcess() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    ||ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this, // Activity
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }else {
                new Thread(new Runnable() {
                    public void run() {
                        doWork();
                        if(!(session.isLoggedIn())){
                            startApp();
                        }else {
                            Intent i = new Intent(MainActivity.this, Container_Main_Class.class);
                            // Closing all the Activities
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Add new Flag to start new Activity
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            // Staring Login Activity
                            startActivity(i);
                            finish();
                        }


                    }
                }).start();
            }
        }else {
            new Thread(new Runnable() {
                public void run() {
                    doWork();
                    if(!(session.isLoggedIn())){
                        startApp();
                    }else {
                        Intent i = new Intent(MainActivity.this, Container_Main_Class.class);
                        // Closing all the Activities
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        // Add new Flag to start new Activity
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        // Staring Login Activity
                        startActivity(i);
                        finish();
                    }


                }
            }).start();
        }

    }

    private void startApp() {
        if (session.pincode().equals("")){
            firstTime = true;
            Intent intent = new Intent(MainActivity.this,Location_Text_Intent.class);
            startActivity(intent);
            finish();
        }else {
            Intent i = new Intent(MainActivity.this, Container_Main_Class.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Staring Login Activity
            startActivity(i);
            finish();
        }

//        mProgress.setVisibility(G);
    }

    private void doWork() {
        for (int progress=0; progress<20; progress+=2) {
            try {
                Thread.sleep(300);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
//                Timber.e(e.getMessage());
            }
        }

    }

    @Override
    public void locationFetched(Location mLocation, Location oldLocation, String time, String locationProvider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    locationget = true;
                    new Thread(new Runnable() {
                        public void run() {
                            doWork();
                            if(!(session.isLoggedIn())){
                                startApp();
                            }else {
                                Intent i = new Intent(MainActivity.this, Container_Main_Class.class);
                                // Closing all the Activities
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                // Staring Login Activity
                                startActivity(i);
                                finish();
                            }


                        }
                    }).start();
                } else {
                    locationget = false;
                    new Thread(new Runnable() {
                        public void run() {
                            doWork();
                            if(!(session.isLoggedIn())){
                                startApp();
                            }else {
                                Intent i = new Intent(MainActivity.this, Container_Main_Class.class);
                                // Closing all the Activities
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                // Add new Flag to start new Activity
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                // Staring Login Activity
                                startActivity(i);
                                finish();
                            }
                        }
                    }).start();
                    Toast.makeText(MainActivity.this, "All Permissions are must for better service", Toast.LENGTH_SHORT).show();
                    // permission was denied
                }
                return;
            }
        }
    }


    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {

            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;
        Log.w("TAG", "Current Version==>" + currentVersion);

        new GetLatestVersion().execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
                String appPackageName2 = "com.abfresh.in";

//It retrieves the latest version by scraping the content of current version from play store at runtime
                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=" + appPackageName2).get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();

            } catch (Exception e) {
                e.printStackTrace();

            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            Log.w("TAG", "Current version===>>>" + currentVersion + " latest version===>> " + latestVersion);
            if (latestVersion != null) {
                if (Double.parseDouble(currentVersion) > Double.parseDouble(latestVersion)) {
                    doRegularProcess();
                } else {
                    if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                        if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                            showUpdateDialog();
                        }

                    } else {

                        doRegularProcess();
                    }
                }

            }  else {

                doRegularProcess();
            }
            //  background.start();
            super.onPostExecute(jsonObject);
        }
    }


    private void showUpdateDialog() {
        final String appPackageName2 = "com.abfresh.in";


        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dlg_alert_new);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView btnSubmit = (TextView) dialog.findViewById(R.id.btnSubmit);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("market://details?id=" + appPackageName2)));

                dialog.dismiss();
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.show();

    }
}
