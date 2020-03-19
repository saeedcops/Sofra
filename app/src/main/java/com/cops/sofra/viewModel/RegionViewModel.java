package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.region.Region;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RegionViewModel extends ViewModel {

  public  MutableLiveData<City> regionMutableLiveData=new MutableLiveData<>();



    public void getRegion(int cityId){

        getClient().getRegion(cityId).enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {

                    regionMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });


    }
}
