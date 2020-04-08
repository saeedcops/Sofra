package com.cops.sofra.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurants.Restaurants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantListViewModel extends ViewModel {

    public MutableLiveData<Restaurants> restaurantsMutableLiveData=new MutableLiveData<>();




    public void getRestaurantList(String key,int regionId,int page){

        getClient().getRestaurants(key,regionId,page).enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {
                try {

                    if (response.body().getStatus()==1) {


                        restaurantsMutableLiveData.setValue(response.body());

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
               // Log.i("error",t.getMessage());
            }
        });
       // return restaurants;
    }
}
