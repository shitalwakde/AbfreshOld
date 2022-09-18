package com.abfresh.in.Abfresh.model;

import java.io.Serializable;
import java.util.ArrayList;

public class CartModel implements Serializable {
    String success;
    String message;
    String delivery_charge;
    int cart_total_amt;
    int gst_rate;
    int gst_amt;
    int coupon_amt;
    int discount_amt;
    int gross_amt;
    int final_amt;
    String cart_count;
    String wishlist_count;
    String coupon_code;
    String total_amt;
    ArrayList<CartItemList> cart_list;
    ArrayList<WishListModel> wishlist_list;
    ArrayList<DeliveryList> delivery_list;
    ArrayList<DeliveryList> delivery_location_list;
    ArrayList<DeliverySlotsModel> delivery_slots_today_list;
    ArrayList<DeliverySlotsModel> delivery_slots_tomorrow_list;
    ArrayList<DeliverySlotsModel> deliverySlot;
    ArrayList<DeliverySlotsModel> coupon_list;
    CartItemList product_list;

    String default_delivery_location_available;
    String delivery_location_id;
    String delivery_location_city_name;
    String delivery_location_state_name;
    String delivery_location_user_name;
    String delivery_location_user_mobile;
    String delivery_location_pincode;
    String delivery_location_house_no;
    String delivery_location_area;
    String delivery_location_address;
    String display_msg;
    String wallet;
    String is_cod_payment;
    String is_wallet_payment;
    String is_online_payment;



    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getIs_cod_payment() {
        return is_cod_payment;
    }

    public void setIs_cod_payment(String is_cod_payment) {
        this.is_cod_payment = is_cod_payment;
    }

    public String getIs_wallet_payment() {
        return is_wallet_payment;
    }

    public void setIs_wallet_payment(String is_wallet_payment) {
        this.is_wallet_payment = is_wallet_payment;
    }

    public String getIs_online_payment() {
        return is_online_payment;
    }

    public void setIs_online_payment(String is_online_payment) {
        this.is_online_payment = is_online_payment;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public int getCart_total_amt() {
        return cart_total_amt;
    }

    public void setCart_total_amt(int cart_total_amt) {
        this.cart_total_amt = cart_total_amt;
    }

    public String getCart_count() {
        return cart_count;
    }

    public void setCart_count(String cart_count) {
        this.cart_count = cart_count;
    }

    public String getTotal_amt() {
        return total_amt;
    }

    public void setTotal_amt(String total_amt) {
        this.total_amt = total_amt;
    }

    public ArrayList<CartItemList> getCart_list() {
        return cart_list;
    }

    public void setCart_list(ArrayList<CartItemList> cart_list) {
        this.cart_list = cart_list;
    }

    public CartItemList getProduct_list() {
        return product_list;
    }

    public void setProduct_list(CartItemList product_list) {
        this.product_list = product_list;
    }

    public ArrayList<DeliveryList> getDelivery_list() {
        return delivery_list;
    }

    public void setDelivery_list(ArrayList<DeliveryList> delivery_list) {
        this.delivery_list = delivery_list;
    }

    public String getDefault_delivery_location_available() {
        return default_delivery_location_available;
    }

    public void setDefault_delivery_location_available(String default_delivery_location_available) {
        this.default_delivery_location_available = default_delivery_location_available;
    }

    public String getDelivery_location_id() {
        return delivery_location_id;
    }

    public void setDelivery_location_id(String delivery_location_id) {
        this.delivery_location_id = delivery_location_id;
    }

    public String getDelivery_location_city_name() {
        return delivery_location_city_name;
    }

    public void setDelivery_location_city_name(String delivery_location_city_name) {
        this.delivery_location_city_name = delivery_location_city_name;
    }

    public String getDelivery_location_state_name() {
        return delivery_location_state_name;
    }

    public void setDelivery_location_state_name(String delivery_location_state_name) {
        this.delivery_location_state_name = delivery_location_state_name;
    }

    public String getDelivery_location_user_name() {
        return delivery_location_user_name;
    }

    public void setDelivery_location_user_name(String delivery_location_user_name) {
        this.delivery_location_user_name = delivery_location_user_name;
    }

    public String getDelivery_location_user_mobile() {
        return delivery_location_user_mobile;
    }

    public void setDelivery_location_user_mobile(String delivery_location_user_mobile) {
        this.delivery_location_user_mobile = delivery_location_user_mobile;
    }

    public String getDelivery_location_pincode() {
        return delivery_location_pincode;
    }

    public void setDelivery_location_pincode(String delivery_location_pincode) {
        this.delivery_location_pincode = delivery_location_pincode;
    }

    public String getDelivery_location_house_no() {
        return delivery_location_house_no;
    }

    public void setDelivery_location_house_no(String delivery_location_house_no) {
        this.delivery_location_house_no = delivery_location_house_no;
    }

    public String getDelivery_location_area() {
        return delivery_location_area;
    }

    public void setDelivery_location_area(String delivery_location_area) {
        this.delivery_location_area = delivery_location_area;
    }

    public String getDelivery_location_address() {
        return delivery_location_address;
    }

    public void setDelivery_location_address(String delivery_location_address) {
        this.delivery_location_address = delivery_location_address;
    }

    public String getDisplay_msg() {
        return display_msg;
    }

    public void setDisplay_msg(String display_msg) {
        this.display_msg = display_msg;
    }

    public ArrayList<DeliveryList> getDelivery_location_list() {
        return delivery_location_list;
    }

    public void setDelivery_location_list(ArrayList<DeliveryList> delivery_location_list) {
        this.delivery_location_list = delivery_location_list;
    }

    public ArrayList<DeliverySlotsModel> getDelivery_slots_today_list() {
        return delivery_slots_today_list;
    }

    public void setDelivery_slots_today_list(ArrayList<DeliverySlotsModel> delivery_slots_today_list) {
        this.delivery_slots_today_list = delivery_slots_today_list;
    }

    public ArrayList<DeliverySlotsModel> getDelivery_slots_tomorrow_list() {
        return delivery_slots_tomorrow_list;
    }

    public void setDelivery_slots_tomorrow_list(ArrayList<DeliverySlotsModel> delivery_slots_tomorrow_list) {
        this.delivery_slots_tomorrow_list = delivery_slots_tomorrow_list;
    }

    public ArrayList<DeliverySlotsModel> getDeliverySlot() {
        return deliverySlot;
    }

    public void setDeliverySlot(ArrayList<DeliverySlotsModel> deliverySlot) {
        this.deliverySlot = deliverySlot;
    }

    public ArrayList<DeliverySlotsModel> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(ArrayList<DeliverySlotsModel> coupon_list) {
        this.coupon_list = coupon_list;
    }

    public int getGst_rate() {
        return gst_rate;
    }

    public void setGst_rate(int gst_rate) {
        this.gst_rate = gst_rate;
    }

    public int getGst_amt() {
        return gst_amt;
    }

    public void setGst_amt(int gst_amt) {
        this.gst_amt = gst_amt;
    }

    public int getCoupon_amt() {
        return coupon_amt;
    }

    public void setCoupon_amt(int coupon_amt) {
        this.coupon_amt = coupon_amt;
    }

    public int getDiscount_amt() {
        return discount_amt;
    }

    public void setDiscount_amt(int discount_amt) {
        this.discount_amt = discount_amt;
    }

    public int getGross_amt() {
        return gross_amt;
    }

    public void setGross_amt(int gross_amt) {
        this.gross_amt = gross_amt;
    }

    public int getFinal_amt() {
        return final_amt;
    }

    public void setFinal_amt(int final_amt) {
        this.final_amt = final_amt;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getWishlist_count() {
        return wishlist_count;
    }

    public void setWishlist_count(String wishlist_count) {
        this.wishlist_count = wishlist_count;
    }

    public ArrayList<WishListModel> getWishlist_list() {
        return wishlist_list;
    }

    public void setWishlist_list(ArrayList<WishListModel> wishlist_list) {
        this.wishlist_list = wishlist_list;
    }
}
