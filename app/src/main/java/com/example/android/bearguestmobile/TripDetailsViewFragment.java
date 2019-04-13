package com.example.android.bearguestmobile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * TripDetailsViewFragment
 * Displayed after selecting a specific trip from TripListFragment
 * Loads TripDaysListFragment into frame underneath page header with trip name and dates
 */
public class TripDetailsViewFragment extends Fragment {

    View tripDetailsView;

    public TripDetailsViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tripDetailsView = inflater.inflate(R.layout.fragment_trip_details_view, container, false);

        // Temp: Get selected Trip from ViewModel
        LiveData<String> selectedTrip = ViewModelProviders.of((MainActivity)getActivity()).get(TripViewModel.class).getSelectedTrip();
        ((TextView) tripDetailsView.findViewById(R.id.selected_trip)).setText(selectedTrip.getValue());

        // Set text of subtitle text view
        ViewModelProviders.of((MainActivity)getActivity()).get(TripViewModel.class).setTripPageHeader("Trip Details");

        // Set screen title and show up arrow
        ToolbarViewModel toolbarViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(ToolbarViewModel.class);
        toolbarViewModel.setToolbarTitle(selectedTrip.getValue());
        toolbarViewModel.setShowBackArrow(true);

        // Load list into day_list frame
        Context context = tripDetailsView.getContext();
        TripDaysListFragment tripDaysListFragment = new TripDaysListFragment();
        FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.day_list_frame, tripDaysListFragment)
                .commit();

        return tripDetailsView;
    }

}
