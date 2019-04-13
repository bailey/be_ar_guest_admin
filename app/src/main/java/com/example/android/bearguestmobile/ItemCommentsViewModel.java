package com.example.android.bearguestmobile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class ItemCommentsViewModel extends ViewModel {
    private LiveData<List<Review>> commentListLiveData;

    public ItemCommentsViewModel() { }

    // Test method: returns all Reviews in the database
    public LiveData<List<Review>> getAllReviews() {
        this.commentListLiveData = ItemCommentsRepository.getInstance().getAllReviews();
        return this.commentListLiveData;
    }

    public LiveData<List<Review>> getAllReviewsByItem(ItemID itemID) {
        this.commentListLiveData = ItemCommentsRepository.getInstance().getAllReviewsByItem(itemID);
        return this.commentListLiveData;
    }

    public void addNewComment(Review newReview, ItemID itemID) {
        ItemCommentsRepository.getInstance().addReviewComment(newReview);
        //this.commentListLiveData = ItemCommentsRepository.getInstance().getAllReviewsByItem(itemID);
    }

    public void updateCommentList(ItemID itemID) {
        this.commentListLiveData = ItemCommentsRepository.getInstance().getAllReviewsByItem(itemID);
    }
}
