package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.restaurants.RestaurantData;
import com.cops.sofra.databinding.ItemRestaurantBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.RestaurantItemsFragment;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<RestaurantData> restaurantDataList = new ArrayList<>();
    public static int restaurantId;
    public static String city;
    public static String region;
    public static String minimumCharger;
    public static String deliveryCost;
    public static String availability;

    public RestaurantAdapter(Activity activity, List<RestaurantData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.restaurantDataList = restaurantDataList;
    }



    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRestaurantBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_restaurant, parent, false);

        return new RestaurantAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(RestaurantAdapter.ViewHolder holder, int position) {

        holder.binding.restaurantAdapterTvName.setText(restaurantDataList.get(position).getName());
        holder.binding.restaurantAdapterTvDeliveryCost.setText(activity.getString(R.string.delivery_cost)+restaurantDataList.get(position).getDeliveryCost()+" $");
        holder.binding.restaurantAdapterTvMiniCost.setText(activity.getString(R.string.minimum_charger)+restaurantDataList.get(position).getMinimumCharger()+" $");
        Glide.with(context).load(restaurantDataList.get(position).getPhotoUrl()).into(holder.binding.restaurantAdapterIv);

        if (restaurantDataList.get(position).getRate()<2) {
            holder.binding.restaurantAdapterIvStar1.setImageResource(R.drawable.star_like);
        }else if(restaurantDataList.get(position).getRate()<3){
            holder.binding.restaurantAdapterIvStar1.setImageResource(R.drawable.star_like);
            holder.binding.restaurantAdapterIvStar2.setImageResource(R.drawable.star_like);
        }else if(restaurantDataList.get(position).getRate()<4){
            holder.binding.restaurantAdapterIvStar1.setImageResource(R.drawable.star_like);
            holder.binding.restaurantAdapterIvStar2.setImageResource(R.drawable.star_like);
            holder.binding.restaurantAdapterIvStar3.setImageResource(R.drawable.star_like);
        }else if(restaurantDataList.get(position).getRate()<5){
            holder.binding.restaurantAdapterIvStar1.setImageResource(R.drawable.star_like);
            holder.binding.restaurantAdapterIvStar2.setImageResource(R.drawable.star_like);
            holder.binding.restaurantAdapterIvStar3.setImageResource(R.drawable.star_like);
            holder.binding.restaurantAdapterIvStar4.setImageResource(R.drawable.star_like);
        }


    }

//

    private void setAction(final RestaurantAdapter.ViewHolder holder, final int position) {
        holder.binding.restaurantAdapterRlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                restaurantId=restaurantDataList.get(position).getId();
                city=restaurantDataList.get(position).getRegion().getCity().getName();
                region=restaurantDataList.get(position).getRegion().getName();
                minimumCharger=restaurantDataList.get(position).getMinimumCharger();
                deliveryCost=restaurantDataList.get(position).getDeliveryCost();
                availability=restaurantDataList.get(position).getAvailability();

                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,new RestaurantItemsFragment())
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantBinding binding;


        public ViewHolder(@NonNull  ItemRestaurantBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
