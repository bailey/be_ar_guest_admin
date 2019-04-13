package com.example.android.bearguestmobile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ItemCommentsRepository {

    private Webservice webservice;
    private static ItemCommentsRepository itemCommentsRepository;
    private static final String BASE_URL = "https://be-ar-guest.herokuapp.com";

    // Test Method: Return all comments, regardless of itemID or userID
    public LiveData<List<Review>> getAllReviews() {
        final MutableLiveData<List<Review>> reviewList = new MutableLiveData<>();

        // Make API call
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        webservice = retrofit.create(Webservice.class);
        Call<List<Review>> call = webservice.getAllReviews();

        // Handle API response, update value of reviewList which notifies ViewModel
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    reviewList.setValue(response.body());
                } else {
                    Log.v("item comment repo", "onResponse not successful");
                }
            }
            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.w("item comment repo", "onFailure", t);
            }
        });

        return reviewList;
    }

    public LiveData<List<Review>> getAllReviewsByItem(ItemID itemID) {
        final MutableLiveData<List<Review>> reviewList = new MutableLiveData<>();

        // Make API call
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        webservice = retrofit.create(Webservice.class);
        Call<List<Review>> call = webservice.getAllReviewsByItemID(itemID);

        // Handle API response, update value of reviewList which notifies ViewModel
        call.enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    // Remove any comments that have rating or comment == null, those are just
                    // used to indicate favorites
                    List<Review> fullList = response.body();
                    Log.v("iter", "fullList was size: " + fullList.size());

                    for (Iterator<Review> iter = fullList.listIterator(); iter.hasNext(); ) {
                        Review r = iter.next();
                        if (r.getComment()==null || r.getRating()==0) {
                            iter.remove();
                        }
                    }
                    Log.v("iter", "fullList changed to size " + fullList.size());

                    reviewList.setValue(fullList);

                } else {
                    Log.v("item comment repo", "onResponse not successful");
                }
            }
            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {
                Log.w("item comment repo", "onFailure", t);
            }
        });
        if(reviewList.getValue()==null) {
            Log.v("iter", "fullList is null");
        }
        else {
            Log.v("iter", "returning reviewList: " + reviewList.getValue().toString());
        }
        return reviewList;
    }

    // Add a review (with comment and rating) to an item
    public void addReviewComment(Review review) {

        // Make API call
        Retrofit retrofit = RetrofitInstance.getRetrofitInstance();
        webservice = retrofit.create(Webservice.class);
        Call<Review> call = webservice.addReviewComment(review);

        // Handle API response, update value of reviewList which notifies ViewModel
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.isSuccessful()) {
                    Log.v("item comment repo", "addReviewComm successful");
                    Log.v("item comment repo", "onResponse = " + response.body());

                } else {
                    Log.v("item comment repo", "addReviewComm onResponse not successful");
                }
            }
            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.w("item comment repo", "addReviewComm onFailure", t);
            }
        });
    }

    public synchronized static ItemCommentsRepository getInstance() {
        //TODO No need to implement this singleton if using Dagger to handle Dependency Injection
        if (itemCommentsRepository == null) {
            itemCommentsRepository = new ItemCommentsRepository();
        }
        return itemCommentsRepository;
    }
}
