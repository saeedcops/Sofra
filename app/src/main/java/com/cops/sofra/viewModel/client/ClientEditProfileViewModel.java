package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.clientProfile.ClientProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientEditProfileViewModel extends ViewModel {

    public MutableLiveData<ClientProfile> clientProfileMutableLiveData=new MutableLiveData<>();



    public void editClientProfile(RequestBody apiToken, RequestBody name, RequestBody phone,
                                  RequestBody email, final RequestBody regionId, MultipartBody.Part profile_image){

        getClient().editClientProfile(apiToken,name,phone,email,regionId,profile_image).enqueue(new Callback<ClientProfile>() {
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
