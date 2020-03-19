package com.cops.sofra.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.category.Category;
import com.cops.sofra.data.model.category.CategoryData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class CategoryViewModel extends ViewModel {

  public  MutableLiveData<List<CategoryData>> categoryMutableLiveData=new MutableLiveData<>();

    public void getCategory(int restaurantId){

        getClient().getCategory(restaurantId).enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                try{
                    if (response.body().getStatus()==1) {

                        categoryMutableLiveData.setValue(response.body().getData());
                       // Log.i("data",response.body().getData().get(0).getName());
                    }else {

                        Log.i("response",response.body().getMsg());
                    }


                }catch (Exception e){}
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

                Log.i("xxxx",t.getMessage());

            }
        });
    }
}
