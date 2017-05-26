package com.cntn14.ngocminhbui.tourexplorer.Database;

import android.content.Context;

import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by ngocminh on 5/7/17.
 */

public class Database {


    public static ArrayList<Landmark> l=null;


    public static ArrayList<Landmark> getLandmarks(Context context){
        if (l==null){
            SQLiteLandmark sqlitelm = new SQLiteLandmark(context);
            l = sqlitelm.getListLandmark();
        }

        return  l;
    }

    public static Landmark getLandmark(Context context, int id){
        if (l==null){
            SQLiteLandmark sqlitelm = new SQLiteLandmark(context);
            l = sqlitelm.getListLandmark();
        }
        for (int i = 0; i < l.size(); i++) {
            if(l.get(i).ID==id)
                return l.get(i);
        }

        return  l.get(1);
    }

    public static ArrayList<Landmark> getFavouriteLandmark(Context context) {
        ArrayList<Landmark> fav = new ArrayList<>();


        if (l==null){
            SQLiteLandmark sqlitelm = new SQLiteLandmark(context);
            l = sqlitelm.getListLandmark();
        }

        for (int i = 0; i < l.size(); i++) {
            if(l.get(i).m_favourite==true)
                fav.add(l.get(i));
        }
        return fav;
    }
}
