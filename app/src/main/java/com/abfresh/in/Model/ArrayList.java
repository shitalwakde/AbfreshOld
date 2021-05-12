package com.abfresh.in.Model;

public class ArrayList {

    String productName,productPrice,name,quantity,weight,catImage;
    String recipeName,recipeTypeName,recipeImage,recipeDuration,recipeServes;
    int productImage,image,productdisc,notHead,notMsg,recName;
    int sizeArray;
    String colorArray;
    String payAmount,payType,payName,payTime;
    String copId,copTitle,copMinOrder,promoCode,copDis,copImage,copValidity;
    public String getIs_in_cart() {
        return Is_in_cart;
    }

    String ProName,ProImage,ProGross,ProPrice,ProDiscount,ProductId,ProductGrosswt,Productwt,ProductStock,Is_in_cart,Cart_qty,no_of_pieces;



    public ArrayList(String recipeName, String recipeTypeName, String recipeImage, String recipeDuration, String recipeServes) {
        this.recipeName = recipeName;
        this.recipeTypeName = recipeTypeName;
        this.recipeImage = recipeImage;
        this.recipeDuration = recipeDuration;
        this.recipeServes = recipeServes;
    }

    public ArrayList( String copId, String copTitle, String copMinOrder, String promoCode, String copDis, String copImage, String copValidity) {
        this.copId = copId;
        this.copTitle = copTitle;
        this.copMinOrder = copMinOrder;
        this.promoCode = promoCode;
        this.copDis = copDis;
        this.copImage = copImage;
        this.copValidity = copValidity;
    }

    public ArrayList(String proName, String proPrice, String proImage, String proGross, String proDiscount, String productId, String productGrosswt, String productwt, String productStock, String is_in_cart, String cart_qty) {

        ProName = proName;
        ProPrice = proPrice;
        ProImage = proImage;
        ProGross = proGross;
        ProDiscount = proDiscount;
        ProductId = productId;
        ProductGrosswt = productGrosswt;
        Productwt = productwt;
        ProductStock = productStock;
        Is_in_cart = is_in_cart;
        Cart_qty = cart_qty;
    }

    public ArrayList(String proName, String proPrice, String proImage, String proGross, String proDiscount, String productId, String productGrosswt, String productwt, String productStock,String no_of_pieces, String is_in_cart, String cart_qty) {

        ProName = proName;
        ProPrice = proPrice;
        ProImage = proImage;
        ProGross = proGross;
        ProDiscount = proDiscount;
        ProductId = productId;
        ProductGrosswt = productGrosswt;
        Productwt = productwt;
        ProductStock = productStock;
        this.no_of_pieces = no_of_pieces;
        Is_in_cart = is_in_cart;
        Cart_qty = cart_qty;
    }

    public ArrayList(int productImage, int recName, int productdisc) {
        this.productImage = productImage;
        this.recName = recName;
        this.productdisc = productdisc;

    }

    public ArrayList(int productImage, String productName, int productdisc) {
        this.productImage = productImage;
        this.productName = productName;
        this.productdisc = productdisc;
    }

    public String getNo_of_pieces() {
        return no_of_pieces;
    }

    public ArrayList(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public ArrayList(String name, String catImage) {
        this.name = name;
        this.catImage = catImage;
    }

    public ArrayList(String productName, String productPrice, int productImage) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productImage = productImage;
    }

//    public ArrayList(int colorArray) {
//        this.colorArray = colorArray;
//    }

    public ArrayList(String payAmount, String payType, String payName, String payTime) {
        this.payAmount = payAmount;
        this.payType = payType;
        this.payName = payName;
        this.payTime = payTime;
    }

    public String getCopId() {
        return copId;
    }

    public String getCopTitle() {
        return copTitle;
    }

    public String getCopMinOrder() {
        return copMinOrder;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public String getCopDis() {
        return copDis;
    }
    public String getCopImage() {
        return copImage;
    }

    public String getCopValidity() {
        return copValidity;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public String getPayType() {
        return payType;
    }

    public String getPayName() {
        return payName;
    }

    public String getPayTime() {
        return payTime;
    }

    public String getProductStock() {
        return ProductStock;
    }

    public String getProductGrosswt() {
        return ProductGrosswt;
    }

    public String getProductwt() {
        return Productwt;
    }

    public ArrayList(String name, String quantity, String weight) {
        this.name = name;
        this.quantity = quantity;
        this.weight = weight;
    }

    public ArrayList(int notHead, int notMsg) {
        this.notHead = notHead;
        this.notMsg = notMsg;
    }

    public int getNotHead() {
        return notHead;
    }

    public int getNotMsg() {
        return notMsg;
    }

    public ArrayList(String colorArray) {
        this.colorArray = colorArray;
    }

    public ArrayList(int sizeArray){
        this.sizeArray = sizeArray;

}

    public String getCart_qty() {
        return Cart_qty;
    }

    public int getSizeArray() {
        return sizeArray;
    }
    public int getRecName() {
        return recName;
    }

    public String getCatImage() {
        return catImage;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public int getProductImage() {
        return productImage;
    }

    public String getColorArray() {
        return colorArray;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public int getProductdisc() {
        return productdisc;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getWeight() {
        return weight;
    }

    public String getProName() {
        return ProName;
    }

    public String getProImage() {
        return ProImage;
    }

    public String getProGross() {
        return ProGross;
    }

    public String getProPrice() {
        return ProPrice;
    }

    public String getProDiscount() {
        return ProDiscount;
    }

    public String getProductId() {
        return ProductId;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getRecipeTypeName() {
        return recipeTypeName;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public String getRecipeDuration() {
        return recipeDuration;
    }

    public String getRecipeServes() {
        return recipeServes;
    }


}
