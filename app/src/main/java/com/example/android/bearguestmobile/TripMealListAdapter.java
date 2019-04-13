package com.example.android.bearguestmobile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TripMealListAdapter extends RecyclerView.Adapter<TripMealListAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    TripMealListAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public TripMealListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_trip_meal_list_row, parent, false);
        return new ViewHolder(view);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View tripMealsView) {
            super(tripMealsView);
            myTextView = tripMealsView.findViewById(R.id.text_meal_type);
            tripMealsView.setOnClickListener(this);
        }

        // Handle clicks to list of trips to show trip's detailed view
        @Override
        public void onClick(View view) {
            //String mealName = getItem(getAdapterPosition());
            //Toast.makeText(context, "meal: " + mealName, Toast.LENGTH_SHORT).show();
        }
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(TripMealListAdapter.ViewHolder holder, int position) {
        String mealName = mData.get(position);
        holder.myTextView.setText("Meal " + (position+1) + " - " + mealName);
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
