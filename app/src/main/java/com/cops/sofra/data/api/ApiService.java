package com.cops.sofra.data.api;


import com.cops.sofra.data.model.acceptOrder.AcceptOrder;
import com.cops.sofra.data.model.addReview.AddReview;
import com.cops.sofra.data.model.category.Category;
import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.clientOrder.ClientOrder;
import com.cops.sofra.data.model.clientProfile.ClientProfile;
import com.cops.sofra.data.model.commission.Commission;
import com.cops.sofra.data.model.myOffer.MyOffer;
import com.cops.sofra.data.model.myOrder.Item;
import com.cops.sofra.data.model.myOrder.MyOrder;
import com.cops.sofra.data.model.newOffer.NewOffer;
import com.cops.sofra.data.model.newOrder.NewOrder;
import com.cops.sofra.data.model.newPassword.ResetPassword;
import com.cops.sofra.data.model.notifications.Notifications;
import com.cops.sofra.data.model.offerDetails.OfferDetails;
import com.cops.sofra.data.model.restaurantCategories.RestaurantCategories;
import com.cops.sofra.data.model.restaurantCategories.RestaurantCategoriesData;
import com.cops.sofra.data.model.restaurantEditItem.RestaurantEditItem;
import com.cops.sofra.data.model.restaurantInfo.RestaurantInfo;
import com.cops.sofra.data.model.restaurantItems.RestaurantItems;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.data.model.restaurantNewItem.RestaurantNewItem;
import com.cops.sofra.data.model.restaurantProfile.RestaurantProfile;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviews;
import com.cops.sofra.data.model.restaurants.Restaurants;
import com.cops.sofra.data.model.updateCategory.UpdateCategory;
import com.cops.sofra.data.model.viewOrder.ViewOrder;

import java.util.List;

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


    @POST("restaurant/change-password")
    @FormUrlEncoded
    Call<OfferDetails> contactUs(@Field("name")String name,
                                 @Field("email")String email,
                                 @Field("phone")String phone,
                                 @Field("type")String type,
                                 @Field("content")String content);

    @POST("client/confirm-order")
    @FormUrlEncoded
    Call<NewOrder> confirmOrder(@Field("order_id") String order_id,
                                @Field("api_token")String apiToken);

    @POST("client/decline-order")
    @FormUrlEncoded
    Call<NewOrder> declineOrder(@Field("order_id") String order_id,
                                @Field("api_token")String apiToken);

    @POST("client/profile")
    @FormUrlEncoded
    Call<ClientProfile> getClientProfile(@Field("api_token")String apiToken);

    @Multipart
    @POST("client/profile")
    Call<ClientProfile> editClientProfile(@Part("api_token") RequestBody apiToken,
                                          @Part("name") RequestBody name,
                                          @Part("phone") RequestBody phone,
                                          @Part("email") RequestBody email,
                                          @Part("region_id") RequestBody regionId,
                                          @Part MultipartBody.Part profile_image);

    @POST("client/new-order")
    @FormUrlEncoded
    Call<NewOrder> newOrder(@Field("restaurant_id")int restaurantId,
                            @Field("note")String note,
                            @Field("address")String address,
                            @Field("payment_method_id")String paymentMethodId,
                            @Field("phone")String phone,
                            @Field("name")String name,
                            @Field("api_token")String apiToken,
                            @Field("items[]") List<Integer> items,
                            @Field("quantities[]") List<Integer> quantities,
                            @Field("notes[]") List<String> notes);

    @POST("client/change-password")
    @FormUrlEncoded
    Call<RestaurantLogin> clientChangePassword(@Field("api_token")String apiToken,
                                         @Field("old_password")String oldPassword,
                                         @Field("password")String password,
                                         @Field("password_confirmation")String passwordConfirmation);

    @POST("restaurant/change-password")
    @FormUrlEncoded
    Call<RestaurantLogin> changePassword(@Field("api_token")String apiToken,
                                  @Field("old_password")String oldPassword,
                                  @Field("password")String password,
                                  @Field("password_confirmation")String passwordConfirmation);

    @POST("restaurant/delete-category")
    @FormUrlEncoded
    Call<NewOffer> deleteCategory(@Field("category_id")int itemId,
                                  @Field("api_token")String apiToken);

    @POST("restaurant/delete-item")
    @FormUrlEncoded
    Call<NewOffer> deleteItem(@Field("item_id")int itemId,
                          @Field("api_token")String apiToken);

    @POST("restaurant/delete-offer")
    @FormUrlEncoded
    Call<NewOffer> deleteOffer(@Field("offer_id")int offerId,
                               @Field("api_token")String apiToken);

    @Multipart
    @POST("restaurant/update-offer")
    Call<NewOffer> updateOffer(@Part("description") RequestBody description,
                            @Part("price") RequestBody price,
                            @Part("starting_at") RequestBody startingAt,
                            @Part("name") RequestBody name,
                            @Part MultipartBody.Part photo,
                            @Part("ending_at") RequestBody endingAt,
                            @Part("api_token") RequestBody apiToken,
                            @Part("offer_id") RequestBody offerId);
    @Multipart
    @POST("restaurant/new-offer")
    Call<NewOffer> newOffer(@Part("description") RequestBody description,
                            @Part("price") RequestBody price,
                            @Part("starting_at") RequestBody startingAt,
                            @Part("name") RequestBody name,
                            @Part MultipartBody.Part photo,
                            @Part("ending_at") RequestBody endingAt,
                            @Part("api_token") RequestBody apiToken,
                            @Part("offer_price") RequestBody offerPrice);

    @Multipart
    @POST("restaurant/new-category")
    Call<RestaurantCategories> newCategory(@Part("name") RequestBody name,
                                           @Part MultipartBody.Part photo,
                                           @Part("api_token") RequestBody apiToken);
    @Multipart
    @POST("restaurant/update-item")
    Call<RestaurantEditItem> editItemFood(@Part("description") RequestBody description,
                                          @Part("price") RequestBody price,
                                          @Part("preparing_time") RequestBody preparingTime,
                                          @Part("name") RequestBody name,
                                          @Part MultipartBody.Part photo,
                                          @Part("item_id") RequestBody itemId,
                                          @Part("api_token") RequestBody apiToken,
                                          @Part("offer_price") RequestBody offerPrice,
                                          @Part("category_id") RequestBody categoryId);
    @Multipart
    @POST("restaurant/new-item")
    Call<RestaurantNewItem> restuarantNewItem(@Part("description") RequestBody description,
                                              @Part("price") RequestBody price,
                                              @Part("preparing_time") RequestBody preparingTime,
                                              @Part("name") RequestBody name,
                                              @Part MultipartBody.Part photo,
                                              @Part("api_token") RequestBody apiToken,
                                              @Part("offer_price") RequestBody offerPrice,
                                              @Part("category_id") RequestBody categoryId);

    @Multipart
    @POST("restaurant/update-category")
    Call<UpdateCategory> updateCategory(@Part("name") RequestBody name,
                                        @Part MultipartBody.Part photo,
                                        @Part("api_token") RequestBody apiToken,

                                        @Part("category_id") RequestBody categoryId);
    @Multipart
    @POST("client/sign-up")
    Call<RestaurantLogin> clientSignUp(@Part("name") RequestBody name,
                                           @Part("email") RequestBody email,
                                           @Part("password") RequestBody password,
                                           @Part("password_confirmation") RequestBody passwordConfirmation,
                                           @Part("phone") RequestBody phone,
                                           @Part("region_id") RequestBody regionId,
                                           @Part MultipartBody.Part profile_image);

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
    @POST("restaurant/profile")
    @FormUrlEncoded
    Call<RestaurantProfile> getRestaurantProfile(@Field("api_token")String apiToken);

    @Multipart
    @POST("restaurant/profile")
    Call<RestaurantProfile> editRestaurantProfile(@Part("email") RequestBody email,
                                                  @Part("name") RequestBody name,
                                                  @Part("phone") RequestBody phone,
                                                  @Part("region_id") RequestBody regionId,
                                                  @Part("delivery_cost") RequestBody deliveryCost,
                                                  @Part("minimum_charger") RequestBody minimumCharger,
                                                  @Part("availability") RequestBody availability,
                                                  @Part MultipartBody.Part photo,
                                                  @Part("api_token") RequestBody apiToken,
                                                  @Part("delivery_time") RequestBody deliveryTime,
                                                  @Part("whatsapp") RequestBody whatsapp);

    @POST("client/login")
    @FormUrlEncoded
    Call<RestaurantLogin> clientLogin(@Field("email")String email,
                                          @Field("password")String password);

    @POST("restaurant/login")
    @FormUrlEncoded
    Call<RestaurantLogin> restaurantLogin(@Field("email")String email,
                                          @Field("password")String password);


    @POST("client/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> clientResetPassword(@Field("email")String email);

    @POST("restaurant/reset-password")
    @FormUrlEncoded
    Call<ResetPassword> restaurantResetPassword(@Field("email")String email);

    @POST("client/new-password")
    @FormUrlEncoded
    Call<ResetPassword> clientNewPassword(@Field("code")String code,
                                          @Field("password")String password,
                                          @Field("password_confirmation")String passwordConfirmation);

    @POST("restaurant/new-password")
    @FormUrlEncoded
    Call<ResetPassword> restaurantNewPassword(@Field("code")String code,
                                          @Field("password")String password,
                                          @Field("password_confirmation")String passwordConfirmation);
    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<AddReview> addReview(@Field("rate")int rate,
                              @Field("comment")String comment,
                              @Field("restaurant_id")int restaurant_id,
                              @Field("api_token")String apiToken);

    @GET("client/my-orders")
    Call<ClientOrder> getClientOrderList(@Query("api_token")String apiToken,
                                         @Query("state")String state,
                                         @Query("page")int page);

    @GET("restaurant/commissions")
    Call<Commission> getCommission(@Query("api_token")String apiToken);

    @GET("restaurant/my-orders")
    Call<MyOrder> getMyOrderList(@Query("api_token")String apiToken,
                                 @Query("state")String state,
                                 @Query("page")int page);

    @GET("offer")
    Call<OfferDetails> getOfferDetails(@Query("offer_id")int offerId);

    @GET("offers")
    Call<MyOffer> getOffers(@Query("page")int page);

    @GET("restaurant/my-offers")
    Call<MyOffer> getMyOffer(@Query("api_token")String apiToken,
                             @Query("page")int page);

    @GET("restaurant/show-order")
    Call<ViewOrder> viewOrder(@Query("api_token")String apiToken,
                              @Query("order_id")int orderId);

    @POST("restaurant/accept-order")
    @FormUrlEncoded
    Call<AcceptOrder> acceptOrder(@Field("api_token")String apiToken,
                                  @Field("order_id")int orderId);
    @POST("restaurant/confirm-order")
    @FormUrlEncoded
    Call<AcceptOrder> confirmOrderDelivery(@Field("api_token")String apiToken,
                                           @Field("order_id")int orderId);

    @POST("restaurant/reject-order")
    @FormUrlEncoded
    Call<AcceptOrder> refuseOrder(@Field("api_token")String apiToken,
                                  @Field("order_id")int orderId,
                                  @Field("refuse_reason")String refuseReason);

    @GET("restaurant/my-categories")
    Call<RestaurantCategories> getRestaurantCategories(@Query("api_token")String apiToken,
                                                       @Query("page")int page);

    @GET("cities-not-paginated")
    Call<City> getCity();

    @GET("regions-not-paginated")
    Call<City> getRegion(@Query("city_id")int cityId);


    @GET("restaurants")
    Call<Restaurants> getRestaurants(@Query("keyword")String keyword,
                                     @Query("region_id")int regionId,
                                     @Query("page")int page);

    @GET("categories")
    Call<Category> getCategory(@Query("restaurant_id")int restaurantId);

    @GET("restaurant/my-items")
    Call<RestaurantItems> getMyFoodItemList(@Query("api_token")String apiToken,
                                            @Query("category_id")int categoryId,
                                            @Query("page")int page);

    @GET("items")
    Call<RestaurantItems> getRestaurantsItems(@Query("restaurant_id")int restaurantId,
                                              @Query("category_id")int categoryId,
                                              @Query("page")int page);

    @GET("restaurant/reviews")
    Call<RestaurantReviews> getRestaurantsReviews(@Query("api_token")String apiToken,
                                                  @Query("page")int page,
                                                  @Query("restaurant_id")int restaurantId);
    @GET("restaurant/notifications")
    Call<Notifications> getNotifications(@Query("api_token")String apiToken,
                                         @Query("page")int page);

    @GET("client/notifications")
    Call<Notifications> getClientNotifications(@Query("api_token")String apiToken,
                                               @Query("page")int page);
    @GET("restaurant")
    Call<RestaurantInfo> getRestaurantInfo(@Query("restaurant_id")int restaurantId);

}
