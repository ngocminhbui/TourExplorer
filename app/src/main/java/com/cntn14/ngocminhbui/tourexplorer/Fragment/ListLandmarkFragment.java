package com.cntn14.ngocminhbui.tourexplorer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cntn14.ngocminhbui.tourexplorer.Adapter.ListLandMarkAdapter;
import com.cntn14.ngocminhbui.tourexplorer.Database.Database;
import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListLandmarkFragment extends Fragment {

    RecyclerView rv_landmarks;
    ArrayList<Landmark> list_landmark = new ArrayList<Landmark>();



    public ListLandmarkFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_landmark, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View v = getView();

        list_landmark = Database.getLandmarks(getActivity());


        rv_landmarks = (RecyclerView) v.findViewById(R.id.rv_landmarks);
        ListLandMarkAdapter lv_landmarks_adapter = new ListLandMarkAdapter(getActivity(),list_landmark);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        rv_landmarks.setLayoutManager(mLayoutManager);
        rv_landmarks.setItemAnimator(new DefaultItemAnimator());
        rv_landmarks.setAdapter(lv_landmarks_adapter);

    }


}
