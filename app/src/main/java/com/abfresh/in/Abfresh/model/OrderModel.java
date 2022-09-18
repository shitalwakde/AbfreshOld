package com.abfresh.in.Abfresh.model;

import android.text.TextUtils;

import java.util.ArrayList;

public class OrderModel {

    String success;
    String message;
    String orderCount;

    ArrayList<OrderModel> order_list;
    ArrayList<OrderModel> order_detail_list;

    String order_id;
    String order_number;
    String order_status;
    String order_payment_type;
    String order_payment_type_short;
    String order_amount;
    String order_date;
    String payment_date;
    String user_name;
    String store_name;
    String delivery_location_city_id;
    String delivery_location_city_name;
    String delivery_location_state_id;
    String delivery_location_state_name;
    String delivery_location_country_id;
    String delivery_location_country_name;
    String delivery_location_person_name;
    String delivery_location_person_mobile;
    String delivery_location_person_alternate_mobile;
    String delivery_location_person_pincode;
    String delivery_location_person_house_no;
    String delivery_location_person_area;
    String latitude;
    String longitude;


    String order_detail_id;
    String category_name;
    String subcategory_name;
    String product_name;
    String product_image;
    String product_id;
    String qty;
    String amt;
    String product_type;
    String product_weight;
    String product_gross_weight;
    String product_discount;
    String product_gross_amt;
    String product_total_amt;

    String id;
    String user_id;
    String name;
    String email;
    String mobile;
    String store_id;
    String delivery_location_id;
    String wallet_pay;
    String coupon_code_id;
    String coupon_code;
    String order_ref_no;
    String invoice_no;
    String store_address;
    String store_city_name;
    String store_person_name;
    String store_person_mobile;
    String gross_amount;
    String balance_order_amt;
    String tax;
    String discount;
    String delivery_charge;
    String delivery_date;
    String delivery_slot;
    String order_received_by;
    String order_tracking_number;
    String payment_status;
    String delivery_location_person_address;
    String delivery_boy_id;
    String delivery_boy_name;
    String invoice_link;
    String place_order;
    String place_order_msg;
    String order_process;
    String order_process_msg;
    String order_process_link;
    String out_of_delivery;
    String out_of_delivery_msg;
    String out_of_delivery_phone;
    String deliverd;
    String deliverd_msg;
    String is_in_cart="No";
    String cart_id;
    String cart_qty;
    String cart_amt;
    String cart_gross_amt;
    String product_amt;
    String product_stock;
    String product_stock_qty;
    String order_detail_img;


    public String getIs_in_cart() {
        return is_in_cart;
    }

    public void setIs_in_cart(String is_in_cart) {
        this.is_in_cart = is_in_cart;
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

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public ArrayList<OrderModel> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(ArrayList<OrderModel> order_list) {
        this.order_list = order_list;
    }

    public ArrayList<OrderModel> getOrder_detail_list() {
        return order_detail_list;
    }

    public void setOrder_detail_list(ArrayList<OrderModel> order_detail_list) {
        this.order_detail_list = order_detail_list;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_payment_type() {
        return order_payment_type;
    }

    public void setOrder_payment_type(String order_payment_type) {
        this.order_payment_type = order_payment_type;
    }

    public String getOrder_payment_type_short() {
        return order_payment_type_short;
    }

    public void setOrder_payment_type_short(String order_payment_type_short) {
        this.order_payment_type_short = order_payment_type_short;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getDelivery_location_city_id() {
        return delivery_location_city_id;
    }

    public void setDelivery_location_city_id(String delivery_location_city_id) {
        this.delivery_location_city_id = delivery_location_city_id;
    }

    public String getDelivery_location_city_name() {
        return delivery_location_city_name;
    }

    public void setDelivery_location_city_name(String delivery_location_city_name) {
        this.delivery_location_city_name = delivery_location_city_name;
    }

    public String getDelivery_location_state_id() {
        return delivery_location_state_id;
    }

    public void setDelivery_location_state_id(String delivery_location_state_id) {
        this.delivery_location_state_id = delivery_location_state_id;
    }

    public String getDelivery_location_state_name() {
        return delivery_location_state_name;
    }

    public void setDelivery_location_state_name(String delivery_location_state_name) {
        this.delivery_location_state_name = delivery_location_state_name;
    }

    public String getDelivery_location_country_id() {
        return delivery_location_country_id;
    }

    public void setDelivery_location_country_id(String delivery_location_country_id) {
        this.delivery_location_country_id = delivery_location_country_id;
    }

    public String getDelivery_location_country_name() {
        return delivery_location_country_name;
    }

    public void setDelivery_location_country_name(String delivery_location_country_name) {
        this.delivery_location_country_name = delivery_location_country_name;
    }

    public String getDelivery_location_person_name() {
        return delivery_location_person_name;
    }

    public void setDelivery_location_person_name(String delivery_location_person_name) {
        this.delivery_location_person_name = delivery_location_person_name;
    }

    public String getDelivery_location_person_mobile() {
        return delivery_location_person_mobile;
    }

    public void setDelivery_location_person_mobile(String delivery_location_person_mobile) {
        this.delivery_location_person_mobile = delivery_location_person_mobile;
    }

    public String getDelivery_location_person_alternate_mobile() {
        return delivery_location_person_alternate_mobile;
    }

    public void setDelivery_location_person_alternate_mobile(String delivery_location_person_alternate_mobile) {
        this.delivery_location_person_alternate_mobile = delivery_location_person_alternate_mobile;
    }

    public String getDelivery_location_person_pincode() {
        return delivery_location_person_pincode;
    }

    public void setDelivery_location_person_pincode(String delivery_location_person_pincode) {
        this.delivery_location_person_pincode = delivery_location_person_pincode;
    }

    public String getDelivery_location_person_house_no() {
        return delivery_location_person_house_no;
    }

    public void setDelivery_location_person_house_no(String delivery_location_person_house_no) {
        this.delivery_location_person_house_no = delivery_location_person_house_no;
    }

    public String getDelivery_location_person_area() {
        return delivery_location_person_area;
    }

    public void setDelivery_location_person_area(String delivery_location_person_area) {
        this.delivery_location_person_area = delivery_location_person_area;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(String order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getSubcategory_name() {
        return subcategory_name;
    }

    public void setSubcategory_name(String subcategory_name) {
        this.subcategory_name = subcategory_name;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQty() {
        return qty;
    }

    public int getCartQuantityInteger(){
        if(!TextUtils.isEmpty(cart_qty))
            return Integer.parseInt(cart_qty);
        return 0;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
    }

    public String getProduct_gross_weight() {
        return product_gross_weight;
    }

    public void setProduct_gross_weight(String product_gross_weight) {
        this.product_gross_weight = product_gross_weight;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    public String getProduct_gross_amt() {
        return product_gross_amt;
    }

    public void setProduct_gross_amt(String product_gross_amt) {
        this.product_gross_amt = product_gross_amt;
    }

    public String getProduct_total_amt() {
        return product_total_amt;
    }

    public void setProduct_total_amt(String product_total_amt) {
        this.product_total_amt = product_total_amt;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getDelivery_location_id() {
        return delivery_location_id;
    }

    public void setDelivery_location_id(String delivery_location_id) {
        this.delivery_location_id = delivery_location_id;
    }

    public String getWallet_pay() {
        return wallet_pay;
    }

    public void setWallet_pay(String wallet_pay) {
        this.wallet_pay = wallet_pay;
    }

    public String getCoupon_code_id() {
        return coupon_code_id;
    }

    public void setCoupon_code_id(String coupon_code_id) {
        this.coupon_code_id = coupon_code_id;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getOrder_ref_no() {
        return order_ref_no;
    }

    public void setOrder_ref_no(String order_ref_no) {
        this.order_ref_no = order_ref_no;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }

    public String getStore_city_name() {
        return store_city_name;
    }

    public void setStore_city_name(String store_city_name) {
        this.store_city_name = store_city_name;
    }

    public String getStore_person_name() {
        return store_person_name;
    }

    public void setStore_person_name(String store_person_name) {
        this.store_person_name = store_person_name;
    }

    public String getStore_person_mobile() {
        return store_person_mobile;
    }

    public void setStore_person_mobile(String store_person_mobile) {
        this.store_person_mobile = store_person_mobile;
    }

    public String getGross_amount() {
        return gross_amount;
    }

    public void setGross_amount(String gross_amount) {
        this.gross_amount = gross_amount;
    }

    public String getBalance_order_amt() {
        return balance_order_amt;
    }

    public void setBalance_order_amt(String balance_order_amt) {
        this.balance_order_amt = balance_order_amt;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getDelivery_slot() {
        return delivery_slot;
    }

    public void setDelivery_slot(String delivery_slot) {
        this.delivery_slot = delivery_slot;
    }

    public String getOrder_received_by() {
        return order_received_by;
    }

    public void setOrder_received_by(String order_received_by) {
        this.order_received_by = order_received_by;
    }

    public String getOrder_tracking_number() {
        return order_tracking_number;
    }

    public void setOrder_tracking_number(String order_tracking_number) {
        this.order_tracking_number = order_tracking_number;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getDelivery_location_person_address() {
        return delivery_location_person_address;
    }

    public void setDelivery_location_person_address(String delivery_location_person_address) {
        this.delivery_location_person_address = delivery_location_person_address;
    }

    public String getDelivery_boy_id() {
        return delivery_boy_id;
    }

    public void setDelivery_boy_id(String delivery_boy_id) {
        this.delivery_boy_id = delivery_boy_id;
    }

    public String getDelivery_boy_name() {
        return delivery_boy_name;
    }

    public void setDelivery_boy_name(String delivery_boy_name) {
        this.delivery_boy_name = delivery_boy_name;
    }

    public String getInvoice_link() {
        return invoice_link;
    }

    public void setInvoice_link(String invoice_link) {
        this.invoice_link = invoice_link;
    }

    public String getPlace_order() {
        return place_order;
    }

    public void setPlace_order(String place_order) {
        this.place_order = place_order;
    }

    public String getPlace_order_msg() {
        return place_order_msg;
    }

    public void setPlace_order_msg(String place_order_msg) {
        this.place_order_msg = place_order_msg;
    }

    public String getOrder_process() {
        return order_process;
    }

    public void setOrder_process(String order_process) {
        this.order_process = order_process;
    }

    public String getOrder_process_msg() {
        return order_process_msg;
    }

    public void setOrder_process_msg(String order_process_msg) {
        this.order_process_msg = order_process_msg;
    }

    public String getOrder_process_link() {
        return order_process_link;
    }

    public void setOrder_process_link(String order_process_link) {
        this.order_process_link = order_process_link;
    }

    public String getOut_of_delivery() {
        return out_of_delivery;
    }

    public void setOut_of_delivery(String out_of_delivery) {
        this.out_of_delivery = out_of_delivery;
    }

    public String getOut_of_delivery_msg() {
        return out_of_delivery_msg;
    }

    public void setOut_of_delivery_msg(String out_of_delivery_msg) {
        this.out_of_delivery_msg = out_of_delivery_msg;
    }

    public String getOut_of_delivery_phone() {
        return out_of_delivery_phone;
    }

    public void setOut_of_delivery_phone(String out_of_delivery_phone) {
        this.out_of_delivery_phone = out_of_delivery_phone;
    }

    public String getDeliverd() {
        return deliverd;
    }

    public void setDeliverd(String deliverd) {
        this.deliverd = deliverd;
    }

    public String getDeliverd_msg() {
        return deliverd_msg;
    }

    public void setDeliverd_msg(String deliverd_msg) {
        this.deliverd_msg = deliverd_msg;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getCart_qty() {
        return cart_qty;
    }

    public void setCart_qty(String cart_qty) {
        this.cart_qty = cart_qty;
    }

    public String getCart_amt() {
        return cart_amt;
    }

    public void setCart_amt(String cart_amt) {
        this.cart_amt = cart_amt;
    }

    public String getCart_gross_amt() {
        return cart_gross_amt;
    }

    public void setCart_gross_amt(String cart_gross_amt) {
        this.cart_gross_amt = cart_gross_amt;
    }

    public String getProduct_amt() {
        return product_amt;
    }

    public void setProduct_amt(String product_amt) {
        this.product_amt = product_amt;
    }

    public String getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(String product_stock) {
        this.product_stock = product_stock;
    }

    public String getProduct_stock_qty() {
        return product_stock_qty;
    }

    public void setProduct_stock_qty(String product_stock_qty) {
        this.product_stock_qty = product_stock_qty;
    }

    public String getOrder_detail_img() {
        return order_detail_img;
    }

    public void setOrder_detail_img(String order_detail_img) {
        this.order_detail_img = order_detail_img;
    }
}
