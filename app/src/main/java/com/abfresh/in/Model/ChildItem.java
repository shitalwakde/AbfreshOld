package com.abfresh.in.Model;


public class ChildItem {

    // Declaration of the variable
    private String ChildItemTitle,delivery_slots_name,is_disable;

    // Constructor of the class
    // to initialize the variable*
//    public ChildItem(String childItemTitle)
//    {
//        this.ChildItemTitle = childItemTitle;
//    }

    public ChildItem(String childItemTitle, String delivery_slots_name,String is_disable) {
        this.ChildItemTitle = childItemTitle;
        this.delivery_slots_name = delivery_slots_name;
        this.is_disable = is_disable;

    }

    // Getter and Setter method
    // for the parameter
    public String getChildItemTitle()
    {
        return ChildItemTitle;
    }

    public void setChildItemTitle(
            String childItemTitle)
    {
        ChildItemTitle = childItemTitle;
    }

    public String getDelivery_slots_name() {
        return delivery_slots_name;
    }

    public void setDelivery_slots_name(String delivery_slots_name) {
        this.delivery_slots_name = delivery_slots_name;
    }

    public String getIs_disable() {
        return is_disable;
    }

    public void setIs_disable(String is_disable) {
        this.is_disable = is_disable;
    }
}
