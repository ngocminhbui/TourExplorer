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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample.BottomSheetTest;
import com.cntn14.ngocminhbui.tourexplorer.Helper.DownloadImage;
import com.cntn14.ngocminhbui.tourexplorer.Helper.OnTaskCompleted;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.ArrayList;

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
        holder.tv_activity_landmark_name.setText(lm.Name);
        holder.rb_activity_landmarkscore.setRating((float) lm.Rating);
        holder.tv_activity_landmarkhour.setText(lm.Hour);
        holder.tv_activity_landmarkdistance.setText(lm.m_distance);
        holder.tv_activity_landmark_description.setText(lm.ShortDescription);




        Glide.with(context).load(lm.ImageURL)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_activity_landmarkimg);



        holder.iv_activity_landmarkimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BottomSheetTest.list_landmark = ListLandMarkAdapter.this.list_landmark;
                BottomSheetTest.landmark=ListLandMarkAdapter.this.list_landmark.get(position);
                Intent intent = new Intent(context, BottomSheetTest.class);
                context.startActivity(intent);


            }
        });


        holder.mfb_activity_landmark_favourite.setFavorite(lm.m_favourite);

        holder.mfb_activity_landmark_favourite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                lm.m_favourite = favorite;
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
        public MaterialFavoriteButton mfb_activity_landmark_favourite;
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
            mfb_activity_landmark_favourite = (MaterialFavoriteButton)itemView.findViewById(R.id.mfb_activity_landmark_favourite);
        }
    }
}
