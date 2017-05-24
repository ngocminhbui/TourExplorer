package com.cntn14.ngocminhbui.tourexplorer.Model;

import com.google.android.gms.maps.model.LatLng;

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

    public Landmark(int ID, String name, String url, String address, double lat, double aLong, String hour, String phone, String shortDescription, String description, String JSON) {
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
    }
}
