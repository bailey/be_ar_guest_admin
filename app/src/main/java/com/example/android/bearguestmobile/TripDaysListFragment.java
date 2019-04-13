package com.example.android.bearguestmobile;


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
 * TripDaysListFragment
 * Fragment to manage recyclerview and adapter for list of days on selected trip
 */
public class TripDaysListFragment extends Fragment {

    private View tripDaysView;

    public TripDaysListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tripDaysView = inflater.inflate(R.layout.fragment_trip_days_list, container, false);

        // Temp data to populate trip list with
        ArrayList<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        ArrayList<String> meals = new ArrayList<>();
        meals.add("Breakfast");
        meals.add("Snack");
        meals.add("Lunch");

        // set up the RecyclerView
        RecyclerView recyclerView = ((RecyclerView)tripDaysView).findViewById(R.id.rvDaysList);
        Context context = tripDaysView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        TripDaysListAdapter adapter = new TripDaysListAdapter(context, days, meals);
        recyclerView.setAdapter(adapter);

        return tripDaysView;
    }

}
