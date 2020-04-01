package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.offerDetails.OfferDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ContactUsViewModel extends ViewModel {

    public MutableLiveData<OfferDetails> contactUsMutableLiveData = new MutableLiveData<>();



    public void contactUs( String name, String email, String phone, String type, String content){

        getClient().contactUs(name,email,phone,type,content).enqueue(new Callback<OfferDetails>() {
            @Override
            public void onResponse(Call<OfferDetails> call, Response<OfferDetails> response) {

                try {

                    contactUsMutableLiveData.setValue(response.body());
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<OfferDetails> call, Throwable t) {

            }
        });
    }
}
