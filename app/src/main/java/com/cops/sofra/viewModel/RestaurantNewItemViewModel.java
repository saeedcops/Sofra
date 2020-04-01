package com.cops.sofra.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantItems.RestaurantItems;
import com.cops.sofra.data.model.restaurantNewItem.RestaurantNewItem;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantNewItemViewModel extends ViewModel {

    public MutableLiveData<RestaurantNewItem> restaurantNewItemListMutableLiveData=new MutableLiveData<>();



    public void addNewItem(RequestBody description, RequestBody price, RequestBody preparingTime, RequestBody name,MultipartBody.Part photo,
                            RequestBody apiToken,RequestBody offerPrice, RequestBody categoryId  ){

        getClient().restuarantNewItem(description,price,preparingTime,name,photo,apiToken,offerPrice,categoryId).enqueue(new Callback<RestaurantNewItem>() {
            @Override
            public void onResponse(Call<RestaurantNewItem> call, Response<RestaurantNewItem> response) {
                try {

                    restaurantNewItemListMutableLiveData.setValue(response.body());
                    Log.i("xxx",response.message());
                    Log.i("response",response.body().getMsg());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantNewItem> call, Throwable t) {

                Log.i("failed",t.getMessage());

            }
        });
    }
}
