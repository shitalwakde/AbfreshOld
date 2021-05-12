package com.abfresh.in.Model;

public class CartList {
    int cartImage;
    String  cartBrandName,productPrice,productWeight,cartInStock,cartQuant,saveAddress,delivery_location_id;
    String  cart_id,Cart_qty,product_name,product_gross_amt,product_discount,product_amt,pro_quantity,pro_image,pro_wt;
    String  cart_id_new,cart_qty_new,product_name_new,product_gross_wt_new,product_piec_new,product_amt_new,product_wt_new,product_gross_amount_new,product_discount_new;
    String order_id,order_number,order_status,order_payment_type,order_amount,order_date,order_address;

//    public CartList(String order_id, String order_number, String order_status, String order_payment_type, String order_amount, String order_date, String order_address,String cart_qty) {
//        this.order_id = order_id;
//        this.order_number = order_number;
//        this.order_status = order_status;
//        this.order_payment_type = order_payment_type;
//        this.order_amount = order_amount;
//        this.order_date = order_date;
//        this.order_address = order_address;
//        this.Cart_qty = cart_qty;
//    }

    public CartList(String cart_id_new, String cart_qty_new, String product_name_new, String product_gross_wt_new, String product_piec_new, String product_amt_new, String product_wt_new, String product_gross_amount_new, String product_discount_new) {
        this.cart_id_new = cart_id_new;
        this.cart_qty_new = cart_qty_new;
        this.product_name_new = product_name_new;
        this.product_gross_wt_new = product_gross_wt_new;
        this.product_piec_new = product_piec_new;
        this.product_amt_new = product_amt_new;
        this.product_wt_new = product_wt_new;
        this.product_gross_amount_new = product_gross_amount_new;
        this.product_discount_new = product_discount_new;
    }

    public CartList(String cart_id, String cart_qty, String product_name, String product_gross_amt, String product_discount, String product_amt, String pro_wt) {
        this.cart_id = cart_id;
        this.Cart_qty = cart_qty;
        this.product_name = product_name;
        this.product_gross_amt = product_gross_amt;
        this.product_discount = product_discount;
        this.product_amt = product_amt;
        this.pro_wt = pro_wt;
    }

    public CartList(String cart_id, String product_name, String product_gross_amt, String product_discount, String product_amt, String pro_quantity, String pro_image,String pro_wt) {
        this.cart_id = cart_id;
        this.product_name = product_name;
        this.product_gross_amt = product_gross_amt;
        this.product_discount = product_discount;
        this.product_amt = product_amt;
        this.pro_quantity = pro_quantity;
        this.pro_image = pro_image;
        this.pro_wt = pro_wt;
    }

    public CartList(String cartBrandName, String productWeight, String productPrice) {
        this.cartBrandName = cartBrandName;
        this.productPrice = productPrice;
        this.productWeight = productWeight;
    }

    public CartList(int cartImage,String cartBrandName,String cartNewPricee, String productWeight, String cartQunt) {
        this.cartImage = cartImage;
        this.cartBrandName = cartBrandName;
        this.productPrice = cartNewPricee;
        this.productWeight = productWeight;
        this.cartQuant = cartQunt;
    }

    public String getCart_id_new() {
        return cart_id_new;
    }

    public String getCart_qty_new() {
        return cart_qty_new;
    }

    public String getProduct_name_new() {
        return product_name_new;
    }

    public String getProduct_gross_wt_new() {
        return product_gross_wt_new;
    }

    public String getProduct_piec_new() {
        return product_piec_new;
    }

    public String getProduct_amt_new() {
        return product_amt_new;
    }

    public String getProduct_wt_new() {
        return product_wt_new;
    }

    public String getProduct_gross_amount_new() {
        return product_gross_amount_new;
    }

    public String getProduct_discount_new() {
        return product_discount_new;
    }

    public String getCart_qty() {
        return Cart_qty;
    }

    public String getPro_wt() {
        return pro_wt;
    }

    public String getPro_quantity() {
        return pro_quantity;
    }

    public String getPro_image() {
        return pro_image;
    }

    public CartList(String saveAddress,String delivery_location_id) {
        this.saveAddress = saveAddress;
        this.delivery_location_id = delivery_location_id;
    }

    public String getSaveAddress() {
        return saveAddress;
    }

    public String getDelivery_location_id() {
        return delivery_location_id;
    }

    public String getCartQuant() {
        return cartQuant;
    }

    public String getCartBrandName() {
        return cartBrandName;
    }

    public int getCartImage() {
        return cartImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductWeight() {
        return productWeight;
    }

    public String getCartInStock() {
        return cartInStock;
    }

    public String getCart_id() {
        return cart_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_gross_amt() {
        return product_gross_amt;
    }

    public String getProduct_discount() {
        return product_discount;
    }

    public String getProduct_amt() {
        return product_amt;
    }

    public String
    getOrder_id() {
        return order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_payment_type() {
        return order_payment_type;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_address() {
        return order_address;
    }
}
