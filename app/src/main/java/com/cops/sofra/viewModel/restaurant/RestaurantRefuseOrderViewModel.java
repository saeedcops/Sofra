package com.cops.sofra.viewModel.restaurant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.acceptOrder.AcceptOrder;
import com.cops.sofra.data.model.myOrder.MyOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantRefuseOrderViewModel extends ViewModel {

    public MutableLiveData<AcceptOrder> restaurantsRefuseOrderMutableLiveData = new MutableLiveData<>();



    public void refuseOrder(String apiToken, int orderId, final String refuse_reason) {


        getClient().refuseOrder(apiToken,orderId,refuse_reason).enqueue(new Callback<AcceptOrder>() {
            @Override
            public void onResponse(Call<AcceptOrder> call, Response<AcceptOrder> response) {
                try {


                    restaurantsRefuseOrderMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<AcceptOrder> call, Throwable t) {

                Log.i("res",t.getMessage());

            }
        });
    }
}
