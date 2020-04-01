package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.myOrder.Item;
import com.cops.sofra.data.model.restaurantItems.RestaurantItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantItemsViewModel extends ViewModel {

    public MutableLiveData<RestaurantItems> restaurantsItemsMutableLiveData=new MutableLiveData<>();



    public void getRestaurantItem(int restaurantId,int categoryId,int page){

        getClient().getRestaurantsItems(restaurantId,categoryId,page).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                try {

                    if (response.body().getStatus()==1) {


                        restaurantsItemsMutableLiveData.setValue(response.body());
                    }

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {

            }
        });
    }
}
