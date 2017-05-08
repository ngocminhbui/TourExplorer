package com.cntn14.ngocminhbui.tourexplorer.Model;

import android.graphics.Bitmap;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by ngocminh on 5/6/17.
 */
public class Landmark implements Serializable {
    public final LatLng m_latLng;
    public final String m_img;
    public final double m_star;
    public final String m_hour;
    public final String m_distance;
    public String m_name = "Nha tho duc ba sai gon";

    public Bitmap m_bm;

    public Landmark(String name, LatLng latLng, String img, int star, String hour, String distance) {
        m_name=name;
        m_latLng = latLng;
        m_img=img;
        m_star=star;
        m_hour=hour;
        m_distance=distance;
    }
}
