package com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.Model.UtilityType;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
/**
 * Created by ngocminh on 5/24/17.
 */
public class UtilityAdapter extends RecyclerView.Adapter<UtilityAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Utility> utilities;

    public UtilityAdapter(Context context, ArrayList<Utility> utilities) {
        this.context = context;
        this.utilities = utilities;
    }

    @Override
    public UtilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.utility_item,parent,false);
        UtilityAdapter.ViewHolder vh = new UtilityAdapter.ViewHolder(itemview);
        return vh;

    }

    final int color[] = {R.color.event_color_01, R.color.event_color_02, R.color.event_color_03, R.color.event_color_04};

    @Override
    public void onBindViewHolder(UtilityAdapter.ViewHolder holder, int position) {
        final Utility u = utilities.get(position);

        GradientDrawable bgShape = (GradientDrawable)holder.bt_utilityitem.getBackground();


        holder.tv_utilityitem_action.setText(u.displaytext);

        Resources resources = context.getResources();

        bgShape.setColor(resources.getColor(color[position%4]));


        holder.bt_utilityitem.setCompoundDrawablesWithIntrinsicBounds(null, resources.getDrawable( UtilityIcon.ARRAY_ICON[u.iconid] ) ,null,null);

        if(u.type==UtilityType.PHONE){
            holder.bt_utilityitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + u.content));
                    if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    context.startActivity(intent);
                }
            });
        }else if(u.type==UtilityType.WEB){
            holder.bt_utilityitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = u.content;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }
            });
        }else if(u.type==UtilityType.MAP){
            holder.bt_utilityitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr="+ u.content));
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return utilities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button bt_utilityitem;
        TextView tv_utilityitem_action;
        public ViewHolder(View itemView) {
            super(itemView);

            bt_utilityitem =(Button) itemView.findViewById(R.id.bt_utilityitem);
            tv_utilityitem_action = (TextView)itemView.findViewById(R.id.tv_utilityitem_action);
        }
    }
}
