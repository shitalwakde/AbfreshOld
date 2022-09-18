package com.abfresh.in.Abfresh.model;

import java.util.ArrayList;

public class DeliverySlotsModel {
    String delivery_slots_id;
    String delivery_slots_name;
    String delivery_slots_time;
    String is_disable;
    String date;
    ArrayList<DeliverySlotsModel> list;

    public String getDelivery_slots_id() {
        return delivery_slots_id;
    }

    public void setDelivery_slots_id(String delivery_slots_id) {
        this.delivery_slots_id = delivery_slots_id;
    }

    public String getDelivery_slots_name() {
        return delivery_slots_name;
    }

    public void setDelivery_slots_name(String delivery_slots_name) {
        this.delivery_slots_name = delivery_slots_name;
    }

    public String getDelivery_slots_time() {
        return delivery_slots_time;
    }

    public void setDelivery_slots_time(String delivery_slots_time) {
        this.delivery_slots_time = delivery_slots_time;
    }

    public String getIs_disable() {
        return is_disable;
    }

    public void setIs_disable(String is_disable) {
        this.is_disable = is_disable;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<DeliverySlotsModel> getList() {
        return list;
    }

    public void setList(ArrayList<DeliverySlotsModel> list) {
        this.list = list;
    }

}
