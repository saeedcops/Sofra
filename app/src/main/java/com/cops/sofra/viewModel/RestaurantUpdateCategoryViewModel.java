package com.cops.sofra.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.restaurantCategories.RestaurantCategories;
import com.cops.sofra.data.model.updateCategory.UpdateCategory;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class RestaurantUpdateCategoryViewModel extends ViewModel {

    public MutableLiveData<UpdateCategory> updateCategoryMutableLiveData=new MutableLiveData<>();


    public void updateCategory(RequestBody name, MultipartBody.Part photo, RequestBody apiToken,RequestBody categoryId){

        getClient().updateCategory(name,photo,apiToken,categoryId).enqueue(new Callback<UpdateCategory>() {
            @Override
            public void onResponse(Call<UpdateCategory> call, Response<UpdateCategory> response) {
                try {

                    updateCategoryMutableLiveData.setValue(response.body());

                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<UpdateCategory> call, Throwable t) {

            }
        });


    }
}
