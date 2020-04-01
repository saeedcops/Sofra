package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantCategories.RestaurantCategories;
import com.cops.sofra.data.model.restaurantCategories.RestaurantCategoriesData;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviews;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviewsData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantCategoriesViewModel extends ViewModel {

    public MutableLiveData<RestaurantCategories> restaurantsCategoriesMutableLiveData=new MutableLiveData<>();



    public void getRestaurantCategories(String apiToken,int page){

        getClient().getRestaurantCategories(apiToken,page).enqueue(new Callback<RestaurantCategories>() {
            @Override
            public void onResponse(Call<RestaurantCategories> call, Response<RestaurantCategories> response) {
                try {

                    restaurantsCategoriesMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantCategories> call, Throwable t) {

            }
        });


    }
}
