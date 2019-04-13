package com.example.android.bearguestmobile;


import android.app.AlertDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.media.Rating;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/*
 * Detailed view of item
 * Loads the ItemCommentsFragment to show list of comments for that Item
 * Provides buttons for adding review, adding to trip, and favoriting
 */
public class ItemViewFragment extends Fragment {
    private View itemFragmentView;
    Context context;

    public ItemViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        itemFragmentView = inflater.inflate(R.layout.fragment_item_view, container, false);

        // Set screen title and up arrow
        ToolbarViewModel toolbarViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(ToolbarViewModel.class);
        toolbarViewModel.setToolbarTitle("Item View");
        toolbarViewModel.setShowBackArrow(true);

        // Initialize number of reviews to zero
        final TextView numRatings = (TextView)itemFragmentView.findViewById(R.id.item_num_ratings);
        numRatings.setText("0 reviews");

        // Handle buttons and rating bar
        addButtonListeners();
        setOverallRating();

        // Load item comments fragment
        context = itemFragmentView.getContext();
        ItemCommentsFragment itemCommentsFragment = new ItemCommentsFragment();
        FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.frame_comments_fragment, itemCommentsFragment)
                .commit();

        return itemFragmentView;
    }

    private void addButtonListeners() {
        // Handle clicks to bookmark, +Trip, and +Review button
        final Button button_addToTrip = (Button) itemFragmentView.findViewById(R.id.button_add_to_trip);
        button_addToTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button_addToTrip){
                    // Switch to Trip Nav
                    //Toast.makeText((MainActivity)getActivity(), "Add to Trip clicked", Toast.LENGTH_SHORT).show();

                    //Temp
                    showErrorDialog("Error", "error", "dismiss");
                }
            }
        });

        final Button button_addReview = (Button) itemFragmentView.findViewById(R.id.button_add_review);
        button_addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button_addReview){
                    // Create full screen fragment to slide up and over entire screen
                    AddReviewFragment addReviewFragment = new AddReviewFragment();
                    FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                    transaction
                            .setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down)
                            .replace(R.id.fragment_container, addReviewFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        final ImageButton button_addFavorite = (ImageButton) itemFragmentView.findViewById(R.id.button_favorite);
        //final Image button_addFavorite = (Image) itemFragmentView.findViewById(R.id.button_favorite);
        button_addFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == button_addFavorite){
                    // Todo: Add to user's favorite
                    ((ImageView)v).setImageResource(R.drawable.item_heart_filled);
                }
            }
        });
    }

    private void setOverallRating() {
        final RatingBar ratingBar = (RatingBar) itemFragmentView.findViewById(R.id.ratingBar);
        final TextView numRatings = (TextView)itemFragmentView.findViewById(R.id.item_num_ratings);

        // Declare View Models
        ItemCommentsViewModel itemCommentsViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(ItemCommentsViewModel.class);
        DashboardViewModel dashboardViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(DashboardViewModel.class);

        // Get the menu item currently selected, create ItemID object for that item
        LiveData<MenuItem> menuItem = dashboardViewModel.getSelectedMenuItem();
        ItemID itemID = new ItemID(menuItem.getValue().getItemID());

        // Observe a list of all comments for that menu item
        itemCommentsViewModel.getAllReviewsByItem(itemID).observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviewList) {
                // Update the UI
                float totalRating = 0;
                int i = 0;
                for (i=0; i<reviewList.size(); i++) {
                    totalRating += reviewList.get((int)i).getRating();
                }
                float averageRating = totalRating / i;

                ratingBar.setRating(averageRating);
                numRatings.setText(i + " reviews");
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Declare View Models
        ItemCommentsViewModel itemCommentsViewModel = ViewModelProviders.of((MainActivity) getActivity()).get(ItemCommentsViewModel.class);
        DashboardViewModel dashboardViewModel = ViewModelProviders.of((MainActivity)getActivity()).get(DashboardViewModel.class);

        // Set item and item description
        //((TextView) itemFragmentView.findViewById(R.id.item_view_title)).setText(dashboardViewModel.getSelectedMenuItem().getValue().getItemName());
        //((TextView) itemFragmentView.findViewById(R.id.item_view_description)).setText(dashboardViewModel.getSelectedMenuItem().getValue().getItemDescription());

        dashboardViewModel.getSelectedMenuItem().observe(this, new Observer<MenuItem>() {
            @Override
            public void onChanged(@Nullable MenuItem selectedItem) {
                ((TextView) itemFragmentView.findViewById(R.id.item_view_title)).setText(selectedItem.getItemName());
                ((TextView) itemFragmentView.findViewById(R.id.item_view_description)).setText(selectedItem.getItemDescription());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("onResume", "here");
    }

    private void showErrorDialog(String errorTitle, String errorText, String dismissText) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((MainActivity)getActivity());

        // Set dialog title
        alertDialogBuilder.setTitle(errorTitle);

        // Set dialog message and button text
        alertDialogBuilder
                .setMessage(errorText)
                .setCancelable(false)
                .setPositiveButton(dismissText,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                    }
                });

        // create and show dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
