package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cntn14.ngocminhbui.tourexplorer.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    void loadCityLandmarkactivity(View v){
        Intent intent = new Intent(this, CityLandMarkActivity.class);
        startActivity(intent);
    }

    void loadUserPlacesactivity(View v){

    }
}
