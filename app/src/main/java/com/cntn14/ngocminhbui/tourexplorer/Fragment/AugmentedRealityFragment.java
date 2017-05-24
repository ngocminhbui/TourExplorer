
package com.cntn14.ngocminhbui.tourexplorer.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AugmentedRealityFragment#newInstance} factory method to
 * create an instance of this fragment.
 *//*

public class AugmentedRealityFragment extends Fragment {

    //region Properties
    private FitEvent mEvent;

    private OverlayView arContent;
    private DisplayView mPreview;

    private ArrayList<EventParticipant> mParticipants;
    private ArrayList<Bitmap> mParticipantsAvatar;
    private ArrayList<TrackingUserLocation> mTrackingUserLocations;

    TextView tvDescription;
    ImageView imgIcon;
    Switch switchAugmentedRealityView;
    FrameLayout frameARContent;

    //endregion

    //region Constructors
    public AugmentedRealityFragment() {
        // Required empty public constructor
    }

    */
/**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AugmentedRealityFragment.
     *//*

    public static AugmentedRealityFragment newInstance() {
        AugmentedRealityFragment fragment = new AugmentedRealityFragment();
        return fragment;
    }
    //endregion

    //region Life circle handling
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent receivedIntent = getActivity().getIntent();
        if (receivedIntent != null) {
            // Get event
            mEvent = receivedIntent.getParcelableExtra(AppConstant.ARG_EVENT);

            // Get id of event sugar entity
            long id = receivedIntent.getLongExtra(AppConstant.ARG_EVENT_ID, 0);
            if (id > 0) {
                mEvent.setId(id);
            }
        }
    }

    @Override
    public void onPause() {
        arContent.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        mPreview.refreshCamera(mPreview.mCamera);
        arContent.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_augmented_reality, container, false);

        // Phone settings
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Binding
        frameARContent = (FrameLayout) view.findViewById(R.id.augmented_reality_view);
        tvDescription = (TextView) view.findViewById(R.id.augmented_reality_description);
        imgIcon = (ImageView) view.findViewById(R.id.augmented_reality_icon);

        // AR content set up
        mPreview = new DisplayView(getActivity().getApplicationContext(), getActivity());
        arContent = new OverlayView(getActivity());

        frameARContent.addView(mPreview);
        frameARContent.addView(arContent);

        // Add / update location for arContent
        registerControlEvents();

        return view;
    }

    //region Utilities

    private void registerControlEvents() {

        // Get list of participants
        mParticipants = new ArrayList<>(SugarUtil.getAllParticipantsByEventId(mEvent.eventId));

        // Get list of participants avatar
        mParticipantsAvatar = SugarUtil.getAllParticipantsAvatar(getActivity(), mParticipants);

        // Update user locations
        new UpdateUserLocationsTask().execute();
    }

    private ArrayList<TrackingUserLocation> getUpdatedTrackingUserLocations() {
        ArrayList<TrackingUserLocation> trackingUserLocations = new ArrayList<>();

        try {
            int length = mParticipants.size();
            for (int i = 0; i < length; i++) {

                // Get user location by id
                TrackingUserLocation userLocation = FlexUtil.getUserLocation(mEvent.eventId, mParticipants.get(i).participantId);

                // Add to result list
                trackingUserLocations.add(userLocation);
            }

            return trackingUserLocations;
        }
        catch (Exception e) {
            e.printStackTrace();
            return trackingUserLocations;
        }
    }

    private void updateARContent() {
        // Clear the previous content
        arContent.mylocations.clear();

        // Loop user location
        int length = mTrackingUserLocations.size();
        for (int i = 0; i < length; i++) {
            String currentUserId = SugarUtil.getCurrentUserId();
            if (currentUserId.compareTo(mTrackingUserLocations.get(i).participantId) == 0)
                continue;

            // Create place
            Place place = new Place();
            place.placeName = SugarUtil.getUsernameById(mTrackingUserLocations.get(i).participantId);
            place.address = "My address";
            place.description = "My description";
            place.imagePath = SugarUtil.getAvatarUrlById(mParticipants.get(i).participantId);
            place.latitude = mTrackingUserLocations.get(i).latitude;
            place.longitude = mTrackingUserLocations.get(i).longitude;

            // Create app location
            AppLocation appLocation = new AppLocation(place);

            // Add to AR content
            arContent.mylocations.add(appLocation);
        }
    }

    public void navigateTo(Fragment targetFragment, int parentViewId, Bundle args) {
        // Navigate only if targetFragment not null
        if (targetFragment != null) {
            targetFragment.setArguments(args);

            // Start navigating ...
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(parentViewId, targetFragment);
            ft.commit();
        } else {
            Toast.makeText(getActivity(), "Create a new target fragment failed! Please try again later!", Toast.LENGTH_SHORT).show();
        }
    }

    //endregion

    private class UpdateUserLocationsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            try {
                // Update list of tracking user location
                mTrackingUserLocations = getUpdatedTrackingUserLocations();

                // Update AR Content, user location
                updateARContent();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            try {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new UpdateUserLocationsTask().execute();
                    }
                }, AppConstant.UPDATE_USER_LOCATION_INTERVAL);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            super.onPostExecute(aVoid);
        }
    }

    //region Generated code

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //endregion
}
*/
