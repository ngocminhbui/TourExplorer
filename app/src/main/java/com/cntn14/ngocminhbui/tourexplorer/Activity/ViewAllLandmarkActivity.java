package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListMapLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.LayoutManager.ScrollingLinearLayoutManager;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Map;

public class ViewAllLandmarkActivity extends AppCompatActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener{

    private GoogleMap mMap;

    static public ArrayList<Landmark> list_landmark;
    public RecyclerView  rv_map_landmarks;
    private int displayedposition;

    ArrayMap<Marker,Integer> marker_ids = new ArrayMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_landmark);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BindComponents();



    }
    private void BindComponents() {

        rv_map_landmarks = (RecyclerView)findViewById(R.id.rv_map_landmarks);



        ListMapLandMarkAdapter lv_landmarks_adapter = new ListMapLandMarkAdapter(this,list_landmark);

        RecyclerView.LayoutManager mLayoutManager = new ScrollingLinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false,1000);//LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        rv_map_landmarks.setLayoutManager(mLayoutManager);
        rv_map_landmarks.setItemAnimator(new DefaultItemAnimator());
        rv_map_landmarks.setAdapter(lv_landmarks_adapter);

        rv_map_landmarks.addOnScrollListener(new RecyclerView.OnScrollListener() {
                 @Override
                 public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                     super.onScrolled(recyclerView, dx, dy);

                     LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();




                     int last = displayedposition;
                     displayedposition = llm.findFirstVisibleItemPosition();

                     if (displayedposition!=last)
                     {
                         Log.i("DEBUG",String.valueOf(displayedposition));
                         mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( list_landmark.get(displayedposition).m_latLng , 18));



                     }

                     //Toast.makeText(getApplicationContext(),String.valueOf(displayedposition),Toast.LENGTH_SHORT).show();
                 }
             }

        );


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        mMap.setOnMarkerClickListener(this);
        for (int i=0;i<list_landmark.size();i++) {
            Landmark item = list_landmark.get(i);
            marker_ids.put(mMap.addMarker(new MarkerOptions().position(item.m_latLng).title(item.m_name)), new Integer(i) );
        }

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( list_landmark.get(0).m_latLng , 18));

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        rv_map_landmarks.getLayoutManager().scrollToPosition( marker_ids.get(marker) );
        marker.showInfoWindow();
        return  true;
    }
}
