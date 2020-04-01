package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.addReview.AddReview;
import com.cops.sofra.data.model.clientProfile.ClientProfile;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientGetProfileViewModel extends ViewModel {

    public MutableLiveData<ClientProfile> clientProfileMutableLiveData=new MutableLiveData<>();



    public void getClientProfile(String apiToken){

        getClient().getClientProfile(apiToken).enqueue(new Callback<ClientProfile>() {
            @Override
            public void onResponse(Call<ClientProfile> call, Response<ClientProfile> response) {
                try {

                    clientProfileMutableLiveData.setValue(response.body());
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ClientProfile> call, Throwable t) {

            }
        });

    }
}
