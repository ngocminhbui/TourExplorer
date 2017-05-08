package com.cntn14.ngocminhbui.tourexplorer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cntn14.ngocminhbui.tourexplorer.Activity.PlaceDetailActivity;
import com.cntn14.ngocminhbui.tourexplorer.Helper.DownloadImage;
import com.cntn14.ngocminhbui.tourexplorer.Helper.OnTaskCompleted;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.google.android.gms.location.places.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngocminh on 5/6/17.
 */
public class ListLandMarkAdapter extends RecyclerView.Adapter<ListLandMarkAdapter.ViewHolder>{

    Context context;
    ArrayList<Landmark> list_landmark;

    public ListLandMarkAdapter(Context cityLandMarkActivity, ArrayList<Landmark> list_landmark) {
        this.context=cityLandMarkActivity;
        this.list_landmark=list_landmark;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_landmarks_item,parent,false);
        final ListLandMarkAdapter.ViewHolder vh =  new ListLandMarkAdapter.ViewHolder(itemview);




        vh.ib_activity_seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vh.tv_activity_landmark_description.isShown()){
                    vh.tv_activity_landmark_description.setVisibility(View.GONE);
                    //vh.tv_activity_landmark_description.startAnimation(animationUp);
                }
                else{
                    vh.tv_activity_landmark_description.setVisibility(View.VISIBLE);
                    //vh.tv_activity_landmark_description.startAnimation(animationDown);
                }
            }
        });


        return  vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Landmark lm = list_landmark.get(position);
        holder.tv_activity_landmark_name.setText(lm.m_name);
        holder.iv_activity_landmarkimg.setImageBitmap(lm.m_bm);
        holder.rb_activity_landmarkscore.setRating((float) lm.m_star);
        holder.tv_activity_landmarkhour.setText(lm.m_hour);
        holder.tv_activity_landmarkdistance.setText(lm.m_distance);
        if(lm.m_bm == null){
            new DownloadImage(context, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Bitmap bm) {
                    holder.iv_activity_landmarkimg.setImageBitmap(bm);
                    lm.m_bm=bm;
                }
            }).execute(lm.m_img);
        }
        else{
            holder.iv_activity_landmarkimg.setImageBitmap(lm.m_bm);
        }

        holder.ib_activity_showdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceDetailActivity.list_landmark = ListLandMarkAdapter.this.list_landmark;
                PlaceDetailActivity.landmark=ListLandMarkAdapter.this.list_landmark.get(position);
                Intent intent = new Intent(context, PlaceDetailActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list_landmark.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_activity_landmark_name,tv_activity_landmark_description,tv_activity_landmarkhour,tv_activity_landmarkdistance;
        public ImageView iv_activity_landmarkimg;
        public ImageButton ib_activity_seemore;
        public RatingBar rb_activity_landmarkscore;
        public ImageButton ib_activity_showdetail;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_activity_landmark_name = (TextView) itemView.findViewById(R.id.tv_activity_landmarkname);
            tv_activity_landmark_description = (TextView)itemView.findViewById(R.id.tv_activity_landmarkdescription);
            iv_activity_landmarkimg = (ImageView)itemView.findViewById(R.id.iv_activity_landmarkimg);
            rb_activity_landmarkscore = (RatingBar)itemView.findViewById(R.id.rb_activity_landmarkscore);
            tv_activity_landmarkhour=(TextView)itemView.findViewById(R.id.tv_activity_landmarkhour);
            tv_activity_landmarkdistance=(TextView)itemView.findViewById(R.id.tv_activity_landmarkdistance);
            ib_activity_seemore = (ImageButton)itemView.findViewById(R.id.ib_activity_seemore);
            ib_activity_showdetail = (ImageButton)itemView.findViewById(R.id.ib_activity_showdetail);
            tv_activity_landmark_description.setVisibility(View.GONE);
        }
    }
}
