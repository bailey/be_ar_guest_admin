package com.example.android.bearguestmobile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/*
 * Trip List Display Fragment
 * Creates the recycler view and adapter to display list of user's trips
 * Loaded into frame on TripsViewFragment
*/
public class TripListFragment extends Fragment {

    private View tripListView;

    public TripListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tripListView = inflater.inflate(R.layout.fragment_trip_list, container, false);

        // Hide the back arrow and set title
        ToolbarViewModel toolbarViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(ToolbarViewModel.class);
        toolbarViewModel.setShowBackArrow(false);
        toolbarViewModel.setToolbarTitle("Trips");

        // Set text of subtitle TextView
        ViewModelProviders.of((MainActivity)getActivity()).get(TripViewModel.class).setTripPageHeader("Upcoming Trips");

        // Temp data to populate trip list with
        ArrayList<String> trips = new ArrayList<>();
        trips.add("Johnny's Birthday");
        trips.add("Anniversary");
        trips.add("Girl's Weekend");

        // set up the RecyclerView and TripListAdapter
        RecyclerView recyclerView = ((RecyclerView)tripListView).findViewById(R.id.rvTripList);
        Context context = tripListView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        TripListAdapter adapter = new TripListAdapter(context, trips);
        recyclerView.setAdapter(adapter);

        return tripListView;
    }
}
