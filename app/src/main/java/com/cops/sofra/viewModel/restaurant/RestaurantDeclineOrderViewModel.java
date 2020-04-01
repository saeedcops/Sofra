package com.cops.sofra.viewModel.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.newOrder.NewOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantDeclineOrderViewModel extends ViewModel {

    public MutableLiveData<NewOrder> clientOrderMutableLiveData=new MutableLiveData<>();



    public void declineOrder(String apiToken, String orderId){

        getClient().declineOrder(orderId,apiToken).enqueue(new Callback<NewOrder>() {
            @Override
            public void onResponse(Call<NewOrder> call, Response<NewOrder> response) {
                try {

                    clientOrderMutableLiveData.setValue(response.body());
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<NewOrder> call, Throwable t) {

            }
        });

    }
}
