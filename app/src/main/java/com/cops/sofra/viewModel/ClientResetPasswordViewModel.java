package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.newPassword.ResetPassword;
import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientResetPasswordViewModel extends ViewModel {

    public MutableLiveData<ResetPassword> clientResetPasswordMutableLiveData=new MutableLiveData<>();




    public void clientResetPassword(String email){

        getClient().clientResetPassword(email).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {

                    clientResetPasswordMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });


    }
}
