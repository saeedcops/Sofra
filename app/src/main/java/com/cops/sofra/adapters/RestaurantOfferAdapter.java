package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.myOffer.MyOfferData;
import com.cops.sofra.data.model.newOffer.NewOffer;
import com.cops.sofra.databinding.ItemRestaurantOfferBinding;
import com.cops.sofra.ui.BaseActivity;
import com.cops.sofra.ui.home.homeCycleRestaurant.EditRestaurantOfferFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class RestaurantOfferAdapter extends RecyclerView.Adapter<RestaurantOfferAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<MyOfferData> myOfferData = new ArrayList<>();

    public RestaurantOfferAdapter(Activity activity, List<MyOfferData> restaurantDataList) {
        this.context = activity;
        this.activity = activity;
        this.myOfferData = restaurantDataList;
    }


    @Override
    public RestaurantOfferAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRestaurantOfferBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_restaurant_offer, parent, false);

        return new RestaurantOfferAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantOfferAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(RestaurantOfferAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(myOfferData.get(position).getPhotoUrl()).into(holder.binding.restaurantAdapterIv);
        holder.binding.restaurantOfferAdapterTvName.setText(myOfferData.get(position).getName());

    }

//

    private void setAction(final RestaurantOfferAdapter.ViewHolder holder, final int position) {

        holder.binding.restaurantOfferAdapterIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mApiToken="";
                if (LoadData(activity, "apiToken") != null) {

                    mApiToken = LoadData(activity, "apiToken");


                }
                getClient().deleteOffer(myOfferData.get(position).getId(),mApiToken).enqueue(new Callback<NewOffer>() {
                    @Override
                    public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {

                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                myOfferData.remove(position);
                                notifyDataSetChanged();
                            }else {
                                Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){}
                    }

                    @Override
                    public void onFailure(Call<NewOffer> call, Throwable t) {

                    }
                });
            }
        });

        holder.binding.restaurantOfferAdapterIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((BaseActivity) activity).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame, new EditRestaurantOfferFragment(
                                myOfferData.get(position).getName(), myOfferData.get(position).getDescription(),
                                myOfferData.get(position).getPrice(), myOfferData.get(position).getId(),
                                myOfferData.get(position).getPhotoUrl(), myOfferData.get(position).getStartingAt(),
                                myOfferData.get(position).getEndingAt()
                        ))
                        .commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return myOfferData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantOfferBinding binding;


        public ViewHolder(@NonNull ItemRestaurantOfferBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

