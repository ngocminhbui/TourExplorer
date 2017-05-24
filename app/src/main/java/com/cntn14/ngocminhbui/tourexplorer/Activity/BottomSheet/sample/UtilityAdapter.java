package com.cntn14.ngocminhbui.tourexplorer.Activity.BottomSheet.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;

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

    @Override
    public void onBindViewHolder(UtilityAdapter.ViewHolder holder, int position) {

        holder.bt_utilityitem.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button bt_utilityitem;

        public ViewHolder(View itemView) {
            super(itemView);

            bt_utilityitem =(Button) itemView.findViewById(R.id.bt_utilityitem);
        }
    }
}
