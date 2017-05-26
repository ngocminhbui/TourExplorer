package com.cntn14.ngocminhbui.tourexplorer.ARDirection;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cntn14.ngocminhbui.tourexplorer.ARHelper.AugmentedPOI;
import com.cntn14.ngocminhbui.tourexplorer.ARHelper.MyCurrentAzimuth;
import com.cntn14.ngocminhbui.tourexplorer.ARHelper.MyCurrentLocation;
import com.cntn14.ngocminhbui.tourexplorer.ARHelper.OnAzimuthChangedListener;
import com.cntn14.ngocminhbui.tourexplorer.ARHelper.OnLocationChangedListener;
import com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample.BottomSheetTest;
import com.cntn14.ngocminhbui.tourexplorer.Database.Database;
//import com.cntn14.ngocminhbui.tourexplorer.PlaceDetail.PlaceDetailActiviy;
import com.cntn14.ngocminhbui.tourexplorer.Activity.PlaceDetailActivity;

import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ARDirection extends AppCompatActivity implements SurfaceHolder.Callback, OnLocationChangedListener, OnAzimuthChangedListener {

    private static final float MAX_DIST = 4500; //m
    Camera mCamera;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    Integer SurWidth, SurHeight, PicWidth, PicHeight;
    RelativeLayout rlRoot;
    ArrayList<View> infoBox = new ArrayList<>();

    private double mAzimuthReal = 0;
    private double mAzimuthTeoretical = 0;
    private static double AZIMUTH_ACCURACY = 30;
//    private double mMyLatitude = 10.7533179;
//    private double mMyLongitude = 106.6586053;
    private double mMyLatitude = 0;
    private double mMyLongitude = 0;

    private MyCurrentAzimuth myCurrentAzimuth;
    private MyCurrentLocation myCurrentLocation;

    ArrayList<AugmentedPOI> ranges = new ArrayList<>();
    ArrayList<AugmentedPOI> ranges_r = new ArrayList<>();
    ArrayList<Landmark> places = new ArrayList<>();
    ArrayList<Landmark> places_r = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ardirection);
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location!=null){
                mMyLongitude = location.getLongitude();
                mMyLatitude = location.getLatitude();
            }
        }

        InitComponents();
        LoadData();
        InitItemLayout();
    }

    private void InitComponents() {
        mSurfaceView = (SurfaceView) findViewById(R.id.ardir_surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        myCurrentLocation = new MyCurrentLocation(this);
        myCurrentLocation.buildGoogleApiClient(this);
        myCurrentLocation.start();

        myCurrentAzimuth = new MyCurrentAzimuth(this, this);
        myCurrentAzimuth.start();

        rlRoot = (RelativeLayout) findViewById(R.id.ardir_root_layout);
    }

    private void LoadData() {
        places = Database.getLandmarks(this);
        for (int i = 0; i < places.size(); i++) {
            double midAzimuth = calculateTeoreticalAzimuth(places.get(i));
            double minAzimuth = calculateAzimuthAccuracy(midAzimuth).get(0);
            double maxAzimuth = calculateAzimuthAccuracy(midAzimuth).get(0);

            ranges.add(new AugmentedPOI(i, midAzimuth, minAzimuth, maxAzimuth));
            Log.d(places.get(i).Name, String.valueOf(midAzimuth));
        }
    }

    private void InitItemLayout() {
        for (int i = 0; i < places.size(); i++) {

            float[] dist = new float[1];
            Location.distanceBetween(mMyLatitude, mMyLongitude,
                    places.get(i).LatLng.latitude, places.get(i).LatLng.longitude, dist);
            Log.d(places.get(i).Name, String.valueOf(mMyLatitude));
            if (dist[0] > MAX_DIST) {
                continue;
            }
            places_r.add(places.get(i));
            ranges_r.add(ranges.get(i));
            View layout = LayoutInflater.from(this).inflate(R.layout.ardir_item_layout, rlRoot, false);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
            params.setMargins(i * 10, i * 10, 0, 0);
            rlRoot.addView(layout, params);
            infoBox.add(layout);

            ImageView ivAvatar = (ImageView) layout.findViewById(R.id.iv_ardir_avatar);
            Picasso.with(this)
                    .load(places.get(i).ImageURL)
                    .resize(60, 60)
                    .centerCrop()
                    .into(ivAvatar);
            TextView tvName = (TextView) layout.findViewById(R.id.tv_ardir_name);
            tvName.setText(places.get(i).Name);

            final int finalI = i;
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BottomSheetTest.landmark = places.get(finalI);
                    Intent intent = new Intent(ARDirection.this, BottomSheetTest.class);
                    startActivity(intent);
                }
            });
        }

    }

    public double calculateTeoreticalAzimuth(Landmark mlandmark) {
        double dX = mlandmark.LatLng.latitude - mMyLatitude;
        double dY = mlandmark.LatLng.longitude - mMyLongitude;

        double phiAngle;
        double tanPhi;
        double azimuth = 0;

        tanPhi = Math.abs(dY / dX);
        phiAngle = Math.atan(tanPhi);
        phiAngle = Math.toDegrees(phiAngle);

        if (dX > 0 && dY > 0) { // I quater
            return azimuth = phiAngle;
        } else if (dX < 0 && dY > 0) { // II
            return azimuth = 180 - phiAngle;
        } else if (dX < 0 && dY < 0) { // III
            return azimuth = 180 + phiAngle;
        } else if (dX > 0 && dY < 0) { // IV
            return azimuth = 360 - phiAngle;
        }

        return phiAngle;
    }

    private List<Double> calculateAzimuthAccuracy(double azimuth) {
        double minAngle = azimuth - AZIMUTH_ACCURACY;
        double maxAngle = azimuth + AZIMUTH_ACCURACY;
        List<Double> minMax = new ArrayList<Double>();

        if (minAngle < 0)
            minAngle += 360;

        if (maxAngle >= 360)
            maxAngle -= 360;

        minMax.clear();
        minMax.add(minAngle);
        minMax.add(maxAngle);

        return minMax;
    }

    @Override
    public void onLocationChanged(Location location) {
        mMyLatitude = location.getLatitude();
        mMyLongitude = location.getLongitude();
        updateTeoreticalAzimuth();


    }

    private void updateTeoreticalAzimuth() {
        for (int i = 0; i < places_r.size(); i++) {
            double midAzimuth = calculateTeoreticalAzimuth(places_r.get(i));
            double minAzimuth = calculateAzimuthAccuracy(midAzimuth).get(0);
            double maxAzimuth = calculateAzimuthAccuracy(midAzimuth).get(1);
            AugmentedPOI r = ranges_r.get(i);
            r.setTeoreticalAzimuth(midAzimuth);
            r.setMinRange(minAzimuth);
            r.setMaxRange(maxAzimuth);
        }
    }

    @Override
    public void onAzimuthChanged(float azimuthChangedTo) {
        mAzimuthReal = azimuthChangedTo;

        for (int i = 0; i < ranges_r.size(); i++) {
            View layout = infoBox.get(i);
            if (ranges_r.get(i).isInside(mAzimuthReal)) {
//                Log.d("Entered", places.get(i).getName());
                Log.d("Your azimuth", String.valueOf(mAzimuthReal));
                Log.d("Left - Right - Mid", String.valueOf(ranges_r.get(i).getMinRange()) + " - " + String.valueOf(ranges_r.get(i).getMaxRange()) + " - " + String.valueOf(ranges_r.get(i).getTeoreticalAzimuth()));


                float[] dist = new float[1];
                Location.distanceBetween(mMyLatitude, mMyLongitude,
                        places_r.get(i).LatLng.latitude, places_r.get(i).LatLng.longitude, dist);


                if (dist[0] > MAX_DIST) {
                    layout.setVisibility(View.INVISIBLE);
                    continue;
                }
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
                double diffAngle = Math.abs(mAzimuthReal - ranges_r.get(i).getTeoreticalAzimuth());
                int scrWidth = getResources().getDisplayMetrics().widthPixels;
                int leftMar = (int) ((scrWidth / 2) / (AZIMUTH_ACCURACY - 10) * diffAngle) - params.width / 2;
                if (mAzimuthReal < ranges_r.get(i).getTeoreticalAzimuth())
                    leftMar += scrWidth / 2;
                else leftMar = scrWidth / 2 - leftMar;
                params.setMargins(leftMar, i * 200 + 20, -400, 0);
                layout.setLayoutParams(params);
                layout.setVisibility(View.VISIBLE);


                TextView tvDistance = (TextView) layout.findViewById(R.id.tv_ardir_distance);
                dist[0] = dist[0] / 1000;
                tvDistance.setText(String.format("%.1f km", dist[0]));

            } else layout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        myCurrentAzimuth.stop();
        myCurrentLocation.stop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCurrentAzimuth.start();
        myCurrentLocation.start();
    }


    /**--- Setup for SurfaceView and Camera ---**/

    public Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public void refreshCamera() {
        if (mSurfaceHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
        }

        try {
            Camera.Parameters parameters = mCamera.getParameters();
            List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
            Camera.Size optimalSize = getOptimalPreviewSize(sizes, getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
            parameters.setPreviewSize(optimalSize.width, optimalSize.height);
            parameters.setPictureSize(1920, 1080);

            SurWidth = optimalSize.width;
            SurHeight = optimalSize.height;
            PicWidth = 1920;
            PicHeight = 1080;

            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
        } catch (RuntimeException e) {
            System.err.println(e);
            return;
        }

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            System.err.println(e);
            return;
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }


}