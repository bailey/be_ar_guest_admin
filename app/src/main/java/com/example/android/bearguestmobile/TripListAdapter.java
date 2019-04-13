package com.example.android.bearguestmobile;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/*
 * Trip List Adapter for TripListFragment
 * Displays a list of all user trips and their date range.
 */
public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    TripListAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public TripListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_trip_list_row, parent, false);
        return new ViewHolder(view);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View tripView) {
            super(tripView);
            myTextView = tripView.findViewById(R.id.trip_name);
            tripView.setOnClickListener(this);
        }

        // Handle clicks to list of trips to show trip's detailed view
        @Override
        public void onClick(View view) {
            // Open that trip, set the selected MenuItem
            String tripName = getItem(getAdapterPosition());
            ViewModelProviders.of((MainActivity)context).get(TripViewModel.class).setSelectedTrip(tripName);

            // Load view of that trip's details into the same frame
            TripDetailsViewFragment tripDetailsViewFragment = new TripDetailsViewFragment();
            FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
            transaction
                    .replace(R.id.trip_list_frame, tripDetailsViewFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(TripListAdapter.ViewHolder holder, int position) {
        String tripName = mData.get(position);
        holder.myTextView.setText(tripName);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

}
