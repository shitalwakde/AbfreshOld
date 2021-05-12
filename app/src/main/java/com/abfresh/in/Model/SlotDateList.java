package com.abfresh.in.Model;

public class SlotDateList {

    String delivery_slots_id,delivery_slots_name,delivery_slots_time;

    public SlotDateList(String delivery_slots_id, String delivery_slots_name, String delivery_slots_time) {
        this.delivery_slots_id = delivery_slots_id;
        this.delivery_slots_name = delivery_slots_name;
        this.delivery_slots_time = delivery_slots_time;
    }

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
}
