package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Database.Database;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CityLandMarkActivity extends AppCompatActivity {
    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 9968;
    private Uri imageUri;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    RecyclerView rv_landmarks;
    ArrayList<Landmark> list_landmark = new ArrayList<Landmark>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_viewmap) {

            Intent intent = new Intent(this, ViewAllLandmarkActivity.class);

            ViewAllLandmarkActivity.list_landmark = this.list_landmark;

            startActivity(intent);

            return true;
        } else if (id == R.id.action_takepicture) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fname_" +
                    String.valueOf(System.currentTimeMillis()) + ".jpg"));
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

                    //use imageUri here to access the image

                    Bundle extras = data.getExtras();

                    Log.e("URI",imageUri.toString());

                    Bitmap bmp = (Bitmap) extras.get("data");

                    // here you will get the image as bitmap


                }
                else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT);
                }
            }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_land_mark);




        PrepareData();

        BindComponents();


    }

    private void PrepareData() {

        list_landmark = Database.getLandmarks();

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
