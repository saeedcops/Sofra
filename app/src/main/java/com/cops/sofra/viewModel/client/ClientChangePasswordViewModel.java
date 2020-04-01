package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientChangePasswordViewModel extends ViewModel {

    public MutableLiveData<RestaurantLogin> clientChangePasswordMutableLiveData = new MutableLiveData<>();



    public void clientChangePassword(String apiToken,String oldPassword,String password,String passwordConfirmation) {

        getClient().clientChangePassword(apiToken,oldPassword,password,passwordConfirmation).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {

                    clientChangePasswordMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {

            }
        });
    }
}
