package com.abfresh.in.Controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.abfresh.in.Container_Main_Class;

import java.util.HashMap;

public class SessionManagement {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "meatShop";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USERID = "userid";

    // Email address (make variable public to access from outside)
    public static final String KEY_MOBILE = "mobile";
    public static final String KEY_City = "city_name";
    public static final String KEY_State = "state_name";
    public static final String KEY_City_ID = "city_id";
    public static final String KEY_Pincode = "pincode";
    public static final String KEY_USERFULLNAME = "user_full_name";
    public static final String KEY_USEREMAIL = "user_email";
    public static final String KEY_USERMOBILE = "user_mobile";
    public static final String KEY_FULLADDRESS = "full_address";
  //  public static final String KEY_TYPE = "type";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String userid, String mobile){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERID, userid);

        // Storing email in pref
        editor.putString(KEY_MOBILE, mobile);
//        editor.putString(KEY_TYPE, type);

        // commit changes
        editor.commit();
    }

    public void updateCity(String cityId,String cityName,String stateName, String pincode){
        editor.putString(KEY_City_ID, cityId);
        editor.putString(KEY_City, cityName);
        editor.putString(KEY_State, stateName);
        editor.putString(KEY_Pincode, pincode);
        editor.commit();
    }
    public void addFullAddress(String fullAdd){
        editor.putString(KEY_FULLADDRESS, fullAdd);
        editor.commit();
    }
    public void updateProfile(String userName,String userEmail, String userMobile){
        editor.putString(KEY_USERFULLNAME, userName);
        editor.putString(KEY_USEREMAIL, userEmail);
        editor.putString(KEY_USERMOBILE, userMobile);
        editor.commit();
    }
    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Container_Main_Class.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_USERID, pref.getString(KEY_USERID, ""));
        user.put(KEY_MOBILE, pref.getString(KEY_MOBILE, null));
        user.put(KEY_City_ID, pref.getString(KEY_City_ID, null));
        user.put(KEY_City, pref.getString(KEY_City, ""));
        user.put(KEY_Pincode, pref.getString(KEY_Pincode, null));
        user.put(KEY_USERFULLNAME, pref.getString(KEY_USERFULLNAME, ""));
        user.put(KEY_USEREMAIL, pref.getString(KEY_USEREMAIL, null));
        user.put(KEY_USERMOBILE, pref.getString(KEY_USERMOBILE, null));
        user.put(KEY_State, pref.getString(KEY_State, ""));
        user.put(KEY_FULLADDRESS, pref.getString(KEY_FULLADDRESS, ""));
//        user.put(KEY_TYPE, pref.getString(KEY_TYPE, null));

        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Container_Main_Class.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String pincode(){
        return pref.getString(KEY_Pincode, "");
    }
}
