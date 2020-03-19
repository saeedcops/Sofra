package com.cops.sofra.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantLogin.RestaurantLogin;
import com.cops.sofra.data.model.restaurants.RestaurantData;
import com.cops.sofra.data.model.restaurants.Restaurants;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantSignUpViewModel extends ViewModel {

    public MutableLiveData<RestaurantLogin> restaurantSignUpMutableLiveData=new MutableLiveData<>();




    public void getRestaurant(RequestBody name, RequestBody email, RequestBody password, RequestBody passwordConf, RequestBody phone
            , RequestBody whatsApp, RequestBody regionId, RequestBody deliveryCost, RequestBody minimumCharger, MultipartBody.Part photo, RequestBody deliveryTime){

        getClient().restaurantSignUp(name,email,password,passwordConf,phone,whatsApp,regionId,deliveryCost,minimumCharger,photo,deliveryTime).enqueue(new Callback<RestaurantLogin>() {
            @Override
            public void onResponse(Call<RestaurantLogin> call, Response<RestaurantLogin> response) {
                try {

                    Log.i("response",response.body().getMsg());
                    Log.i("xxxx",response.message());
                    restaurantSignUpMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<RestaurantLogin> call, Throwable t) {
                Log.i("faild",t.getMessage());

            }
        });


    }
}
