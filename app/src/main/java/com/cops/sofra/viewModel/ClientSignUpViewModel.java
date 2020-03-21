package com.cops.sofra.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientSignUpViewModel extends ViewModel {

    public MutableLiveData<RestaurantLogin> clientSignUpMutableLiveData=new MutableLiveData<>();




    public void getClientSignUp(RequestBody name, RequestBody email, RequestBody password, RequestBody passwordConf, RequestBody phone
            ,  RequestBody regionId,   MultipartBody.Part photo){

        getClient().clientSignUp(name,email,password,passwordConf,phone,regionId,photo).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {
                    clientSignUpMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {

            }
        });


    }
}
