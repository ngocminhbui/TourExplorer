package com.cntn14.ngocminhbui.tourexplorer.Model;

import android.graphics.Bitmap;

import com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample.Utility;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ngocminh on 5/19/17.
 */

public class Landmark {
    public int ID;
    public String Name;
    public String Url;
    public String Address;
    public double Lat;
    public double Long;
    public String Hour;
    public String Phone;
    public String ShortDescription;
    public String Description;
    public String JSON;
    public LatLng LatLng;
    public String m_imUrl;
    public float Rating;
    public Bitmap m_bm;
    public String m_distance;
    public String ImageURL;
    public boolean m_favourite;
    public ArrayList<com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample.Utility> m_utilities;
    public String UtilityJSON;

    public String LandmarkType;


    public Landmark(int ID, String name, String url, String address, double lat, double aLong, String hour, String phone, String shortDescription, String description, String JSON, String ImageURL, String UtilityJSON, String landmarkType) {
        this.ID = ID;
        Name = name;
        Url = url;
        Address = address;
        Lat = lat;
        Long = aLong;
        Hour = hour;
        Phone = phone;
        ShortDescription = shortDescription;
        Description = description;
        this.JSON = JSON;
        LatLng = new LatLng(Lat,Long);
        this.ImageURL=ImageURL;
        this.UtilityJSON = UtilityJSON;
        this.LandmarkType = landmarkType;
    }
}
