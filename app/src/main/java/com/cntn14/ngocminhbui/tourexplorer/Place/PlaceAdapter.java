package com.nht.dtle.mtrip.Place;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.nht.dtle.mtrip.MapViewPager;

/**
 * Created by yoh on 5/7/17.
 */

public class PlaceAdapter extends MapViewPager.Adapter {
    public static final String[] SPOT_TITLES = { "Nha tho duc ba",  "Bao Tang Chung Tich Chien Tranh","Cho Ben Thanh", "Dinh Doc Lap", "Hung Vuong Plaza" };
    public static final String[] SPOT_DES = { "Nha tho duc ba", "Bao Tang Chung Tich Chien Tranh", "Cho Ben Thanh", "Dinh Doc Lap", "Hung Vuong Plaza" };
    public static final String[] SPOT_IMG = { "im_nha_tho_duc_ba",  "im_bao_tang", "im_cho_ben_thanh","im_dinh_doc_lap", "im_hung_vuong_plaza" };

    public static final CameraPosition[] POSITIONS = {
            CameraPosition.fromLatLngZoom(new LatLng(10.7797838,106.6989948), 15f),
            CameraPosition.fromLatLngZoom(new LatLng(10.7794711,106.6899383), 15f),
            CameraPosition.fromLatLngZoom(new LatLng(10.7719233,106.6961583), 15f),
            CameraPosition.fromLatLngZoom(new LatLng(10.7777346,106.6945906), 15f),
            CameraPosition.fromLatLngZoom(new LatLng(10.7560659,106.6608797), 15f)
    };

    public PlaceAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CameraPosition getCameraPosition(int position) {
        return POSITIONS[position];
    }

    @Override
    public Fragment getItem(int position) {
        return PlaceFragment.newInstance(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return SPOT_TITLES[position];
    }

    @Override
    public int getCount() {
        return SPOT_TITLES.length;
    }
}
