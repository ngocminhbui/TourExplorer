package com.cntn14.ngocminhbui.tourexplorer.Model;

import android.location.Location;

/**
 * Created by ngocminh on 5/6/17.
 */

public class GlobalSetting {
    public static Location currentLocation = new Location("DF");
    static boolean isLocationEnabled=false;

    GlobalSetting(){
        currentLocation.setLatitude(10.7527459);
        currentLocation.setLongitude(106.6630288);

    }

    static public Location getCurrentLocation() {
        return currentLocation;
    }
}
