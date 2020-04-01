package com.cops.sofra.viewModel.restaurant;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantProfile.RestaurantProfile;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantSetProfileViewModel extends ViewModel {

    public MutableLiveData<RestaurantProfile> restaurantSetProfileMutableLiveData=new MutableLiveData<>();




    public void setRestaurantProfile(RequestBody email, RequestBody name, RequestBody phone, final RequestBody regionId, RequestBody deliveryCost,
                                     RequestBody minimumCharger, RequestBody availability, MultipartBody.Part photo, RequestBody apiToken, RequestBody deliveryTime,RequestBody whatsapp){

        getClient().editRestaurantProfile(email,name,phone,regionId,deliveryCost,minimumCharger,availability,photo,apiToken,deliveryTime,whatsapp).enqueue(new Callback<RestaurantProfile>() {
            @Override
            public void onResponse(Call<RestaurantProfile> call, Response<RestaurantProfile> response) {

                restaurantSetProfileMutableLiveData.setValue(response.body());
                Log.i("resp",response.message());
            }

            @Override
            public void onFailure(Call<RestaurantProfile> call, Throwable t) {
                Log.i("failed",t.getMessage());

            }
        });

    }
}
