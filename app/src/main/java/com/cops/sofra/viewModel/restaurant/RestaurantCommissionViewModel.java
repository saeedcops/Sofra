package com.cops.sofra.viewModel.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.commission.Commission;
import com.cops.sofra.data.model.myOffer.MyOffer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantCommissionViewModel extends ViewModel {

    public MutableLiveData<Commission> restaurantsCommissionMutableLiveData = new MutableLiveData<>();



    public void getCommission(String apiToken) {

        getClient().getCommission(apiToken).enqueue(new Callback<Commission>() {
            @Override
            public void onResponse(Call<Commission> call, Response<Commission> response) {

                try {

                    restaurantsCommissionMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<Commission> call, Throwable t) {

            }
        });
    }
}
