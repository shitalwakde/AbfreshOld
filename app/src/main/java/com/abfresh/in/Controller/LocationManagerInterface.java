package com.abfresh.in.Controller;

import android.location.Location;

public interface LocationManagerInterface {
    String TAG = LocationManagerInterface.class.getSimpleName();

    void locationFetched(Location mLocation, Location oldLocation, String time, String locationProvider);

}