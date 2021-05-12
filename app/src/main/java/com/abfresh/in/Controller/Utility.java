package com.abfresh.in.Controller;

import java.util.ArrayList;

public class Utility {

//    public static String ServerUrl = "http://ourwaysolutions.com/demo/meat/restAPI/index.php/";
//    public static String ServerUrl = "http://3.7.12.246/restAPI/index.php/";
    public static String ServerUrl = "http://abfresh.in/restAPI/index.php/";

//    public static String webviewUrl = "http://3.7.12.246/home/";
    public static String webviewUrl = "https://abfresh.in/home/";
    public static String ServerUsername = "Authorization";
    public static String ServerPassword = "Basic bWVhdDoxMTAw";
    public static String GoogleApi="https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyA2MstzpRrBbVFNMKBTM259X-OpvekWqxw&libraries=places&address=";
    public static String UserId="0";
    public static int CartCount;
    public static boolean FromCart;
    public static boolean FromPD;
    public static boolean FromMainClass;
    public static boolean FromTabClass;
    public static String CityName;
    public static String CityID;
    public static String MyAddress;
    public static String MyAddressNew="";
    public static String Delivery_location_id="";
    public static String deliverySlot;
    public static String deliveryDay;
    public static String couponCode="";
    public static Boolean fromThanks=false;

    public static int CartTabCount=0;
//    public static String array[] ;
     public static  ArrayList<String> cartLists = new ArrayList<>();
    public static  String Login = ServerUrl + "Login/login";
    public static  String Home = ServerUrl + "Home/home";
    public static  String CategoryWiseProduct = ServerUrl + "Home/categoryWiseProduct";
    public static  String VerifyOTP = ServerUrl + "Login/verifyOTP";
    public static  String ResendOTP = ServerUrl + "Login/resendOTP";
    public static  String ProductDetails = ServerUrl + "Home/productDetails";
    public static  String AddToCart = ServerUrl + "Order/addToCart";
    public static  String CartList = ServerUrl + "Order/cartList";
    public static  String CartSummery = ServerUrl + "Order/cartSummery";
    public static  String WalletTransaction = ServerUrl + "Order/walletTransaction";
    public static  String SaveDeliveryLocation = ServerUrl + "Order/saveDeliveryLocation";
    public static  String RemoveFromCart = ServerUrl + "Order/removeFromCart";
    public static  String RemoveDeliveryLocation = ServerUrl + "Order/deleteDeliveryLocation";
    public static  String GetCity = ServerUrl + "Home/getCity";
    public static  String GetDeliveryLocation = ServerUrl + "Order/getDeliveryLocation";
    public static  String PlaceOrder = ServerUrl + "Order/placeOrder";
    public static  String MyOrder = ServerUrl + "Order/myOrder";
    public static  String MyCoupons = ServerUrl + "Order/viewCouponCode";
    public static  String ApplyCoupons = ServerUrl + "Order/applyCouponCode";
    public static  String RemoveCoupons = ServerUrl + "Order/removeCouponCode";
    public static  String CancelledOrder = ServerUrl + "Order/cancelledOrder";
    public static  String MyOrderDetails = ServerUrl + "Order/myOrderDetails";
    public static  String getChecksum = ServerUrl + "Order/checksum";
    public static  String payemntResponse = ServerUrl + "Order/payemntResponse";
    public static  String getProfile = ServerUrl + "Profile/getProfile";
    public static  String updateProfile = ServerUrl + "Profile/updateProfile";
    public static  String Recipes = ServerUrl + "Home/recipes";
    public static  String Search = ServerUrl + "Home/search";
    public static  String Offers = ServerUrl + "Home/offers";
    public static  String GetLocation = ServerUrl + "Home/getLocation";
    public static  String SaveLocation = ServerUrl + "Home/saveLocation";
    public static  String VerifyCityPincode = ServerUrl + "Home/verifyCityPincode";
    public static  String Notifications = ServerUrl + "Home/notifications";
    public static  String BlogUrl = webviewUrl + "blog?app=yes";
    public static  String FaqUrl = webviewUrl + "faq?app=yes";
//    public static  String PrivacyUrl = webviewUrl + "privacy?app=yes";
    public static  String PrivacyUrl = ServerUrl + "Home/privacy";
//    public static  String TermsConditionUrl = webviewUrl + "termsCondition?app=yes";
    public static  String TermsConditionUrl = ServerUrl + "Home/termsCondition";

}
