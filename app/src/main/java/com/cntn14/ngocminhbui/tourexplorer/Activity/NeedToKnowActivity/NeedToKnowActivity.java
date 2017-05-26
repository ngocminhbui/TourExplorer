package com.cntn14.ngocminhbui.tourexplorer.Activity.NeedToKnowActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.cntn14.ngocminhbui.tourexplorer.Fragment.CurrencyFragment;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.EmergencyFragment;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.NetworkFragment;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.OverViewFragment;
import com.cntn14.ngocminhbui.tourexplorer.Fragment.TransportationFragment;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;
import java.util.List;

public class NeedToKnowActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_to_know);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverViewFragment(), "Tổng quan");
        adapter.addFragment(new EmergencyFragment(), "Khẩn cấp");
        adapter.addFragment(new CurrencyFragment(), "Tiền tệ");
        adapter.addFragment(new NetworkFragment(), "Mạng di động");
        adapter.addFragment(new TransportationFragment(), "Phương tiện");
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
