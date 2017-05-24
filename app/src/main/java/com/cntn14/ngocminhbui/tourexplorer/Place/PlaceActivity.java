package com.cntn14.ngocminhbui.tourexplorer.Place;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.SupportMapFragment;
import com.cntn14.ngocminhbui.tourexplorer.MapViewPager;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.cntn14.ngocminhbui.tourexplorer.Utils;

public class PlaceActivity extends AppCompatActivity implements MapViewPager.Callback {

    private ViewPager viewPager;
    private MapViewPager mvp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot);

        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setPageMargin(Utils.dp(this, 10));
        Utils.setMargins(viewPager, 0, 0, 0, Utils.getNavigationBarHeight(this));
        mvp = new MapViewPager.Builder(this)
                .mapFragment(map)
                .viewPager(viewPager)
                .position(2)
                .adapter(new PlaceAdapter(getSupportFragmentManager()))
                .callback(this)
                .build();
    }

    @Override
    public void onMapViewPagerReady() {
        mvp.getMap().setPadding(
                0,
                Utils.dp(this, 20),
                Utils.getNavigationBarWidth(this),
                viewPager.getHeight() + Utils.getNavigationBarHeight(this));
    }
}
