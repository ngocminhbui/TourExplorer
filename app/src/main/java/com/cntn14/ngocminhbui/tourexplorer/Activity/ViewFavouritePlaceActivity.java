package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Database.Database;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.ListLandmarkFragment;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;

public class ViewFavouritePlaceActivity extends AppCompatActivity {

    RecyclerView rv_landmarks;
    ArrayList<Landmark> list_landmark = new ArrayList<Landmark>();

    private ArrayAdapter<String> mAdapter;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_favourite_place);

        //favourite_fragment_container
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ListLandmarkFragment fragment = new ListLandmarkFragment();
        fragment.setList_landmark(Database.getFavouriteLandmark(this));
        fragmentTransaction.add(R.id.favourite_fragment_container, fragment);
        fragmentTransaction.commit();
    }


    private void PrepareData() {

        list_landmark = Database.getFavouriteLandmark(this);

    }

    private void BindComponents() {



    }

}
