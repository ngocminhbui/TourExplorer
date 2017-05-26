package com.cntn14.ngocminhbui.tourexplorer.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.ARDirection.ARDirection;
import com.cntn14.ngocminhbui.tourexplorer.Places.ui.ActivityList.AboutScreen;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;



import java.util.Map;

public class FindFriendActivity extends AppCompatActivity {

    String groupSigature = "default";
    String userAlias="Le";

    String tempSignature;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.view_find_friend_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.createqr) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Đổi nhóm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    groupSigature=tempSignature;
                }
            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog dialog = builder.create();
            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.qr_show_dialog, null);
            dialog.setView(dialogLayout);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface d) {
                    ImageView image = (ImageView) dialog.findViewById(R.id.qrshowdialog);


                    Bitmap bmp;
                    QRCodeWriter writer = new QRCodeWriter();
                    try {
                        tempSignature="OKidFJE";
                        BitMatrix bitMatrix = writer.encode(tempSignature, BarcodeFormat.QR_CODE, 512, 512);
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();
                        bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }

                        image.setImageBitmap(bmp);

                        float imageWidthInPX = (float)image.getWidth();



                    } catch (WriterException e) {
                        e.printStackTrace();
                    }


                }
            });

            dialog.show();


        } else if (id == R.id.readqr) {

            try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                startActivityForResult(intent, 2992);

            } catch (Exception e) {

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);

            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2992) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);


        BroadcastReceiver locationReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                Location location = new Location(intent.getStringExtra("Provider"));
                location.setLatitude(Double.valueOf(intent.getDoubleExtra("Latitude",3)));
                location.setLongitude(Double.valueOf(intent.getDoubleExtra("Longitude",3)));

                DatabaseReference rootref = FirebaseDatabase.getInstance().getReference(groupSigature+"/"+userAlias);

                Map<String, Object> maps = new ArrayMap<String,Object>();
                maps.put("Lat", location.getLatitude());
                maps.put("Long", location.getLongitude());
                rootref.updateChildren(maps);
            }
        };
        this.registerReceiver(locationReceiver,new IntentFilter("com.cntn14.ngocminhbui.tourexplorer.Service.locationupdate"));


        /*

        ImageView iv = (ImageView)findViewById(R.id.iv_findfriend_qr);


        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode("OK that was tough", BarcodeFormat.QR_CODE, 512, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            iv.setImageBitmap(bmp);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        */
    }
}
