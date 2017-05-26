package com.cntn14.ngocminhbui.tourexplorer.Database;

import android.content.Context;
import android.database.Cursor;

import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ngocminh on 5/19/17.
 */

public class SQLiteLandmark extends SQLiteDataController {
    public SQLiteLandmark(Context context) {
        super(context);
    }

    public ArrayList<Landmark> getListLandmark(){
        ArrayList<Landmark> listlm = new ArrayList<>();
        try{

            openDataBase();
            Cursor cs = database.rawQuery("select * from Landmark", null);
            Landmark lopHoc;
            while (cs.moveToNext()) {

                Landmark lm = new Landmark( cs.getInt(0), cs.getString(1), cs.getString(2),cs.getString(3), cs.getDouble(4), cs.getDouble(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10),cs.getString(11),cs.getString(12),cs.getString(13));

                listlm.add(lm);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {

            close();
        }
        return  listlm;
    }



    public static ArrayList<Landmark> getListLandmarkByType(ArrayList<Landmark> allLandmarks, String type){
        ArrayList<Landmark> ret = new ArrayList<>();
        for(int i=0;i<allLandmarks.size();i++)
            if (allLandmarks.get(i).LandmarkType.compareTo(type) == 0){
                ret.add(allLandmarks.get(i));
            }

        return ret;
    }

    public Landmark getLandmark(int ID) {

        ArrayList<Landmark> listlm = new ArrayList<>();
        try {

            openDataBase();
            Cursor cs = database.rawQuery("select * from Landmark where id = " + String.valueOf(ID), null);
            while (cs.moveToNext()) {

                Landmark lm = new Landmark(cs.getInt(0), cs.getString(1), cs.getString(2), cs.getString(3), cs.getDouble(4), cs.getDouble(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10), cs.getString(11), cs.getString(12),cs.getString(13));

                listlm.add(lm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            close();
        }
        if (listlm.size()==0){
            return  null;
        }else
            return listlm.get(0);
    }
}
