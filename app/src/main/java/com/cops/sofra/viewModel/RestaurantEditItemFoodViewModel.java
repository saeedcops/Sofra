package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantEditItem.RestaurantEditItem;
import com.cops.sofra.data.model.updateCategory.UpdateCategory;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantEditItemFoodViewModel extends ViewModel {

    public MutableLiveData<RestaurantEditItem> editItemMutableLiveData=new MutableLiveData<>();


    public void editItemFood(RequestBody description, RequestBody price, RequestBody preparing_time, RequestBody name, MultipartBody.Part photo,
                             RequestBody item_id, RequestBody api_token, RequestBody offer_price, RequestBody category_id){

        getClient().editItemFood(description,price,preparing_time,name,photo,item_id,api_token,offer_price,category_id).enqueue(new Callback<RestaurantEditItem>() {
            @Override
            public void onResponse(Call<RestaurantEditItem> call, Response<RestaurantEditItem> response) {
                try {
                    editItemMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantEditItem> call, Throwable t) {

            }
        });


    }
}
