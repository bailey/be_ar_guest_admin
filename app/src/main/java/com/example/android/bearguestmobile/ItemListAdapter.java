package com.example.android.bearguestmobile;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<MenuItem> mData;
    private LayoutInflater mInflater;
    private Context context;

    // data is passed into the constructor
    ItemListAdapter(Context context, List<MenuItem> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_item_list_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    /* @Override
    public void onBindViewHolder(ItemListAdapter.ViewHolder holder, int position) {
        MenuItem menuItem = mData.get(position);
        holder.myTextView.setText(menuItem.getItemName());
    } */

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        // Capture handle to the delete icon
        ImageView button_delete;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.item_name);
            button_delete = itemView.findViewById(R.id.temp_delete_icon);

            // Set listener for the added icon
            button_delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        // Handle clicks to list of items to show items detailed item view
        @Override
        public void onClick(View view) {
            // ItemViewFragment itemViewFragment = new ItemViewFragment();

            // Set the selected MenuItem
            MenuItem selectedMenuItem = getItem(getAdapterPosition());
            ViewModelProviders.of((MainActivity)context).get(DashboardViewModel.class).setSelectedMenuItem(selectedMenuItem);

            /* FragmentTransaction transaction = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
            transaction
                    .replace(R.id.fragment_container, itemViewFragment)
                    .addToBackStack(null)
                    .commit(); */
            // Capture clicks to delete icon exclusively
            if(view.getId() == button_delete.getId()) {
                // Create ItemID object with the clicked itemID
                ItemID itemID = new ItemID(selectedMenuItem.getItemID());
                verifyDeleteItem(itemID);

                // Debugging
                //Toast.makeText(context, "Clicked delete for item " + selectedMenuItem.getItemName(), Toast.LENGTH_SHORT).show();
            }

            // If the row (but not the delete icon) is clicked, show the next screen
            // For admin app, maybe just delete this part and never load the next screen??
            else {
                ItemViewFragment itemViewFragment = new ItemViewFragment();
                FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                transaction
                        .replace(R.id.fragment_container, itemViewFragment, "ITEM_VIEW_FRAGMENT")
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ItemListAdapter.ViewHolder holder, int position) {
        MenuItem menuItem = mData.get(position);
        holder.myTextView.setText(menuItem.getItemName());
    }

    // convenience method for getting data at click position
    MenuItem getItem(int id) {
        return mData.get(id);
    }

    public void setMenuItemList(List<MenuItem> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    // NEW show alert to verify user wants to delete this item
    private void verifyDeleteItem(ItemID itemToDelete) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder((MainActivity)context, R.style.AppCompatAlertDialogStyle);

        // Set dialog message and button text
        alertDialogBuilder
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item? This action cannot be undone.")
                .setCancelable(true)
                .setPositiveButton("Delete",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        // Route is not published yet, but it'll work like this (hopefully)
                        // DashboardRepository.getInstance().deleteMenuItem(itemToDelete);
                        DashboardRepository.getInstance().deleteMenuItem(itemToDelete);

                        // Reload item view fragment to refresh comment list and overall rating
                        // this might not work... I'm searching by the tag ITEM_LIST_FRAGMENT which I added in
                        // RestaurantListAdapter. This is meant to refresh the item list so it automatically disappears,
                        // even without reload
                        /* Fragment frg = null;
                        frg = ((MainActivity)context).getSupportFragmentManager().findFragmentByTag("ITEM_LIST_FRAGMENT");
                        final FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                        if(frg!=null) {
                            ft.detach(frg);
                            ft.attach(frg);
                            ft.commit();
                            Log.v("ItemComments", "Reloaded item list fragment");
                        }
                        else{
                            Log.v("ItemComments", "Failed to reload item list fragment");
                        } */

                        // Notify user of success
                        Toast toast = Toast.makeText(((MainActivity)context), "Item deleted", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();

                        // Close window
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // create and show dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
