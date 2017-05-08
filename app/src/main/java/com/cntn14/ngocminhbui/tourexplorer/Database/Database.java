package com.cntn14.ngocminhbui.tourexplorer.Database;

import android.location.Location;

import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.google.android.gms.maps.model.LatLng;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ngocminh on 5/7/17.
 */

public class Database {
    public static ArrayList<Landmark> getLandmarks(){
        ArrayList<Landmark> l = new ArrayList<>();
        l.add(new Landmark("Nhà thờ đức bà", new LatLng(10.7797891,106.6968008), "https://lh4.googleusercontent.com/-ZLah2Ch8SvI/V77TPWOhh8I/AAAAAAAA9ZU/aYqs0LgkVJ8zSq6OMAX8wvbzFEVV5CsBQCLIB/w408-h271-k-no/" , 4, "7h30-15h30","3.5km"));
        l.add(new Landmark("Dinh độc lập", new LatLng(10.7771599,106.6931468), "https://lh6.googleusercontent.com/-sJspMH9wU04/V7_KT3a7CcI/AAAAAAAAKqw/d3NCZhTnypcUI2U8d98QdPz7RZM9nLo-gCLIB/w408-h271-k-no/", 3, "7h30-14h","4.5km"));
        l.add(new Landmark("Phố Nguyễn Huệ", new LatLng(10.7747954,106.7006726), "https://lh3.googleusercontent.com/-F0KORaz2ElI/V-MbOKr-S9I/AAAAAAALOQs/vVkclHduDogdT85Bip5Mx6Nps-29DJy2ACLIB/w408-h200-k-no-pi-6.941883-ya97.5-ro-0-fo100/", 5, "19h-23h","5.5km"));
        l.add(new Landmark("Chợ Bến Thành", new LatLng(10.7725795,106.6958337), "https://lh5.googleusercontent.com/-0-nGKRS5imY/WAZE2rdLy7I/AAAAAAAAGo8/xkuM5sekmtgQAZkpAaaaXYUAiWf28-qRwCLIB/w408-h270-k-no/", 1, "19-h22","2.5km"));

        return l;
    }

}
