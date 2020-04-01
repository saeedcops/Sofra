package com.cops.sofra.viewModel.restaurant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.myOffer.MyOffer;
import com.cops.sofra.data.model.newOffer.NewOffer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantNewOfferViewModel extends ViewModel {

    public MutableLiveData<NewOffer> restaurantsNewOfferMutableLiveData = new MutableLiveData<>();



    public void newOffer(RequestBody description, RequestBody price, RequestBody startingAt, RequestBody name,
                             MultipartBody.Part photo,RequestBody endingAt,RequestBody apiToken,RequestBody offerPrice) {

        getClient().newOffer(description,price,startingAt,name,photo,endingAt,apiToken,offerPrice).enqueue(new Callback<NewOffer>() {
            @Override
            public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {

                try{
                    restaurantsNewOfferMutableLiveData.setValue(response.body());
                    Log.i("response",response.message());
                }catch (Exception e){
                    Log.i("Exception",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NewOffer> call, Throwable t) {
                Log.i("onFailure",t.getMessage());
            }
        });
    }
}
