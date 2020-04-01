package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.myOffer.MyOffer;
import com.cops.sofra.data.model.offerDetails.OfferDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientOfferDetailsViewModel extends ViewModel {

    public MutableLiveData<OfferDetails> getOfferMutableLiveData = new MutableLiveData<>();



    public void getOfferDetails( int offerId) {

        getClient().getOfferDetails(offerId).enqueue(new Callback<OfferDetails>() {
            @Override
            public void onResponse(Call<OfferDetails> call, Response<OfferDetails> response) {

                try {

                    getOfferMutableLiveData.setValue(response.body());
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<OfferDetails> call, Throwable t) {

            }
        });
    }
}
