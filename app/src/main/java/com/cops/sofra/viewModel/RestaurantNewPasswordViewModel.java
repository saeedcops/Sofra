package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.newPassword.ResetPassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantNewPasswordViewModel extends ViewModel {

    public MutableLiveData<ResetPassword> restaurantNewPasswordMutableLiveData=new MutableLiveData<>();




    public void getRestaurantNewPassword(String code, String password, String passwordConfirm){

        getClient().restaurantNewPassword(code,password,passwordConfirm).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {

                    restaurantNewPasswordMutableLiveData.setValue(response.body());
                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {

            }
        });

    }
}
