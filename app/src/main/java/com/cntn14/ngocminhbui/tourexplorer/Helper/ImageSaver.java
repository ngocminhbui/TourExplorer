package com.cntn14.ngocminhbui.tourexplorer.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ngocminh on 5/6/17.
 */

public class ImageSaver {

    private String mDirectoryName = "images";
    private String mFileName = "image.png";
    private Context mContext;

    public ImageSaver(Context context) {
        this.mContext = context;
    }

    public ImageSaver setFileName(String fileName) {
        this.mFileName = fileName;
        return this;
    }

    public ImageSaver setDirectoryName(String directoryName) {
        this.mDirectoryName = directoryName;
        return this;
    }

    public void save(Bitmap bitmapImage) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile());
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private File createFile() {
        File directory = mContext.getDir(mDirectoryName, Context.MODE_PRIVATE);
        return new File(directory, mFileName);
    }

    public Bitmap load() {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile());
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}