package com.cntn14.ngocminhbui.tourexplorer.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.cntn14.ngocminhbui.tourexplorer.Model.Landmark;
import com.cntn14.ngocminhbui.tourexplorer.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class LandmarkDescriptionFragment extends Fragment {


    public LandmarkDescriptionFragment() {
        // Required empty public constructor
    }


    TextView tv_landmark_description ;
    TextView tv_landmarkname;



    private Animation animationUp;
    private Animation animationDown;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_landmark_description, container, false);


        tv_landmark_description =  (TextView)v.findViewById(R.id.tv_f_landmarkdescription);
        tv_landmarkname = (TextView)v.findViewById(R.id.tv_f_landmarkname);

        //landmark_description.setVisibility(View.GONE);
        animationUp = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_up);
        animationDown = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_down);

        tv_landmarkname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "CLicked",Toast.LENGTH_LONG).show();
                if(tv_landmark_description.isShown()){
                    tv_landmark_description.setVisibility(View.GONE);
                    tv_landmark_description.startAnimation(animationUp);
                }else{
                    tv_landmark_description.setVisibility(View.VISIBLE);
                    tv_landmark_description.startAnimation(animationDown);
                }
            }
        });

        return v;
    }

    public void setLandmark(Landmark landmark) {
        Toast.makeText(getActivity().getApplicationContext(), "setLandmark",Toast.LENGTH_LONG).show();

        tv_landmarkname.setText("sdfsdfdsfdsf");

    }
}
