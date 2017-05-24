package com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.lib.BottomSheetBehaviorGoogleMapsLike;
import com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.lib.MergedAppBarLayoutBehavior;
import com.cntn14.ngocminhbui.tourexplorer.Activity.SlideshowDialogFragment;
import com.cntn14.ngocminhbui.tourexplorer.Adapter.GalleryAdapter;
import com.cntn14.ngocminhbui.tourexplorer.AppController;
import com.cntn14.ngocminhbui.tourexplorer.Interface.DirectionFinder;
import com.cntn14.ngocminhbui.tourexplorer.Interface.DirectionFinderListener;
import com.cntn14.ngocminhbui.tourexplorer.Interface.Route;
import com.cntn14.ngocminhbui.tourexplorer.Model.Image;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetTest extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static ArrayList<Landmark> list_landmark;
    public static Landmark landmark;


    TextView bottomSheetTextView;


    private String TAG = BottomSheetTest.class.getSimpleName();
    private static final String endpoint = "http://api.androidhive.info/json/glide.json";
    private ArrayList<Image> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    ItemPagerAdapter pageradapter;
    private RecyclerView photoGalleryRecyclerView;
    RecyclerView utilityRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheettest);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.gm_bottomsheet_map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(" ");
        }


        ArrayList<Utility> utilities = landmark.m_utilities;
        UtilityAdapter utilityAdapter = new UtilityAdapter((Context)this, (ArrayList<Utility>)utilities );
        utilityRecyclerView = (RecyclerView) findViewById(R.id.rv_bottomsheet_utilities);
        RecyclerView.LayoutManager utilityLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        utilityRecyclerView.setLayoutManager(utilityLayoutManager);
        utilityRecyclerView.setItemAnimator(new DefaultItemAnimator());
        utilityRecyclerView.setAdapter(utilityAdapter);



        photoGalleryRecyclerView = (RecyclerView) findViewById(R.id.rv_bottomsheet_photogallery);

        pDialog = new ProgressDialog(this);
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(getApplicationContext(), images);


        pageradapter = new ItemPagerAdapter(this,images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        photoGalleryRecyclerView.setLayoutManager(mLayoutManager);
        photoGalleryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        photoGalleryRecyclerView.setAdapter(mAdapter);

        photoGalleryRecyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(getApplicationContext(), photoGalleryRecyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", images);
                bundle.putInt("position", position);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }

            @Override
            public void onLongClick(View view, int position) {


            }
        }));

        fetchImages();
        

        /**
         * If we want to listen for states callback
         */
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_sheet);
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        routeLandmark(landmark);
                        Log.d("bottomsheet-", "STATE_COLLAPSED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        Log.d("bottomsheet-", "STATE_DRAGGING");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED:
                        Log.d("bottomsheet-", "STATE_EXPANDED");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT:
                        Log.d("bottomsheet-", "STATE_ANCHOR_POINT");
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN:
                        Log.d("bottomsheet-", "STATE_HIDDEN");
                        break;
                    default:
                        Log.d("bottomsheet-", "STATE_SETTLING");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        AppBarLayout mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Place Detail");
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        bottomSheetTextView = (TextView) bottomSheet.findViewById(R.id.tv_bottomsheet_placename);
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(pageradapter);

        behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT);

        Binding();
    }

    private void fetchImages() {
        pDialog.setMessage("Đang tải dữ liệu...");
        pDialog.show();


        JsonArrayRequest req = new JsonArrayRequest(landmark.JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        pDialog.hide();

                        images.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Image image = new Image();
                                image.setName(landmark.Name);

                                JSONObject url = object.getJSONObject("url");
                                image.setMedium(url.getString("medium"));
                                image.setSmall(url.getString("medium"));
                                image.setLarge(url.getString("medium"));
                                image.setTimestamp("hiện tại");

                                images.add(image);

                            } catch (JSONException e) {
                                Log.e(TAG, "Json parsing error: " + e.getMessage());
                            }
                        }

                        mAdapter.notifyDataSetChanged();
                        pageradapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);

    }

    private void Binding() {
        TextView tv_bottomsheet_placename = (TextView)findViewById(R.id.tv_bottomsheet_placename);
        TextView tv_bottomsheet_placenshortdescription1= (TextView)findViewById(R.id.tv_bottomsheet_placenshortdescription1);

        RatingBar  rb_bottomsheet_landmarkscore = (RatingBar)findViewById(R.id.rb_bottomsheet_landmarkscore);
        tv_bottomsheet_placename.setText(landmark.Name);
        tv_bottomsheet_placenshortdescription1.setText("Đang mở cửa");
        rb_bottomsheet_landmarkscore.setRating((float) landmark.Rating);

        FloatingActionButton fab_bottomsheet_go = (FloatingActionButton)findViewById(R.id.fab_bottomsheet_go);
        fab_bottomsheet_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri.encode("Dai hoc khoa hoc tu nhien");

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr="+Uri.encode("Dai hoc khoa hoc tu nhien")+"&daddr="+Uri.encode(BottomSheetTest.landmark.Name)));
                startActivity(intent);
            }
        });


        Button button_bottomsheet_goweb =(Button) findViewById(R.id.button_bottomsheet_goweb);
        Button button_bottomsheet_gocall = (Button)findViewById(R.id.button_bottomsheet_gocall);
        Button button_bottomsheet_openhours = (Button) findViewById(R.id.button_bottomsheet_openhours);


        button_bottomsheet_openhours.setText("Giờ mở cửa: "+landmark.Hour);

        button_bottomsheet_goweb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = landmark.Url;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        button_bottomsheet_goweb.setText("Đi tới: "+ landmark.Url);

        button_bottomsheet_gocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + landmark.Phone));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });

        button_bottomsheet_gocall.setText("Liên lạc: "+landmark.Phone);

        TextView tv_bottomsheet_placedescription = (TextView)findViewById(R.id.tv_bottomsheet_placedescription);
        tv_bottomsheet_placedescription.setText(landmark.ShortDescription+"\r\n"+landmark.Description);

        TextView tckf = (TextView)findViewById(R.id.tv_bottomsheet_placename);

        MaterialFavoriteButton favouriteButton = (MaterialFavoriteButton)findViewById(R.id.mfb_bottomsheet_favourite);
        favouriteButton.setFavorite(landmark.m_favourite);
        favouriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                landmark.m_favourite=favorite;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);



    }

    void routeLandmark(Landmark lm){
        String start = "Truong dai hoc Khoa hoc Tu nhien";
        String end = lm.Name;//String.format("%f,%f", lm.LatLng.latitude,lm.LatLng.longitude); //lm.LatLng.latitude ;
        if (start.isEmpty() || end.isEmpty()){
            Toast.makeText(getApplicationContext(),"fill", Toast.LENGTH_LONG).show();
        }
        try{
            new DirectionFinder(onDirectionFinished,start,end).execute();

        }catch (Exception e) {
            e.printStackTrace();
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom( lm.LatLng, 14));
    }

    List<Marker> startpointMarkers = new ArrayList<>();
    List<Marker> endpointMarkers =new ArrayList<>();

    List<Polyline> polyPath = new ArrayList<>();
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

                startpointMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(r.startAddress)
                        .position(r.startLocation)));

                endpointMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(r.endAddress)
                        .position(r.endLocation)));

                for (int i = 0; i < r.points.size(); i++) {
                    polylineOptions.add(r.points.get(i));
                }

                polyPath.add(mMap.addPolyline(polylineOptions));

            }
        }

    };
}
