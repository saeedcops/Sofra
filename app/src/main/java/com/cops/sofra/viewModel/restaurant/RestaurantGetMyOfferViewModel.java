package com.cops.sofra.viewModel.restaurant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.myOffer.MyOffer;
import com.cops.sofra.data.model.myOrder.MyOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantGetMyOfferViewModel extends ViewModel {

    public MutableLiveData<MyOffer> restaurantsGetofferMutableLiveData = new MutableLiveData<>();



    public void getOfferList(String apiToken,  int page) {

        getClient().getMyOffer(apiToken,page).enqueue(new Callback<MyOffer>() {
            @Override
            public void onResponse(Call<MyOffer> call, Response<MyOffer> response) {

                try {
                    restaurantsGetofferMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<MyOffer> call, Throwable t) {

            }
        });
    }
}
