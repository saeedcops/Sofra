package com.cops.sofra.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cops.sofra.R;
import com.cops.sofra.data.model.restaurantItems.RestaurantItemsData;
import com.cops.sofra.databinding.ItemFoodListBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycle.RestaurantItemsFragment;
import com.cops.sofra.ui.home.homeCycle.SelectedItemFragment;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItemsAdapter extends RecyclerView.Adapter<RestaurantItemsAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<RestaurantItemsData> restaurantItemsData = new ArrayList<>();

    public RestaurantItemsAdapter(Activity activity, List<RestaurantItemsData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.restaurantItemsData = restaurantDataList;
    }



    @Override
    public RestaurantItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemFoodListBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_food_list, parent, false);

        return new RestaurantItemsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantItemsAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(RestaurantItemsAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(restaurantItemsData.get(position).getPhotoUrl()).into(holder.binding.foodListAdapterIv);
        holder.binding.foodListAdapterTvName.setText(restaurantItemsData.get(position).getName());
        holder.binding.foodListAdapterTvKind.setText(restaurantItemsData.get(position).getDescription());

        if (restaurantItemsData.get(position).getHasOffer()) {
            holder.binding.foodListAdapterTvOldPrice.setVisibility(View.VISIBLE);
            holder.binding.foodListAdapterTvOldPrice.setPaintFlags(holder.binding.foodListAdapterTvPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.binding.foodListAdapterTvOldPrice.setText(restaurantItemsData.get(position).getPrice()+" $");

            holder.binding.foodListAdapterTvPrice.setText(restaurantItemsData.get(position).getOfferPrice()+" $");
        }else {
            holder.binding.foodListAdapterTvPrice.setText(restaurantItemsData.get(position).getPrice()+" $");
        }



    }

//

    private void setAction(final RestaurantItemsAdapter.ViewHolder holder, final int position) {

        holder.binding.foodListAdapterCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("imageUrl",restaurantItemsData.get(position).getPhotoUrl());
                bundle.putString("name",restaurantItemsData.get(position).getName());
                bundle.putString("description",restaurantItemsData.get(position).getDescription());
                if (restaurantItemsData.get(position).getHasOffer()) {
                    bundle.putString("price", restaurantItemsData.get(position).getOfferPrice());
                }else {
                    bundle.putString("price", restaurantItemsData.get(position).getPrice());
                }
                SelectedItemFragment SIF=new SelectedItemFragment();
                SIF.setArguments(bundle);

                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.home_activity_fl_frame,SIF)
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return restaurantItemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemFoodListBinding binding;


        public ViewHolder(@NonNull  ItemFoodListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

