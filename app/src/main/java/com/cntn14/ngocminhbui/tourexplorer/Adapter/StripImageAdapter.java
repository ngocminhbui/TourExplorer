package com.cntn14.ngocminhbui.tourexplorer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cntn14.ngocminhbui.tourexplorer.Activity.CityLandMarkActivity;
import com.cntn14.ngocminhbui.tourexplorer.Activity.ImageGalleryActivity;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;

/**
 * Created by ngocminh on 5/6/17.
 */
public class StripImageAdapter extends RecyclerView.Adapter<StripImageAdapter.ViewHolder> {
    public Context context;
    public ArrayList<Image> list_image;

    public StripImageAdapter(Context context, ArrayList<Image> p1) {
        this.context=context;
        list_image=p1;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_strip_image_item,parent,false);

        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageGalleryActivity.class);
                context.startActivity(intent);
            }
        });

        ViewHolder vh = new ViewHolder(itemview);


        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position==this.getItemCount()-1)
            holder.stripimg.setImageResource(R.drawable.loading);
        else
            holder.stripimg.setImageResource(R.drawable.nhatho1);
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView stripimg;
        public ViewHolder(View itemView) {
            super(itemView);
            stripimg = (ImageView)itemView.findViewById(R.id.iv_strip_img);
        }


    }
}
