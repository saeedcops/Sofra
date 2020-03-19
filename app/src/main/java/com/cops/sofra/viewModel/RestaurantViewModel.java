package com.cops.sofra.viewModel;

import android.util.JsonReader;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurants.RestaurantData;
import com.cops.sofra.data.model.restaurants.Restaurants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantViewModel extends ViewModel {

    public MutableLiveData<List<RestaurantData>> restaurantsMutableLiveData=new MutableLiveData<>();
    public static int lastPage=0;



    public void getRestaurant(int page){

        getClient().getRestaurants(page).enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                try {

                    if (response.body().getStatus()==1) {
                        lastPage=response.body().getData().getLastPage();

                        restaurantsMutableLiveData.setValue(response.body().getData().getData());

                        Log.i("responce",response.body().getMsg());
                    }else {
                        Log.i("responce",response.body().getMsg());
                    }

                }catch (Exception e){
                    Log.i("error",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {
                Log.i("error",t.getMessage());
            }
        });
       // return restaurants;
    }
}
