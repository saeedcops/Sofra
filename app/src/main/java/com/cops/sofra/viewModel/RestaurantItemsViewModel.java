package com.cops.sofra.viewModel;

import android.app.ExpandableListActivity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantItems.RestaurantItems;
import com.cops.sofra.data.model.restaurantItems.RestaurantItemsData;
import com.cops.sofra.data.model.restaurants.RestaurantData;
import com.cops.sofra.data.model.restaurants.Restaurants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantItemsViewModel extends ViewModel {

    public MutableLiveData<List<RestaurantItemsData>> restaurantsItemsMutableLiveData=new MutableLiveData<>();
    public static int itemLastPage=0;


    public void getRestaurantItem(int restaurant_id,int page){

        getClient().getRestaurantsItems(restaurant_id,page).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                try {

                    if (response.body().getStatus()==1) {
                        itemLastPage=response.body().getData().getLastPage();

                        restaurantsItemsMutableLiveData.setValue(response.body().getData().getData());
                    }

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {

            }
        });
    }
}
