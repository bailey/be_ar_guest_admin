package com.example.android.bearguestmobile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
 * Main Tab View: Trips
 * Displays a list of all user trips and their date range.
 * Loads TripListFragment into frame
 * Launches detailed view of each trip when clicked
 */
public class TripsViewFragment extends Fragment {

    private View tripListView;

    public TripsViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tripListView = inflater.inflate(R.layout.fragment_upcoming_trips, container, false);

        // Load fragment displaying the list of trips into frame underneath header text
        Context context = tripListView.getContext();
        TripListFragment tripListFragment = new TripListFragment();
        FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.trip_list_frame, tripListFragment)
                .commit();

        // Capture clicks to newTrip FAB
        addButtonListeners();

        return tripListView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TripViewModel tripViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(TripViewModel.class);

        // Observe the text for the subtitle TextView
        tripViewModel.getTripPageHeader().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String header) {
                // Update the UI
                if(header!=null){
                    ((TextView) tripListView.findViewById(R.id.upcoming_trips)).setText(header);
                }
                else {
                    Toast.makeText(getActivity(), "Error: observable object String header is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addButtonListeners() {
        // Handle clicks to bookmark, +Trip, and +Review button
        final FloatingActionButton button_addNewTrip = (FloatingActionButton) tripListView.findViewById(R.id.fabNewTrip);
        button_addNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button_addNewTrip){
                    // Create full screen fragment to slide up and over entire screen
                    TripCreateFragment tripCreateFragment = new TripCreateFragment();
                    FragmentTransaction transaction = ((MainActivity)getActivity()).getSupportFragmentManager().beginTransaction();
                    transaction
                            .setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down)
                            .replace(android.R.id.content, tripCreateFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

}
