package com.cops.sofra.viewModel.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.acceptOrder.AcceptOrder;
import com.cops.sofra.data.model.newOrder.NewOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantConfirmOrderViewModel extends ViewModel {

    public MutableLiveData<AcceptOrder> confirmOrderMutableLiveData=new MutableLiveData<>();



    public void confirmOrder(String apiToken, String orderId){

        int id=Integer.parseInt(orderId);

        getClient().confirmOrderDelivery(apiToken,id).enqueue(new Callback<AcceptOrder>() {
            @Override
            public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                try {
                    confirmOrderMutableLiveData.setValue(response.body());
                }catch (Exception e){}

            }

            @Override
            public void onFailure(Call<AcceptOrder> call, Throwable t) {

            }
        });

    }
}
