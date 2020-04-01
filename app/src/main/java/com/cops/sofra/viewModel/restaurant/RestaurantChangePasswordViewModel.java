package com.cops.sofra.viewModel.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.newOffer.NewOffer;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantChangePasswordViewModel extends ViewModel {

    public MutableLiveData<RestaurantLogin> restaurantsChangePasswordMutableLiveData = new MutableLiveData<>();



    public void changePassword(String apiToken,String oldPassword,String password,String passwordConfirmation) {

        getClient().changePassword(apiToken,oldPassword,password,passwordConfirmation).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {

                    restaurantsChangePasswordMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {

            }
        });
    }
}
