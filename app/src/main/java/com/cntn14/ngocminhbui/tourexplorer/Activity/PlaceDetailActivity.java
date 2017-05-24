package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cntn14.ngocminhbui.tourexplorer.Adapter.PlaceImageSliderAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Adapter.StripImageAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class PlaceDetailActivity extends AppCompatActivity {

    public static ArrayList<Landmark> list_landmark;
    public static Landmark landmark;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN = {R.drawable.nhatho1, R.drawable.nhatho2, R.drawable.nhatho3};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();


    public TextView tv_activity_detail_landmark_name, tv_activity_detail_landmark_description, tv_activity_detail_landmarkhour, tv_activity_detail_landmarkdistance;
    public RatingBar rb_activity_detail_landmarkscore;
    public ImageButton ib_call,ib_openwebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        init();

        BindViews();


        RecyclerView rv_place_image_strip = (RecyclerView) findViewById(R.id.rv_place_image_strip);
        // Instance of ImageAdapter Class
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_place_image_strip.setLayoutManager(linearLayoutManager);
        rv_place_image_strip.setItemAnimator(new DefaultItemAnimator());
        rv_place_image_strip.setAdapter(new StripImageAdapter((Context) this, null));

        // Instance of ImageAdapter Class

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView rv_self_image_strip = (RecyclerView) findViewById(R.id.rv_self_image_strip);
        rv_self_image_strip.setLayoutManager(linearLayoutManager2);
        rv_self_image_strip.setItemAnimator(new DefaultItemAnimator());
        rv_self_image_strip.setAdapter(new StripImageAdapter((Context) this, null));


    }

    private void BindViews() {
        tv_activity_detail_landmark_name = (TextView) findViewById(R.id.tv_activity_detail_landmarkname);
        tv_activity_detail_landmark_description = (TextView) findViewById(R.id.tv_activity_detail_landmarkdescription);
        rb_activity_detail_landmarkscore = (RatingBar) findViewById(R.id.rb_activity_detail_landmarkscore);
        tv_activity_detail_landmarkhour = (TextView) findViewById(R.id.tv_activity_detail_landmarkhour);
        tv_activity_detail_landmarkdistance = (TextView) findViewById(R.id.tv_activity_detail_landmarkdistance);
        tv_activity_detail_landmark_description.setVisibility(View.GONE);
        ib_call = (ImageButton) findViewById(R.id.ib_call);
        ib_openwebsite=(ImageButton)findViewById(R.id.ib_openwebsite);


        tv_activity_detail_landmark_name.setText(landmark.Name);
        rb_activity_detail_landmarkscore.setRating((float) landmark.Rating);
        tv_activity_detail_landmarkhour.setText(landmark.Hour);
        tv_activity_detail_landmarkdistance.setText(landmark.m_distance);
        ib_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "08 1080"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
        ib_openwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.svhtt.hochiminhcity.gov.vn/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });



    }

    private void init() {

        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new PlaceImageSliderAdapter(this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);

    }
}
