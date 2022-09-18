package com.abfresh.in.Abfresh.model;

import android.text.TextUtils;

public class    CartItemList {
    String cart_id;
    String qty;
    String cart_amt;
    String cart_gross_amt;
    String product_id;
    String product_name;
    String product_gross_amt;
    String product_discount;
    String product_amt;
    String product_image;
    String product_type;
    String no_of_pieces;
    String serves;
    String product_gross_weight;
    String product_weight;
    String product_stock;
    String product_stock_qty;


    String is_recommended;
    String recommended_product_id;
    String recommended_category_id;
    String recommended_product_name;
    String recommended_product_gross_amt;
    String recommended_product_discount;
    String recommended_product_amt;
    String recommended_amt;
    String recommended_product_type;
    String recommended_product_gross_weight;
    String recommended_product_weight;
    String recommended_product_stock;
    String recommended_product_stock_qty;
    String recommended_product_description;
    String recommended_product_disc_name;
    String recommended_product_brief;
    String recommended_product_image;
    String recommended_no_of_pieces;
    String recommended_serves;
    String recommended_what_you_get;
    String recommended_what_you_get_image;
    String recommended_sourcing;
    String recommended_no_of_order;
    String recommended_sourcing_image;
    String recommended_is_in_whishlist;
    String recommended_whishlist_id;
    String recommended_is_in_cart;
    String recommended_cart_id;
    String recommended_cart_qty;
    String recommended_no_of_pieces_icon;
    String recommended_serves_icon;
    String recommended_gross_weight_icon;
    String recommended_net_weight_icon;
    String recommended_no_of_orders;
    String recommended_apply_offer_mrp;



    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public int getQtyInteger(){
        if(!TextUtils.isEmpty(qty))
            return Integer.parseInt(qty);
        return 0;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
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

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
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

    public String getIs_recommended() {
        return is_recommended;
    }

    public void setIs_recommended(String is_recommended) {
        this.is_recommended = is_recommended;
    }

    public String getRecommended_product_id() {
        return recommended_product_id;
    }

    public void setRecommended_product_id(String recommended_product_id) {
        this.recommended_product_id = recommended_product_id;
    }

    public String getRecommended_category_id() {
        return recommended_category_id;
    }

    public void setRecommended_category_id(String recommended_category_id) {
        this.recommended_category_id = recommended_category_id;
    }

    public String getRecommended_product_name() {
        return recommended_product_name;
    }

    public void setRecommended_product_name(String recommended_product_name) {
        this.recommended_product_name = recommended_product_name;
    }

    public String getRecommended_product_gross_amt() {
        return recommended_product_gross_amt;
    }

    public void setRecommended_product_gross_amt(String recommended_product_gross_amt) {
        this.recommended_product_gross_amt = recommended_product_gross_amt;
    }

    public String getRecommended_product_discount() {
        return recommended_product_discount;
    }

    public void setRecommended_product_discount(String recommended_product_discount) {
        this.recommended_product_discount = recommended_product_discount;
    }

    public String getRecommended_product_amt() {
        return recommended_product_amt;
    }

    public void setRecommended_product_amt(String recommended_product_amt) {
        this.recommended_product_amt = recommended_product_amt;
    }

    public String getRecommended_amt() {
        return recommended_amt;
    }

    public void setRecommended_amt(String recommended_amt) {
        this.recommended_amt = recommended_amt;
    }

    public String getRecommended_product_type() {
        return recommended_product_type;
    }

    public void setRecommended_product_type(String recommended_product_type) {
        this.recommended_product_type = recommended_product_type;
    }

    public String getRecommended_product_gross_weight() {
        return recommended_product_gross_weight;
    }

    public void setRecommended_product_gross_weight(String recommended_product_gross_weight) {
        this.recommended_product_gross_weight = recommended_product_gross_weight;
    }

    public String getRecommended_product_weight() {
        return recommended_product_weight;
    }

    public void setRecommended_product_weight(String recommended_product_weight) {
        this.recommended_product_weight = recommended_product_weight;
    }

    public String getRecommended_product_stock() {
        return recommended_product_stock;
    }

    public void setRecommended_product_stock(String recommended_product_stock) {
        this.recommended_product_stock = recommended_product_stock;
    }

    public String getRecommended_product_stock_qty() {
        return recommended_product_stock_qty;
    }

    public void setRecommended_product_stock_qty(String recommended_product_stock_qty) {
        this.recommended_product_stock_qty = recommended_product_stock_qty;
    }

    public String getRecommended_product_description() {
        return recommended_product_description;
    }

    public void setRecommended_product_description(String recommended_product_description) {
        this.recommended_product_description = recommended_product_description;
    }

    public String getRecommended_product_disc_name() {
        return recommended_product_disc_name;
    }

    public void setRecommended_product_disc_name(String recommended_product_disc_name) {
        this.recommended_product_disc_name = recommended_product_disc_name;
    }

    public String getRecommended_product_brief() {
        return recommended_product_brief;
    }

    public void setRecommended_product_brief(String recommended_product_brief) {
        this.recommended_product_brief = recommended_product_brief;
    }

    public String getRecommended_product_image() {
        return recommended_product_image;
    }

    public void setRecommended_product_image(String recommended_product_image) {
        this.recommended_product_image = recommended_product_image;
    }

    public String getRecommended_no_of_pieces() {
        return recommended_no_of_pieces;
    }

    public void setRecommended_no_of_pieces(String recommended_no_of_pieces) {
        this.recommended_no_of_pieces = recommended_no_of_pieces;
    }

    public String getRecommended_serves() {
        return recommended_serves;
    }

    public void setRecommended_serves(String recommended_serves) {
        this.recommended_serves = recommended_serves;
    }

    public String getRecommended_what_you_get() {
        return recommended_what_you_get;
    }

    public void setRecommended_what_you_get(String recommended_what_you_get) {
        this.recommended_what_you_get = recommended_what_you_get;
    }

    public String getRecommended_what_you_get_image() {
        return recommended_what_you_get_image;
    }

    public void setRecommended_what_you_get_image(String recommended_what_you_get_image) {
        this.recommended_what_you_get_image = recommended_what_you_get_image;
    }

    public String getRecommended_sourcing() {
        return recommended_sourcing;
    }

    public void setRecommended_sourcing(String recommended_sourcing) {
        this.recommended_sourcing = recommended_sourcing;
    }

    public String getRecommended_no_of_order() {
        return recommended_no_of_order;
    }

    public void setRecommended_no_of_order(String recommended_no_of_order) {
        this.recommended_no_of_order = recommended_no_of_order;
    }

    public String getRecommended_sourcing_image() {
        return recommended_sourcing_image;
    }

    public void setRecommended_sourcing_image(String recommended_sourcing_image) {
        this.recommended_sourcing_image = recommended_sourcing_image;
    }

    public String getRecommended_is_in_whishlist() {
        return recommended_is_in_whishlist;
    }

    public void setRecommended_is_in_whishlist(String recommended_is_in_whishlist) {
        this.recommended_is_in_whishlist = recommended_is_in_whishlist;
    }

    public String getRecommended_whishlist_id() {
        return recommended_whishlist_id;
    }

    public void setRecommended_whishlist_id(String recommended_whishlist_id) {
        this.recommended_whishlist_id = recommended_whishlist_id;
    }

    public String getRecommended_is_in_cart() {
        return recommended_is_in_cart;
    }

    public void setRecommended_is_in_cart(String recommended_is_in_cart) {
        this.recommended_is_in_cart = recommended_is_in_cart;
    }

    public String getRecommended_cart_id() {
        return recommended_cart_id;
    }

    public void setRecommended_cart_id(String recommended_cart_id) {
        this.recommended_cart_id = recommended_cart_id;
    }

    public String getRecommended_cart_qty() {
        return recommended_cart_qty;
    }

    public void setRecommended_cart_qty(String recommended_cart_qty) {
        this.recommended_cart_qty = recommended_cart_qty;
    }

    public String getRecommended_no_of_pieces_icon() {
        return recommended_no_of_pieces_icon;
    }

    public void setRecommended_no_of_pieces_icon(String recommended_no_of_pieces_icon) {
        this.recommended_no_of_pieces_icon = recommended_no_of_pieces_icon;
    }

    public String getRecommended_serves_icon() {
        return recommended_serves_icon;
    }

    public void setRecommended_serves_icon(String recommended_serves_icon) {
        this.recommended_serves_icon = recommended_serves_icon;
    }

    public String getRecommended_gross_weight_icon() {
        return recommended_gross_weight_icon;
    }

    public void setRecommended_gross_weight_icon(String recommended_gross_weight_icon) {
        this.recommended_gross_weight_icon = recommended_gross_weight_icon;
    }

    public String getRecommended_net_weight_icon() {
        return recommended_net_weight_icon;
    }

    public void setRecommended_net_weight_icon(String recommended_net_weight_icon) {
        this.recommended_net_weight_icon = recommended_net_weight_icon;
    }

    public String getRecommended_no_of_orders() {
        return recommended_no_of_orders;
    }

    public void setRecommended_no_of_orders(String recommended_no_of_orders) {
        this.recommended_no_of_orders = recommended_no_of_orders;
    }

    public String getRecommended_apply_offer_mrp() {
        return recommended_apply_offer_mrp;
    }

    public void setRecommended_apply_offer_mrp(String recommended_apply_offer_mrp) {
        this.recommended_apply_offer_mrp = recommended_apply_offer_mrp;
    }
}
