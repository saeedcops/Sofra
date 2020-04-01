package com.cops.sofra.viewModel.client;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.clientOrder.ClientOrder;
import com.cops.sofra.data.model.myOrder.MyOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientGetOrderViewModel extends ViewModel {

    public MutableLiveData<ClientOrder> clientGetOrderMutableLiveData = new MutableLiveData<>();



    public void getOrderList(String apiToken, String state, int page) {

        getClient().getClientOrderList(apiToken,state,page).enqueue(new Callback<ClientOrder>() {
            @Override
            public void onResponse(Call<ClientOrder> call, Response<ClientOrder> response) {

                try {
                    clientGetOrderMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ClientOrder> call, Throwable t) {

            }
        });
    }
}
