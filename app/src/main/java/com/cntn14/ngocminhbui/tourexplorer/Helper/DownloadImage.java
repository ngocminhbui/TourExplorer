package com.cntn14.ngocminhbui.tourexplorer.Helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;


import java.io.InputStream;

/**
 * Created by ngocminh on 5/7/17.
 */

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    private final OnTaskCompleted callback;
    Context context;
    public DownloadImage(Context context, OnTaskCompleted callback){
        this.context=context;
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... URL) {

        String imageURL = URL[0];

        Bitmap bitmap = null;
        try {
            InputStream input = new java.net.URL(imageURL).openStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        this.callback.onTaskCompleted(result);
    }
}