package com.cntn14.ngocminhbui.tourexplorer;

import com.cntn14.ngocminhbui.tourexplorer.Place.Place;

import java.util.ArrayList;

/**
 * Created by yoh on 5/10/17.
 */

public class LocalDatabase {
    public static ArrayList<Place> list_place = getPlaceData();
    public static ArrayList<Place> getPlaceData(){
        ArrayList<Place> l = new ArrayList<>();
        l.add(new Place("Nha Tho Duc Ba",
                10.7797838,106.6989948,
                "https://dotrongle.github.io/places_thumb/im_nha_tho_duc_ba.jpg",
                "im_nha_tho_duc_ba",
                "Gioi thieu Nha tho duc ba",
                "0812345678",
                "http://www.vietnamtourism.gov.vn"
                ));
        l.add(new Place("Bao Tang Chung Tich Chien Tranh", 10.7794711,106.6899383,"https://dotrongle.github.io/places_thumb/im_bao_tang_chien_tranh.jpg",
                "im_bao_tang",
                "Gioi thieu Nha tho duc ba",
                "0812345678",
                "http://www.vietnamtourism.gov.vn"));
        l.add(new Place("Cho Ben Thanh", 10.7719233,106.6961583,"https://dotrongle.github.io/places_thumb/im_cho_ben_thanh.jpg",
                "im_cho_ben_thanh",
                "Gioi thieu Nha tho duc ba",
                "0812345678",
                "http://www.vietnamtourism.gov.vn"));
        l.add(new Place("Dinh Doc Lap", 10.7777346,106.6945906,"https://dotrongle.github.io/places_thumb/im_dinh_doc_lap.jpg",
                "im_dinh_doc_lap",
                "Gioi thieu Nha tho duc ba",
                "0812345678",
                "http://www.vietnamtourism.gov.vn"));
        l.add(new Place("Hung Vuong Plaza", 10.7560659,106.6608797,"https://dotrongle.github.io/places_thumb/im_hung_vuong_plaza.jpg",
                "im_hung_vuong_plaza",
                "Gioi thieu Nha tho duc ba",
                "0812345678",
                "http://www.vietnamtourism.gov.vn"));
        return l;
    }
}
