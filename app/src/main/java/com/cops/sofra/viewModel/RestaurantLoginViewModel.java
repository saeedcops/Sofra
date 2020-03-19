package com.cops.sofra.viewModel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.data.model.restaurants.RestaurantData;
import com.cops.sofra.data.model.restaurants.Restaurants;
import com.cops.sofra.ui.auth.AuthActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantLoginViewModel extends ViewModel {

    public MutableLiveData<RestaurantLogin> restaurantsLoginMutableLiveData=new MutableLiveData<>();




    public void getRestaurantLogin(String email,String password){

        getClient().restaurantLogin(email,password).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {

                try {

                    restaurantsLoginMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {

            }
        });


    }
}
