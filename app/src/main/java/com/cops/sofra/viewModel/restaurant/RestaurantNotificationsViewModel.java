package com.cops.sofra.viewModel.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.commission.Commission;
import com.cops.sofra.data.model.notifications.Notifications;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantNotificationsViewModel extends ViewModel {

    public MutableLiveData<Notifications> restaurantsNotificationsMutableLiveData = new MutableLiveData<>();



    public void getNotifications(String apiToken, int page) {

        getClient().getNotifications(apiToken,page).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {

                try {

                    restaurantsNotificationsMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {

            }
        });
    }
}
