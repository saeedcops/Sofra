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
import com.cops.sofra.data.model.newOffer.NewOffer;
import com.cops.sofra.data.model.restaurantCategories.RestaurantCategoriesData;
import com.cops.sofra.databinding.ItemRestaurantCategoriesBinding;
import com.cops.sofra.ui.home.HomeActivity;
import com.cops.sofra.ui.home.homeCycleRestaurant.ItemFoodListFragment;
import com.cops.sofra.ui.dialog.ItemDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cops.sofra.data.api.RetroritClient.getClient;
import static com.cops.sofra.data.local.sharedPreference.SharedPreferencesManger.LoadData;

public class RestaurantCategoriesAdapter extends RecyclerView.Adapter<RestaurantCategoriesAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<RestaurantCategoriesData> categoryData = new ArrayList<>();

    public RestaurantCategoriesAdapter(Activity activity, List<RestaurantCategoriesData> restaurantDataList) {
        this.context = activity;
        this.activity=activity;
        this.categoryData = restaurantDataList;
    }



    @Override
    public RestaurantCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemRestaurantCategoriesBinding binding=DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_restaurant_categories, parent, false);

        return new RestaurantCategoriesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantCategoriesAdapter.ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);

    }

    private void setData(RestaurantCategoriesAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(categoryData.get(position).getPhotoUrl()).into(holder.binding.restaurantCategoriesAdapterIvItem);
        holder.binding.restaurantCategoriesAdapterTvName.setText(categoryData.get(position).getName());

    }

//

    private void setAction(final RestaurantCategoriesAdapter.ViewHolder holder, final int position) {

        holder.binding.restaurantCategoriesAdapterCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle =new Bundle();
                bundle.putString("name",categoryData.get(position).getName());
                bundle.putInt("categoryId",categoryData.get(position).getId());

                ItemFoodListFragment itemFoodListFragment=new ItemFoodListFragment();
                itemFoodListFragment.setArguments(bundle);
                ((HomeActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_activity_fl_frame,itemFoodListFragment).addToBackStack(null).commit();

            }
        });

        holder.binding.restaurantCategoriesAdapterIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mApiToken="";
                if (LoadData(activity, "apiToken") != null) {

                    mApiToken = LoadData(activity, "apiToken");


                }
                getClient().deleteCategory(categoryData.get(position).getId(),mApiToken).enqueue(new Callback<NewOffer>() {
                    @Override
                    public void onResponse(Call<NewOffer> call, Response<NewOffer> response) {
                        try {

                            if (response.body().getStatus()==1) {
                                Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }else{
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

        holder.binding.restaurantCategoriesAdapterIvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ItemDialog itemDialog =new ItemDialog(categoryData.get(position).getName(),categoryData.get(position).getPhotoUrl(),categoryData.get(position).getId());
                itemDialog.show(((HomeActivity) activity).getSupportFragmentManager(),"Dialog");

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemRestaurantCategoriesBinding binding;


        public ViewHolder(@NonNull  ItemRestaurantCategoriesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}

