package com.example.android.bearguestmobile;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Webservice {

    @GET("/restaurant/getAll")
    Call<List<Restaurant>> getRestaurant();

    @POST("/park/getAllRestaurantsByParkID")
    Call<List<Restaurant>> getRestaurantByParkID(@Body ParkID parkID);

    @POST("/land/getLandsByPark")
    Call<List<Land>> getLandListByParkID(@Body ParkID parkID);

    @POST("/land/getAll")
    Call<List<Land>> getAllLands();

    @POST("/land/getRestaurantsByLand")
    Call<List<Restaurant>> getRestaurantsByLand(@Body Land land);

    @POST("/restaurant/getAllItemsByRestaurantID")
    Call<List<MenuItem>> getMenuItemsByRestaurant(@Body RestaurantID restaurantID);

    @POST("/restaurant/getAllItemsByRestaurantName")
    Call<List<MenuItem>> getMenuItemsByRestaurantName(@Body Restaurant restaurantName);

    @POST("/item/getAll")
    Call<List<MenuItem>> getAllMenuItems();

    @POST("/profile/create")
    Call<User> createUser(@Body User user);

    @POST("/profile/getProfileById")
    Call<List<Profile>> getUser(@Body Uid uid);

    @POST("review/getAll")
    Call<List<Review>> getAllReviews();

    @POST("review/getAllByItemID")
    Call<List<Review>> getAllReviewsByItemID(@Body ItemID itemID);

    @POST("review/add")
    Call<Review> addReviewComment(@Body Review review);

}
