package com.cntn14.ngocminhbui.tourexplorer.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cntn14.ngocminhbui.tourexplorer.Helper.DownloadImage;
import com.cntn14.ngocminhbui.tourexplorer.Helper.OnTaskCompleted;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.List;

/**
 * Created by ngocminh on 5/6/17.
 */
public class ListMapLandMarkAdapter extends RecyclerView.Adapter<ListMapLandMarkAdapter.ViewHolder> {
    Context context;
    List<Landmark> list_landmark;


    private Animation animationUp;
    private Animation animationDown;

    public ListMapLandMarkAdapter(Context cityLandMarkActivity, List<Landmark> list_landmark) {
        this.context=context;
        this.list_landmark=list_landmark;


    }

    @Override
    public ListMapLandMarkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_map_landmarks_item,parent,false);
        final ListMapLandMarkAdapter.ViewHolder vh =  new ListMapLandMarkAdapter.ViewHolder(itemview);

        vh.tv_landmark_description.setVisibility(View.GONE);
        vh.ib_seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vh.tv_landmark_description.isShown()){
                    vh.tv_landmark_description.setVisibility(View.GONE);
                    //vh.tv_landmark_description.startAnimation(animationUp);
                }
                else{
                    vh.tv_landmark_description.setVisibility(View.VISIBLE);
                    //vh.tv_landmark_description.startAnimation(animationDown);
                }
            }
        });

        return  vh;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Landmark lm = list_landmark.get(position);
        holder.tv_landmark_name.setText(lm.m_name);
        holder.iv_landmarkimg.setImageBitmap(lm.m_bm);
        holder.rb_landmarkscore.setRating((float) lm.m_star);
        holder.tv_landmarkhour.setText(lm.m_hour);
        holder.tv_landmarkdistance.setText(lm.m_distance);
        if(lm.m_bm == null){
            new DownloadImage(context, new OnTaskCompleted() {
                @Override
                public void onTaskCompleted(Bitmap bm) {
                    holder.iv_landmarkimg.setImageBitmap(bm);
                    lm.m_bm=bm;
                }
            }).execute(lm.m_img);
        }
        else{
            holder.iv_landmarkimg.setImageBitmap(lm.m_bm);
        }
    }


    @Override
    public int getItemCount() {
        return list_landmark.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_landmark_name,tv_landmark_description,tv_landmarkhour,tv_landmarkdistance;
        public ImageView iv_landmarkimg;
        public ImageButton ib_seemore;
        public RatingBar rb_landmarkscore;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_landmark_name = (TextView) itemView.findViewById(R.id.tv_landmarkname);
            tv_landmark_description = (TextView)itemView.findViewById(R.id.tv_landmarkdescription);
            iv_landmarkimg = (ImageView)itemView.findViewById(R.id.iv_landmarkimg);
            rb_landmarkscore = (RatingBar)itemView.findViewById(R.id.rb_landmarkscore);
            tv_landmarkhour=(TextView)itemView.findViewById(R.id.tv_landmarkhour);
            tv_landmarkdistance=(TextView)itemView.findViewById(R.id.tv_landmarkdistance);
            ib_seemore = (ImageButton)itemView.findViewById(R.id.ib_seemore);
        }

    }
}
