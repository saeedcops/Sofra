package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.addReview.AddReview;
import com.cops.sofra.data.model.restaurantInfo.RestaurantInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientAddReviewViewModel extends ViewModel {

    public MutableLiveData<AddReview> clientReviewMutableLiveData=new MutableLiveData<>();



    public void addReview(int rate,String comment,int restaurant_id,String apiToken){

        getClient().addReview(rate,comment,restaurant_id,apiToken).enqueue(new Callback<AddReview>() {
            @Override
            public void onResponse(Call<AddReview> call, Response<AddReview> response) {
                try {
                    clientReviewMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<AddReview> call, Throwable t) {

            }
        });


    }
}
