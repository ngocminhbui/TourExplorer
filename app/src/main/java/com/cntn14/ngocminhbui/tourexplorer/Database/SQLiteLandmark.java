package com.cntn14.ngocminhbui.tourexplorer.Database;

import android.content.Context;
import android.database.Cursor;

import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;

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

                Landmark lm = new Landmark( cs.getInt(0), cs.getString(1), cs.getString(2),cs.getString(3), cs.getDouble(4), cs.getDouble(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10),cs.getString(11),"dont have");

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
}
