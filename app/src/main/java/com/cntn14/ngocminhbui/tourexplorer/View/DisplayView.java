package com.cntn14.ngocminhbui.tourexplorer.View;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;


public class DisplayView extends SurfaceView implements SurfaceHolder.Callback {

    //region Properties

    public static final String DEBUG_TAG = "ArDisplayView Log";
    public Camera mCamera;
    private SurfaceHolder mHolder;
    private Activity mActivity;

    //endregion

    //region Constructor

    public DisplayView(Context context, Activity activity) {
        super(context);

        mActivity = activity;
        mHolder = getHolder();

        // This value is supposedly deprecated and set "automatically" when
        // needed.
        // Without this, the application crashes.
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // callbacks implemented by ArDisplayView
        mHolder.addCallback(this);
    }

    //endregion


    //region Utilities

    public void refreshCamera(Camera camera) {
        if (mHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        setCamera(camera);
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void setCamera(Camera camera) {
        //method to set a camera instance
        mCamera = camera;
    }

    //endregion

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(DEBUG_TAG, "surfaceCreated");

        // Grab the camera
        mCamera = Camera.open();

        // Set Display orientation
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);

        int rotation = mActivity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        mCamera.setDisplayOrientation((info.orientation - degrees + 360) % 360);

        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (IOException e) {
            Log.e(DEBUG_TAG, "surfaceCreated exception: ", e);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(DEBUG_TAG, "surfaceChanged");

        Camera.Parameters params = mCamera.getParameters();

        // Find an appropriate preview size that fits the surface
        List<Camera.Size> prevSizes = params.getSupportedPreviewSizes();
        for (Camera.Size s : prevSizes) {
            if ((s.height <= height) && (s.width <= width)) {
                params.setPreviewSize(s.width, s.height);
                break;
            }

        }

        // Confirm the parameters
        mCamera.setParameters(params);
        mCamera.startPreview();

        // Begin previewing
        //refreshCamera(mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(DEBUG_TAG, "surfaceDestroyed");

        // Shut down camera preview
        mCamera.stopPreview();
        mCamera.release();
    }
}
