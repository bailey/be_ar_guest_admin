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

// Instantiates horizontal RecyclerView showing the meals on each day of the selected trip
public class TripMealListFragment extends Fragment {

    private View tripMealListView;

    public TripMealListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tripMealListView = inflater.inflate(R.layout.fragment_trip_meal_list, container, false);

        // Temp data to populate meal list with
        ArrayList<String> meals = new ArrayList<>();
        meals.add("Breakfast");
        meals.add("Snack");
        meals.add("Lunch");

        // set up the RecyclerView
        RecyclerView recyclerView = ((RecyclerView)tripMealListView).findViewById(R.id.rvMealsList);
        Context context = tripMealListView.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        TripMealListAdapter adapter = new TripMealListAdapter(context, meals);
        recyclerView.setAdapter(adapter);

        return tripMealListView;
    }

}
