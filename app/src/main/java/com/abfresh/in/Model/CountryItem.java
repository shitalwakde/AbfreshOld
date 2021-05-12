package com.abfresh.in.Model;

public class CountryItem {
    private String mCountryName,mCountryId;
    public CountryItem(String countryName,String countryId) {
        mCountryName = countryName;
        mCountryId =  countryId;
    }
    public String getCountryName() {
        return mCountryName;
    }

    public String getmCountryId() {
        return mCountryId;
    }
}
