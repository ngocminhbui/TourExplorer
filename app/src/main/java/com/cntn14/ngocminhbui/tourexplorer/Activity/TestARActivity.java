package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.cntn14.ngocminhbui.tourexplorer.R;
import com.cntn14.ngocminhbui.tourexplorer.View.DisplayView;

public class TestARActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ar);


        DisplayView arDisplay = new DisplayView(this,this);
    }
}
