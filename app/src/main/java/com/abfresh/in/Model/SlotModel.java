package com.abfresh.in.Model;

import java.util.List;

public class SlotModel {
    String date;
    private List<ChildItem> date_list;

    public SlotModel(String date, List<ChildItem> country_list) {
        this.date = date;
        this.date_list = country_list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ChildItem> getDate_list() {
        return date_list;
    }

    public void setDate_list(List<ChildItem> date_list) {
        this.date_list = date_list;
    }
}
