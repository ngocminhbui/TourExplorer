package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListMapLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Interface.DirectionFinder;
import com.cntn14.ngocminhbui.tourexplorer.Interface.DirectionFinderListener;
import com.cntn14.ngocminhbui.tourexplorer.Interface.Route;
import com.cntn14.ngocminhbui.tourexplorer.LayoutManager.ScrollingLinearLayoutManager;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewAllLandmarkActivity extends AppCompatActivity implements OnMapReadyCallback , GoogleMap.OnMarkerClickListener, GoogleMap.InfoWindowAdapter, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;

    static public ArrayList<Landmark> list_landmark;
    public RecyclerView  rv_map_landmarks;
    private int displayedposition;



    ArrayMap<Marker,Integer> marker_ids = new ArrayMap<>();

    List<Marker> startpointMarkers = new ArrayList<>();
    List<Marker> endpointMarkers =new ArrayList<>();

    List<Polyline> polyPath = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_landmark);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BindComponents();



    }


    DirectionFinderListener onDirectionFinished = new DirectionFinderListener() {
        @Override
        public void onDirectionFinderStart() {

            for(Marker mar: startpointMarkers)
                mar.remove();
            for(Marker mar: endpointMarkers)
                mar.remove();
            for(Polyline mar: polyPath)
                mar.remove();
        }

        @Override
        public void onDirectionFinderSuccess(List<Route> route) {
            for (Route r:route){


                PolylineOptions polylineOptions = new PolylineOptions()
                        .geodesic(true)
                        .color(Color.BLUE)
                        .width(10);

                for (int i = 0; i < r.points.size(); i++) {
                    polylineOptions.add(r.points.get(i));
                }

                polyPath.add(mMap.addPolyline(polylineOptions));

            }
        }

    };

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

                         Landmark lm = list_landmark.get(displayedposition);

                         routeLandmark(lm);


                     }

                     //Toast.makeText(getApplicationContext(),String.valueOf(displayedposition),Toast.LENGTH_SHORT).show();
                 }
             }

        );

    }
    void routeLandmark(Landmark lm){
        String start = "Truong dai hoc Khoa hoc Tu nhien";
        String end = lm.m_name;//String.format("%f,%f", lm.m_latLng.latitude,lm.m_latLng.longitude); //lm.m_latLng.latitude ;
        if (start.isEmpty() || end.isEmpty()){
            Toast.makeText(getApplicationContext(),"fill", Toast.LENGTH_LONG).show();
        }
        try{
            new DirectionFinder(onDirectionFinished,start,end).execute();

        }catch (Exception e) {
            e.printStackTrace();
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( lm.m_latLng , 14));
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
        mMap.setInfoWindowAdapter(this);
        mMap.setOnInfoWindowClickListener(this);

        routeLandmark(list_landmark.get(0));
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        rv_map_landmarks.getLayoutManager().scrollToPosition( marker_ids.get(marker) );
        marker.showInfoWindow();
        return  true;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return  null;
    }

    @Override
    public View getInfoContents(Marker marker) {

        TextView tv_inforwindow_placename;
        ImageView iv_inforwindow_landmarkimg;
        Button bt_inforwindow_seemore;

        final Landmark lm =list_landmark.get(marker_ids.get(marker));

        View v = getLayoutInflater().inflate(R.layout.place_infor_window, null);
        tv_inforwindow_placename = (TextView)v.findViewById(R.id.tv_inforwindow_placename);
        iv_inforwindow_landmarkimg =(ImageView)v.findViewById(R.id.iv_inforwindow_landmarkimg);

        tv_inforwindow_placename.setText(lm.m_name);
        iv_inforwindow_landmarkimg.setImageBitmap(lm.m_bm);

        return v;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        final Landmark lm =list_landmark.get(marker_ids.get(marker));
        PlaceDetailActivity.list_landmark = ViewAllLandmarkActivity.this.list_landmark;
        PlaceDetailActivity.landmark= lm;
        Intent intent = new Intent(ViewAllLandmarkActivity.this, PlaceDetailActivity.class);
        ViewAllLandmarkActivity.this.startActivity(intent);

    }


}
