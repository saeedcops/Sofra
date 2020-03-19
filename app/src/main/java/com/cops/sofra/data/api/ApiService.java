package com.cops.sofra.data.api;


import com.cops.sofra.data.model.category.Category;
import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.restaurantItems.RestaurantItems;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviews;
import com.cops.sofra.data.model.region.Region;
import com.cops.sofra.data.model.restaurants.Restaurants;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {

    @Multipart
    @POST("restaurant/sign-up")
    Call<RestaurantLogin> restaurantSignUp(@Part("name") RequestBody name,
                                           @Part("email") RequestBody email,
                                           @Part("password") RequestBody password,
                                           @Part("password_confirmation") RequestBody passwordConfirmation,
                                           @Part("phone") RequestBody phone,
                                           @Part("whatsapp") RequestBody whatsapp,
                                           @Part("region_id") RequestBody regionId,
                                           @Part("delivery_cost") RequestBody deliveryCost,
                                           @Part("minimum_charger") RequestBody minimumCharger,
                                           @Part MultipartBody.Part photo,
                                           @Part("delivery_time") RequestBody deliveryTime);




    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantLogin> restaurantLogin(@Field("email")String email,
                                          @Field("password")String password);


    @GET("cities-not-paginated")
    Call<City> getCity();

    @GET("regions-not-paginated")
    Call<City> getRegion(@Query("city_id")int cityId);

//    @GET("regions-not-paginated")
//    Call<Region> getRegion(@Query("city_id")int cityId);

    @GET("restaurants")
    Call<Restaurants> getRestaurants(@Query("page")int page);

    @GET("categories")
    Call<Category> getCategory(@Query("restaurant_id")int restaurantId);

    @GET("items")
    Call<RestaurantItems> getRestaurantsItems(@Query("restaurant_id")int restaurantId,
                                              @Query("page")int page);

    @GET("restaurant/reviews")
    Call<RestaurantReviews> getRestaurantsReviews(@Query("api_token")String apiToken,
                                                  @Query("restaurant_id")int restaurantId);

}
