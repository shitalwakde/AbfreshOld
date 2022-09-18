package com.abfresh.in.Abfresh.model;

import java.util.ArrayList;

public class UserModel {

    String success;
    String message;
    String is_new_user;
    String user_id;
    String name;
    String email;
    String password;
    String mobile;
    String wallet;
    String abfresh_cash;
    String referal_code;
    String profile_img;
    String order_count;
    String share_msg;
    String display_msg;



    ArrayList<CouponListModel> coupon_list;

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

    public String getIs_new_user() {
        return is_new_user;
    }

    public void setIs_new_user(String is_new_user) {
        this.is_new_user = is_new_user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWallet() {
        return wallet;
    }

    public void setWallet(String wallet) {
        this.wallet = wallet;
    }

    public String getReferal_code() {
        return referal_code;
    }

    public void setReferal_code(String referal_code) {
        this.referal_code = referal_code;
    }

    public String getProfile_img() {
        return profile_img;
    }

    public void setProfile_img(String profile_img) {
        this.profile_img = profile_img;
    }

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public String getShare_msg() {
        return share_msg;
    }

    public void setShare_msg(String share_msg) {
        this.share_msg = share_msg;
    }

    public String getDisplay_msg() {
        return display_msg;
    }

    public void setDisplay_msg(String display_msg) {
        this.display_msg = display_msg;
    }

    public String getAbfresh_cash() {
        return abfresh_cash;
    }

    public void setAbfresh_cash(String abfresh_cash) {
        this.abfresh_cash = abfresh_cash;
    }

    public ArrayList<CouponListModel> getCoupon_list() {
        return coupon_list;
    }

    public void setCoupon_list(ArrayList<CouponListModel> coupon_list) {
        this.coupon_list = coupon_list;
    }


}
