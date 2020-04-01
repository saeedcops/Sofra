package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantInfo.RestaurantInfo;
import com.cops.sofra.data.model.restaurantReviews.RestaurantReviews;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantInfoViewModel extends ViewModel {

    public MutableLiveData<RestaurantInfo> restaurantInfoMutableLiveData=new MutableLiveData<>();



    public void getRestaurantInfo(int restaurant_id){

        getClient().getRestaurantInfo(restaurant_id).enqueue(new Callback<RestaurantInfo>() {
            @Override
            public void onResponse(Call<RestaurantInfo> call, Response<RestaurantInfo> response) {
                try {
                    restaurantInfoMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantInfo> call, Throwable t) {

            }
        });


    }
}
