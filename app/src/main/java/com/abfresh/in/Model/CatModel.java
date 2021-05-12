package com.abfresh.in.Model;

public class CatModel {
    String catName,catGrossAmountNew,catDiscountNew,catproduct_amt,catImage,catdiscName,catbrief,catProductId,catProductGwt,catProductwt,catProductStock,catProduct_nop,is_in_cart,cart_qty,delivery_time;

    public CatModel(String catName, String catGrossAmountNew, String catDiscountNew, String catproduct_amt, String catImage, String catdiscName, String catbrief, String catProductId, String catProductGwt, String catProductwt, String catProductStock, String catProduct_nop, String is_in_cart, String cart_qty,String delivery_time) {
        this.catName = catName;
        this.catGrossAmountNew = catGrossAmountNew;
        this.catDiscountNew = catDiscountNew;
        this.catproduct_amt = catproduct_amt;
        this.catImage = catImage;
        this.catdiscName = catdiscName;
        this.catbrief = catbrief;
        this.catProductId = catProductId;
        this.catProductGwt = catProductGwt;
        this.catProductwt = catProductwt;
        this.catProductStock = catProductStock;
        this.catProduct_nop = catProduct_nop;
        this.is_in_cart = is_in_cart;
        this.cart_qty = cart_qty;
        this.delivery_time = delivery_time;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatGrossAmountNew() {
        return catGrossAmountNew;
    }

    public void setCatGrossAmountNew(String catGrossAmountNew) {
        this.catGrossAmountNew = catGrossAmountNew;
    }

    public String getCatDiscountNew() {
        return catDiscountNew;
    }

    public void setCatDiscountNew(String catDiscountNew) {
        this.catDiscountNew = catDiscountNew;
    }

    public String getCatproduct_amt() {
        return catproduct_amt;
    }

    public void setCatproduct_amt(String catproduct_amt) {
        this.catproduct_amt = catproduct_amt;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getCatdiscName() {
        return catdiscName;
    }

    public void setCatdiscName(String catdiscName) {
        this.catdiscName = catdiscName;
    }

    public String getCatbrief() {
        return catbrief;
    }

    public void setCatbrief(String catbrief) {
        this.catbrief = catbrief;
    }

    public String getCatProductId() {
        return catProductId;
    }

    public void setCatProductId(String catProductId) {
        this.catProductId = catProductId;
    }

    public String getCatProductGwt() {
        return catProductGwt;
    }

    public void setCatProductGwt(String catProductGwt) {
        this.catProductGwt = catProductGwt;
    }

    public String getCatProductwt() {
        return catProductwt;
    }

    public void setCatProductwt(String catProductwt) {
        this.catProductwt = catProductwt;
    }

    public String getCatProductStock() {
        return catProductStock;
    }

    public void setCatProductStock(String catProductStock) {
        this.catProductStock = catProductStock;
    }

    public String getCatProduct_nop() {
        return catProduct_nop;
    }

    public void setCatProduct_nop(String catProduct_nop) {
        this.catProduct_nop = catProduct_nop;
    }

    public String getIs_in_cart() {
        return is_in_cart;
    }

    public void setIs_in_cart(String is_in_cart) {
        this.is_in_cart = is_in_cart;
    }

    public String getCart_qty() {
        return cart_qty;
    }

    public void setCart_qty(String cart_qty) {
        this.cart_qty = cart_qty;
    }
}
