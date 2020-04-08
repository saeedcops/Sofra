package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.viewOrder.ViewOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientViewOrderViewModel extends ViewModel {

    public MutableLiveData<ViewOrder> clientViewOrderMutableLiveData = new MutableLiveData<>();



    public void clientViewOrder(String apiToken, int orderId) {

        getClient().clientViewOrder(apiToken,orderId).enqueue(new Callback<ViewOrder>() {
            @Override
            public void onResponse(Call<ViewOrder> call, Response<ViewOrder> response) {

                try {
                    clientViewOrderMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ViewOrder> call, Throwable t) {

            }
        });
    }
}
