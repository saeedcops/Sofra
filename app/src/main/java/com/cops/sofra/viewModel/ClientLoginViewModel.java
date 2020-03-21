package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientLoginViewModel extends ViewModel {

    public MutableLiveData<RestaurantLogin> clientLoginMutableLiveData=new MutableLiveData<>();




    public void getClientLogin(String email,String password){

        getClient().clientLogin(email,password).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {
                    clientLoginMutableLiveData.setValue(response.body());


                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {

            }
        });


    }
}
