package com.cops.sofra.viewModel.client;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.notifications.Notifications;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientNotificationsViewModel extends ViewModel {

    public MutableLiveData<Notifications> clientNotificationsMutableLiveData = new MutableLiveData<>();



    public void getClientNotifications(String apiToken, int page) {

        getClient().getClientNotifications(apiToken,page).enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(Call<Notifications> call, Response<Notifications> response) {

                try {

                    clientNotificationsMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<Notifications> call, Throwable t) {

            }
        });
    }
}
