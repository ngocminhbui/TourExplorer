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
import android.support.v4.app.NavUtils;
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
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.ARDirection.ARDirection;
import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Database.Database;
import com.cntn14.ngocminhbui.tourexplorer.Database.LandmarkType;
import com.cntn14.ngocminhbui.tourexplorer.Database.SQLiteLandmark;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.ListLandmarkFragment;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.OneFragment;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.Places.ui.ActivityList.AboutScreen;
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

    } else if (id == R.id.action_ar_direction) {
            Intent intent = new Intent(this, ARDirection.class);
            startActivity(intent);
            return true;
    } else if (id == R.id.action_recognition) {
            Intent intent = new Intent(this, AboutScreen.class);
            intent.putExtra("ACTIVITY_TO_LAUNCH",
                    "Places.app.Places.Books");
            intent.putExtra("ABOUT_TEXT_TITLE", "Books");
            intent.putExtra("ABOUT_TEXT", "Books/CR_about.html");
            startActivity(intent);
    }else if (id==android.R.id.home){
            finish();
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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        list_landmark = Database.getLandmarks(this);

        ListLandmarkFragment allList = new ListLandmarkFragment();
        allList.setList_landmark(list_landmark);

        ListLandmarkFragment historicalList = new ListLandmarkFragment();
        historicalList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.HISTORICAL));


        ListLandmarkFragment museumList = new ListLandmarkFragment();
        museumList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.MUSEUM));

        ListLandmarkFragment amusementList = new ListLandmarkFragment();
        amusementList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.AMUSEMENT));


        ListLandmarkFragment parkList = new ListLandmarkFragment();
        parkList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.PARK));

        ListLandmarkFragment foodList = new ListLandmarkFragment();
        foodList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.FOOD));

        ListLandmarkFragment hotelList = new ListLandmarkFragment();
        hotelList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.HOTEL));


        ListLandmarkFragment restaurantList = new ListLandmarkFragment();
        restaurantList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.RESTAURANT));

        ListLandmarkFragment cinemaList = new ListLandmarkFragment();
        cinemaList.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.CINEMA));


        ListLandmarkFragment other = new ListLandmarkFragment();
        other.setList_landmark(SQLiteLandmark.getListLandmarkByType(list_landmark, LandmarkType.OTHER));



        adapter.addFragment(allList, "Tất cả");
        adapter.addFragment(museumList, "Bảo tàng");
        adapter.addFragment(amusementList,"Giải trí");
        adapter.addFragment(restaurantList,"Nhà hàng");
        adapter.addFragment(hotelList, "Khách sạn");

        adapter.addFragment(other, "Khác");


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
