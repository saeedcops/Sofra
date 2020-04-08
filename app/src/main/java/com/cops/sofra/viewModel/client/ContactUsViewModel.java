package com.cops.sofra.viewModel.client;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.contactUs.ContactUs;
import com.cops.sofra.data.model.offerDetails.OfferDetails;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ContactUsViewModel extends ViewModel {

    public MutableLiveData<ContactUs> contactUsMutableLiveData = new MutableLiveData<>();



    public void contactUs( String name, String email, String phone, String type, String content){

        getClient().contactUs(name,email,phone,type,content).enqueue(new Callback<ContactUs>() {
            @Override
            public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {

                try {
                    Log.i("res",response.message());

                    Log.i("messsa",response.body().getMsg());
                    contactUsMutableLiveData.setValue(response.body());
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ContactUs> call, Throwable t) {

                Log.i("onFailure",t.getMessage());
            }
        });
    }
}
