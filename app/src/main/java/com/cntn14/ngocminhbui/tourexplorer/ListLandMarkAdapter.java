package com.cntn14.ngocminhbui.tourexplorer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cntn14.ngocminhbui.tourexplorer.Place.Place;

import java.util.ArrayList;

public class ListLandMarkAdapter extends RecyclerView.Adapter<ListLandMarkAdapter.ViewHolder>{

    Context context;
    ArrayList<Place> list_landmark;

    public ListLandMarkAdapter(Context cityLandMarkActivity, ArrayList<Place> list_landmark) {
        this.context=cityLandMarkActivity;
        this.list_landmark=list_landmark;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_place_item,parent,false);
        final ListLandMarkAdapter.ViewHolder vh =  new ListLandMarkAdapter.ViewHolder(itemview);


        return  vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Place place = list_landmark.get(position);
        holder.tv_activity_landmark_name.setText(place.getName());

        int resID = context.getResources().getIdentifier(place.getImLocal(), "drawable", context.getPackageName());
        holder.iv_activity_landmarkimg.setImageResource(resID);

    }

    @Override
    public int getItemCount() {
        return list_landmark.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_activity_landmark_name;
        public ImageView iv_activity_landmarkimg;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_activity_landmark_name = (TextView) itemView.findViewById(R.id.tv_activity_landmarkname);
            iv_activity_landmarkimg = (ImageView)itemView.findViewById(R.id.iv_activity_landmarkimg);

        }
    }
}
