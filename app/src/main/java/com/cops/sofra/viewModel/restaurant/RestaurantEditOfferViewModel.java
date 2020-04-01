package com.cops.sofra.viewModel.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.newOffer.NewOffer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantEditOfferViewModel extends ViewModel {

    public MutableLiveData<NewOffer> restaurantsUpdateOfferMutableLiveData = new MutableLiveData<>();



    public void updateOffer(RequestBody description, RequestBody price, RequestBody startingAt, RequestBody name,
                             MultipartBody.Part photo,RequestBody endingAt,RequestBody apiToken,RequestBody offerId) {

        getClient().updateOffer(description,price,startingAt,name,photo,endingAt,apiToken,offerId).enqueue(new Callback<NewOffer>() {
            @Override
            public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {
                try {
                    restaurantsUpdateOfferMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<NewOffer> call, Throwable t) {

            }
        });
    }
}
