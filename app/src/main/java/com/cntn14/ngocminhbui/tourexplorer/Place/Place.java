package com.nht.dtle.mtrip.Place;

import java.util.List;

/**
 * Created by yoh on 5/10/17.
 */

public class Place {
    private String name;
    private double latitude;
    private double longitude;
    private String imUrl;
    private String imLocal;
    private String description;
    private String phone;
    private String webUrl;
    public Place(String name, double latitude, double longitude, String imUrl){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imUrl = imUrl;

    }
    public Place(String name, double latitude, double longitude, String imUrl, String imLocal, String description, String phone, String webUrl){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imUrl = imUrl;
        this.imLocal = imLocal;
        this.description = description;
        this.phone = phone;
        this.webUrl = webUrl;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImUrl() {
        return imUrl;
    }

    public void setImUrl(String imUrl) {
        this.imUrl = imUrl;
    }

    public String getImLocal() {
        return imLocal;
    }

    public void setImLocal(String imLocal) {
        this.imLocal = imLocal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }
}
