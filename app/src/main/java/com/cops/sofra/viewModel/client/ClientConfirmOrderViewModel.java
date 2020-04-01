package com.cops.sofra.viewModel.client;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.newOrder.NewOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientConfirmOrderViewModel extends ViewModel {

    public MutableLiveData<NewOrder> clientOrderMutableLiveData=new MutableLiveData<>();



    public void confirmOrder(String apiToken, String orderId){

        getClient().confirmOrder(orderId,apiToken).enqueue(new Callback<NewOrder>() {
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
