/*

package com.cntn14.ngocminhbui.tourexplorer.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.text.TextPaint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import com.cntn14.ngocminhbui.tourexplorer.Helper.ImageSaver;
import com.cntn14.ngocminhbui.tourexplorer.Model.GlobalSetting;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Vector;

public class OverlayView extends View implements SensorEventListener, LocationListener {

    public static final String DEBUG_TAG = "OverlayView Log";

    private final Context context;
    private Handler handler;
    private int indexSelect;

    public ArrayList<AppLocation> mylocations;

    String accelData = "Accelerometer Data";
    String compassData = "Compass Data";
    String gyroData = "Gyro Data";
    String rotateData = "Rotate Data";
    String orientationData = "Orientation Data";

    private boolean isAccelAvailable;
    private boolean isCompassAvailable;
    private boolean isGyroAvailable;
    private boolean isOrientationAvailable;
    private boolean isRotateAvailable;

    private LocationManager locationManager = null;
    private SensorManager sensors = null;

    private Location lastLocation;
    private float[] lastAccelerometer;
    private float[] lastCompass;

    private float verticalFOV;
    private float horizontalFOV;

    private Sensor accelSensor;
    private Sensor compassSensor;
    private Sensor gyroSensor;
    private Sensor rotateSensor;

    private TextPaint contentPaint;
    private TextPaint titlePaint;

    private Paint targetPaint;
    private boolean notifyLocationChange;

    public OverlayView(Context context) {
        super(context);
        notifyLocationChange = true;
        this.context = context;
        this.handler = new Handler();

        locationManager = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);

        sensors = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);

        accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        compassSensor = sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        //gyroSensor = sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        //rotateSensor = sensors.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        startSensors();
        startGPS();

        // get some camera parameters
        Camera camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
        verticalFOV = params.getVerticalViewAngle();
        horizontalFOV = params.getHorizontalViewAngle();
        camera.release();

        SetUpPaint();
    }

    public void SetUpPaint() {
        // paint for text
        contentPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        contentPaint.setTextAlign(Paint.Align.LEFT);
        contentPaint.setTextSize(18);
        contentPaint.setColor(Color.BLACK);
        contentPaint.setStrokeWidth(10);

        titlePaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        titlePaint.setTextAlign(Paint.Align.LEFT);
        titlePaint.setTextSize(22);
        titlePaint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        titlePaint.setStrokeWidth(20);

        // paint for target

        targetPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        targetPaint.setARGB(200, 245, 245, 245);
    }




    private void startSensors() {
        isAccelAvailable = sensors.registerListener(this, accelSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        isCompassAvailable = sensors.registerListener(this, compassSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        //isGyroAvailable = sensors.registerListener(this, gyroSensor,
        //        SensorManager.SENSOR_DELAY_NORMAL);
        //isRotateAvailable = sensors.registerListener(this, rotateSensor,
        //        SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void startGPS() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String best = locationManager.getBestProvider(criteria, true);

        Log.v(DEBUG_TAG, "Best provider: " + best);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(best, 50, 0, this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        try {
            int locationSize = mylocations.size();
            if (notifyLocationChange) {
                ClearAllLocation();
                // Draw something fixed (for now) over the camera view
                for (int i = 0; i < locationSize; i++) {
                    DoWhenLocationChange(i, canvas);
                }
            } else {
                for (int i = 0; i < locationSize; i++) {
                    DoWhileLocationNoChange(i, canvas);
                }
            }
            notifyLocationChange = false;
            canvas.restore();
            canvas.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoWhileLocationNoChange(int i, Canvas canvas) {
        float curBearingToMW = 0.0f;
        float distanceToMW = 0.0f;
        String distanceText;
        float rotation[] = new float[9];
        float identity[] = new float[9];

        if (lastAccelerometer != null && lastCompass != null) {
            boolean gotRotation = SensorManager.getRotationMatrix(rotation,
                    identity, lastAccelerometer, lastCompass);
            if (gotRotation) {
                float cameraRotation[] = new float[9];
                SensorManager.remapCoordinateSystem(rotation,
                        SensorManager.AXIS_X, SensorManager.AXIS_Z,
                        cameraRotation);

                float orientation[] = new float[3];
                SensorManager.getOrientation(cameraRotation, orientation);

                if (lastLocation != null) {
                    Log.d(DEBUG_TAG, "Last location: " + lastLocation.getLatitude() + " " + lastLocation.getLongitude());

                    curBearingToMW = lastLocation.bearingTo(GlobalSetting.currentLocation);

                    distanceToMW = lastLocation.distanceTo(GlobalSetting.currentLocation) / 1000;
                }

                float dx = (float) ((canvas.getWidth() / horizontalFOV) * (Math.toDegrees(orientation[0]) - curBearingToMW));

                Bitmap bm = new ImageSaver(context)
                        .setDirectoryName(AppConstant.FB_AVATAR_DIRECTORY)
                        .setFileName(GlobalSetting.currentLocation.toString()+ (new Time().toString()))
                        .load();
                bm = Bitmap.createScaledBitmap(bm, AppConstant.AR_AVATAR_WIDTH, AppConstant.AR_AVATAR_HEIGHT, false);

                float xCoodinate = canvas.getWidth() / 2 - dx;
                float yCoodinate = mylocations.get(i).RecForChange.Top;

                // Draw avatar
                canvas.drawBitmap(bm, xCoodinate, yCoodinate, targetPaint);

                // Draw content rectangle
                canvas.drawRect(
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH,
                        yCoodinate,
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + AppConstant.AR_RECT_WIDTH,
                        yCoodinate + AppConstant.AR_RECT_HEIGHT,
                        mylocations.get(i).BackGround
                );

                String textfilter = mylocations.get(i).Name;
                if (mylocations.get(i).Name.length() > 16) {
                    textfilter = mylocations.get(i).Name.substring(0, 15) + "...";
                }

                // Draw text content
                canvas.drawText(
                        textfilter,
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + 10,
                        yCoodinate + AppConstant.AR_FIRST_LINE_HEIGHT,
                        titlePaint);

                canvas.drawText(
                        "Distance: " + (double) Math.round(distanceToMW * 100) / 100 + " km",
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + 10,
                        yCoodinate + AppConstant.AR_SECOND_LINE_HEIGHT,
                        contentPaint
                );

                mylocations.get(i).updateRec(
                        xCoodinate,
                        yCoodinate,
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + AppConstant.AR_RECT_WIDTH,
                        yCoodinate + AppConstant.AR_AVATAR_HEIGHT);
            }
        }
    }

    public void DoWhenLocationChange(int i, Canvas canvas) {
        float curBearingToMW = 0.0f;
        float distanceToMW = 0.0f;
        // Get distance
        if (lastLocation != null) {
            Log.d(DEBUG_TAG, "Last location: " + lastLocation.getLatitude() + " " + lastLocation.getLongitude());
            Location tmp = new Location("manual");
            tmp.setLongitude(mylocations.get(i).Longitude);
            tmp.setLatitude(mylocations.get(i).Latitude);
            curBearingToMW = lastLocation.bearingTo(tmp);

            distanceToMW = lastLocation.distanceTo(tmp) / 1000;
        }
        // compute rotation matrix
        float rotation[] = new float[9];
        float identity[] = new float[9];
        if (lastAccelerometer != null && lastCompass != null) {
            boolean gotRotation = SensorManager.getRotationMatrix(rotation,
                    identity, lastAccelerometer, lastCompass);
            if (gotRotation) {
                float cameraRotation[] = new float[9];
                // remap such that the camera is pointing straight down the Y
                // axis
                SensorManager.remapCoordinateSystem(rotation,
                        SensorManager.AXIS_X, SensorManager.AXIS_Z,
                        cameraRotation);

                // orientation vector
                float orientation[] = new float[3];
                SensorManager.getOrientation(cameraRotation, orientation);


                // draw horizon line (a nice sanity check piece) and the target (if it's on the screen)
                canvas.save();
                // use roll for screen rotation

                // Translate, but normalize for the FOV of the camera -- basically, pixels per degree, times degrees == pixels
                float dx = (float) ((canvas.getWidth() / horizontalFOV) * (Math.toDegrees(orientation[0]) - curBearingToMW));

                // wait to translate the dx so the horizon doesn't get pushed off
                float xCoodinate = canvas.getWidth() / 2;
                float yCoodinate = canvas.getHeight() / 2 - 200;

                // translate the dx
                xCoodinate -= dx;

                int distanceToChange = 0;
                while (true) {
                    //check 4 angle
                    if (checkDumplicate(xCoodinate, yCoodinate - distanceToChange) || checkDumplicate(xCoodinate + 350, yCoodinate - distanceToChange)) {
                        distanceToChange += 100;
                        continue;
                    }
                    yCoodinate -= distanceToChange;
                    break;
                }
                // draw our point -- we've rotated and translated this to the right spot already

                Bitmap bm = new ImageSaver(context)
                        .setDirectoryName(AppConstant.FB_AVATAR_DIRECTORY)
                        .setFileName(mylocations.get(i).CurrentPlace.imagePath)
                        .load();

                // Create scale bitmap
                bm = Bitmap.createScaledBitmap(bm, AppConstant.AR_AVATAR_WIDTH, AppConstant.AR_AVATAR_HEIGHT, false);

                // Draw avatar
                canvas.drawBitmap(bm, xCoodinate, yCoodinate, targetPaint);

                // Draw rectangle content
                canvas.drawRect(
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH,
                        yCoodinate,
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + AppConstant.AR_RECT_WIDTH,
                        yCoodinate + AppConstant.AR_AVATAR_HEIGHT,
                        mylocations.get(i).BackGround
                );

                // Cut off text
                String textfilter = mylocations.get(i).Name;
                if (mylocations.get(i).Name.length() > 16) {
                    textfilter = mylocations.get(i).Name.substring(0, 15) + "...";
                }

                // Draw text content
                canvas.drawText(
                        textfilter,
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + 10,
                        yCoodinate + AppConstant.AR_FIRST_LINE_HEIGHT,
                        titlePaint
                );

                canvas.drawText(
                        "Distance: " + (double) Math.round(distanceToMW * 100) / 100 + " km",
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + 10,
                        yCoodinate + AppConstant.AR_SECOND_LINE_HEIGHT,
                        contentPaint
                );

                // Update
                mylocations.get(i).updateRec(
                        xCoodinate,
                        yCoodinate,
                        xCoodinate + AppConstant.AR_AVATAR_WIDTH + AppConstant.AR_RECT_WIDTH,
                        yCoodinate + AppConstant.AR_AVATAR_HEIGHT);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_UP) {
            onTouchUpEvent(x, y);
        }
        return true;
    }

    // this is not an override
    public void onPause() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(this);
        sensors.unregisterListener(this);
    }

    // this is not an override
    public void onResume() {
        startSensors();
        startGPS();
    }

    private void onTouchUpEvent(float x, float y) {
        int size = mylocations.size();
        for (int i = 0; i < size; i++) {
            if (mylocations.get(i).CheckOn(x, y)) {
                startActivityToViewDetail(mylocations.get(i).CurrentPlace);
            }
        }
    }

    private boolean checkDumplicate(float x, float y) {
        int size = mylocations.size();
        for (int i = 0; i < size; i++) {
            if (mylocations.get(i).CheckDumLocation(x, y)) {
                return true;
            }
        }
        return false;
    }

    private void ClearAllLocation() {
        int locationSize = mylocations.size();
        for (int i = 0; i < locationSize; i++) {
            mylocations.get(i).RecForChange.Clear();
        }
    }

    private void startActivityToViewDetail(Place currentPlace) {
        // Nothing to do
    }

    @Override
    public void onLocationChanged(Location location) {
        // store it off for use when we need it
        Log.d(DEBUG_TAG, "onLocationChange");
        notifyLocationChange = true;
        lastLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Nothing to do
    }

    @Override
    public void onProviderEnabled(String provider) {
        // Nothing to do
    }

    @Override
    public void onProviderDisabled(String provider) {
        // Nothing to do
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        StringBuilder msg = new StringBuilder(event.sensor.getName())
                .append(" ");
        for (float value : event.values) {
            msg.append("[").append(String.format("%.3f", value)).append("]");
        }

        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                lastAccelerometer = event.values.clone();
                accelData = msg.toString();
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroData = msg.toString();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                lastCompass = event.values.clone();
                compassData = msg.toString();
                break;
            case Sensor.TYPE_ROTATION_VECTOR:
                rotateData = msg.toString();
                break;
            case Sensor.TYPE_ORIENTATION:
                orientationData = msg.toString();
                break;
        }

        this.invalidate();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
*/
