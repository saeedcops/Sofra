package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantItems.RestaurantItems;
import com.cops.sofra.data.model.restaurantItems.RestaurantItemsData;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviews;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviewsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantReviewsViewModel extends ViewModel {

    public MutableLiveData<List<RestaurantReviewsData>> restaurantsReviewsMutableLiveData=new MutableLiveData<>();



    public void getRestaurantReviews(String apiToken,int restaurant_id){

        getClient().getRestaurantsReviews(apiToken,restaurant_id).enqueue(new Callback<RestaurantReviews>() {
            @Override
            public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {
                try {

                    if (response.body().getStatus()==1) {

                        restaurantsReviewsMutableLiveData.setValue(response.body().getData().getData());
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<RestaurantReviews> call, Throwable t) {

            }
        });


    }
}
