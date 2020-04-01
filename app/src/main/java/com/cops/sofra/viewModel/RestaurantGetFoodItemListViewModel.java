package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantItems.RestaurantItems;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantGetFoodItemListViewModel extends ViewModel {

    public MutableLiveData<RestaurantItems> restaurantsGetFoodItemListMutableLiveData=new MutableLiveData<>();



    public void getFoodItemList(String apiToken,int categoryId,int page){

        getClient().getMyFoodItemList(apiToken,categoryId,page).enqueue(new Callback<RestaurantItems>() {
            @Override
            public void onResponse(Call<RestaurantItems> call, Response<RestaurantItems> response) {
                try {

                    restaurantsGetFoodItemListMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantItems> call, Throwable t) {

            }
        });
    }
}
