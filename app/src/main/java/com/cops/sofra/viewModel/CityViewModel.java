package com.cops.sofra.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.category.Category;
import com.cops.sofra.data.model.category.CategoryData;
import com.cops.sofra.data.model.city.City;
import com.cops.sofra.data.model.city.CityData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class CityViewModel extends ViewModel {

  public  MutableLiveData<City> cityMutableLiveData=new MutableLiveData<>();



    public void getCity(){

        getClient().getCity().enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                try {

                    if (response.body().getStatus()==1) {
                        cityMutableLiveData.setValue(response.body());
                    }

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

            }
        });

    }
}
