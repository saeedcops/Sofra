package com.cops.sofra.viewModel.client;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cops.sofra.data.model.addReview.AddReview;
import com.cops.sofra.data.model.newOrder.NewOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;

public class ClientNewOrderViewModel extends ViewModel {

    public MutableLiveData<NewOrder> clientOrderMutableLiveData=new MutableLiveData<>();



    public void newOrder(int restaurantId, String note, String address, String paymentMethodId, String phone, String name,
                         String apiToken, List<Integer> items,List<Integer> quantities,List<String> notes){

        getClient().newOrder(restaurantId,note,address,paymentMethodId,phone,name,apiToken,items,quantities,notes).enqueue(new Callback<NewOrder>() {
            @Override
            public void onResponse(Call<NewOrder> call, Response<NewOrder> response) {

                try {

                    Log.i("res",response.message());
                    clientOrderMutableLiveData.setValue(response.body());
                }catch (Exception e){
                    Log.i("ex",e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NewOrder> call, Throwable t) {
//                Log.i("fail",t.getMessage());
            }
        });


    }
}
