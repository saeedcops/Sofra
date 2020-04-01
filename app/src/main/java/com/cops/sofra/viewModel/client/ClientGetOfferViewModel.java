package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.myOffer.MyOffer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientGetOfferViewModel extends ViewModel {

    public MutableLiveData<MyOffer> getOfferMutableLiveData = new MutableLiveData<>();



    public void getOfferList( int page) {

        getClient().getOffers(page).enqueue(new Callback<MyOffer>() {
            @Override
            public void onResponse(Call<MyOffer> call, Response<MyOffer> response) {
                try {

                    getOfferMutableLiveData.setValue(response.body());


                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<MyOffer> call, Throwable t) {

            }
        });
    }
}
