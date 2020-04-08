package com.cops.sofra.viewModel.restaurant;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.myOrder.MyOrder;
import com.cops.sofra.data.model.myOrder.MyOrderData;
import com.cops.sofra.data.model.restaurantItems.RestaurantItems;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantGetOrderViewModel extends ViewModel {

    public MutableLiveData <MyOrder>restaurantsGetOrderMutableLiveData=new MutableLiveData();



    public void getOrderList(String apiToken,final String state, int page) {


 getClient().getMyOrderList(apiToken, state, page).enqueue(new Callback<MyOrder>() {
            @Override
            public void onResponse(Call<MyOrder> call, Response<MyOrder> response) {
                try {
                    Log.i("msd", response.message());
                    Log.i("data", response.body().getMsg());

                    restaurantsGetOrderMutableLiveData.setValue(response.body());

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<MyOrder> call, Throwable t) {
                Log.i("failed", t.getMessage());
            }
        });
    }
}
