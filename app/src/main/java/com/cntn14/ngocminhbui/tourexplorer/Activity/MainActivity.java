package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.cntn14.ngocminhbui.tourexplorer.Activity.TripPlanner.ViewCalendarActivity;
import com.cntn14.ngocminhbui.tourexplorer.Database.SQLiteDataController;
import com.cntn14.ngocminhbui.tourexplorer.Database.SQLiteLandmark;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDB();

        CardView btnListLandmark = (CardView)findViewById(R.id.cv_activity_main_actionListLandmark);
        CardView btnFavourite = (CardView)findViewById(R.id.cv_activity_main_actionListFavourite);
        CardView btnSchedule = (CardView)findViewById(R.id.cv_activity_main_actionCalendar);
        btnListLandmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityLandMarkActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        btnFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewFavouritePlaceActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewCalendarActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    private void createDB() {
        SQLiteDataController sql = new SQLiteDataController(this);
        try {

            sql.isCreatedDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void actionListLandmark(View v){

    }

    void actionListFavourite(View v){

    }
}
