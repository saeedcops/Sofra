package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.myOrder.Item;
import com.cops.sofra.data.model.newOffer.NewOffer;
import com.cops.sofra.databinding.ItemRestaurantFoodListBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycleRestaurant.EditItemFoodFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class RestaurantItemFoodListAdapter extends RecyclerView.Adapter<RestaurantItemFoodListAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<Item> restaurantItemsData = new ArrayList<>();

    public RestaurantItemFoodListAdapter(Activity activity, List<Item> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.restaurantItemsData = restaurantDataList;
    }



    @Override
    public RestaurantItemFoodListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRestaurantFoodListBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_restaurant_food_list, parent, false);

        return new RestaurantItemFoodListAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemFoodListAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(RestaurantItemFoodListAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(restaurantItemsData.get(position).getPhotoUrl()).into(holder.binding.restaurantItemFoodListAdapterIvItem);
        holder.binding.restaurantItemFoodListAdapterTvName.setText(restaurantItemsData.get(position).getName());
        holder.binding.restaurantItemFoodListAdapterTvDetails.setText(restaurantItemsData.get(position).getDescription());
        if(restaurantItemsData.get(position).getOfferPrice()==null) {

            holder.binding.restaurantItemFoodListAdapterTvCost.setText(restaurantItemsData.get(position).getPrice() + " $");

        }else {
            holder.binding.restaurantItemFoodListAdapterTvCost.setText(restaurantItemsData.get(position).getOfferPrice() + " $");
        }

    }

//

    private void setAction(final RestaurantItemFoodListAdapter.ViewHolder holder, final int position) {

        holder.binding.restaurantItemFoodListAdapterIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mApiToken="";
                if (LoadData(activity, "apiToken") != null) {

                    mApiToken = LoadData(activity, "apiToken");


                }

                getClient().deleteItem(restaurantItemsData.get(position).getId(),mApiToken).enqueue(new Callback<NewOffer>() {
                    @Override
                    public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {
                        if (response.body().getStatus()==1) {
                            Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            restaurantItemsData.remove(position);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<NewOffer> call, Throwable t) {

                    }
                });

            }
        });

        holder.binding.restaurantItemFoodListAdapterIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("imageUrl",restaurantItemsData.get(position).getPhotoUrl());
                bundle.putString("name",restaurantItemsData.get(position).getName());
                bundle.putString("description",restaurantItemsData.get(position).getDescription());
                bundle.putString("priceOffer", restaurantItemsData.get(position).getOfferPrice());
                bundle.putInt("id", restaurantItemsData.get(position).getId());
                bundle.putString("categoryId", restaurantItemsData.get(position).getCategoryId());
                bundle.putString("price", restaurantItemsData.get(position).getPrice());

                EditItemFoodFragment editItemFoodFragment=new EditItemFoodFragment();
                editItemFoodFragment.setArguments(bundle);

                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,editItemFoodFragment)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantFoodListBinding binding;


        public ViewHolder(@NonNull  ItemRestaurantFoodListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

