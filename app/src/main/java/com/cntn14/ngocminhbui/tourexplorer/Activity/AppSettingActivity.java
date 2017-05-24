package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.R;

public class AppSettingActivity extends AppCompatActivity {


    SeekBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        pb = (SeekBar) findViewById(R.id.seekbar2);
        final int oldprogress = pb.getProgress();

        pb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    getApplicationContext().setTheme(R.style.AppTheme2);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
