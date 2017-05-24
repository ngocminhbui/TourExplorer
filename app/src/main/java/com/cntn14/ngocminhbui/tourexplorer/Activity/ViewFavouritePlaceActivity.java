package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.content.Intent;
import android.content.res.Configuration;
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
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;

public class ViewFavouritePlaceActivity extends AppCompatActivity {

    RecyclerView rv_landmarks;
    ArrayList<Landmark> list_landmark = new ArrayList<Landmark>();

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_city_land_mark);

        mDrawerList = (ListView)findViewById(R.id.navList);

        addDrawerItems();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){

                    Intent intent = new Intent(getApplicationContext(), ImageGalleryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    getApplicationContext().startActivity(intent);

                }
                else if (position==1){


                }else if (position==2) {
                    //about
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        setupDrawer();

        PrepareData();

        BindComponents();


    }
    private void addDrawerItems() {
        String[] osArray = { "View Image Gallery", "App setting", "About" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Select Action");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }
    private void PrepareData() {

        list_landmark = Database.getFavouriteLandmark(this);

    }

    private void BindComponents() {
        rv_landmarks = (RecyclerView) findViewById(R.id.rv_landmarks);

        ListLandMarkAdapter lv_landmarks_adapter = new ListLandMarkAdapter(this,list_landmark);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_landmarks.setLayoutManager(mLayoutManager);
        rv_landmarks.setItemAnimator(new DefaultItemAnimator());
        rv_landmarks.setAdapter(lv_landmarks_adapter);



    }

}
