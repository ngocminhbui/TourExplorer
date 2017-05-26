package com.cntn14.ngocminhbui.tourexplorer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alamkanak.weekview.WeekViewEvent;
import com.cntn14.ngocminhbui.tourexplorer.Activity.TripPlanner.MyWeekViewEvent;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by ngocminh on 5/25/17.
 */

public class SQLiteWeekSchedule extends SQLiteDataController {
    Context m_context;
    public SQLiteWeekSchedule(Context context) {
        super(context);
        m_context=context;
    }

    public boolean create(MyWeekViewEvent object) {

        ContentValues values = new ContentValues();

        values.put("ID", object.getId());
        values.put("Name", object.getName());
        values.put("StartTime",object.getStartTime().getTimeInMillis());
        values.put("EndTime",object.getEndTime().getTimeInMillis());
        values.put("Location", object.getLocation());
        values.put("Color",object.getColor());
        values.put("LandmarkID", object.mLandmark.ID);
        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("WeekSchedule", null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public ArrayList<MyWeekViewEvent> getListEvent(){
        ArrayList<MyWeekViewEvent> listevents = new ArrayList<>();
        SQLiteLandmark sqLiteLandmark = new SQLiteLandmark(m_context);
        try{

            openDataBase();
            Cursor cs = database.rawQuery("select * from WeekSchedule", null);
            WeekViewEvent event;
            while (cs.moveToNext()) {

                MyWeekViewEvent lm = new MyWeekViewEvent(); //new WeekViewEvent( cs.getInt(0), cs.getString(1), cs.getString(2),cs.getString(3), cs.getDouble(4), cs.getDouble(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10),cs.getString(11),cs.getString(12));


                Calendar startTime = Calendar.getInstance();
                startTime.setTimeInMillis(cs.getLong(2));

                Calendar endTime = Calendar.getInstance();
                endTime.setTimeInMillis(cs.getLong(3));


                lm.setId(cs.getInt(0));
                lm.setStartTime(startTime);
                lm.setEndTime(endTime);
                lm.setName(cs.getString(1));
                lm.setLocation(cs.getString(4));
                lm.setColor(cs.getInt(5));


                Landmark k = sqLiteLandmark.getLandmark(cs.getInt(6));
                lm.mLandmark = k;

                listevents.add(lm);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }finally {

            close();
        }
        return  listevents;
    }

}
