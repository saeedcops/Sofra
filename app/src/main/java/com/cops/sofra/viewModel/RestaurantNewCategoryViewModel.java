package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantCategories.RestaurantCategories;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantNewCategoryViewModel extends ViewModel {

    public MutableLiveData<RestaurantCategories> newCategoryMutableLiveData=new MutableLiveData<>();


    public void addNewCategory(RequestBody name, MultipartBody.Part photo, RequestBody apiToken){

        getClient().newCategory(name,photo,apiToken).enqueue(new Callback<RestaurantCategories>() {
            @Override
            public void onResponse(Call<RestaurantCategories> call, Response<RestaurantCategories> response) {
                try {

                    newCategoryMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantCategories> call, Throwable t) {

            }
        });


    }
}
