package com.cops.sofra.viewModel.restaurant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.data.model.restaurantProfile.RestaurantProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantGetProfileViewModel extends ViewModel {

    public MutableLiveData<RestaurantProfile> restaurantGetProfileMutableLiveData=new MutableLiveData<>();




    public void getRestaurantProfile(String apiToken){

        getClient().getRestaurantProfile(apiToken).enqueue(new Callback<RestaurantProfile>() {
            @Override
            public void onResponse(Call<RestaurantProfile> call, Response<RestaurantProfile> response) {
                try {

                    restaurantGetProfileMutableLiveData.setValue(response.body());


                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantProfile> call, Throwable t) {

            }
        });


    }
}
