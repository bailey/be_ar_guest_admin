package com.example.android.bearguestmobile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ItemCommentsAdapter extends RecyclerView.Adapter<ItemCommentsAdapter.ViewHolder> {

    private List<Review> mCommentListData;
    private Profile mProfile;
    private LayoutInflater mInflater;
    private Context context;
    private UserViewModel userViewModel;

    // data is passed into the constructor
    ItemCommentsAdapter(Context context, List<Review> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mCommentListData = data;
        this.context = context;
        this.userViewModel = ViewModelProviders.of((MainActivity)context).get(UserViewModel.class);
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.fragment_item_comments_row, parent, false);
        return new ViewHolder(view);
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView commentText;
        TextView reviewNameText;
        TextView dateOfComment;
        ImageView reviewerImage;
        RatingBar ratingBar;

        ViewHolder(View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.item_comment);
            reviewNameText = itemView.findViewById(R.id.item_reviewer_name);
            dateOfComment = itemView.findViewById(R.id.item_comment_date);
            reviewerImage = itemView.findViewById(R.id.imageView_reviewer);
            ratingBar = itemView.findViewById(R.id.ratingBarIndividual);
            itemView.setOnClickListener(this);
        }

        // Handle clicks to list of items to show items detailed item view
        @Override
        public void onClick(View view) {
            // Eventually let user flag a comment? Don't handle the click?
        }
    }

    // binds the data to the Views (specified above) in each row
    @Override
    public void onBindViewHolder(ItemCommentsAdapter.ViewHolder holder, int position) {
        String comment = mCommentListData.get(position).getComment();
        float rating = mCommentListData.get(position).getRating();
        String imageURL = mCommentListData.get(position).getImageURL();
        String dateFromJson = mCommentListData.get(position).getDateOfComment();

        // Capture date as a String, parse by input format and set UTC time zone, then reformat
        // by desired output format and time zone before calling setText
        // Input Json String example: 2019-04-09T16:31:22.000Z
        if(dateFromJson!=null) {
            SimpleDateFormat formatterInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            SimpleDateFormat formatterOutput = new SimpleDateFormat("M/d/yyyy");

            // Label input time zone as UTC, output as local time zone
            formatterInput.setTimeZone(TimeZone.getTimeZone("UTC"));
            formatterOutput.setTimeZone(Calendar.getInstance().getTimeZone());

            try {
                Date dDate = formatterInput.parse(dateFromJson);
                holder.dateOfComment.setText(formatterOutput.format(dDate));

            } catch (ParseException e) {
                Log.v("item adapter", "parse exception", e);
            }
        }
        else {
            holder.dateOfComment.setText("* date unavailable *");
        }

        // Concatanate first and last names
        String fName = mCommentListData.get(position).getfName();
        String lName = mCommentListData.get(position).getlName();
        String user = (fName==null & lName==null) ? "Anonymous User" : (fName+" "+lName);

        if(imageURL!=null) {
            // TODO: Update image
        }

        holder.commentText.setText(comment);
        holder.reviewNameText.setText(user);
        holder.ratingBar.setRating(rating);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mCommentListData.size();
    }

    // Update the data (list of reviews) when changed
    public void setReviewList(List<Review> data) {
        this.mCommentListData = data;
        this.notifyDataSetChanged();
    }

    public void setReviewUser(Profile data) {
        this.mProfile = data;
        this.notifyDataSetChanged();
    }
}
