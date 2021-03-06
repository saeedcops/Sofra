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
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.SaveData;

public class RestaurantSignUpViewModel extends ViewModel {

    public MutableLiveData<RestaurantLogin> restaurantSignUpMutableLiveData=new MutableLiveData<>();




    public void getRestaurant( RequestBody name, RequestBody email, RequestBody password, RequestBody passwordConf, RequestBody phone
            , RequestBody whatsApp, RequestBody regionId, RequestBody deliveryCost, RequestBody minimumCharger, MultipartBody.Part photo, RequestBody deliveryTime){

        getClient().restaurantSignUp(name,email,password,passwordConf,phone,whatsApp,regionId,deliveryCost,minimumCharger,photo,deliveryTime).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {

                    if (response.body().getStatus()==1) {


                        restaurantSignUpMutableLiveData.setValue(response.body());
                    }

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {
                Log.i("faild",t.getMessage());

            }
        });


    }
}
