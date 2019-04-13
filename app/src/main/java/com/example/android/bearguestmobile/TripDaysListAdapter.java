package com.example.android.bearguestmobile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TripDaysListAdapter extends RecyclerView.Adapter<TripDaysListAdapter.ViewHolder> {

    private List<String> mDataDays;
    private List<String> mDataMeals;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    TripDaysListAdapter(Context context, List<String> data1, List<String> data2) {
        this.mInflater = LayoutInflater.from(context);
        this.mDataDays = data1;
        this.mDataMeals = data2;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public TripDaysListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_trip_days_list_row, parent, false);
        return new ViewHolder(view);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        RecyclerView recyclerView;

        ViewHolder(View tripDaysView) {
            super(tripDaysView);
            myTextView = tripDaysView.findViewById(R.id.text_day_header);
            recyclerView = tripDaysView.findViewById(R.id.rv_horiz_meals_list);
            tripDaysView.setOnClickListener(this);
        }

        // Handle clicks to list of trips to show trip's detailed view
        @Override
        public void onClick(View view) {
            // Open that trip
            // Set the selected MenuItem
            //String dayName = getItem(getAdapterPosition());
            //ViewModelProviders.of((MainActivity)context).get(TripViewModel.class).setSelectedTrip(tripName);

            //Toast.makeText(context, "day: " + dayName, Toast.LENGTH_SHORT).show();

//            TripDetailsViewFragment tripDetailsViewFragment = new TripDetailsViewFragment();
//            FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
//            transaction
//                    .replace(R.id.fragment_container, tripDetailsViewFragment)
//                    .addToBackStack(null)
//                    .commit();
        }
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(TripDaysListAdapter.ViewHolder holder, int position) {
        String tripName = mDataDays.get(position);
        holder.myTextView.setText("Day " + (position+1) + " - " + tripName);

        // Exp: get horizontal scroll of meals in each vertical view
        TripMealListAdapter adapter = new TripMealListAdapter(context, mDataMeals);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mDataDays.size();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mDataDays.get(id);
    }
}
