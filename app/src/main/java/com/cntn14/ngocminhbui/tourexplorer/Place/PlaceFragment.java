package com.nht.dtle.mtrip.Place;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nht.dtle.mtrip.PlaceDetail.PlaceDetailActiviy;
import com.nht.dtle.mtrip.R;

import static com.nht.dtle.mtrip.Place.PlaceAdapter.SPOT_DES;
import static com.nht.dtle.mtrip.Place.PlaceAdapter.SPOT_IMG;
import static com.nht.dtle.mtrip.Place.PlaceAdapter.SPOT_TITLES;


public class PlaceFragment extends Fragment implements View.OnClickListener{

    private int index;
    private TextView tvName;
    private TextView tvDes;
    private ImageView img;
    public PlaceFragment() { }
    private Context context;

    public static PlaceFragment newInstance(int i) {
        PlaceFragment f = new PlaceFragment();
        Bundle args = new Bundle();
        args.putInt("INDEX", i);
        f.setArguments(args);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot, container, false);
        tvDes=(TextView) view.findViewById(R.id.tv_landmarkdes);
        tvName = (TextView) view.findViewById(R.id.tv_landmarkname);
        img = (ImageView) view.findViewById(R.id.iv_landmarkimg);
        context = view.getContext();
        view.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) index = args.getInt("INDEX", 0);

        tvName.setText(SPOT_TITLES[index]);
        tvDes.setText(SPOT_DES[index]);
        int resID = getResources().getIdentifier(SPOT_IMG[index] , "drawable", this.getContext().getPackageName());
        img.setImageResource(resID);
        ViewCompat.setElevation(getView(), 10f);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this.getActivity(), PlaceDetailActiviy.class);
        intent.putExtra("position", index);
        startActivity(intent);
    }
}
