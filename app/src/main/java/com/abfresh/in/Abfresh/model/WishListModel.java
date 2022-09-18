package com.abfresh.in.Abfresh.model;

import android.text.TextUtils;

public class WishListModel {
    String product_id;
    String category_id;
    String product_name;
    String product_gross_amt;
    String product_discount;
    String product_amt;
    String amt;
    String product_type;
    String product_gross_weight;
    String product_weight;
    String product_stock;
    String product_stock_qty;
    String product_description;
    String product_disc_name;
    String product_brief;
    String product_image;
    String no_of_pieces;
    String serves;
    String what_you_get;
    String what_you_get_image;
    String sourcing;
    String no_of_order;
    String sourcing_image;
    String is_in_whishlist;
    String whishlist_id;
    String is_in_cart;
    String cart_id;
    String cart_qty;
    String no_of_pieces_icon;
    String serves_icon;
    String gross_weight_icon;
    String net_weight_icon;
    String delivery_time;


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_gross_amt() {
        return product_gross_amt;
    }

    public void setProduct_gross_amt(String product_gross_amt) {
        this.product_gross_amt = product_gross_amt;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(String product_discount) {
        this.product_discount = product_discount;
    }

    public String getProduct_amt() {
        return product_amt;
    }

    public void setProduct_amt(String product_amt) {
        this.product_amt = product_amt;
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

    public String getProduct_gross_weight() {
        return product_gross_weight;
    }

    public void setProduct_gross_weight(String product_gross_weight) {
        this.product_gross_weight = product_gross_weight;
    }

    public String getProduct_weight() {
        return product_weight;
    }

    public void setProduct_weight(String product_weight) {
        this.product_weight = product_weight;
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

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_disc_name() {
        return product_disc_name;
    }

    public void setProduct_disc_name(String product_disc_name) {
        this.product_disc_name = product_disc_name;
    }

    public String getProduct_brief() {
        return product_brief;
    }

    public void setProduct_brief(String product_brief) {
        this.product_brief = product_brief;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getNo_of_pieces() {
        return no_of_pieces;
    }

    public void setNo_of_pieces(String no_of_pieces) {
        this.no_of_pieces = no_of_pieces;
    }

    public String getServes() {
        return serves;
    }

    public void setServes(String serves) {
        this.serves = serves;
    }

    public String getWhat_you_get() {
        return what_you_get;
    }

    public void setWhat_you_get(String what_you_get) {
        this.what_you_get = what_you_get;
    }

    public String getWhat_you_get_image() {
        return what_you_get_image;
    }

    public void setWhat_you_get_image(String what_you_get_image) {
        this.what_you_get_image = what_you_get_image;
    }

    public String getSourcing() {
        return sourcing;
    }

    public void setSourcing(String sourcing) {
        this.sourcing = sourcing;
    }

    public String getNo_of_order() {
        return no_of_order;
    }

    public void setNo_of_order(String no_of_order) {
        this.no_of_order = no_of_order;
    }

    public String getSourcing_image() {
        return sourcing_image;
    }

    public void setSourcing_image(String sourcing_image) {
        this.sourcing_image = sourcing_image;
    }

    public String getIs_in_whishlist() {
        return is_in_whishlist;
    }

    public void setIs_in_whishlist(String is_in_whishlist) {
        this.is_in_whishlist = is_in_whishlist;
    }

    public String getWhishlist_id() {
        return whishlist_id;
    }

    public void setWhishlist_id(String whishlist_id) {
        this.whishlist_id = whishlist_id;
    }

    public String getIs_in_cart() {
        return is_in_cart;
    }

    public void setIs_in_cart(String is_in_cart) {
        this.is_in_cart = is_in_cart;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }


    public int getQtyInteger(){
        if(!TextUtils.isEmpty(cart_qty))
            return Integer.parseInt(cart_qty);
        return 0;
    }

    public String getCart_qty() {
        return cart_qty;
    }

    public void setCart_qty(String cart_qty) {
        this.cart_qty = cart_qty;
    }

    public String getNo_of_pieces_icon() {
        return no_of_pieces_icon;
    }

    public void setNo_of_pieces_icon(String no_of_pieces_icon) {
        this.no_of_pieces_icon = no_of_pieces_icon;
    }

    public String getServes_icon() {
        return serves_icon;
    }

    public void setServes_icon(String serves_icon) {
        this.serves_icon = serves_icon;
    }

    public String getGross_weight_icon() {
        return gross_weight_icon;
    }

    public void setGross_weight_icon(String gross_weight_icon) {
        this.gross_weight_icon = gross_weight_icon;
    }

    public String getNet_weight_icon() {
        return net_weight_icon;
    }

    public void setNet_weight_icon(String net_weight_icon) {
        this.net_weight_icon = net_weight_icon;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }
}
