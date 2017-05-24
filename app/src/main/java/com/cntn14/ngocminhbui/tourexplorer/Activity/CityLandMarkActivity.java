package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.ARDirection.ARDirection;
import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Database.Database;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.ListLandmarkFragment;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.OneFragment;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CityLandMarkActivity extends AppCompatActivity {

    RecyclerView rv_landmarks;
    ArrayList<Landmark> list_landmark = new ArrayList<Landmark>();

    private ArrayAdapter<String> mAdapter;
    private String mActivityTitle;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_viewmap) {

            Intent intent = new Intent(this, ViewAllLandmarkActivity.class);

            ViewAllLandmarkActivity.list_landmark = this.list_landmark;

            startActivity(intent);

            return true;

    } else if (id == R.id.action_recognition) {
            Intent intent = new Intent(this, ARDirection.class);
            startActivity(intent);
            return true;
    }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_land_mark);


        toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.activity_main_viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.activity_main_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mActivityTitle = getTitle().toString();

        PrepareData();

        BindComponents();
        */

    }
    /*
    private void PrepareData() {

        list_landmark = Database.getLandmarks(this);

    }
    private void BindComponents() {
        rv_landmarks = (RecyclerView) findViewById(R.id.rv_landmarks);

        ListLandMarkAdapter lv_landmarks_adapter = new ListLandMarkAdapter(this,list_landmark);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        rv_landmarks.setLayoutManager(mLayoutManager);
        rv_landmarks.setItemAnimator(new DefaultItemAnimator());
        rv_landmarks.setAdapter(lv_landmarks_adapter);


    }
    */


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListLandmarkFragment(), "ONE");
        adapter.addFragment(new ListLandmarkFragment(), "TWO");
        adapter.addFragment(new ListLandmarkFragment(), "THREE");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
