package com.cops.sofra.viewModel.restaurant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.myOrder.MyOrder;
import com.cops.sofra.data.model.viewOrder.ViewOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantViewOrderViewModel extends ViewModel {

    public MutableLiveData<ViewOrder> restaurantsViewOrderMutableLiveData = new MutableLiveData<>();



    public void viewOrder(String apiToken, int orderId) {

        getClient().viewOrder(apiToken,orderId).enqueue(new Callback<ViewOrder>() {
            @Override
            public void onResponse(Call<ViewOrder> call, Response<ViewOrder> response) {

                try {
                    restaurantsViewOrderMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ViewOrder> call, Throwable t) {

            }
        });
    }
}
